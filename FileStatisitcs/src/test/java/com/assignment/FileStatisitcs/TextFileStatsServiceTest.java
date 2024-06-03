package com.assignment.FileStatisitcs;


import com.assignment.FileStatisitcs.dao.FileUploadHistoryRepository;
import com.assignment.FileStatisitcs.response.FileStatisticResponse;
import com.assignment.FileStatisitcs.services.impl.TextFileStatsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TextFileStatsServiceTest {

    @InjectMocks
    private TextFileStatsServiceImpl textFileStatsService;

    @Mock
    private FileUploadHistoryRepository fileUploadHistoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessFile_Success() throws IOException, InterruptedException, ExecutionException {
        String content = "Hello world\nThis is a test file.";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());

        FileStatisticResponse stats = textFileStatsService.processFile(file);

        assertNotNull(stats);
        assertEquals(7, stats.getWordCount());
        assertEquals(25, stats.getLetterCount());
        assertEquals(1, stats.getSymbolCount());

        verify(fileUploadHistoryRepository, times(1)).save(any());
    }

    @Test
    void testProcessFile_EmptyFile() throws IOException, InterruptedException, ExecutionException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "".getBytes());

        FileStatisticResponse stats = textFileStatsService.processFile(file);

        assertNotNull(stats);
        assertEquals(0, stats.getWordCount());
        assertEquals(0, stats.getLetterCount());
        assertEquals(0, stats.getSymbolCount());

        verify(fileUploadHistoryRepository, times(1)).save(any());
    }
}