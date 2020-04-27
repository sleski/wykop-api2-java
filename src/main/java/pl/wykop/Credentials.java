package pl.wykop;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Slawomir Leski on 27-04-2020.
 */
public enum Credentials {

	WYKOP;

	final Properties properties;

	final String DEFAULT_VALUE = "NOT_SET";

	Credentials() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("wykop.properties"));
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		validate();
	}

	private void validate() {
		if (DEFAULT_VALUE.equals(login())
			|| DEFAULT_VALUE.equals(accountkey())
			|| DEFAULT_VALUE.equals(appkey())
			|| DEFAULT_VALUE.equals(secretkey())) {
			throw new RuntimeException("One or more properties needed to login did not set correctly!");
		}
	}

	public String login() {
		return properties.getProperty("login");
	}

	public String accountkey() {
		return properties.getProperty("accountkey");
	}

	public String appkey() {
		return properties.getProperty("appkey");
	}

	public String secretkey() {
		return properties.getProperty("secretkey");
	}
}
