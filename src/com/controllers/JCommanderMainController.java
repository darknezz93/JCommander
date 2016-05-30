package com.controllers;

import com.domain.JFile;
import com.domain.JRoot;
import com.services.JFileService;
import com.services.JRootService;

import application.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class JCommanderMainController {
	
	@FXML
	private TableView<JFile> jDirectoryTable;
	
	@FXML
	private TableView<JFile> jDirectoryTable1;
	
	@FXML
	private ComboBox<String> jRootComboBox;
	
	@FXML
	private ComboBox<String> jRootComboBox1;
	
	@FXML
	private TableColumn<JFile, String> nameColumn;
	
	@FXML
	private TableColumn<JFile, String> sizeColumn;
	
	@FXML
	private TableColumn<JFile, String> createDateColumn;
	
	@FXML
	private TableColumn<JFile, String> nameColumn1;
	
	@FXML
	private TableColumn<JFile, String> sizeColumn1;
	
	@FXML
	private TableColumn<JFile, String> createDateColumn1;
	
	
	@FXML 
	Button button;
	
	@FXML
	Button button1;
	
	@FXML
	Button cancelButton;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private TextField curDirTextField;
	
	@FXML
	private TextField curDirTextField1;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private Label sizeLabel;
	
	@FXML
	private Label createDateLabel;
	
	@FXML
	private ProgressBarController progressBarController;
	
	@FXML
	private Label progressBarLabel;
	
	@FXML
	private Button progressBarCancel;
	
	private String dateFormat = "MM/dd/yyyy";
	
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	
	private Main main;
	
	private JFileService fileService = new JFileService();
	
	private JRootService rootService = new JRootService();
	
	private RootLayoutController rootController;
	
	private CopyScreenController copyController;
	private MoveScreenController moveController;
	
	private ResourceBundle resourceBundle;
	
	private ObservableList<JFile> all;
	
		
	public JCommanderMainController() {
		
	}
	
	public void setResourceBundle(ResourceBundle bundle) {
		this.resourceBundle = bundle;
	}
	
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
		createDateColumn.setCellValueFactory(cellData -> cellData.getValue().fileTimeProperty());
		nameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		sizeColumn1.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
		createDateColumn1.setCellValueFactory(cellData -> cellData.getValue().fileTimeProperty());
	}
	
    public void setMain(Main main) throws IOException {
        this.main = main;
        
        rootController = main.getRootController();
        resourceBundle = rootController.getResourceBundle();
        rootController.setJCommaderMainController(this);
		copyController = main.getCopyScreenController();
	    moveController = main.getMoveScreenController();
	    progressBarController = main.getProgressController();
        initializeComponents();
        
        main.getRootController().getEnglish().setSelected(true);
        listenComboBoxItemsChange();
        listenComboBox1ItemsChange();
        onCurDirTextFieldEnter();
        onCurDirTextField1Enter();
        listenTableDoubleClick();
        listenTable1DoubleClick();
        listenButton();
        listenButton1();
        onJDirectoryTableKeyEvent();
        onJDirectoryTable1KeyEvent();

    }
    
    public void setElementsDateFormat(String dateFormat) throws IOException {
    	all = fileService.getDirectoriesAndFiles(rootService.getSystemRoots().get(0), dateFormat);
    }
    
    public void initializeComponents() throws IOException {
        all = fileService.getDirectoriesAndFiles(rootService.getSystemRoots().get(0), dateFormat);
        // Add observable list data to the table
        rootController.listenEnglish();
        jDirectoryTable.setItems(all);
        jDirectoryTable1.setItems(all);
        jRootComboBox.setItems(rootService.getSystemRoots());
        jRootComboBox1.setItems(rootService.getSystemRoots());
        jRootComboBox.getSelectionModel().select(rootService.getSystemRoots().get(0));
        jRootComboBox1.getSelectionModel().select(rootService.getSystemRoots().get(0));
        curDirTextField.setText(rootService.getSystemRoots().get(0));
        curDirTextField1.setText(rootService.getSystemRoots().get(0));
    }
    
    public void updateDirectoryContent(String dirPath) throws IOException {
    	ObservableList<JFile> all = fileService.getDirectoriesAndFiles(dirPath, dateFormat);
    	jDirectoryTable.setItems(all);
    }
    
    public void updateDirectoryContent() throws IOException {
    	ObservableList<JFile> all = fileService.getDirectoriesAndFiles(curDirTextField.getText(), dateFormat);
    	jDirectoryTable.setItems(all);
    }
    
    public void updateDirectory1Content(String dirPath) throws IOException {
    	ObservableList<JFile> all = fileService.getDirectoriesAndFiles(dirPath, dateFormat);
    	jDirectoryTable1.setItems(all);
    }
    
    public void updateDirectory1Content() throws IOException {
    	ObservableList<JFile> all = fileService.getDirectoriesAndFiles(curDirTextField1.getText(), dateFormat);
    	jDirectoryTable1.setItems(all);
    }
    
    public void listenComboBoxItemsChange() {
        jRootComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	try {
					jDirectoryTable.setItems(fileService.getDirectoriesAndFiles(jRootComboBox.getValue(), dateFormat));
					curDirTextField.setText(jRootComboBox.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
            }    
        });
    }
    
    public void listenComboBox1ItemsChange() {
        jRootComboBox1.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	try {
					jDirectoryTable1.setItems(fileService.getDirectoriesAndFiles(jRootComboBox1.getValue(), dateFormat));
					curDirTextField1.setText(jRootComboBox1.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
            }    
        });
    }
    	
	public void onJDirectoryTableKeyEvent() throws IOException {
		
		copyController.setJCommaderMainController(this);
		moveController.setJCommaderMainController(this);

		jDirectoryTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				final JFile selectedItem = jDirectoryTable.getSelectionModel().getSelectedItem();

				if (selectedItem != null) {
					/////////////////////COPY JDIR//////////////////////////////////////////////////
					if (keyEvent.getCode().equals(KeyCode.F5)) {

							String copyFromPath = fileService.validateDirectoryPath(curDirTextField.getText(), selectedItem.getName());
							copyController.setTextFieldCopyFrom(copyFromPath);

							if (selectedItem.getSize().equals("<DIR>")) {
								System.out.println("Directory");
								main.showCopyScreenStage();
								//copyController.setTextLabelCopyTo("To: ");
								String copyTo = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
								copyController.setPathTextFieldText(copyTo);
								
							} else {
								System.out.println("Copying file.");
								
								 main.showCopyScreenStage();
								 //copyController.setTextLabelCopyTo("To: ");
								 String copyTo = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
								 copyController.setPathTextFieldText(copyTo);
								 
							}
					} //////////////////// DELETE JDIR///////////////////////////////////////////
					else if (keyEvent.getCode().equals(KeyCode.DELETE)) {

						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle(resourceBundle.getString("label.deleting.title"));
						alert.setHeaderText(resourceBundle.getString("label.deleting") + selectedItem.getName());
						alert.setContentText(resourceBundle.getString("label.confirm"));

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == ButtonType.OK) {

							main.showProgressStage();
							ProgressBarController progressController = main.getProgressController();
							progressController.setTextLabel(resourceBundle.getString("label.deleting") + selectedItem.getName());

							if (selectedItem.getSize().equals("<DIR>")) {
								
								Task<Void> task = new Task<Void>() {
									@Override
									public Void call() throws InterruptedException, IOException {
										String directoryToDelete = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
										double filesInDirectoryCounter = fileService.getFilesInDirectoryCount(directoryToDelete);
										fileService.deleteDirectory(directoryToDelete, progressController, filesInDirectoryCounter);
										Platform.runLater(new Runnable() {
										    public void run() {
										        main.closeProgressStage();
										        progressController.setProgress(0);
										        try {
													updateDirectory1Content(curDirTextField1.getText());
												} catch (IOException e) {
													e.printStackTrace();
												}
										    }
										});
										return null;
									}
								};

								Thread th = new Thread(task);
								th.setDaemon(true);
								th.start();
								
							} else {
								System.out.println("Deleting file.");
								
								Task<Void> task = new Task<Void>() {
									@Override
									public Void call() throws InterruptedException, IOException {
										String fileToDelete = fileService.validateDirectoryPath(curDirTextField.getText(), selectedItem.getName());
										fileService.deleteFile(fileToDelete, progressController);
										Platform.runLater(new Runnable() {
										    public void run() {
										        main.closeProgressStage();
										        progressController.setProgress(0);
										        try {
													updateDirectoryContent(curDirTextField.getText());
												} catch (IOException e) {
													e.printStackTrace();
												}
										    }
										});
										return null;
									}
								};
								
								Thread th = new Thread(task);
								th.setDaemon(true);
								th.start();
							}
						}
					}
					/////////////////////////////////////// MOVE JDIR //////////////////////////////////////////////////////////////////////////
					else if(keyEvent.getCode().equals(KeyCode.F6)) {
						String moveFromPath = fileService.validateDirectoryPath(curDirTextField.getText(), selectedItem.getName());
						moveController.setTextFieldMoveFrom(moveFromPath);
						
						if (selectedItem.getSize().equals("<DIR>")) {
							System.out.println("Directory");
							main.showMoveScreenStage();
							String moveTo = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
							moveController.setPathTextFieldText(moveTo);
							
						} else {
							System.out.println("Copying file.");
							
							 main.showMoveScreenStage();
							 String moveTo = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
							 moveController.setPathTextFieldText(moveTo);
						}
					}
				}
			}
		});
	}
	
	
	public void onJDirectoryTable1KeyEvent() throws IOException {
		
		copyController.setJCommaderMainController(this);
		moveController.setJCommaderMainController(this);

		jDirectoryTable1.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				final JFile selectedItem = jDirectoryTable1.getSelectionModel().getSelectedItem();

				if (selectedItem != null) {
					/////////////////////COPY JDIR1////////////////////////////////////////
					if (keyEvent.getCode().equals(KeyCode.F5)) {

							String copyFromPath = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
							copyController.setTextFieldCopyFrom(copyFromPath);

							if (selectedItem.getSize().equals("<DIR>")) {
								System.out.println("Directory");
								main.showCopyScreenStage();
								copyController.setTextLabelCopyTo("To: ");
								String copyTo = fileService.validateDirectoryPath(curDirTextField.getText(), selectedItem.getName());
								copyController.setPathTextFieldText(copyTo);
								
							} else {
								System.out.println("Copying file.");
								
								 main.showCopyScreenStage();
								 copyController.setTextLabelCopyTo("To: ");
								 String copyTo = fileService.validateDirectoryPath(curDirTextField.getText(), selectedItem.getName());
								 copyController.setPathTextFieldText(copyTo);
								 
							}
				    //////////////////////DELETE JDIR1////////////////////////////////////////
					} else if(keyEvent.getCode().equals(KeyCode.DELETE)) {
						
						
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle(resourceBundle.getString("label.deleting.title"));
						alert.setHeaderText(resourceBundle.getString("label.deleting") + selectedItem.getName());
						alert.setContentText(resourceBundle.getString("label.confirm"));

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == ButtonType.OK) {

							main.showProgressStage();
							ProgressBarController progressController = main.getProgressController();
							progressController.setTextLabel("Deleting: " + selectedItem.getName());

							if (selectedItem.getSize().equals("<DIR>")) {
								
								Task<Void> task = new Task<Void>() {
									@Override
									public Void call() throws InterruptedException, IOException {
										String directoryToDelete = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
										double filesInDirectoryCounter = fileService.getFilesInDirectoryCount(directoryToDelete);
										fileService.deleteDirectory(directoryToDelete, progressController, filesInDirectoryCounter);
										Platform.runLater(new Runnable() {
										    public void run() {
										        main.closeProgressStage();
										        try {
													updateDirectory1Content(curDirTextField1.getText());
												} catch (IOException e) {
													e.printStackTrace();
												}
										    }
										});
										return null;
									}
								};

								Thread th = new Thread(task);
								th.setDaemon(true);
								th.start();
								
							} else {
								System.out.println("Deleting file.");
								
								Task<Void> task = new Task<Void>() {
									@Override
									public Void call() throws InterruptedException, IOException {
										String fileToDelete = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
										fileService.deleteFile(fileToDelete, progressController);
										Platform.runLater(new Runnable() {
										    public void run() {
										        main.closeProgressStage();
										        try {
													updateDirectory1Content(curDirTextField1.getText());
												} catch (IOException e) {
													e.printStackTrace();
												}
										    }
										});
										return null;
									}
								};
								
								Thread th = new Thread(task);
								th.setDaemon(true);
								th.start();
							}
						}
					} /////////////////////////////////////// MOVE JDIR //////////////////////////////////////////////////////////////////////////
					else if(keyEvent.getCode().equals(KeyCode.F6)) {
						String moveFromPath = fileService.validateDirectoryPath(curDirTextField1.getText(), selectedItem.getName());
						moveController.setTextFieldMoveFrom(moveFromPath);
						
						if (selectedItem.getSize().equals("<DIR>")) {
							System.out.println("Directory");
							main.showMoveScreenStage();
							String moveTo = fileService.validateDirectoryPath(curDirTextField.getText(), selectedItem.getName());
							moveController.setPathTextFieldText(moveTo);
							
						} else {
							System.out.println("Copying file.");
							
							 main.showMoveScreenStage();
							 String moveTo = fileService.validateDirectoryPath(curDirTextField.getText(), selectedItem.getName());
							 moveController.setPathTextFieldText(moveTo);
						}
					}
				}
			}
		});
	} 
    
    public void onCurDirTextField1Enter() throws IOException {
    	
    	curDirTextField1.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	if(fileService.checkDirectoryExistance(curDirTextField1.getText())) {
                		try {
							jDirectoryTable1.setItems(fileService.getDirectoriesAndFiles(curDirTextField1.getText(), dateFormat));
						} catch (IOException e) {
							e.printStackTrace();
						}
                	}
                }
            }
        });
    }
    
    public void onCurDirTextFieldEnter() throws IOException {
    	
    	curDirTextField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	if(fileService.checkDirectoryExistance(curDirTextField.getText())) {
                		try {
							jDirectoryTable.setItems(fileService.getDirectoriesAndFiles(curDirTextField.getText(), dateFormat));
						} catch (IOException e) {
							e.printStackTrace();
						}
                	}
                }
            }
        });
    }
    
    public void listenTableDoubleClick() {
    	jDirectoryTable.setRowFactory( tv -> {
    	    TableRow<JFile> row = new TableRow<JFile>();
    	    row.setOnMouseClicked(event -> {
    	        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
    	            JFile rowData = row.getItem();
    	            if(rowData.getSize().equals("<DIR>")) {
        	            curDirTextField.setText(curDirTextField.getText() + rowData.getName() +  File.separator);
    					try {
							jDirectoryTable.setItems(fileService.getDirectoriesAndFiles(curDirTextField.getText(), dateFormat));
						} catch (Exception e) {
							e.printStackTrace();
						}
    	            }
    	        }
    	    });
    	    return row ;
    	});
    }
    
    public void listenTable1DoubleClick() {
    	jDirectoryTable1.setRowFactory( tv -> {
    	    TableRow<JFile> row = new TableRow<JFile>();
    	    row.setOnMouseClicked(event -> {
    	        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
    	            JFile rowData = row.getItem();
    	            if(rowData.getSize().equals("<DIR>")) {
        	            curDirTextField1.setText(curDirTextField1.getText() + rowData.getName() +  File.separator);
    					try {
							jDirectoryTable1.setItems(fileService.getDirectoriesAndFiles(curDirTextField1.getText(), dateFormat));
						} catch (Exception e) {
							e.printStackTrace();
						}
    	            }
    	        }
    	    });
    	    return row ;
    	});
    }
    
    public void listenButton() {
    	button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent actionEvent) {
    	        String path = curDirTextField.getText();
    	        if(!rootService.checkIfRootPath(path)) {
    	        	File file = new File(path);
    	        	try {
						String absolutePath = file.getAbsolutePath();
						String finalPath = absolutePath.replace(file.getName(), "");
						curDirTextField.setText(finalPath);
						jDirectoryTable.setItems(fileService.getDirectoriesAndFiles(finalPath, dateFormat));
					} catch (IOException e) {
						e.printStackTrace();
					}
    	        }
    	    }
    	});
    }
    
    public void listenButton1() {
    	button1.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent actionEvent) {
    	        String path = curDirTextField1.getText();
    	        if(!rootService.checkIfRootPath(path)) {
    	        	File file = new File(path);
    	        	try {
						String absolutePath = file.getAbsolutePath();
						String finalPath = absolutePath.replace(file.getName(), "");
						curDirTextField1.setText(finalPath);
						jDirectoryTable1.setItems(fileService.getDirectoriesAndFiles(finalPath, dateFormat));
					} catch (IOException e) {
						e.printStackTrace();
					}
    	        }
    	    }
    	});
    }
    
    public void updateJCommanderMainLocale(ResourceBundle bundle) {
    	nameColumn.setText(bundle.getString("label.name_column"));
    	sizeColumn.setText(bundle.getString("label.size_column"));
    	createDateColumn.setText(bundle.getString("label.date_column"));
    	
    	nameColumn1.setText(bundle.getString("label.name_column"));
    	sizeColumn1.setText(bundle.getString("label.size_column"));
    	createDateColumn1.setText(bundle.getString("label.date_column"));
    }
   
    public void updateRootLocale(ResourceBundle bundle) {
    	rootController.setTextFile(bundle.getString("label.file"));
    	rootController.setTextExit(bundle.getString("label.exit"));
    	rootController.setTextEnglish(bundle.getString("label.english"));
    	rootController.setTextPolish(bundle.getString("label.polish"));
    	rootController.setTextLanguage(bundle.getString("label.language"));
    }
    
    
    public void updateCopyLocale(ResourceBundle bundle) throws IOException {

    	copyController.getLabelCopyFrom().setText(bundle.getString("label.copyFrom"));
    	copyController.getLabelCopyTo().setText(bundle.getString("label.copyTo"));
    	copyController.getCopyButton().setText(bundle.getString("label.copyButton"));
    	copyController.getCancelButton().setText(bundle.getString("label.cancelButton"));
    	copyController.setDirectoryAlertContextText(bundle.getString("label.directoryLabeContextText"));
    	copyController.setDirectoryAlertHeader(bundle.getString("label.directoryAlertHeader"));
    	copyController.setFileAlertContextText(bundle.getString("label.fileLabeContextText"));
    	copyController.setFileAlertHeader(bundle.getString("label.fileAlertHeader"));
    }
    
    public void updateMoveLocale(ResourceBundle bundle) {
    	moveController.setTextCancelButton(bundle.getString("label.cancelButton"));
    	moveController.setTextMoveButton(bundle.getString("label.moveButton"));
    	moveController.setTextLabelMoveTo(bundle.getString("label.moveTo"));
    	moveController.setTextLabelMoveFrom(bundle.getString("label.moveFrom"));
    }
    
    public void updateProgressBarLocale(ResourceBundle bundle) {
    	progressBarController.getCancelButton().setText(bundle.getString("label.cancelButton"));
    }
    
	public void updateDateFormat(String dateFormat) throws IOException {
		this.dateFormat = dateFormat;

		all = fileService.getDirectoriesAndFiles(rootService.getSystemRoots().get(0), dateFormat);
		jDirectoryTable.setItems(all);
		jDirectoryTable1.setItems(all);

	}

    
	Menu file;
	
	@FXML
	Menu language;
	
	@FXML
	CheckMenuItem polish;
	
	@FXML
	CheckMenuItem english;
	
	@FXML
	MenuItem exit;
    
	

}
