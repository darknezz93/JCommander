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
import java.util.ArrayList;
import java.util.List;

import com.domain.JDirectory;
import com.domain.JFile;

public class JDirectoryService {
	
	
	public boolean checkDirectoryExistance(String dirPath) {
		
		File f = new File(dirPath);
		if(f.exists() && f.isDirectory()) { 
		    return true;
		}
		return false;
	}
	
	public List<JDirectory> getDirectoriesInDirectory(String dirPath) {
		
		List<JDirectory> directories = new ArrayList<>();		
		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();
		
		if(checkDirectoryExistance(dirPath)) {
			for (File file : listOfFiles) {
				if (file.isDirectory()) {
					JDirectory jDir = new JDirectory();
					jDir.setName(file.getName());
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
	
	
	
}
