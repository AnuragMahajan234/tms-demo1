package org.yash.rms.helper;
/**
 * 
 */


import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @author ritesh.mandlik
 * 
 * Method is used to send email asynchronous  to improve performance
 *
 */
@Component(value="asyncEmailSendHelper")
@EnableAsync
public class AsyncEmailSendHelper {
  
  private static final Logger logger = LoggerFactory.getLogger(AsyncEmailSendHelper.class);

	@Async
	public void sendEmailAsynchronously(JavaMailSenderImpl mailSender, MimeMessage msg) {
    try {
      mailSender.send(msg);
      System.out.println(msg);
    } catch (RuntimeException e) {
    	
      logger.error("Exception occures while sending the email: ", e);
      logger.info("Exception occures while sending the email: ", e);
    }
	}
}
