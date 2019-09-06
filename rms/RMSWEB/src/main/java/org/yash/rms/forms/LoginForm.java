/**
 * 
 */
package org.yash.rms.forms;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author purvesh.maheshwari
 * 
 */
public class LoginForm implements Validator {
	private String userName;
	private String password;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean supports(Class<?> clazz) {
		return LoginForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		LoginForm loginForm = (LoginForm) target;

		if (loginForm.getUserName() == null
				|| loginForm.getUserName().equalsIgnoreCase("")) {
			errors.rejectValue("userName", "security_login_form_name_message");
		}

		if (loginForm.getPassword() == null
				|| loginForm.getPassword().equalsIgnoreCase("")) {
			errors.rejectValue("password",
					"security_login_form_password_message");
		}
	}
}
