package com.controllers;

import com.domain.JFile;
import com.domain.JRoot;
import com.services.JFileService;
import com.services.JRootService;

import application.Main;

import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
	private TextField curDirTextField;
	
	@FXML
	private TextField curDirTextField1;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private Label sizeLabel;
	
	@FXML
	private Label createDateLabel;
	
	private Main main;
	
	private JFileService fileService = new JFileService();
	
	private JRootService rootService = new JRootService();
		
	public JCommanderMainController() {
		
	}
	
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
		createDateColumn.setCellValueFactory(cellData -> cellData.getValue().fileTimeProperty().asString());
		nameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		sizeColumn1.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
		createDateColumn1.setCellValueFactory(cellData -> cellData.getValue().fileTimeProperty().asString());
	}
	
    public void setMain(Main main) throws IOException {
        this.main = main;
        
        initializeComponents();
        
        setComboBoxItems();
        setComboBox1Items();
        onCurDirTextFieldEnter();

   
    }
    
    public void initializeComponents() throws IOException {
        ObservableList<JFile> all = fileService.getDirectoriesAndFiles(rootService.getSystemRoots().get(0));
        // Add observable list data to the table
        jDirectoryTable.setItems(all);
        jDirectoryTable1.setItems(all);
        jRootComboBox.setItems(rootService.getSystemRoots());
        jRootComboBox1.setItems(rootService.getSystemRoots());
        jRootComboBox.getSelectionModel().select(rootService.getSystemRoots().get(0));
        jRootComboBox1.getSelectionModel().select(rootService.getSystemRoots().get(0));
        curDirTextField.setText(rootService.getSystemRoots().get(0));
        curDirTextField1.setText(rootService.getSystemRoots().get(0));
    }
    
    public void setComboBoxItems() {
        jRootComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	try {
					jDirectoryTable.setItems(fileService.getDirectoriesAndFiles(jRootComboBox.getValue()));
					curDirTextField.setText(jRootComboBox.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
            }    
        });
    }
    
    public void setComboBox1Items() {
        jRootComboBox1.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	try {
					jDirectoryTable1.setItems(fileService.getDirectoriesAndFiles(jRootComboBox1.getValue()));
					curDirTextField1.setText(jRootComboBox1.getValue());
				} catch (IOException e) {
					e.printStackTrace();
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
							jDirectoryTable.setItems(fileService.getFilesForDirectory(curDirTextField.getText()));
						} catch (IOException e) {
							e.printStackTrace();
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
							jDirectoryTable1.setItems(fileService.getFilesForDirectory(curDirTextField1.getText()));
						} catch (IOException e) {
							e.printStackTrace();
						}
                	}
                }
            }
        });
    }
    
	

}
