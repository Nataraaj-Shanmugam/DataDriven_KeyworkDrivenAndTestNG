package com.utilities;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.genericKeywords.GenericKeywords;
import com.genericKeywords.ProjectCustomException;

public class FileUtilities extends GenericKeywords{
	
	public void renameFile(String sourcefilePath, String destinationfilePath, String currentFileName, String currentFileExtension, String renameFileName, String renameFileExtension) {
		try {
			getFile(sourcefilePath, currentFileName+"."+currentFileExtension).renameTo(new File(destinationfilePath+"\\"+renameFileName+"."+renameFileExtension));
		} catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to perform file rename operation for file </br>Source Path : "+sourcefilePath+"</br>Source file Name: "+currentFileName+"."+currentFileExtension+"</br>Destination Path : "+destinationfilePath);
		}
	}

	
	public boolean deleteFile(String filePath, String fileName) {
		boolean flag = false;
		try {
			if(getFile(filePath, fileName).delete())
				flag=true;
			else
				new Exception();
		}catch (NullPointerException e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to perform file delete operation for file </br>File Path : "+filePath+"</br>File Name: "+fileName);
		}
		return  flag;
	}

	
	public boolean deleteDirectory(String filePath) {
		try {
			FileUtils.deleteDirectory(getFile(filePath, ""));
		} catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to delete directory</br>Directory Path : "+filePath);
		}
		return true;
	}

	
	public File getFile(String filePath, String fileName) {
		File file = null ;
		try {
			for (File currentFile : getFiles(filePath)) {
				if(currentFile.getName().equals(fileName)) {
					file=currentFile;
					break;
				}
			}
		} catch (Exception e) {
			new ProjectCustomException(getClassName(), getMethodName(), e,"Unable to get file '"+fileName+"' from specified path '"+filePath+"'");
		}
		return file;
	}

	
	public File[] getFiles(String filePath) {
		return new File(filePath).listFiles();
	}
}
