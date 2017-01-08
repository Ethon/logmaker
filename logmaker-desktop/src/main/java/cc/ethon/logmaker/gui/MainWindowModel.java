package cc.ethon.logmaker.gui;

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

	private final BooleanProperty closeApplication;
	private final IntegerProperty selectedLogReader;
	private final TemplateGenerator generator;
	private final StringProperty selectedTemplate;

	public MainWindowModel(Settings settings) throws Exception {

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

		selectedTemplate = new SimpleStringProperty();
		if (settings.getSelectedTemplate() != null) {
			selectedTemplate.setValue(settings.getSelectedTemplate());
		}
		selectedTemplate.addListener((obs, old, new_) -> settings.setSelectedTemplate(new_));
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

	public StringProperty getSelectedTemplate() {
		return selectedTemplate;
	}

}
