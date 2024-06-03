package com.assignment.FileStatisitcs.dao;

import com.assignment.FileStatisitcs.model.FileUploadHistory;

import java.util.List;

public interface IFileUploadHistoryRepository {


    public void save(FileUploadHistory history);

    public List<FileUploadHistory> findAll();

}
