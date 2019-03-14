package com.demo.springbootForFileOperationInDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.demo.springbootForFileOperationInDB.model.DBFile;
import com.demo.springbootForFileOperationInDB.repository.DBFileRepository;

@Service
public class DBFileStorageService {

	@Autowired
	private DBFileRepository dbFileRepository;

	public DBFile storeFile(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		System.out.println("from service:fileName:" + fileName);

		try {
			if (fileName.contains("..")) {
				throw new Exception("File name contains invalid character sequence:" + fileName);
			}

			DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

			return dbFileRepository.save(dbFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DBFile getFile(String fileId) throws Exception {

		return dbFileRepository.findById(fileId).orElseThrow(() -> new Exception("File not found with id:" + fileId));
	}

}
