package com.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.services.JFileService;

import application.Main;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class MoveScreenController {
	
	@FXML
	private TextField textFieldMoveFrom;
	
	@FXML
	private Label labelMoveTo;
	
	@FXML
	private TextField pathTextField;

	@FXML
	private Button moveButton;
	
	@FXML
	private Button cancelButton;
	
    private Main main;
    
    private JFileService fileService = new JFileService();
    
    private JCommanderMainController jMainController;
    
    private Thread th = new Thread();
    
    public void setJCommaderMainController(JCommanderMainController jMainController) {
    	this.jMainController = jMainController;
    }
    
    public MoveScreenController() {
    	
    }
    
    @FXML
    private void initialize() {

    }
    
    public void setMainApp(Main main) {
        this.main = main;
        
       // listenCancelButton();
       // listenCopyButton();
    }
    
    public void setPathTextFieldText(String text) {
    	this.pathTextField.setText(text);
    }
 
    
    public void listenCancelButton() {

    	/*cancelButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent actionEvent) {
    	    	Platform.exit();
    	    }
    	});*/
    	if(th.isAlive()) {
    		System.out.println("stop");
        	th.stop();
    	}
    	main.closeMoveScreenStage();
    }
    
	public void listenMoveButton() {
		
		MoveScreenController controller = this;

	//	moveButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
		//	@Override
		//	public void handle(ActionEvent actionEvent) {
				
				System.out.println("Moveing");
				String copyFrom = textFieldMoveFrom.getText();
				String copyTo = pathTextField.getText();

				File file = new File(copyTo);
				File fileFrom = new File(copyFrom);

				boolean move = true;

				if (file.exists()) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirm");
					if (file.isDirectory()) {
						alert.setHeaderText("Directory exists in selected location");
						alert.setContentText("Replace existing directory?");
					} else {
						alert.setHeaderText("File exists in selected location");
						alert.setContentText("Replace existing file?");
					}

					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == ButtonType.CANCEL) {

						main.closeCopyScreenStage();
						move = false;
					}
				}

				if (move) {

					if (fileFrom.isDirectory()) {
						Task<Void> task = new Task<Void>() {
							@Override
							public Void call() throws InterruptedException, IOException {		
								System.out.println("Moving dir " +  copyFrom);
								fileService.moveDirectory(copyFrom, copyTo);
								System.out.println("sucess " + copyTo);
								Platform.runLater(new Runnable() {
									public void run() {
										try {
											jMainController.updateDirectory1Content();
											jMainController.updateDirectoryContent();
											main.closeMoveScreenStage();
										} catch (IOException e) {
											e.printStackTrace();
										}

									}
								});
								return null;
							}
						};

						th = new Thread(task);
						th.setDaemon(true);
						th.start();

					} else {

						Task<Void> task = new Task<Void>() {
							@Override
							public Void call() throws InterruptedException, IOException {
								
								fileService.moveFile(copyFrom, copyTo);
								
								Platform.runLater(new Runnable() {
									public void run() {
										try {
											jMainController.updateDirectory1Content();
											jMainController.updateDirectoryContent();
											main.closeMoveScreenStage();
										} catch (IOException e) {
											e.printStackTrace();
										}

									}
								});

								return null;
							}
						};

						th = new Thread(task);
						th.setDaemon(true);
						th.start();
					}

				}
		//	}
		//});
	}
    
	public void setTextFieldMoveFrom(String text) {
		this.textFieldMoveFrom.setText(text);
	}
	

	
	public void setTextLabelCMoveTo(String text) {
		this.labelMoveTo.setText(text);
	}
	
}
