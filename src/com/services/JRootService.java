package com.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.domain.JRoot;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JRootService {
	
	public ObservableList<String> getSystemRoots() {

		ObservableList<String> roots = FXCollections.observableArrayList();
		File[] paths;
		try {
			paths = File.listRoots();
			for (File path : paths) {
				JRoot root = new JRoot(new SimpleStringProperty(path.toString()));
				//roots.add(root);
				roots.add(path.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roots;
	}
	
	public boolean checkIfRootPath(String path) {
		ObservableList<String> roots = getSystemRoots();
		for(String root :  roots) {
			if(root.equals(path)) {
				return true;
			}
		}
		return false;
	}
	
	

}
