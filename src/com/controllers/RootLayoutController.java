package com.controllers;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class RootLayoutController {
	
	@FXML
	Menu file;
	
	@FXML
	Menu language;
	
	@FXML
	CheckMenuItem polish;
	
	@FXML
	CheckMenuItem english;
	
	@FXML
	MenuItem exit;
	
	private ResourceBundle bundle;
	
	private Locale locale;
	
	public ResourceBundle getResourceBundle() {
		return this.bundle;
	}
	
	
	
    public Menu getFile() {
		return file;
	}


	public void setFile(Menu file) {
		this.file = file;
	}


	public Menu getLanguage() {
		return language;
	}


	public void setLanguage(Menu language) {
		this.language = language;
	}


	public MenuItem getExit() {
		return exit;
	}


	public void setExit(MenuItem exit) {
		this.exit = exit;
	}


	public Locale getLocale() {
		return locale;
	}


	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void setTextEnglish(String text) {
		this.english.setText(text);
	}
	
	public void setTextPolish(String text) {
		this.polish.setText(text);
	}
	
	public void setTextFile(String text) {
		this.file.setText(text);
	}
	
	public void setTextExit(String text) {
		this.exit.setText(text);
	}
	
	public void setTextLanguage(String text) {
		this.language.setText(text);
	}



	private Main main;
	
    private JCommanderMainController jMainController;
    
    public void setJCommaderMainController(JCommanderMainController jMainController) {
    	this.jMainController = jMainController;
    }
    
    
    public RootLayoutController() {
    }
    
    
    
    public CheckMenuItem getPolish() {
		return polish;
	}


	public void setPolish(CheckMenuItem polish) {
		this.polish = polish;
	}


	public CheckMenuItem getEnglish() {
		return english;
	}


	public void setEnglish(CheckMenuItem english) {
		this.english = english;
	}


	@FXML
    private void initialize(ResourceBundle resources) {
		bundle = resources;
		
    }
    
    public void setMainApp(Main main) {
        this.main = main;
        //listenFileExit();

    }
    
	public void listenFileExit() {
		Platform.exit();
	}
	
	public void listenPolish() throws IOException {
		english.setSelected(false);
		loadLang("pl");
		jMainController.setResourceBundle(bundle);
		jMainController.updateJCommanderMainLocale(bundle);
		jMainController.updateRootLocale(bundle);
		jMainController.updateMoveLocale(bundle);
		jMainController.updateCopyLocale(bundle);
		jMainController.updateMoveLocale(bundle);
		jMainController.updateProgressBarLocale(bundle);
		jMainController.updateDateFormat("yyyy-MM-dd");
		System.out.println("Zmieniam na polski");
	}
	
	public void listenEnglish() throws IOException {
		polish.setSelected(false);
		loadLang("en");
		jMainController.setResourceBundle(bundle);
		jMainController.updateJCommanderMainLocale(bundle);
		jMainController.updateRootLocale(bundle);
		jMainController.updateMoveLocale(bundle);
		jMainController.updateCopyLocale(bundle);
		jMainController.updateMoveLocale(bundle);
		jMainController.updateProgressBarLocale(bundle);
		jMainController.updateDateFormat("MM/dd/yyyy");
		System.out.println("Zmieniam na angielski");
	}
	
	private void loadLang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("com.locale.lang", locale);
	}
	
	
	
	

}
