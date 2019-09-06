package org.yash.rms.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class ResourceResumeHelper {
	
	private String uploadPath;
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public void uploadResume(int EmployeeId,String fileName,byte[] resumeFile)
	{
		try{
			FileOutputStream fop = null;
			File directory = new File(this.uploadPath+EmployeeId);
			 
			if (!directory.isDirectory()) {
				directory.mkdir();
			 }
			 if(resumeFile!=null && resumeFile.length>0){
				directory = new File(this.uploadPath+EmployeeId+"/"+fileName);
				if(!directory.isFile()){
					directory.createNewFile();
				}
			
				fop = new FileOutputStream(directory);
				fop.write(resumeFile);
			 }
		   }
		   catch(IOException ex){
				ex.printStackTrace();
		   }
	}
	
	public byte[] viewuploadedResume(int EmployeeId,String fileName)
	{
		File directory = new File(this.uploadPath+File.separator+EmployeeId+File.separator+fileName);
		 FileInputStream fin=null;
		 byte fileContent[]=null;
		try {
			fin = new FileInputStream(directory);
		 	  fileContent = new byte[(int) directory.length()];
			 try {
				fin.read(fileContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileContent; 
	}

}
