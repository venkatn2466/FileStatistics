package com.assignment.FileStatisitcs.response;

import com.assignment.FileStatisitcs.model.FileStatistic;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Statistics of the uploaded text file")
public class FileStatisticResponse extends BaseResponse{

    @Schema(description = "Number of letters in the file", example = "500")
    private int wordCount;

    @Schema(description = "Number of letters in the file", example = "500")
    private int letterCount;

    @Schema(description = "Number of symbols in the file", example = "50")
    private int symbolCount;

    @Schema(description = "Top words used in the file", example = "apple,bannana,grapes")
    private List<String> topWords;

    @Schema(description = "Most used letter in the file", example = "a,b,c")
    private List<Character> topLetters;



    public FileStatisticResponse(int wordCount, int letterCount, int symbolCount, List<String> topWords, List<Character> topLetters) {
        this.wordCount = wordCount;
        this.letterCount = letterCount;
        this.symbolCount = symbolCount;
        this.topWords = topWords;
        this.topLetters = topLetters;
    }

    public FileStatisticResponse(){

    }

    public FileStatisticResponse(FileStatistic model){
        this.wordCount = model.getWordCount();
        this.letterCount = model.getLetterCount();
        this.symbolCount = model.getSymbolCount();
        this.topWords = model.getTopWords();
        this.topLetters = model.getTopLetters();
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getLetterCount() {
        return letterCount;
    }

    public int getSymbolCount() {
        return symbolCount;
    }

    public List<String> getTopWords() {
        return topWords;
    }

    public List<Character> getTopLetters() {
        return topLetters;
    }

}
