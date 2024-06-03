package com.assignment.FileStatisitcs.services.impl;

import com.assignment.FileStatisitcs.constants.FileConstants;
import com.assignment.FileStatisitcs.dao.FileUploadHistoryRepository;
import com.assignment.FileStatisitcs.model.FIleStatisticBuilder;
import com.assignment.FileStatisitcs.model.FileStatistic;
import com.assignment.FileStatisitcs.model.FileUploadHistory;
import com.assignment.FileStatisitcs.response.FileStatisticResponse;
import com.assignment.FileStatisitcs.services.IFileStatsService;
import com.assignment.FileStatisitcs.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class TextFileStatsServiceImpl implements IFileStatsService {

    Logger logger = LoggerFactory.getLogger(TextFileStatsServiceImpl.class);


    @Value("${max.file.size}")
    private int maxFileSize;
    @Value("${chunk.size.lines}")
    private int chunkSize;

    @Autowired
    FileUploadHistoryRepository fileUploadHistoryRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public FileStatisticResponse processFile(MultipartFile file) throws IOException, InterruptedException, ExecutionException {
        List<Future<FileStatistic>> futures = new ArrayList<>();
        logger.info("File " + file.getOriginalFilename() +" - processing started ");
        AtomicInteger chunkCount = new AtomicInteger();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<String> chunk = new ArrayList<>();
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null ) {
                chunk.add(line);
                lineCount++;
                if (lineCount >= chunkSize) {
                    logger.info("### line no " + line);
                    chunkCount.getAndIncrement();
                    futures.add(processChunkAsync(chunk));
                    chunk = new ArrayList<>();
                    lineCount = 0;
                }
            }
            // Process remaining chunk
            if (!chunk.isEmpty()) {
                futures.add(processChunkAsync(chunk));
            }
        }
        logger.info("Total chunk Size for file "  + file.getOriginalFilename() + " is " + chunkCount.get());

        List<FileStatistic> statsList = new ArrayList<>();
        for (Future<FileStatistic> future : futures) {
            statsList.add(future.get());
        }

        FileStatistic fileStatistic = aggregateStats(statsList);

        FileUploadHistory history = new FileUploadHistory(file.getOriginalFilename(), FileUtils.getDate(System.currentTimeMillis()));
        fileUploadHistoryRepository.save(history);

        return FileUtils.prepareStatisticResponse(fileStatistic);
    }

    /*
    Can add text file specific checks(File Type Implementation specific
     */
    public boolean validateFile(MultipartFile file, FileStatisticResponse response){
        logger.info(" file name : " + file.getOriginalFilename() + " maxSIxe " + maxFileSize + "chunk size " + chunkSize);
        // 1024*1024 = 1MB
        if(file.getSize() > (maxFileSize * 1024 * 1024)) {
            logger.info("File : " + file.getOriginalFilename() + " size has exceeded " + maxFileSize + " MB" + "chunk size " + chunkSize);
            response.setErrorCode(FileConstants.FILE_SIZE_EXCEEDED_CODE);
            response.setErrorMsg(FileConstants.FILE_SIZE_EXCEEDED_MSG + "Text File Max Size :" + maxFileSize + "MB");
            return false;
        } else
            return true;
    }


    public List<FileUploadHistory> fetchUploadHistory() {
        return fileUploadHistoryRepository.findAll();
    }


    /*
    Using lamda func() for Callable functional Interface withe Return Type
      FileStatistic
     */
    private Future<FileStatistic> processChunkAsync(List<String> chunk) {
        return executor.submit(() -> processChunk(chunk));
    }

    private FileStatistic processChunk(List<String> chunk) {
        String text = String.join(" ", chunk);
        int wordCount = getWordCount(text);
        int letterCount = getLetterCount(text);
        int symbolCount = getSymbolCount(text);
        List<String> topWords = getTopNWords(text, 3);
        List<Character> topLetters = getTopNLetters(text, 3);

        return new FileStatistic(wordCount, letterCount, symbolCount, topWords, topLetters);
    }

/*    private int getWordCount(String text) {
        String[] words = text.split("\\s+");
        return (int) Arrays.stream(words)
                .filter(word -> !word.isEmpty())
                .count();
    }*/

    private int getWordCount(String content) {
        return Arrays.stream(content.split("\\s+"))
                .filter(word -> !word.trim().isEmpty() && hasLetter(word))
                .mapToInt(word -> 1)
                .sum();
    }

    private boolean hasLetter(String word) {
        return word.chars().anyMatch(Character::isLetter);
    }

    private int getLetterCount(String text) {
        return (int) text.chars().filter(Character::isLetter).count();
    }

    private int getSymbolCount(String text) {
        return (int) text.chars().filter(c -> !Character.isLetterOrDigit(c) && !Character.isWhitespace(c)).count();
    }


    private List<String> getTopNWords(String text, int n) {
        Map<String, Long> wordCountMap = Arrays.stream(text.toLowerCase().split("\\s+"))
                .filter(word -> !word.trim().isEmpty() && hasLetter(word))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));


        return wordCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Character> getTopNLetters(String text, int n) {
        Map<Character, Long> letterCountMap = text.chars()
                .filter(Character::isLetter)
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        return letterCountMap.entrySet().stream()
                .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private FileStatistic aggregateStats(List<FileStatistic> statsList) {
        int totalWordCount = 0;
        int totalLetterCount = 0;
        int totalSymbolCount = 0;
        Map<String, Long> wordFrequency = new HashMap<>();
        Map<Character, Long> letterFrequency = new HashMap<>();

        for (FileStatistic stats : statsList) {
            totalWordCount += stats.getWordCount();
            totalLetterCount += stats.getLetterCount();
            totalSymbolCount += stats.getSymbolCount();

            for (String word : stats.getTopWords()) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0L) + 1);
            }
            for (Character letter : stats.getTopLetters()) {
                letterFrequency.put(letter, letterFrequency.getOrDefault(letter, 0L) + 1);
            }
        }

        List<String> topWords = wordFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Character> topLetters = letterFrequency.entrySet().stream()
                .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

       //Builder pattern used can be extendable for other file Types to keep code clean
        return new FIleStatisticBuilder().setWordCount(totalWordCount).setLetterCount(totalLetterCount).setSymbolCount(totalSymbolCount)
                  .setTopWords(topWords).setTopLetters(topLetters)
                  .build();
    }
}
