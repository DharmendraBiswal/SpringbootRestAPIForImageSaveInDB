package com.demo.springbootForFileOperationInDB.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.springbootForFileOperationInDB.model.DBFile;
import com.demo.springbootForFileOperationInDB.service.DBFileStorageService;



@RestController
public class FileStorageController {

	@Autowired
	private DBFileStorageService dbFileStorageService;

	@PostMapping("/uploadFile")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

		DBFile dbFile = dbFileStorageService.storeFile(file);

		return new ResponseEntity<>(dbFile.getFileName(), HttpStatus.OK);
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileId) throws Exception {

		try {
			DBFile dbFile = dbFileStorageService.getFile(fileId);
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + dbFile.getFileName() + "\"")
					.body(new ByteArrayResource(dbFile.getData()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
	@PostMapping("/uploadMultipleFiles")
	public List<ResponseEntity<?>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
		
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

}
