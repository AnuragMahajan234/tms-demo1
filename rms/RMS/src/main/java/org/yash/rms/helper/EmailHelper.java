package org.yash.rms.helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;
//import org.yash.rms.util.UserUtil;
import org.yash.rms.util.UserUtil;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component(value = "emailHelper")
public class EmailHelper {

	@Autowired
	@Qualifier("mailSender")
	private MailSender mailSender;

	@Autowired
	@Qualifier("freemarkerConfiguration")
	private Configuration configuration;

	private Set<String> userEmailIds;
	public String emailID;

	@Autowired
	private AsyncEmailSendHelper asyncEmailSendHelper;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	ResourceService resourceService;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailHelper.class);

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public Set<String> getUserEmailIds() {
		return userEmailIds;
	}

	public void setUserEmailIds(Set<String> userEmailIds) {
		this.userEmailIds = userEmailIds;
	}

	/**
	 * This method is used to send email when logged in user submits time
	 * sheets. The submission email should go to the user only. type 1- is for
	 * timesheet submit.(email to employee only) type 2- is for edit
	 * profile.(email to manager only)
	 * 
	 * @param model
	 * @throws Exception
	 */

	public void sendEmailNotification(Map<?, ?> model, Set emailIds,
			String ccEmailIds[]) {
		try {
			String emailText = getEmailMessage(model, configuration);
			System.out.println(emailText);
			if (null != emailText && !StringUtils.isEmpty(emailText)) {
				if (null != emailIds) {
					JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
					MimeMessage msg = message.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(msg);
					// setting Email addressed according to Environments
					helper.setFrom(Constants.FROM_MAIL);
					// if environment is prod then do as it is... for rest of
					// environments pick mailIds from property files
					if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
						String[] toAddress = new String[emailIds.size()];
						int i = 0;
						for (Iterator iterator = emailIds.iterator(); iterator
								.hasNext();) {
							String toAdd = (String) iterator.next();
							toAddress[i] = toAdd;
							i++;
						}
						helper.setTo(toAddress);
						// if model contains CC field then add emails in CC
						if (ccEmailIds != null && ccEmailIds.length > 0) {
							helper.setCc(ccEmailIds);
						}
					} else {
						helper.setTo(Constants.TO_MAIL);

						if (ccEmailIds != null && ccEmailIds.length > 0) {
							helper.setCc(Constants.CC_MAIL);
						}
					}
					helper.setSubject((String) model.get(Constants.SUBJECT));
					helper.setText(emailText, true);

					// message.send(msg);
					// For sending email asynchronously
					asyncEmailSendHelper.sendEmailAsynchronously(message, msg);

				} else
					sendMessage(emailText, model, null);
			}
		} catch (MessagingException e) {
		  logger.info("Exception occures while sending email: ", e);
		} 
	}

	public void sendEmail(Map<?, ?> model, List<MailConfiguration> mailConfg)
			 {
		
    	    String emailText = null;
            emailText = getEmailMessage(model, configuration);
			String[] emailIdTo;
			String[] emailIdCc;
			String[] emailIdBcc;
			boolean to = false;
			boolean cc = false;
			boolean bcc = false;
			Set<String> emailidToList = new HashSet<String>();
			Set<String> emailidcCList = new HashSet<String>();
			Set<String> emailidBccList = new HashSet<String>();

			List<String> emailToList = new ArrayList<String>();
			List<String> emailCcList = new ArrayList<String>();
			List<String> emailBccList = new ArrayList<String>();
			if (model.keySet() != null && model.keySet().size() > 0) {
				for (MailConfiguration confg : mailConfg) {

					/*
					 * if(confg.getConfgId().getName().equals("TimesheetSubmission"
					 * )) {
					 */

					if (confg.isTo()) {
						to = true;

						if (confg.getRoleId().getRole().equals("IRM")) {
							// emailIdTo[0] =
							// userUtil.getLoggedInResource().getCurrentReportingManager().getEmailId();
							if (model.keySet().contains(Constants.IRM_EMAIL_ID)) {
								if (!(model.get(Constants.IRM_EMAIL_ID)
										.toString().equals("")))
									emailidToList.add((String) model
											.get(Constants.IRM_EMAIL_ID));
							}
						}

						if (confg.getRoleId().getRole().equals("SRM")) {
							if (model.keySet().contains(Constants.SRM_EMAIL_ID)) {
								if (!(model.get(Constants.SRM_EMAIL_ID)
										.toString().equals(""))) {
									emailidToList.add((String) model
											.get(Constants.SRM_EMAIL_ID));
								}
							}
						}

						if (confg.getRoleId().getRole().equals("User")) {
							if (model.keySet().contains(Constants.EMAIL_ID)) {
								// emailIdTo[0] = (String)
								// model.get(Constants.EMAIL_ID);
								emailidToList.add((String) model
										.get(Constants.EMAIL_ID));
							}
						}

						if (confg.getRoleId().getRole()
								.equals("ProjectManager")) {
							if (model.keySet().contains(Constants.CC_EMAIL_ID)) {
								emailidToList.add((String) model
										.get(Constants.CC_EMAIL_ID));
							}
						}

						if (confg.getRoleId().getRole().equals("ADMIN")
								|| confg.getRoleId().getRole()
										.equals("BG_ADMIN")) {
							if (model.keySet().contains(
									Constants.PROJECT_BUNAME)) {
								int buId = (Integer) model
										.get(Constants.PROJECT_BUNAME);
								List bu = new ArrayList<Integer>();
								bu.add(buId);
								List<String> list = resourceService
										.getEmailIds(confg.getRoleId()
												.getRole(), bu);
								if (list != null && list.size() > 0)
									emailidToList.addAll(list);
							}
						}

						if (confg.getRoleId().getRole().equals("ROLE_MANAGER")
								|| confg.getRoleId().getRole()
										.equals("DEL_MANAGER")) {

							if (confg.getRoleId().getRole()
									.equals("DEL_MANAGER")) {
								if (model.keySet().contains(
										Constants.MANAGER_IRM_EMAIL_ID)) {
									if (!(model.get(
											Constants.MANAGER_IRM_EMAIL_ID)
											.toString().equals(""))) {
										emailidToList
												.add((String) model
														.get(Constants.MANAGER_IRM_EMAIL_ID));
									}
								}
								if (model.keySet().contains(
										Constants.MANAGER_SRM_EMAIL_ID)) {
									if (!(model.get(
											Constants.MANAGER_SRM_EMAIL_ID)
											.toString().equals(""))) {
										emailidToList
												.add((String) model
														.get(Constants.MANAGER_SRM_EMAIL_ID));
									}
								}
							}
							if (model.containsKey(Constants.CC_EMAIL_ID)) {
								emailidToList.add((String) model
										.get(Constants.CC_EMAIL_ID));
							}

						}
					}

					if (confg.isCc()) {
						cc = true;

						if (confg.getRoleId().getRole().equals("IRM")) {
							if (model.keySet().contains(Constants.IRM_EMAIL_ID)) {
								if (!(model.get(Constants.IRM_EMAIL_ID)
										.toString().equals(""))) {
									emailidcCList.add((String) model
											.get(Constants.IRM_EMAIL_ID));
								}
							}
						}
						if (confg.getRoleId().getRole().equals("SRM")) {
							if (model.keySet().contains(Constants.SRM_EMAIL_ID)) {
								if (!(model.get(Constants.SRM_EMAIL_ID)
										.toString().equals(""))) {
									emailidcCList.add((String) model
											.get(Constants.SRM_EMAIL_ID));
								}
							}
						}

						if (confg.getRoleId().getRole().equals("User")) {
							if (model.keySet().contains(Constants.EMAIL_ID)) {
								emailidcCList.add((String) model
										.get(Constants.EMAIL_ID));
							}
						}

						if (confg.getRoleId().getRole()
								.equals("ProjectManager")) {
							if (model.containsKey(Constants.CC_EMAIL_ID)) {
								emailidcCList.add((String) model
										.get(Constants.CC_EMAIL_ID));
							}
						}

						if (confg.getRoleId().getRole().equals("ADMIN")
								|| confg.getRoleId().getRole()
										.equals("BG_ADMIN")) {
							if (model.keySet().contains(
									Constants.PROJECT_BUNAME)) {
								int buId = (Integer) model
										.get(Constants.PROJECT_BUNAME);
								List bu = new ArrayList<Integer>();
								bu.add(buId);
								List<String> list = resourceService
										.getEmailIds(confg.getRoleId()
												.getRole(), bu);
								if (list != null && list.size() > 0) {
									emailidcCList.addAll(list);
								}
							}
						}

						if (confg.getRoleId().getRole().equals("ROLE_MANAGER")
								|| confg.getRoleId().getRole()
										.equals("DEL_MANAGER")) {
							if (confg.getRoleId().getRole()
									.equals("DEL_MANAGER")) {
								if (model.keySet().contains(
										Constants.MANAGER_IRM_EMAIL_ID)) {
									if (!(model.get(
											Constants.MANAGER_IRM_EMAIL_ID)
											.toString().equals(""))) {
										emailidcCList
												.add((String) model
														.get(Constants.MANAGER_IRM_EMAIL_ID));
									}
								}

								if (model.keySet().contains(
										Constants.MANAGER_SRM_EMAIL_ID)) {
									if (!(model.get(
											Constants.MANAGER_SRM_EMAIL_ID)
											.toString().equals(""))) {
										emailidcCList
												.add((String) model
														.get(Constants.MANAGER_SRM_EMAIL_ID));
									}
								}
							}
							if (model.containsKey(Constants.CC_EMAIL_ID)) {
								emailidcCList.add((String) model
										.get(Constants.CC_EMAIL_ID));
							}

						}
					}

					if (confg.isBcc()) {
						bcc = true;

						if (confg.getRoleId().getRole().equals("IRM")) {
							if (model.containsKey(Constants.IRM_EMAIL_ID)) {
								if (!(model.get(Constants.IRM_EMAIL_ID)
										.toString().equals(""))) {
									emailidBccList.add((String) model
											.get(Constants.IRM_EMAIL_ID));
								}
							}
						}
						if (confg.getRoleId().getRole().equals("SRM")) {
							if (model.containsKey(Constants.SRM_EMAIL_ID)) {
								if (!(model.get(Constants.SRM_EMAIL_ID)
										.toString().equals(""))) {
									emailidBccList.add((String) model
											.get(Constants.SRM_EMAIL_ID));
								}
							}
						}
						if (confg.getRoleId().getRole().equals("User")) {
							emailIdBcc = new String[1];
							if (model.containsKey(Constants.EMAIL_ID)) {
								emailidBccList.add((String) model
										.get(Constants.EMAIL_ID));
							}
						}
						if (confg.getRoleId().getRole()
								.equals("ProjectManager")) {
							if (model.containsKey(Constants.CC_EMAIL_ID)) {
								emailidBccList.add((String) model
										.get(Constants.CC_EMAIL_ID));
							}
						}

						if (confg.getRoleId().getRole().equals("ADMIN")
								|| confg.getRoleId().getRole()
										.equals("BG_ADMIN")) {
							if (model.containsKey(Constants.PROJECT_BUNAME)) {
								int buId = (Integer) model
										.get(Constants.PROJECT_BUNAME);
								List bu = new ArrayList<Integer>();
								bu.add(buId);
								List<String> list = resourceService
										.getEmailIds(confg.getRoleId()
												.getRole(), bu);
								if (list != null && list.size() > 0)
									emailidBccList.addAll(list);
							}
						}

						if (confg.getRoleId().getRole().equals("ROLE_MANAGER")
								|| confg.getRoleId().getRole()
										.equals("DEL_MANAGER")) {
							if (confg.getRoleId().getRole()
									.equals("DEL_MANAGER")) {
								if (model
										.containsKey(Constants.MANAGER_IRM_EMAIL_ID)) {
									if (!(model.get(
											Constants.MANAGER_IRM_EMAIL_ID)
											.toString().equals(""))) {
										emailidBccList
												.add((String) model
														.get(Constants.MANAGER_IRM_EMAIL_ID));
									}
								}
								if (model
										.containsKey(Constants.MANAGER_SRM_EMAIL_ID)) {
									if (!(model.get(
											Constants.MANAGER_SRM_EMAIL_ID)
											.toString().equals(""))) {
										emailidBccList
												.add((String) model
														.get(Constants.MANAGER_SRM_EMAIL_ID));
									}
								}
							}
							if (model.containsKey(Constants.CC_EMAIL_ID)) {
								emailidBccList.add((String) model
										.get(Constants.CC_EMAIL_ID));
							}

						}
					}

				}
			}

			emailToList.addAll(emailidToList);
			emailCcList.addAll(emailidcCList);
			emailBccList.addAll(emailidBccList);

			emailIdTo = new String[emailidToList.size()];
			emailIdCc = new String[emailidcCList.size()];
			emailIdBcc = new String[emailidBccList.size()];

			for (int i = 0; i < emailidToList.size(); i++) {
				emailIdTo[i] = emailToList.get(i).toString();

			}
			for (int j = 0; j < emailidcCList.size(); j++) {
				emailIdCc[j] = emailCcList.get(j).toString();

			}
			for (int k = 0; k < emailidBccList.size(); k++) {
				emailIdBcc[k] = emailBccList.get(k).toString();

			}
			System.out.println("emailIdTo-----" + emailIdTo);
			System.out.println("emailIdCC-----" + emailIdCc);
			System.out.println("emailIdBCC-----" + emailIdBcc);

			if (null != emailText && !StringUtils.isEmpty(emailText)) {
				if (null != emailIdTo || null != emailIdCc
						|| null != emailIdBcc) {
					JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
					MimeMessage msg = message.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(msg);
					// setting Emails addressed according to Enviornments
      			try {
                  helper.setFrom(Constants.FROM_MAIL);
                } catch (MessagingException e) {
                  logger.info("Exception occures while setting the sender email address: ", e);
                }
					// if enviornment is prod then do as it is... for rest of
					// enviornments pick mailIds from property files
					if (to) {
						if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
					try {
                    helper.setTo(emailIdTo);
                  } catch (MessagingException e) {
                    logger.info("Exception occures while setting the recepient email address: ", e);
                  }
						} else {
    							try {
                    helper.setTo(Constants.TO_MAIL);
                  } catch (MessagingException e) {
                    logger.info("Exception occures while setting the recepient email address: ", e);
                  }
						}
					}
					if (cc) {
						if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
      				try {
                      helper.setCc(emailIdCc);
                    } catch (MessagingException e) {
                      logger.info("Exception occures while setting the CC recepient email address: ", e);
                    }
					} else
                    try {
                      helper.setCc(Constants.CC_MAIL);
                    } catch (MessagingException e) {
                      logger.info("Exception occures while setting the CC recepient email address: ", e);
                    }
					}
					if (bcc) {
						if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
      				try {
                      helper.setBcc(emailIdBcc);
                    } catch (MessagingException e) {
                      logger.info("Exception occures while setting the BCC recepient email address: ", e);
                    }
						} else
                  try {
                    helper.setBcc(Constants.BCC_MAIL);
                  } catch (MessagingException e) {
                    logger.info("Exception occures while setting the BCC recepient email address: ", e);
                  }
					}
    		 try {
                helper.setSubject((String) model.get(Constants.SUBJECT));
              } catch (MessagingException e) {
                logger.info("Exception occures while setting the Subject of email address: ", e);
              }
    		 
    		 try {
                helper.setText(emailText, true);
              } catch (MessagingException e) {
                logger.info("Exception occures while setting the text of email address: ", e);
              }
    		   
              try {
                asyncEmailSendHelper.sendEmailAsynchronously(message, msg);
              } catch (RuntimeException e) {
                logger.info("Exception occures while sending the email: ", e);
              }
            } 
			else
              try {
                sendMessage(emailText, model, null);
              } catch (MessagingException e) {
                logger.info("Exception occures while sending email address: ", e);
              }
			}

		
	}

	/**
	 * This method is used to send email when time-sheet is unfilled to last
	 * week. The submission email goes to the employee and manager.
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void sendEmail(Map<?, ?> model, String emailId) throws Exception {
		if ((emailId != null) && (emailId.trim().length() > 0)) {
			String[] emails = emailId.split(",");
			String emailText = getEmailMessage(model, configuration);
      if (!(Boolean) model.get("newResource")) {
      	if ((emailText != null)
      			&& (!StringUtils.isEmpty(emailText))) {
      		sendMessage(emailText, model, emails);
      	}
      }
      if ((Boolean) model.get("newResource")) {
      	sendMessage(emailText, model, emails);
      }
		}
	}

	/* Generates mail for resource request */

	public void sendEmail(Map<?, ?> model, String[] tomailIDs,
			String[] ccmailIDs, File tempFile, String reqId) {
    String emailText = getEmailMessage(model, configuration);
    if ((emailText != null) && (!StringUtils.isEmpty(emailText))) {
      try {
        sendRequestMessage(emailText, model, tomailIDs, ccmailIDs,tempFile,reqId);
      } catch (MessagingException e) {
        logger.info("Exception occures on sending email: ", e);
      }
    }
  }

	
	// }
	/**
	 * This method is used to send email to manager and employee when timesheet
	 * for last week is unfilled.
	 * 
	 * @param emailText
	 *            , the message
	 * @param model
	 * @throws MessagingException
	 */
	private void sendMessage(String emailText, Map<?, ?> model,
			String[] emailAddressesList) throws MessagingException {
		JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
		String[] emails = new String[2];
		for (int i = 0; i < emailAddressesList.length; i++) {
			if (i < 2)
				emails[i] = emailAddressesList[i];
		}
		String rm1 = (String) model.get("rm1Email");
		String rm2 = (String) model.get("rm2Email");
		String[] emailsCc = { rm1, rm2 };

		MimeMessage msg = message.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		// setting Emails addressed according to Environments
		if ((Boolean) model.get("newResource")) {
			helper.setFrom(Constants.FROM_MAIL_NEW_RESOURCE);
			if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
				helper.setCc(emailsCc);
			} else {
				helper.setCc(Constants.CC_MAIL);
			}

		} else {
			helper.setFrom(Constants.FROM_MAIL);
		}
		// if environment is prod then do as it is... for rest of environments
		// pick mailIds from property files
		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
			helper.setTo(emails[0]);
		} else {
			helper.setTo(Constants.TO_MAIL);
			// helper.setTo( emails[0]);
		}
		if (emailAddressesList.length > 1) {
			if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
				helper.setCc(emails[1]);
			} else {
				helper.setCc(Constants.CC_MAIL);
			}
		}

		helper.setSubject((String) model.get(Constants.SUBJECT));
		helper.setText(emailText, true);
		// message.send(msg);
		// For sending email asynchronously
		asyncEmailSendHelper.sendEmailAsynchronously(message, msg);
	}

	@SuppressWarnings("null")
	private void sendRequestMessage(String emailText, Map<?, ?> model,
			String[] tomailIDs, String[] ccMailIDs, File tempFile, String reqId) throws MessagingException {
		JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
		MimeMessage msg = message.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, 2);
		String sendMailFrom = (String) model.get("sendMailFrom");
		helper.setFrom(sendMailFrom);
		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
			
			if (ccMailIDs!=null && ccMailIDs.length>0 && !(ccMailIDs[0].equalsIgnoreCase("no"))) {
				for (int i = 0; i < ccMailIDs.length; i++) {
					System.out.println("ccMailIDs: " + i + "--- "+ ccMailIDs[i]);
				}
				helper.setCc(ccMailIDs);
			}
		} else {
			if(ccMailIDs!=null && ccMailIDs.length>0 && !(ccMailIDs[0].equalsIgnoreCase("no"))){
				for (int i = 0; i < ccMailIDs.length; i++) {
					System.out.println("ccMailIDs: " + i + "--- "+ccMailIDs[i]);
				}
				helper.setCc(Constants.CC_MAIL);
			}
			else
			{
				helper.setCc(Constants.CC_MAIL);
			}
		}
		/*
		 * String tomailIDs[] = null; for(int i=0; i<tomailIDsList.size(); i++){
		 * tomailIDs[i] = (String) tomailIDsList.get(i); }
		 */
		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
			for (int i = 0; i < tomailIDs.length; i++) {
				System.out.println("tomailIDs: " + i + "--- "+tomailIDs[i]);
			}
			helper.setTo(tomailIDs);
		} else {
			for (int i = 0; i < tomailIDs.length; i++) {
				System.out.println("tomailIDs: " + i + "--- "+tomailIDs[i]);
			}
			helper.setTo(Constants.TO_MAIL);
		}

		helper.setSubject((String) model.get(Constants.SUBJECT));
		helper.setText(emailText, true);
		// message.send(msg);
		// For sending email asynchronously
		
		//new code for attachment
		if(tempFile!=null) {
		try {
			helper.addAttachment("RRF_"+reqId+".pdf", new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(tempFile))));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		//end
		asyncEmailSendHelper.sendEmailAsynchronously(message, msg);
	}

	/**
	 * This method is used to process the ftl file through Freemarker
	 * configuration.
	 * 
	 * @param model
	 *            , the map
	 * @param configuration
	 *            , the object
	 * @return String, the email message
	 * @throws IOException
	 * @throws TemplateException
	 */
	private String getEmailMessage(Map<?, ?> model, Configuration configuration)
			 {
		
			String template = (String) model.get(Constants.FILE_NAME);
			String text = null;
            try {
              text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(template),
                  model);
            } catch (IOException e) {
              logger.info("Exception occures when template file not found while sending email: ", e);
            } catch (TemplateException e) {
              logger.info("Exception occures while reading the template file while sending email: ", e);
            }
			return text;
		
		
	}

	public AsyncEmailSendHelper getAsyncEmailSendHelper() {
		return asyncEmailSendHelper;
	}

	public void setAsyncEmailSendHelper(
			AsyncEmailSendHelper asyncEmailSendHelper) {
		this.asyncEmailSendHelper = asyncEmailSendHelper;
	}

	/*
	 * public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() { return
	 * threadPoolTaskExecutor; }
	 * 
	 * public void setThreadPoolTaskExecutor( ThreadPoolTaskExecutor
	 * threadPoolTaskExecutor) { this.threadPoolTaskExecutor =
	 * threadPoolTaskExecutor; }
	 */

	/**
	 * This method returns the array of email addresses which can have the
	 * current logged in user and his managers email addresses.
	 * 
	 * @return emailAddressesArr
	 */
	/*
	 * private String[] getEmailAddresses() { ArrayList<String> emailAddressList
	 * = new ArrayList<String>(); //String emailId =
	 * UserUtil.getCurrentResource().getEmailId(); String emailId=
	 * "deepti.gupta@yash.com"; String reportingManager1EmailId = ""; String
	 * reportingManager2EmailId = "";
	 *//** Add logged in resource email id */
	/*
	 * emailAddressList.add(emailId); //reportingManager1EmailId
	 * =UserUtil.getCurrentResource().getCurrentReportingManager().getEmailId();
	 * 
	 * if ( UserUtil.getCurrentResource().getCurrentReportingManager()!=null &&
	 * !
	 * (UserUtil.getCurrentResource().getCurrentReportingManager().getEmailId().
	 * equals(""))) { //reportingManager1EmailId =
	 * UserUtil.getCurrentResource().getCurrentReportingManager().getEmailId();
	 * reportingManager1EmailId="ankita.shukla@yash.com";
	 * emailAddressList.add(reportingManager1EmailId); }
	 * //reportingManager2EmailId
	 * =UserUtil.getCurrentResource().getCurrentReportingManagerTwo
	 * ().getEmailId(); if (
	 * UserUtil.getCurrentResource().getCurrentReportingManagerTwo() !=null &&
	 * !(
	 * UserUtil.getCurrentResource().getCurrentReportingManagerTwo().getEmailId
	 * ().equals(""))) { //reportingManager2EmailId =
	 * UserUtil.getCurrentResource
	 * ().getCurrentReportingManagerTwo().getEmailId(); reportingManager2EmailId
	 * = "radhika.kabra@yash.com";
	 * emailAddressList.add(reportingManager2EmailId); }
	 *//** Convert Array list to String Array */
	/*
	 * String[] emailAddressesArr = emailAddressList.toArray(new
	 * String[emailAddressList.size()]); return emailAddressesArr; }
	 */
	public void sendEmailEditProfile(Map<?, ?> model) {
		String emailText = getEmailMessage(model, configuration);
    String emailId = null;

    if (((userUtil.getLoggedInResource().getCurrentReportingManager()) != null)
    		&& !(userUtil.getLoggedInResource()
    				.getCurrentReportingManager().getEmailId()
    				.equals(""))) {
    	// setting Emails addressed according to Enviornments
    	if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
    		emailId = userUtil.getLoggedInResource()
    				.getCurrentReportingManager().getEmailId();
    	} else {
    		emailId = "rms.tool@yash.com";
    	}

    }

    if (null != emailText && !StringUtils.isEmpty(emailText)) {
    	if (null != emailId) {
    		JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
    		MimeMessage msg = message.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(msg);
    		// setting Emails addressed according to Enviornments
      		try {
            helper.setFrom(Constants.FROM_MAIL);
          } catch (MessagingException e) {
            logger.info("Exception occures while setting the sender email address  in email: ", e);
          }
    		// if enviornment is prod then do as it is... for rest of
    		// enviornments pick mailIds from property files
    		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
    			try {
            helper.setTo(emailId);
          } catch (MessagingException e) {
            logger.info("Exception occures while setting the recipient email address on sending email: ", e);
          }
    		} else {
    			try {
            helper.setTo(Constants.TO_MAIL);
          } catch (MessagingException e) {
            logger.info("Exception occures while setting the recipient email address on sending email: ", e);
          }
    		}
    		try {
          helper.setSubject((String) model.get(Constants.SUBJECT));
        } catch (MessagingException e) {
          logger.info("Exception occures while setting the subject of email address on sending email: ", e);
        }
    		try {
          helper.setText(emailText, true);
        } catch (MessagingException e) {
          logger.info("Exception occures while setting the text of email address on sending email: ", e);
        }

    		// message.send(msg);
    		// For sending email asynchronously
    		asyncEmailSendHelper.sendEmailAsynchronously(message, msg);

    	} else
        try {
          sendMessage(emailText, model, null);
        } catch (MessagingException e) {
          logger.info("Exception occures while setting the recipient email address on sending email: ", e);
        }
    }
	}

	public void sendEmailCATicket(Map<String, Object> model,
			String[] assigneeEmail, String[] ccMailID,
			List<MailConfiguration> confgs, CATicket caTicket) throws Exception {
		String[] emailIdTo = null;
		String[] emailIdCc = null;
		String[] emailIdBcc = null;
		boolean to = false;
		boolean cc = false;
		boolean bcc = false;
		Set<String> emailidToList = new HashSet<String>();
		Set<String> emailidcCList = new HashSet<String>();
		Set<String> emailidBccList = new HashSet<String>();

		List<String> emailToList = new ArrayList<String>();
		List<String> emailCcList = new ArrayList<String>();
		List<String> emailBccList = new ArrayList<String>();

		for (MailConfiguration confg : confgs) {
			if (confg.isTo()) {
				to = true;
				if (confg.getRoleId().getRole().equals("IRM")) {
					String assigneeMailId = model.get(Constants.EMAIL_ID)
							.toString();
					Resource resource = resourceService
							.findResourcesByEmailIdEquals(assigneeMailId);
					emailidToList.add(resource.getCurrentReportingManager()
							.getEmailId());
				}

				if (confg.getRoleId().getRole().equals("SRM")) {
					String assigneeMailId = model.get(Constants.EMAIL_ID)
							.toString();
					Resource resource = resourceService
							.findResourcesByEmailIdEquals(assigneeMailId);
					emailidToList.add(resource.getCurrentReportingManagerTwo()
							.getEmailId());
				}

				if (confg.getRoleId().getRole().equals("User")) {
					if (model.keySet().contains(Constants.EMAIL_ID)) {
						emailidToList.add((String) model
								.get(Constants.EMAIL_ID));
						emailidToList.add(ccMailID[0]);
					}
				}

				if (confg.getRoleId().getRole().equals("ProjectManager")) {
					int projectId = caTicket.getModuleId().getId();
					Project project = projectService.findProject(projectId);
					emailidToList.add(project.getOffshoreDelMgr().getEmailId());
				}

				if (confg.getRoleId().getRole().equals("ADMIN")
						|| confg.getRoleId().getRole().equals("BG_ADMIN")) {
					int projectId = caTicket.getModuleId().getId();
					Project project = projectService.findProject(projectId);

					int buId = project.getOrgHierarchy().getId();
					List bu = new ArrayList<Integer>();
					bu.add(buId);
					List<String> list = resourceService.getEmailIds(confg
							.getRoleId().getRole(), bu);
					if (list != null && list.size() > 0)
						emailidToList.addAll(list);

				}

				if (confg.getRoleId().getRole().equals("ROLE_MANAGER")
						|| confg.getRoleId().getRole().equals("DEL_MANAGER")) {

					int projectId = caTicket.getModuleId().getId();
					Project project = projectService.findProject(projectId);

					int buId = project.getOrgHierarchy().getId();
					List bu = new ArrayList<Integer>();
					bu.add(buId);
					List<String> list = resourceService.getEmailIds(confg
							.getRoleId().getRole(), bu);
					if (list != null && list.size() > 0)
						emailidToList.addAll(list);
				}

			}

			if (confg.isCc()) {
				cc = true;
				if (confg.getRoleId().getRole().equals("IRM")) {
					String assigneeMailId = model.get(Constants.EMAIL_ID)
							.toString();
					Resource resource = resourceService
							.findResourcesByEmailIdEquals(assigneeMailId);
					emailidcCList.add(resource.getCurrentReportingManager()
							.getEmailId());
				}
				if (confg.getRoleId().getRole().equals("SRM")) {
					String assigneeMailId = model.get(Constants.EMAIL_ID)
							.toString();
					Resource resource = resourceService
							.findResourcesByEmailIdEquals(assigneeMailId);
					emailidcCList.add(resource.getCurrentReportingManagerTwo()
							.getEmailId());
				}

				if (confg.getRoleId().getRole().equals("User")) {
					if (model.keySet().contains(Constants.EMAIL_ID)) {
						// emailIdTo[0] = (String)
						// model.get(Constants.EMAIL_ID);
						emailidcCList.add((String) model
								.get(Constants.EMAIL_ID));
						emailidcCList.add(ccMailID[0]);
					}
				}

				if (confg.getRoleId().getRole().equals("ProjectManager")) {

					int projectId = caTicket.getModuleId().getId();
					Project project = projectService.findProject(projectId);
					emailidcCList.add(project.getOffshoreDelMgr().getEmailId());
				}

				if (confg.getRoleId().getRole().equals("ADMIN")
						|| confg.getRoleId().getRole().equals("BG_ADMIN")) {

					int projectId = caTicket.getModuleId().getId();
					Project project = projectService.findProject(projectId);

					int buId = project.getOrgHierarchy().getId();
					List bu = new ArrayList<Integer>();
					bu.add(buId);
					List<String> list = resourceService.getEmailIds(confg
							.getRoleId().getRole(), bu);
					if (list != null && list.size() > 0)
						emailidcCList.addAll(list);
				}

				/*
				 * if(confg.getRoleId().getRole().equals("ROLE_MANAGER") ||
				 * confg.getRoleId().getRole().equals("DEL_MANAGER") ) {
				 * if(confg.getRoleId().getRole().equals("DEL_MANAGER")){ if
				 * (model.keySet().contains(Constants.MANAGER_IRM_EMAIL_ID)) {
				 * if( !
				 * (model.get(Constants.MANAGER_IRM_EMAIL_ID).toString().equals
				 * (""))){ emailidcCList.add((String)
				 * model.get(Constants.MANAGER_IRM_EMAIL_ID)); } }
				 * 
				 * if (model.keySet().contains(Constants.MANAGER_SRM_EMAIL_ID))
				 * { if( !
				 * (model.get(Constants.MANAGER_SRM_EMAIL_ID).toString().
				 * equals(""))){ emailidcCList.add((String)
				 * model.get(Constants.MANAGER_SRM_EMAIL_ID)); } } } if
				 * (model.containsKey(Constants.CC_EMAIL_ID)) {
				 * emailidcCList.add((String) model.get(Constants.CC_EMAIL_ID));
				 * }
				 * 
				 * }
				 */
			}

			if (confg.isBcc()) {
				bcc = true;
				if (confg.getRoleId().getRole().equals("IRM")) {
					String assigneeMailId = model.get(Constants.EMAIL_ID)
							.toString();
					Resource resource = resourceService
							.findResourcesByEmailIdEquals(assigneeMailId);
					emailidBccList.add(resource.getCurrentReportingManager()
							.getEmailId());
				}
				if (confg.getRoleId().getRole().equals("SRM")) {
					String assigneeMailId = model.get(Constants.EMAIL_ID)
							.toString();
					Resource resource = resourceService
							.findResourcesByEmailIdEquals(assigneeMailId);
					emailidBccList.add(resource.getCurrentReportingManagerTwo()
							.getEmailId());
				}
				if (confg.getRoleId().getRole().equals("User")) {
					if (model.keySet().contains(Constants.EMAIL_ID)) {
						emailidBccList.add((String) model
								.get(Constants.EMAIL_ID));
						emailidBccList.add(ccMailID[0]);
					}
				}
				if (confg.getRoleId().getRole().equals("ProjectManager")) {
					int projectId = caTicket.getModuleId().getId();
					Project project = projectService.findProject(projectId);
					emailidBccList
							.add(project.getOffshoreDelMgr().getEmailId());
				}

				if (confg.getRoleId().getRole().equals("ADMIN")
						|| confg.getRoleId().getRole().equals("BG_ADMIN")) {
					int projectId = caTicket.getModuleId().getId();
					Project project = projectService.findProject(projectId);
					int buId = project.getOrgHierarchy().getId();
					List bu = new ArrayList<Integer>();
					bu.add(buId);
					List<String> list = resourceService.getEmailIds(confg
							.getRoleId().getRole(), bu);
					if (list != null && list.size() > 0)
						emailidBccList.addAll(list);

				}

				/*
				 * if(confg.getRoleId().getRole().equals("ROLE_MANAGER")||
				 * confg.getRoleId().getRole().equals("DEL_MANAGER")) {
				 * if(confg.getRoleId().getRole().equals("DEL_MANAGER")){ if
				 * (model.containsKey(Constants.MANAGER_IRM_EMAIL_ID)) { if( !
				 * (model
				 * .get(Constants.MANAGER_IRM_EMAIL_ID).toString().equals(""))){
				 * emailidBccList.add((String)
				 * model.get(Constants.MANAGER_IRM_EMAIL_ID)); } } if
				 * (model.containsKey(Constants.MANAGER_SRM_EMAIL_ID)) { if( !
				 * (model
				 * .get(Constants.MANAGER_SRM_EMAIL_ID).toString().equals(""))){
				 * emailidBccList.add((String)
				 * model.get(Constants.MANAGER_SRM_EMAIL_ID)); } } } if
				 * (model.containsKey(Constants.CC_EMAIL_ID)) {
				 * emailidBccList.add((String)
				 * model.get(Constants.CC_EMAIL_ID)); }
				 * 
				 * }
				 */
			}
		}

		emailToList.addAll(emailidToList);
		emailCcList.addAll(emailidcCList);
		emailBccList.addAll(emailidBccList);

		emailIdTo = new String[emailidToList.size()];
		emailIdCc = new String[emailidcCList.size()];
		emailIdBcc = new String[emailidBccList.size()];

		for (int i = 0; i < emailidToList.size(); i++) {
			emailIdTo[i] = emailToList.get(i).toString();

		}
		for (int j = 0; j < emailidcCList.size(); j++) {
			emailIdCc[j] = emailCcList.get(j).toString();

		}
		for (int k = 0; k < emailidBccList.size(); k++) {
			emailIdBcc[k] = emailBccList.get(k).toString();

		}
		System.out.println("emailIdTo-----" + emailIdTo);
		System.out.println("emailIdCC-----" + emailIdCc);
		System.out.println("emailIdBCC-----" + emailIdBcc);

		String emailText = getEmailMessage(model, configuration);
    if ((emailText != null)) {

    	if (null != emailIdTo || null != emailIdCc
    			|| null != emailIdBcc) {
    		JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
    		MimeMessage msg = message.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(msg);
    		// setting Emails addressed according to Enviornments
    		helper.setFrom(Constants.FROM_MAIL);
    		// if enviornment is prod then do as it is... for rest of
    		// enviornments pick mailIds from property files
    		if (to) {
    			if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
    				helper.setTo(emailIdTo);
    			} else {
    				helper.setTo(Constants.TO_MAIL);
    			}
    		}
    		if (cc) {
    			if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
    				helper.setCc(emailIdCc);
    			} else
    				helper.setCc(Constants.CC_MAIL);
    		}
    		if (bcc) {
    			if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
    				helper.setBcc(emailIdBcc);
    			} else
    				helper.setBcc(Constants.BCC_MAIL);
    		}
    		helper.setSubject((String) model.get(Constants.SUBJECT));
    		helper.setText(emailText, true);

    		asyncEmailSendHelper.sendEmailAsynchronously(message, msg);

    	} else
    		sendMessage(emailText, model, null);
    }
	}

	public void sendEmail(Map<String, Object> model, String[] tomailIDsList, String[] sentMailCCTo,
			MultipartFile[] resumeFiles) {
		String emailText = getEmailMessage(model, configuration);
	    if ((emailText != null) && (!StringUtils.isEmpty(emailText))) {
	      try {
	        sendRequestMessage(emailText, model, tomailIDsList, sentMailCCTo,resumeFiles);
	      } catch (MessagingException e) {
	        logger.info("Exception occures on sending email: ", e);
	      }
	    }
		
	}
	
	public void sendEmailForInterview(Map<String, Object> model, String[] tomailIDsList, String[] sentMailCCTo) {
		String emailText = getEmailMessage(model, configuration);
		System.out.println(emailText);
	    if ((emailText != null) && (!StringUtils.isEmpty(emailText))) {
	      try {
	        sendInterviewMessage(emailText, model, tomailIDsList, sentMailCCTo);
	      } catch (MessagingException e) {
	        logger.info("Exception occures on sending email: ", e);
	      }
	    }
		
	}
	
