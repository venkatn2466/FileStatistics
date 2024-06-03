package com.assignment.FileStatisitcs.model;

import java.util.List;

public class FIleStatisticBuilder {

     int wordCount;
     int letterCount;
     int symbolCount;
     List<String> topWords;
     List<Character> topLetters;



    public FIleStatisticBuilder setWordCount(int wordCount) {
        this.wordCount = wordCount;
        return this;
    }



    public FIleStatisticBuilder setLetterCount(int letterCount) {
        this.letterCount = letterCount;
        return this;

    }



    public FIleStatisticBuilder setSymbolCount(int symbolCount) {
        this.symbolCount = symbolCount;
        return this;

    }


    public FIleStatisticBuilder setTopWords(List<String> topWords) {
        this.topWords = topWords;
        return this;

    }



    public FIleStatisticBuilder setTopLetters(List<Character> topLetters) {
        this.topLetters = topLetters;
        return this;
    }

    public FileStatistic build() {
        return new FileStatistic(this);
    }
}
