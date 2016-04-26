package com.domain;

import java.nio.file.attribute.FileTime;
import java.util.Date;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JFile{
	
	protected StringProperty name;
	private StringProperty size;
	private final ObjectProperty<FileTime> fileTime;
	
	public JFile() {
		this("", "", null);
	}
	
	public JFile(String name, String size, FileTime time) {
		this.name = new SimpleStringProperty(name);
		this.size = new SimpleStringProperty(size);
		this.fileTime = new SimpleObjectProperty<FileTime>(time);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty sizeProperty() {
		return size;
	}
	
	public String getSize() {
		return size.get();
	}
	public void setSize(String size) {
		this.size.set(size);;
	}
	
	public ObjectProperty<FileTime> fileTimeProperty() {
		return fileTime;
	}
	
	public FileTime getFileTime() {
		return fileTime.get();
	}

	public void setFileTime(FileTime fileTime) {
		this.fileTime.set(fileTime);
	}

	
	
	
	

}
