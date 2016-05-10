package com.rip.roomies.controllers;

import com.rip.roomies.events.login.LoginListener;
import com.rip.roomies.events.login.PassRetrieveListener;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * The controller to handle login related events.
 */
public class LoginController {
	private static final Logger log = Logger.getLogger(LoginController.class.getName());

	private static LoginController controller;

	/**
	 * Gets the singleton login controller.
	 * @return The login controller
	 */
	public static LoginController getController() {
		if (controller == null) {
			controller = new LoginController();
		}

		return controller;
	}

	/**
	 * Attempts to login the user with the specified credentials.
	 * @param listener The listener to post results to
	 * @param username The username to check
	 * @param passwd The password to check
	 */
	public void login(final LoginListener listener, final String username, final String passwd) {
		// Create and run a new thread
		new Thread() {
			@Override
			public void run() {
				log.info(String.format(Locale.US, InfoStrings.LOGIN_CONTROLLER, username));

				// Create request user and get response from login()
				User request = new User(username, passwd);
				User response = request.login();

				// If fail, call fail callback. Otherwise, call success callback
				if (response == null) {
					listener.loginFail();
				}
				else {
					listener.loginSuccess(response);
				}
			}
		}.start();
	}

	/**
	 * Logs off the user by calling User's static logoff().
	 */
	public void logoff() {
		User.logoff();
	}

	/**
	 * Attempts to retrieve the password of a registered user using the specified email.
	 * @param listener The listener to post results to
	 * @param email The email to attempt to password recover
	 */
	public void passRetrieve(final PassRetrieveListener listener, final String email) {
		// Create and run a new thread
		new Thread() {
			@Override
			public void run() {
				// Create request user
				User request = new User(0, null, email);

				log.info(String.format(Locale.US, InfoStrings.PASSRETRIEVE_CONTROLLER, email));

				// If fail, call fail callback. Otherwise, call success callback
				if (!request.passRetrieve()) {
					listener.passRetrieveFail();
				}
				else {
					listener.passRetrieveSuccess();
				}
			}
		}.start();
	}
}
