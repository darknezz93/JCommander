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
import java.util.ArrayList;
import java.util.List;

import com.domain.JFile;

public class JFileService {
	
	//JDirectoryService directoryService= new JDirectoryService();
	
	public List<JFile> getFilesForDirectory(String fPath) throws IOException {
		List<JFile> files = new ArrayList<>();		
		File folder = new File(fPath);
		File[] listOfFiles = folder.listFiles();
		
		if(checkDirectoryExistance(fPath)) {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					JFile jFile = new JFile();
					jFile.setName(file.getName());
					jFile.setSize(getFileSize(fPath + File.separator + file.getName()));
					jFile.setFileTime(getFileCreateDate(fPath + File.separator + file.getName()));
					files.add(jFile);
				}
			}
		}
		return files;
	}
	
	
	public FileTime getFileCreateDate(String filePath) {
		Path path = Paths.get(filePath);
		BasicFileAttributes attr;
		FileTime fileTime = null;

		if(checkDirectoryExistance(filePath)) {
			try {
				attr = Files.readAttributes(path, BasicFileAttributes.class);
				fileTime = attr.creationTime();

			} catch (IOException e) {
				System.out.println("oops error! " + e.getMessage());
			}
		}
		return fileTime;
	}
	
	public long getFileSize(String filePath) {
		Path path = Paths.get(filePath);
		BasicFileAttributes attr;
		long size = 0;
		
		if(checkDirectoryExistance(filePath)) {
			try {
				attr = Files.readAttributes(path, BasicFileAttributes.class);
				size = attr.size();

			} catch (IOException e) {
				System.out.println("oops error! " + e.getMessage());
			}
		}
		return size;
	}
	
	public boolean checkDirectoryExistance(String dirPath) {	
		Path path = Paths.get(dirPath);
		if(!Files.exists(path)) {
		    return false;
		}
		return true;
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
	
	
	
	
	
	
	

}
