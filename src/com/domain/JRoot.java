package com.domain;

import javafx.beans.property.StringProperty;

public class JRoot {
	
	private StringProperty path;
	
	public JRoot(StringProperty path) {
		this.path = path;
	}

	public StringProperty getPath() {
		return path;
	}

	public void setPath(StringProperty path) {
		this.path = path;
	}
	
	

}
