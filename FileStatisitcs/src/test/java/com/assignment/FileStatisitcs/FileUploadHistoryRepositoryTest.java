package com.assignment.FileStatisitcs;

import com.assignment.FileStatisitcs.dao.FileUploadHistoryRepository;
import com.assignment.FileStatisitcs.dao.IFileUploadHistoryRepository;
import com.assignment.FileStatisitcs.model.FileUploadHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUploadHistoryRepositoryTest {

    private IFileUploadHistoryRepository fileUploadHistoryRepository;

    @BeforeEach
    void setUp() {
        fileUploadHistoryRepository = new InMemoryFileUploadHistoryRepository();
    }

    @Test
    void testSave() {
        FileUploadHistory fileUploadHistory = new FileUploadHistory("test.txt", String.valueOf(System.currentTimeMillis()));
        fileUploadHistoryRepository.save(fileUploadHistory);

        List<FileUploadHistory> historyList = fileUploadHistoryRepository.findAll();
        assertEquals(1, historyList.size());
        assertEquals("test.txt", historyList.get(0).getFileName());
    }

    @Test
    void testFindAll() {
        FileUploadHistory file1 = new FileUploadHistory("test1.txt", String.valueOf(System.currentTimeMillis()));
        FileUploadHistory file2 = new FileUploadHistory("test2.txt", String.valueOf(System.currentTimeMillis() - 1000));
        fileUploadHistoryRepository.save(file1);
        fileUploadHistoryRepository.save(file2);

        List<FileUploadHistory> historyList = fileUploadHistoryRepository.findAll();
        assertEquals(2, historyList.size());
        assertTrue(historyList.stream().anyMatch(f -> f.getFileName().equals("test1.txt")));
        assertTrue(historyList.stream().anyMatch(f -> f.getFileName().equals("test2.txt")));
    }
}

class InMemoryFileUploadHistoryRepository implements IFileUploadHistoryRepository {
    private List<FileUploadHistory> historyList = new ArrayList<>();

    @Override
    public void save(FileUploadHistory fileUploadHistory) {
        historyList.add(fileUploadHistory);
    }

    @Override
    public List<FileUploadHistory> findAll() {
        return new ArrayList<>(historyList);
    }
}