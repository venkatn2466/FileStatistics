package com.assignment.FileStatisitcs.utils;

import com.assignment.FileStatisitcs.FileTypeEnum;
import com.assignment.FileStatisitcs.constants.FileConstants;
import com.assignment.FileStatisitcs.model.FileStatistic;
import com.assignment.FileStatisitcs.response.FileStatisticResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class FileUtils {


    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);


    public static boolean validateFile(MultipartFile file, FileStatisticResponse response) {
        String fileType = FileUtils.fetchFileType(StringUtils.getFilenameExtension(file.getOriginalFilename()));
        logger.info("Uploaded the fileName is  " + file.getOriginalFilename() + " and File Type is "  + fileType);
        if(Objects.isNull(fileType)){
            logger.info("Uploaded file : " + file.getOriginalFilename() + " is empty ");
            response.setErrorMsg(FileConstants.INVALID_EXTENSION_ERROR_MSG);
            response.setErrorCode(FileConstants.INVALID_FILE_EXTENSION_CODE);
            return false;
        } else if (file.getSize() == 0) {
            logger.info("Uploaded file " + file.getOriginalFilename() + " is empty " + file.isEmpty());
            response.setErrorMsg(FileConstants.EMPTY_FILE_CONTENT_ERROR_MSG);
            response.setErrorCode(FileConstants.EMPTY_FILE_ERROR_CODE);
            return false;
        } else {
            return true;
        }
    }


    public static String fetchFileType(String fileExtension){

        switch (fileExtension){
            case FileConstants.FILE_EXTENSION_TEXT :
                   return FileTypeEnum.TEXT.getFileType();
            default:
                return null;
        }
    }

    /*
    Can use to map with conditions and additionals changes as required from model data to client
     */
    public static FileStatisticResponse prepareStatisticResponse(FileStatistic fileStatistic){
        FileStatisticResponse response = new FileStatisticResponse(fileStatistic);
        return  response;
    }


    public static String getDate(long milliseconds){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date resultdate = new Date(milliseconds);
        return sdf.format(resultdate);
    }
}
