package com.popiang.service;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.popiang.exceptions.ImageTooSmallException;
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
	public FileInfo saveImgFile(MultipartFile file, String baseDirectory, String subDirPrefix, String filePrefix, int width, int height) throws InvalidFileException, IOException, ImageTooSmallException {
		
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
		// Files.deleteIfExists(filePath);
		
		// resize the image
		BufferedImage resizedImage = resizeImage(file, width, height);
		
		// copy the uploaded file into the file path
		// Files.copy(file.getInputStream(), filePath);
		
		// saving the image
		ImageIO.write(resizedImage, extension, filePath.toFile());
		
		return new FileInfo(filename, extension, subDirectory.getName(), baseDirectory);
		
	}

	//
	// method to resize the image
	//
	private BufferedImage resizeImage(MultipartFile file, int width, int height) throws IOException, ImageTooSmallException {
		
		BufferedImage image = ImageIO.read(file.getInputStream());
		
		if(image.getWidth() < width || image.getHeight() < height) {
			throw new ImageTooSmallException();
		}
		
		double widthScale = (double)width/image.getWidth();
		double heightScale = (double)height/image.getHeight();
		double scale = Math.max(widthScale, heightScale);
		
		BufferedImage scaledImage = new BufferedImage((int)(scale * image.getWidth()), (int)(scale * image.getHeight()), image.getType());
		
		Graphics2D g = scaledImage.createGraphics();
		
		AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
		
		g.drawImage(image, transform, null);
		
		return scaledImage.getSubimage(0, 0, width, height);
	}
	
}















