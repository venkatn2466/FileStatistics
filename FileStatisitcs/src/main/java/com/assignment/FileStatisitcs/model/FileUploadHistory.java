package com.assignment.FileStatisitcs.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "History of uploaded files")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadHistory {

    @Schema(description = "Name of the uploaded file", example = "example.txt")
    private String fileName;

    @Schema(description = "Timestamp of the upload", example = "1623412341234")
    private String uploadTimestamp;

    public FileUploadHistory(String fileName, String uploadTimestamp) {
        this.fileName = fileName;
        this.uploadTimestamp = uploadTimestamp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(String uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }
}
