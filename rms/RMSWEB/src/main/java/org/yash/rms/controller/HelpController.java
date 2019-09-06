package org.yash.rms.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/help")
public class HelpController {

	private static final Logger logger = LoggerFactory.getLogger(HelpController.class);
	
	@RequestMapping (method = RequestMethod.GET)
	public String list () throws Exception {
        return "help/list";
    }
	
	
	@RequestMapping(value = "/pptDownload")
	protected void pptDownload(HttpServletRequest request,	@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/*");
		File file = new File("C:/rms/help/"+fileName+".pptx");
		logger.info("help contoller filepath" +  file.getName()  +   file.getAbsolutePath()  +   file.getPath() );
		response.setHeader("Content-Disposition","attachment; filename="+fileName+".pptx");
		FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());
    	return;

	}
}
