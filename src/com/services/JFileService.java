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

import com.domain.JFile;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JFileService {
	
	//JDirectoryService directoryService= new JDirectoryService();
	
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
					jFile.setFileTime(getFileCreateDate(fPath + File.separator + file.getName()));
					files.add(jFile);
				}
			}
		}
		System.out.println(files.size());
		return files;
	}
	
	
	public FileTime getFileCreateDate(String filePath) {
		Path path = Paths.get(filePath);
		BasicFileAttributes attr;
		FileTime fileTime = null;

		//if(checkFileExistance(filePath)) {
			try {
				attr = Files.readAttributes(path, BasicFileAttributes.class);
				fileTime = attr.creationTime();

			} catch (IOException e) {
				System.out.println("oops error! " + e.getMessage());
			}
		//}
		return fileTime;
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
	
	
	public boolean copyFile(String sourceFilePath, String destinationFilePath) {

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
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();

			System.out.println("File is copied successful!");
			result = true;

		} catch (IOException e) {
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
	
	public boolean deleteFile(String filePath) {
		boolean result = false;
		try {
			File file = new File(filePath);
			if(file.delete()) {
				System.out.println("File deleted successfully!");
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
	
	
	public boolean copyDirectory(String sourcePath, String destinationPath) throws IOException {
		File src = new File(sourcePath);
		File dest = new File(destinationPath);
		boolean result = false;
		
		if (src.isDirectory()) {

			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to " + dest);
			}

			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyDirectory(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
			}

		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length); 
			}

			in.close();	 
			out.close();
			result = true;
			System.out.println("File copied from " + src + " to " + dest);
		}
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
	
	public boolean deleteDirectory(String dirPath) {
		File directory = new File(dirPath);
		
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i < files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i].getAbsolutePath());
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
	
	
	/*public List<String> changeDateFormat(ObservableList<JFile> files, String format) {
		List<String>
		for(JFile file : files) {
			Date date = new Date(file.getFileTime().toMillis());
			String modified = new SimpleDateFormat(format).format(date);
		}
		
	}*/
	
	
	
	

}
