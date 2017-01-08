package cc.ethon.logmaker.gui.gen.template;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TemplateGeneratorView extends HBox {

	private final TemplateGeneratorModel model;

	private void createTemplateSelection() {
		final Label label = new Label("Chosen template:");
		HBox.setMargin(label, new Insets(0, 10, 0, 0));

		final ComboBox<String> selectedTemplateComboBox = new ComboBox<String>();
		selectedTemplateComboBox.setItems(model.getAvailableTemplates());

		final String previouslySelectedTemplate = model.getSelectedTemplate().get();
		if (previouslySelectedTemplate != null && model.getAvailableTemplates().contains(previouslySelectedTemplate)) {
			selectedTemplateComboBox.getSelectionModel().select(previouslySelectedTemplate);
		} else if (!model.getAvailableTemplates().isEmpty()) {
			selectedTemplateComboBox.getSelectionModel().select(0);
		}

		model.getSelectedTemplate().bind(selectedTemplateComboBox.getSelectionModel().selectedItemProperty());
		getChildren().add(selectedTemplateComboBox);

		final HBox hbox = new HBox(label, selectedTemplateComboBox);
		setMargin(hbox, new Insets(10));
		getChildren().add(hbox);
	}

	public TemplateGeneratorView(TemplateGeneratorModel model) {
		this.model = model;
		createTemplateSelection();
	}

}
