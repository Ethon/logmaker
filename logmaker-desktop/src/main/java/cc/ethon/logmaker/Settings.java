package cc.ethon.logmaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {

	private static final String KEY_SELECTEDTEMPLATE = "selectedTemplate";
	private static final String KEY_SELECTEDLOGREADER = "selectedLogReader";
	private static final String KEY_CLOSEAPPLICATION = "closeApplication";

	private static final String TEMPLATES_DEFAULT_DIRECTORY = "templates/";
	private static final String SETTINGS_FILENAME = "logmaker-settings.xml";

	private static Settings instance;

	private Properties properties;
	private Boolean closeApplication;
	private Integer selectedLogReader;
	private String selectedTemplate;

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

	public String getSettingByKey(String key) {
		return properties.getProperty(key);
	}

	public void setSettingByKey(String key, String value) {
		properties.setProperty(key, value);
	}

	public Boolean getCloseApplication() {
		if (closeApplication == null) {
			closeApplication = "true".equals(properties.getProperty(KEY_CLOSEAPPLICATION));
			if (!properties.contains(KEY_CLOSEAPPLICATION)) {
				properties.setProperty(KEY_CLOSEAPPLICATION, closeApplication.toString());
			}
		}
		return closeApplication;
	}

	public void setCloseApplication(Boolean closeApplication) {
		this.closeApplication = closeApplication;
		properties.setProperty(KEY_CLOSEAPPLICATION, closeApplication.toString());
	}

	public Integer getSelectedLogReader() {
		if (selectedLogReader == null) {
			final String value = properties.getProperty(KEY_SELECTEDLOGREADER);
			selectedLogReader = value != null ? Integer.parseInt(value) : 0;
			if (!properties.contains(KEY_SELECTEDLOGREADER)) {
				properties.setProperty(KEY_SELECTEDLOGREADER, String.valueOf(selectedLogReader));
			}
		}
		return selectedLogReader;
	}

	public void setSelectedLogReader(Integer selectedLogReader) {
		this.selectedLogReader = selectedLogReader;
		properties.setProperty(KEY_SELECTEDLOGREADER, String.valueOf(selectedLogReader));
	}

	public File getTemplatesDir() {
		return new File(TEMPLATES_DEFAULT_DIRECTORY);
	}

	public String getSelectedTemplate() {
		if (selectedTemplate == null) {
			selectedTemplate = properties.getProperty(KEY_SELECTEDTEMPLATE);
		}
		return selectedTemplate;
	}

	public void setSelectedTemplate(String selectedTemplate) {
		this.selectedTemplate = selectedTemplate;
		properties.setProperty(KEY_SELECTEDTEMPLATE, selectedTemplate);
	}

}
