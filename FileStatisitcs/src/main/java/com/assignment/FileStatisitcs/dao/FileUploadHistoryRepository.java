package com.assignment.FileStatisitcs.dao;

import com.assignment.FileStatisitcs.model.FileUploadHistory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FileUploadHistoryRepository implements IFileUploadHistoryRepository {

    private final List<FileUploadHistory> fileUploadHistories = new ArrayList<>();

    public void save(FileUploadHistory history) {
        fileUploadHistories.add(history);
    }

    public List<FileUploadHistory> findAll() {
        return new ArrayList<>(fileUploadHistories);
    }

}
