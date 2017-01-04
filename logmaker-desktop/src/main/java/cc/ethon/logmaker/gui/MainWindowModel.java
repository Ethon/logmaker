package cc.ethon.logmaker.gui;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gen.TemplateGenerator;

public class MainWindowModel {

	private final StringProperty exportFile;
	private final BooleanProperty deleteExportFile;
	private final BooleanProperty closeApplication;
	private final IntegerProperty selectedLogReader;
	private final TemplateGenerator generator;

	public MainWindowModel(Settings settings) throws Exception {

		exportFile = new SimpleStringProperty();
		if (settings.getExportFile() != null) {
			exportFile.setValue(settings.getExportFile().getAbsolutePath());
		}
		exportFile.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				final File file = new File(newValue);
				settings.setExportFile(file.getAbsoluteFile());
			}
		});

		deleteExportFile = new SimpleBooleanProperty(settings.getDeleteExportFile());
		deleteExportFile.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				settings.setDeleteExportFile(newValue);
			}
		});

		closeApplication = new SimpleBooleanProperty(settings.getCloseApplication());
		closeApplication.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				settings.setCloseApplication(newValue);
			}
		});

		selectedLogReader = new SimpleIntegerProperty(settings.getSelectedLogReader());
		selectedLogReader.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				settings.setSelectedLogReader(newValue.intValue());
			}
		});

		generator = new TemplateGenerator(settings.getTemplatesDir());
	}

	public StringProperty getExportFile() {
		return exportFile;
	}

	public BooleanProperty getDeleteExportFile() {
		return deleteExportFile;
	}

	public BooleanProperty getCloseApplication() {
		return closeApplication;
	}

	public IntegerProperty getSelectedLogReader() {
		return selectedLogReader;
	}

	public TemplateGenerator getGenerator() {
		return generator;
	}

}
