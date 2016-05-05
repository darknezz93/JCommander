package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.sound.midi.Patch;

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

public class CopyScreenController {
	
	@FXML
	private TextField textFieldCopyFrom;
	
	@FXML
	private Label labelCopyTo;
	
	@FXML
	private TextField pathTextField;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private Button copyButton;
	
	@FXML
	private Label labelCopyFrom;
	
	@FXML
	private Button cancelButton;
	
	private String directoryAlertHeader;
	
	private String directoryAlertContextText;
	
	private String fileAlertHeader;
	
	private String fileAlertContextText;
	
	
	
    public String getDirectoryAlertHeader() {
		return directoryAlertHeader;
	}

	public void setDirectoryAlertHeader(String directoryAlertHeader) {
		this.directoryAlertHeader = directoryAlertHeader;
	}

	public String getFileAlertHeader() {
		return fileAlertHeader;
	}

	public void setFileAlertHeader(String fileAlertHeader) {
		this.fileAlertHeader = fileAlertHeader;
	}

	public Label getLabelCopyFrom() {
		return labelCopyFrom;
	}

	public void setLabelCopyFrom(Label labelCopyFrom) {
		this.labelCopyFrom = labelCopyFrom;
	}

	public Label getLabelCopyTo() {
		return labelCopyTo;
	}

	public void setLabelCopyTo(Label labelCopyTo) {
		this.labelCopyTo = labelCopyTo;
	}

	public TextField getPathTextField() {
		return pathTextField;
	}

	public void setPathTextField(TextField pathTextField) {
		this.pathTextField = pathTextField;
	}

	public Button getCopyButton() {
		return copyButton;
	}

	public void setCopyButton(Button copyButton) {
		this.copyButton = copyButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public String getDirectoryAlertContextText() {
		return directoryAlertContextText;
	}

	public void setDirectoryAlertContextText(String directoryAlertContextText) {
		this.directoryAlertContextText = directoryAlertContextText;
	}

	public String getFileAlertContextText() {
		return fileAlertContextText;
	}

	public void setFileAlertContextText(String fileAlertContextText) {
		this.fileAlertContextText = fileAlertContextText;
	}



	private Main main;
    
    private Thread th = new Thread();
    
    private JFileService fileService = new JFileService();
    
    private JCommanderMainController jMainController;
    
    public void setJCommaderMainController(JCommanderMainController jMainController) {
    	this.jMainController = jMainController;
    }
    
    public CopyScreenController() {
    	
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
    
	public void setProgress(double progress) {
		this.progressBar.setProgress(progress);
	}
    
    public void listenCancelButton() {
    	
    	/*
    	cancelButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent actionEvent) {
    	    	Platform.exit();
    	    }
    	});*/
    	if(th.isAlive()){
    		System.out.println("stop");
    		th.stop();
    	}
    	main.closeCopyScreenStage();
    }
    
	public void listenCopyButton() {
		CopyScreenController controller = this;

		//copyButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			//@Override
			//public void handle(ActionEvent actionEvent) {

				String copyFrom = textFieldCopyFrom.getText();
				String copyTo = pathTextField.getText();

				File file = new File(copyTo);
				File fileFrom = new File(copyFrom);

				boolean copy = true;

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
						copy = false;
					}
				}

				if (copy) {

					if (fileFrom.isDirectory()) {
						Task<Void> task = new Task<Void>() {
							@Override
							public Void call() throws InterruptedException, IOException {
								double directorySize = fileService.getDirectorySize(fileFrom);
								System.out.println(directorySize);
								fileService.copyDirectory(copyFrom, copyTo, controller, directorySize);
								Platform.runLater(new Runnable() {
									public void run() {
										try {
											jMainController.updateDirectory1Content();
											jMainController.updateDirectoryContent();
											main.closeCopyScreenStage();
											setProgress(0);
											fileService.setTotal(0);
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

								boolean result = fileService.copyFile(copyFrom, copyTo, controller);

								Platform.runLater(new Runnable() {
									public void run() {
										try {
											jMainController.updateDirectory1Content();
											jMainController.updateDirectoryContent();
											main.closeCopyScreenStage();
											setProgress(0);
											fileService.setTotal(0);
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
			//}
		//});
	}
    
	public void setTextFieldCopyFrom(String text) {
		this.textFieldCopyFrom.setText(text);
	}
	

	
	public void setTextLabelCopyTo(String text) {
		this.labelCopyTo.setText(text);
	}
	
	

}
