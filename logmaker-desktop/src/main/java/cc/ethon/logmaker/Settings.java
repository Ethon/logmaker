package cc.ethon.logmaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {

	private static final String TEMPLATES_DEFAULT_DIRECTORY = "templates/";
	private static final String SETTINGS_FILENAME = "logmaker-settings.xml";

	private static Settings instance;

	private Properties properties;

	private void loadFromFile() throws IOException {
		properties = new Properties();
		final File file = new File(SETTINGS_FILENAME);
		if (file.isFile()) {
			try (InputStream in = new FileInputStream(file)) {
				properties.loadFromXML(in);
			}
		}
	}

	private void saveToFile() throws IOException {
		try (OutputStream out = new FileOutputStream(SETTINGS_FILENAME)) {
			properties.storeToXML(out, "");
		}
	}

	private Settings() throws IOException {
		loadFromFile();
	}

	public static Settings getInstance() throws IOException {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	public void save() throws IOException {
		saveToFile();
	}

	public String getString(String key) {
		return properties.getProperty(key);
	}

	public void setString(String key, String value) {
		properties.setProperty(key, value);
	}

	public boolean getBoolean(String key) {
		return "true".equals(properties.getProperty(key));
	}

	public void setBoolean(String key, boolean value) {
		properties.setProperty(key, Boolean.toString(value));
	}

	public int getInt(String key, int defaultValue) {
		return properties.getProperty(key) != null ? Integer.valueOf(properties.getProperty(key)) : defaultValue;
	}

	public void setInt(String key, int value) {
		properties.setProperty(key, Integer.toString(value));
	}

	public File getTemplatesDir() {
		return new File(TEMPLATES_DEFAULT_DIRECTORY);
	}

}
