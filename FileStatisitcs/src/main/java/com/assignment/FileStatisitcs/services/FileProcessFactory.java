package com.assignment.FileStatisitcs.services;


import com.assignment.FileStatisitcs.FileTypeEnum;
import com.assignment.FileStatisitcs.services.impl.TextFileStatsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileProcessFactory {
   @Autowired
   private TextFileStatsServiceImpl textFileStatsService;

   private static  final Logger logger = LoggerFactory.getLogger(FileProcessFactory.class);


   public IFileStatsService getFileTypeProcessor(String fileType){

      FileTypeEnum fileTypeEnum = FileTypeEnum.valueOf(fileType);

      switch (fileTypeEnum){

         case TEXT :
            return textFileStatsService;

         default:
            throw new RuntimeException("Invalid fileType ");
      }

   }
}
