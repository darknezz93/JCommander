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
	private final SimpleStringProperty fileTime;
	
	public JFile() {
		this("", "", null);
	}
	
	public JFile(String name, String size, String time) {
		this.name = new SimpleStringProperty(name);
		this.size = new SimpleStringProperty(size);
		this.fileTime = new SimpleStringProperty(time);
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
	
	public StringProperty fileTimeProperty() {
		return fileTime;
	}
	
	public String getFileTime() {
		return fileTime.get();
	}

	public void setFileTime(String fileTime) {
		this.fileTime.set(fileTime);
	}

	
	
	
	

}
