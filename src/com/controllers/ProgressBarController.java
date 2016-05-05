package com.controllers;

import java.io.File;
import java.io.IOException;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgressBarController {
	
	@FXML
	private ProgressBar progressBar;
	
	
	@FXML
	private Button cancelButton;
	
	@FXML 
	private Label label;
	
	
	public void setProgress(double progress) {
		this.progressBar.setProgress(progress);
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
	
	public void setTextLabel(String text) {
		this.label.setText(text);
	}
	
    // Reference to the main application.
    private Main main;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ProgressBarController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;

    }
    
    
    public void listenCancelButton() {
    	cancelButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent actionEvent) {
    	       Platform.exit();
    	    }
    	});
    }

	
	
	

}
