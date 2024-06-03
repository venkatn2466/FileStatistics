package com.assignment.FileStatisitcs;

public enum FileTypeEnum {
    TEXT("TEXT");

    private final String fileType;

    FileTypeEnum(String fileType){
        this.fileType = fileType;
    }

    public String getFileType(){return fileType;}
}
