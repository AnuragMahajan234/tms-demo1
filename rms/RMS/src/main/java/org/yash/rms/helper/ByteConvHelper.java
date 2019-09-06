package org.yash.rms.helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class ByteConvHelper implements MultipartFile{
	
	private final byte[] fileContent;

	private String fileName;

	private String contentType;

	private File file;

	//private String destPath = "D:\\New folder";

	private FileOutputStream fileOutputStream;
	
	
	public ByteConvHelper(byte[] fileData, String name) {
	    this.fileContent = fileData;
	    this.fileName = name;
	  //  file = new File(destPath + fileName);
	    file = new File(fileName);

	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public long getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public byte[] getBytes() throws IOException {
		// TODO Auto-generated method stub
		return fileContent;
	}

	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		 return new ByteArrayInputStream(fileContent);
	}

	public void transferTo(File dest) throws IOException, IllegalStateException {
		// TODO Auto-generated method stub
		fileOutputStream = new FileOutputStream(dest);
	    fileOutputStream.write(fileContent);
	     
		
	}


}