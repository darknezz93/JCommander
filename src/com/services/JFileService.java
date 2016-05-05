package com.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.controllers.CopyScreenController;
import com.controllers.ProgressBarController;
import com.domain.JFile;

import application.Main;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;

public class JFileService {
	
	//JDirectoryService directoryService= new JDirectoryService();
	
    private final ReadOnlyLongWrapper filesDeleted = new ReadOnlyLongWrapper();
    private double totalFilesCounter;
    private double total = 0;
    
    public void setTotal(double total) {
    	this.total = total;
    }
    
   // private final ReadOnlyLongWrapper filesToDelete = new ReadOnlyLongWrapper();
	
	public final long getFilesDeleted() {
		return filesDeleted.get();
	}

	public ObservableList<JFile> getDirectoriesAndFiles(String dirPath) throws IOException {
		ObservableList<JFile> directories = getDirectoriesInDirectory(dirPath);
		ObservableList<JFile> files = getFilesForDirectory(dirPath);
		directories.addAll(files);
		return directories;
	}
	
	public ObservableList<JFile> getFilesForDirectory(String fPath) throws IOException {
		ObservableList<JFile> files =  FXCollections.observableArrayList();		
		File folder = new File(fPath);
		File[] listOfFiles = folder.listFiles();
		
		if(checkDirectoryExistance(fPath)) {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					JFile jFile = new JFile();
					jFile.setName(file.getName());
					jFile.setSize(String.valueOf(getFileSize(fPath + File.separator + file.getName())));
					//SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					jFile.setFileTime(String.valueOf(getFileCreateDate(fPath + File.separator + file.getName())));
					files.add(jFile);
				}
			}
		}
		System.out.println(files.size());
		return files;
	}
	
	
	public String getFileCreateDate(String filePath) {
		Path path = Paths.get(filePath);
		BasicFileAttributes attr;
		FileTime fileTime = null;
		String time = "";
		//if(checkFileExistance(filePath)) {
			try {
				attr = Files.readAttributes(path, BasicFileAttributes.class);
				fileTime = attr.creationTime();
				time = fileTime.toString();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		//}
		return time;
	}
	
	public long getFileSize(String filePath) {
		Path path = Paths.get(filePath);
		BasicFileAttributes attr;
		long size = 0;
		
		if(checkFileExistance(filePath)) {
			try {
				attr = Files.readAttributes(path, BasicFileAttributes.class);
				size = attr.size();

			} catch (IOException e) {
				System.out.println("oops error! " + e.getMessage());
			}
		}
		return size;
	}
	
	
	public boolean copyFile(String sourceFilePath, String destinationFilePath, CopyScreenController controller) {
		

		InputStream inStream = null;
		OutputStream outStream = null;
		boolean result = false;

		try {
			File afile = new File(sourceFilePath);
			File bfile = new File(destinationFilePath);
			
			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);
			
			byte[] buffer = new byte[1024];

			int length;
			double fileSize = getFileSize(sourceFilePath);
			double total = 0.0;
			
			while ((length = inStream.read(buffer)) > 0) {
				total += length;
				outStream.write(buffer, 0, length);
				controller.setProgress(total/fileSize);
			}
			
			inStream.close();
			outStream.close();

			System.out.println("File is copied successful!");
			result = true;

		} catch (IOException e) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Access denied.");
			alert.setContentText("Access to directory denied.");
			e.printStackTrace();
		}	
		return result;
	}
	
	public boolean moveFile(String filePath, String newFilePath) {
		boolean result = false;
    	try{
    		
     	   File afile = new File(filePath);
     		
     	   if(afile.renameTo(new File(newFilePath))) {
     		   System.out.println("File is moved successful!");     		   
     	   }
     	   result = true;
     	}catch(Exception e){
     		e.printStackTrace();
     	}
    	return result;
	}
	
	public boolean deleteFile(String filePath, ProgressBarController progressController) {
		boolean result = false;
		try {
			File file = new File(filePath);
			if(file.delete()) {
				System.out.println("File deleted successfully!");
				progressController.setProgress(1);
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean checkFileExistance(String filePath) {
		File f = new File(filePath);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		return false;
	}
	
	
	public boolean checkDirectoryExistance(String dirPath) {
		
		File f = new File(dirPath);
		if(f.exists() && f.isDirectory()) { 
		    return true;
		}
		return false;
	}
	
	
	
	public ObservableList<JFile> getDirectoriesInDirectory(String dirPath) {
		
		ObservableList<JFile> directories = FXCollections.observableArrayList();		
		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();
		
		if(checkDirectoryExistance(dirPath)) {
			for (File file : listOfFiles) {
				if (file.isDirectory()) {
					JFile jDir = new JFile();
					jDir.setName(file.getName());
					jDir.setFileTime(getFileCreateDate(dirPath));
					jDir.setSize("<DIR>");
					directories.add(jDir);
				}
			}
		}
		return directories;
	}
	
	
	public boolean copyDirectory(String sourcePath, String destinationPath, CopyScreenController controller, double directorySize) throws IOException {
		File src = new File(sourcePath);
		File dest = new File(destinationPath);
		boolean result = false;
		
		if (src.isDirectory()) {

			if (!dest.exists()) {
				dest.mkdir();
			}

			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyDirectory(srcFile.getAbsolutePath(), destFile.getAbsolutePath(), controller, directorySize);
			}

		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				total += length;
				out.write(buffer, 0, length);
			}
			
			System.out.println((total/directorySize));
			controller.setProgress( (total/directorySize));
			
			in.close();	 
			out.close();
			result = true;
		}
		//total = 0;
		return result;
	}
	
	
	public boolean moveDirectory(String dirPath, String newDirPath) {
		boolean result = false;
    	try{
    		
     	   File afile = new File(dirPath);
     		
     	   if(afile.renameTo(new File(newDirPath))) {
     		   System.out.println("Directory is moved successful!");     		   
     	   }
     	   result = true;
     	}catch(Exception e){
     		e.printStackTrace();
     	}
    	return result;
	}
	
	public double getFilesInDirectoryCount(String directoryPath) {
		  File file = new File(directoryPath);
		  File[] files = file.listFiles();
		  long count = 0;
		  for (File f : files)
		    if (f.isDirectory())
		      count += getFilesInDirectoryCount(f.getAbsolutePath());
		    else
		      count++;

		  return count;
	}
	
	public boolean deleteDirectory(String dirPath, ProgressBarController controller, double filesInDirectoryCounter) throws IOException {
		File directory = new File(dirPath);
	    if(directory.exists()){
	    	
	        File[] files = directory.listFiles();
	        
	        if(null!=files){
	            for(int i=0; i < files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i].getAbsolutePath(), controller, filesInDirectoryCounter);
	                }
	                else {
	                    files[i].delete();
	                    totalFilesCounter++;
	                    filesDeleted.set((long) totalFilesCounter);
	                    controller.setProgress(totalFilesCounter/filesInDirectoryCounter);
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
	
	public String validateDirectoryPath(String path, String direcotryName) {
		String finalPath = "";
		if(path.endsWith(File.separator)) {
			finalPath = path + direcotryName;
		} else {
			finalPath += path + File.separator + direcotryName;
		}
		
		return finalPath;
	}
	
	public double getDirectorySize(File directory) {
	    double length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += getDirectorySize(file);
	    }
	    return length;
	}
	
	
	
	
	

}
