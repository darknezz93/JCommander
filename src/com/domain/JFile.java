package com.domain;

import java.nio.file.attribute.FileTime;
import java.util.Date;

public class JFile {
	
	private String name;
	private Long size;
	private FileTime fileTime;
	
	public JFile() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}

	public FileTime getFileTime() {
		return fileTime;
	}

	public void setFileTime(FileTime fileTime) {
		this.fileTime = fileTime;
	}

	
	
	
	

}
