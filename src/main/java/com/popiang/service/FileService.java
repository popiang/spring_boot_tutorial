package com.popiang.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.popiang.exceptions.InvalidFileException;
import com.popiang.model.FileInfo;

@Service
public class FileService {

	@Value("${photo.file.extensions}")
	private String imageExtensions;
	
	private Random random = new Random();
	
	//
	// get the image file extension
	//
	private String getFileExtension(String filename) {
		
		int dotPosition = filename.lastIndexOf(".");
		
		if(dotPosition < 0) {
			return null;
		}
		
		return filename.substring(dotPosition+1).toLowerCase();
	}
	
	//
	// checking if the extension is an image file extension
	//	
	private boolean isImageExtension(String extension) {
		
		String testExtension = extension.toLowerCase();
		
		for(String validExtension : imageExtensions.split(",")) {
			
			if(testExtension.equals(validExtension)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	//
	// creating sub-directory to upload photos to
	//
	private File makeSubdirectory(String basePath, String prefix) {
		
		// getting random number from 0 to 999 to append to sub-directory name
		int nDirectory = random.nextInt(1000);
		
		// append the sub-directory name with the random generated number
		String sDirectory = String.format("%s%03d", prefix, nDirectory);
		
		// creating the file directory object
		File directory = new File(basePath, sDirectory);
		
		// creating the directory if it is not exist yet
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		return directory;
		
	}
	
	//
	// saving the uploaded image files
	//
	public FileInfo saveImgFile(MultipartFile file, String baseDirectory, String subDirPrefix, String filePrefix) throws InvalidFileException, IOException {
		
		// generated random number from 0 to 999 to append to filename
		int nFilename = random.nextInt(1000);
		
		// append the filename with the random generated number
		String filename = String.format("%s%03d", filePrefix, nFilename);
		
		// getting the extension from the uploaded file
		String extension = getFileExtension(file.getOriginalFilename());
		
		// checking if there's an extension
		if(extension == null) {
			throw new InvalidFileException("No file extension!");
		}
		
		// checking if the extension is not an image extension
		if(isImageExtension(extension) == false) {
			throw new InvalidFileException("Not an image file!");
		}
		
		// creating sub-directory
		File subDirectory = makeSubdirectory(baseDirectory, subDirPrefix);
		
		// creating full file path
		Path filePath = Paths.get(subDirectory.getCanonicalPath(), filename + "." + extension);
		
		// deleting the file if already exist
		Files.deleteIfExists(filePath);
		
		// copy the uploaded file into the file path
		Files.copy(file.getInputStream(), filePath);
		
		return new FileInfo(filename, extension, subDirectory.getName(), baseDirectory);
		
	}
	
}