private void sendInterviewMessage(String emailText, Map<String, Object> model, String[] tomailIDsList,
			String[] sentMailCCTo) throws MessagingException {

	JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
	int sizeOfMailsArray = tomailIDsList.length + sentMailCCTo.length ; 
	String[] emails = new String[sizeOfMailsArray];
	for (int i = 0; i < sentMailCCTo.length; i++) {
			emails[i] = sentMailCCTo[i];
	}
	for (int i = 0; i < tomailIDsList.length; i++) {
		emails[i] = tomailIDsList[i];
	}
	


	MimeMessage msg = message.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(msg);
		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
			helper.setCc(sentMailCCTo);
		} else {
			helper.setCc(Constants.CC_MAIL);
		}

		helper.setFrom(Constants.FROM_MAIL);
	// if environment is prod then do as it is... for rest of environments
	// pick mailIds from property files
	if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
		helper.setTo(tomailIDsList);
	} else {
		helper.setTo(Constants.TO_MAIL);
		// helper.setTo( emails[0]);
	}
		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
			helper.setCc(sentMailCCTo);
		} else {
			helper.setCc(Constants.CC_MAIL);
		}

	helper.setSubject((String) model.get(Constants.SUBJECT));
	helper.setText(emailText, true);
	// message.send(msg);
	// For sending email asynchronously
	asyncEmailSendHelper.sendEmailAsynchronously(message, msg);

		
	}

	//this is different from the other method(?) : Samiksha 
	private void sendRequestMessage(String emailText, Map<String, Object> model, String[] tomailIDs,
			String[] ccMailIDs, MultipartFile[] resumeFiles) throws MessagingException {
		JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
		MimeMessage msg = message.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, 2);
		String sendMailFrom = (String) model.get("sendMailFrom");
		helper.setFrom(sendMailFrom);
		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
			if (ccMailIDs!=null && ccMailIDs.length>0 && !(ccMailIDs[0].equalsIgnoreCase("no"))) {
				helper.setCc(ccMailIDs);
			}
		} else {
			helper.setCc(Constants.CC_MAIL); 
		}
		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
			helper.setTo(tomailIDs);
		} else {
			helper.setTo(Constants.TO_MAIL); 
		}

		helper.setSubject((String) model.get(Constants.SUBJECT));
		helper.setText(emailText, true);
		//new code for attachment
		if(resumeFiles!=null) {
		try {
			 for (MultipartFile file : resumeFiles) {
				 helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(IOUtils.toByteArray(new ByteArrayInputStream(file.getBytes()))));
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		}
		//end
		asyncEmailSendHelper.sendEmailAsynchronously(message, msg);
		
	}
	
	/**
	 * Send Email to RMGNonSAP with attached Report in xsl format having Non-billable blocked Resource list
	 * Who is going to be on bench next day when Email will be sent. 
	 * 
	 * @author rahul.shah
	 * @param emailText
	 * @param model
	 * @param tomailIDs
	 * @param ccMailIDs
	 * @param tempFile
	 * @throws MessagingException
	 */
	
	@SuppressWarnings("null")
	public void sendEmailforBlockedResource(Map<?, ?> model, String[] tomailIDs, String[] ccMailIDs, String[] bccMailIDs, File tempFile) throws MessagingException {
		JavaMailSenderImpl message = (JavaMailSenderImpl) mailSender;
		MimeMessage msg = message.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, 2);

		String sendMailFrom = (String) model.get(Constants.SENDEMAILID);
		
		if(sendMailFrom == null)
			throw new MessagingException("From Address Must not be Null");
		helper.setFrom(sendMailFrom);
		
		helper.setSubject((String) model.get(Constants.SUBJECT));
		helper.setText((String) model.get(Constants.EMAIL_TEXT), false);
		
		if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
			if(tomailIDs == null || tomailIDs.length < 1)
				throw new MessagingException("To Address Must not be Null");
			helper.setTo(tomailIDs);
			
			if(ccMailIDs != null || ccMailIDs.length > 0)		
				helper.setCc(ccMailIDs);
			
			if(bccMailIDs != null || bccMailIDs.length > 0)		
				helper.setBcc(bccMailIDs);
		}
		else{
			helper.setTo(Constants.TO_MAIL); 
			helper.setCc(Constants.CC_MAIL);
			helper.setBcc(Constants.BCC_MAIL);
		}
		
		
		//new code for attachment
		if(tempFile != null && tempFile.exists()) {
			try {
				helper.addAttachment(tempFile.getName(),new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(tempFile))));
			} catch (FileNotFoundException e) {
				logger.error("FileNotFoundException - Email File Attachement failed ", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("IOException - Email File Attachement failed ", e.getMessage());
				e.printStackTrace();
			}
		}
		
		asyncEmailSendHelper.sendEmailAsynchronously(message, msg);
	}
	
	
}
