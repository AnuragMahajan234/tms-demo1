package org.yash.rms.forms;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUploadBean {
	 private CommonsMultipartFile file;

	    public void setFile(CommonsMultipartFile file) {
	        this.file = file;
	    }

	    public CommonsMultipartFile getFile() {
	        return file;
	    }
}
