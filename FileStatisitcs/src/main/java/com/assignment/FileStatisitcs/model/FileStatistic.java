package com.assignment.FileStatisitcs.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


/*
  This is model class.
  Can also be persisted in Db for persistent storage and statistics
  as an Entity class wuth additional column as id for unique identification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileStatistic  {

    private int wordCount;
    private int letterCount;
    private int symbolCount;
    private List<String> topWords;
    private List<Character> topLetters;



    public FileStatistic(int wordCount, int letterCount, int symbolCount, List<String> topWords, List<Character> topLetters) {
        this.wordCount = wordCount;
        this.letterCount = letterCount;
        this.symbolCount = symbolCount;
        this.topWords = topWords;
        this.topLetters = topLetters;
    }

    public FileStatistic(FIleStatisticBuilder builder){
        this.wordCount = builder.wordCount;
        this.letterCount = builder.letterCount;
        this.symbolCount = builder.symbolCount;
        this.topWords = builder.topWords;
        this.topLetters = builder.topLetters;
    }

    public FileStatistic(){

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
