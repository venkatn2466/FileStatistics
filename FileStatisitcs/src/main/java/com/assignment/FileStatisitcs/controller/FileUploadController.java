package com.assignment.FileStatisitcs.controller;


import com.assignment.FileStatisitcs.constants.FileConstants;
import com.assignment.FileStatisitcs.model.FileUploadHistory;
import com.assignment.FileStatisitcs.response.BaseResponse;
import com.assignment.FileStatisitcs.response.FileStatisticResponse;
import com.assignment.FileStatisitcs.services.FileProcessFactory;
import com.assignment.FileStatisitcs.services.IFileStatsService;
import com.assignment.FileStatisitcs.utils.FileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/file/upload")
public class FileUploadController {
    @Autowired
    private IFileStatsService fileStatsService;

    @Autowired
    private FileProcessFactory fileProcessFactory;

    Logger logger = LoggerFactory.getLogger(FileUploadController.class);



    @Operation(summary = "Upload a text file and get statistics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File processed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileStatisticResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Input Request - Either File Empty/Invalid Extension/File Size Exceeded",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = FileStatisticResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error processing file.Please try after sometime",
                    content = @Content(mediaType  ="application/json" , schema = @Schema(implementation = HashMap.class)))
    })

    // enabled for testing so no need for client specifc cors control to see easy output,
    // can be commented and client specific can be configured w.r.t client
    @CrossOrigin



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @Parameter(description = "The text file to be uploaded", required = true,content = @Content(mediaType = "multipart/form-data"))
            @RequestPart("file") MultipartFile file) {
        String fileType = FileUtils.fetchFileType(StringUtils.getFilenameExtension(file.getOriginalFilename()));
        logger.info("Uploaded the fileName is  " + file.getOriginalFilename() + " and File Type is "  + fileType);
        FileStatisticResponse statsResponse = new FileStatisticResponse();
         if (!FileUtils.validateFile(file , statsResponse)) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statsResponse);
        }
        IFileStatsService fileStatsService = fileProcessFactory.getFileTypeProcessor(fileType);
        try {
            logger.info("File being processed is  " + file.getOriginalFilename() + " size " + file.getSize());
            if(fileStatsService.validateFile(file, statsResponse)) {
                statsResponse = fileStatsService.processFile(file);
                return ResponseEntity.ok(statsResponse);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statsResponse);
            }
        } catch (IOException | InterruptedException | ExecutionException e ) {
            statsResponse = new FileStatisticResponse();
            statsResponse.setErrorMsg(FileConstants.FILE_PROCESSING_ERROR_MSG + FileConstants.COLON_DELIMITER + file.getOriginalFilename() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statsResponse);
        }
    }


    @Operation(summary = "Get history of uploaded files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "History retrieved successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FileUploadHistory.class)))}),
            @ApiResponse(responseCode = "500", description = "Unable to fetch Records.Server Error",
                    content = @Content(mediaType  ="application/json" , schema = @Schema(implementation = HashMap.class)))

    })
    @GetMapping("/history")
    @CrossOrigin
    public ResponseEntity<?> getFileUploadHistory() {
        try {
            List<FileUploadHistory> historyList = fileStatsService.fetchUploadHistory();
            return ResponseEntity.ok(historyList);
        } catch (Exception e){
            BaseResponse response = new BaseResponse();
            response.setErrorMsg(FileConstants.FILE_PROCESSING_ERROR_MSG );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
