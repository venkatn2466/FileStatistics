package com.assignment.FileStatisitcs.services;

import com.assignment.FileStatisitcs.model.FileUploadHistory;
import com.assignment.FileStatisitcs.response.FileStatisticResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFileStatsService {
    FileStatisticResponse processFile(MultipartFile file) throws IOException, InterruptedException, ExecutionException;

    public boolean validateFile(MultipartFile file, FileStatisticResponse request);

    public List<FileUploadHistory> fetchUploadHistory();

}
