package cc.ethon.logmaker.gui.gen.template;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import cc.ethon.logmaker.Settings;

public class TemplateGeneratorModel {

	private static final String KEY_SELECTEDTEMPLATE = TemplateGeneratorModel.class.getSimpleName() + ".selectedTemplate";

	private final StringProperty selectedTemplate;
	private final ObservableList<String> availableTemplates;

	public TemplateGeneratorModel(Settings settings) {
		selectedTemplate = new SimpleStringProperty();
		if (settings.getString(KEY_SELECTEDTEMPLATE) != null) {
			selectedTemplate.set(settings.getString(KEY_SELECTEDTEMPLATE));
		}
		selectedTemplate.addListener((obs, o, n) -> settings.setString(KEY_SELECTEDTEMPLATE, n));

		availableTemplates = FXCollections.observableArrayList();
	}

	public StringProperty getSelectedTemplate() {
		return selectedTemplate;
	}

	public ObservableList<String> getAvailableTemplates() {
		return availableTemplates;
	}

}
