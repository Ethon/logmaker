package cc.ethon.logmaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {

	private static final String KEY_SELECTEDLOGREADER = "selectedLogReader";
	private static final String KEY_CLOSEAPPLICATION = "closeApplication";
	private static final String KEY_DELETEEXPORTFILE = "deleteExportFile";
	private static final String KEY_EXPORTFILE = "exportFile";

	private static final String TEMPLATES_DEFAULT_DIRECTORY = "templates/";
	private static final String SETTINGS_FILENAME = "logmaker-settings.xml";

	private static Settings instance;

	private Properties properties;
	private File exportFile;
	private Boolean deleteExportFile;
	private Boolean closeApplication;
	private Integer selectedLogReader;

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

	public File getExportFile() {
		if (exportFile == null) {
			final String filename = properties.getProperty(KEY_EXPORTFILE);
			if (filename != null) {
				exportFile = new File(filename);
			}
		}
		return exportFile;
	}

	public void setExportFile(File exportFile) {
		this.exportFile = exportFile;
		properties.setProperty(KEY_EXPORTFILE, exportFile.getAbsolutePath());
	}

	public Boolean getDeleteExportFile() {
		if (deleteExportFile == null) {
			deleteExportFile = "true".equals(properties.getProperty(KEY_DELETEEXPORTFILE));
			if (!properties.contains(KEY_DELETEEXPORTFILE)) {
				properties.setProperty(KEY_DELETEEXPORTFILE, deleteExportFile.toString());
			}
		}
		return deleteExportFile;
	}

	public void setDeleteExportFile(Boolean deleteExportFile) {
		this.deleteExportFile = deleteExportFile;
		properties.setProperty(KEY_DELETEEXPORTFILE, deleteExportFile.toString());
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

}
