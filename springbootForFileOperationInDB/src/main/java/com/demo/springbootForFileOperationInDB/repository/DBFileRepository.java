package com.demo.springbootForFileOperationInDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.springbootForFileOperationInDB.model.DBFile;


@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String>{

}
