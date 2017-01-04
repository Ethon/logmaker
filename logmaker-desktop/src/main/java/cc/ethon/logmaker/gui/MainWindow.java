package cc.ethon.logmaker.gui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.WendlerFormula;
import cc.ethon.logmaker.gui.readermodel.LogReaderModel;
import cc.ethon.logmaker.gui.readermodel.RedyGymLogCsvReaderModel;
import cc.ethon.logmaker.gui.readermodel.RedyGymLogDbReaderModel;
import cc.ethon.logmaker.reader.LogReader;

public class MainWindow extends VBox {

	private final Stage stage;
	private final MainWindowModel model;

	private ComboBox<LogReaderModel> selectedLogReaderComboBox;
	private ComboBox<String> selectedTemplateComboBox;

	private void createReaderSelection() {
		selectedLogReaderComboBox = new ComboBox<LogReaderModel>();
		selectedLogReaderComboBox.setItems(FXCollections.observableArrayList(new RedyGymLogCsvReaderModel(), new RedyGymLogDbReaderModel()));
		selectedLogReaderComboBox.getSelectionModel().select(model.getSelectedLogReader().get());
		model.getSelectedLogReader().bind(selectedLogReaderComboBox.getSelectionModel().selectedIndexProperty());
		getChildren().add(selectedLogReaderComboBox);
	}

	private void createExportFileChooserRow() {
		final Label label = new Label("Chosen export file:");
		HBox.setMargin(label, new Insets(10));

		final TextField textField = new TextField();
		textField.textProperty().bind(model.getExportFile());
		HBox.setHgrow(textField, Priority.ALWAYS);
		HBox.setMargin(textField, new Insets(10));

		final Button button = new Button("Choose file");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Export File");
				final File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					model.getExportFile().setValue(file.getAbsolutePath());
				}
			}
		});
		HBox.setMargin(button, new Insets(10));

		final HBox hbox = new HBox();
		hbox.getChildren().addAll(label, textField, button);
		getChildren().add(hbox);
	}

	private void createOptionsRow() {
		final CheckBox deleteExportFile = new CheckBox("Delete export file after generation");
		deleteExportFile.setSelected(model.getDeleteExportFile().get());
		model.getDeleteExportFile().bind(deleteExportFile.selectedProperty());
		HBox.setMargin(deleteExportFile, new Insets(10));

		final CheckBox closeApplication = new CheckBox("Close logmaker after generation");
		closeApplication.setSelected(model.getCloseApplication().get());
		model.getCloseApplication().bind(closeApplication.selectedProperty());
		HBox.setMargin(closeApplication, new Insets(10));

		final HBox hbox = new HBox();
		hbox.getChildren().addAll(deleteExportFile, closeApplication);
		getChildren().add(hbox);
	}

	private void createTemplateFileSelection() throws IOException {
		final List<String> availableTemplates = model.getGenerator().getAvailableTemplates();
		final String previouslySelectedTemplate = model.getSelectedTemplate().get();

		selectedTemplateComboBox = new ComboBox<String>();
		selectedTemplateComboBox.setItems(FXCollections.observableArrayList(availableTemplates));

		if (previouslySelectedTemplate != null && availableTemplates.contains(previouslySelectedTemplate)) {
			selectedTemplateComboBox.getSelectionModel().select(previouslySelectedTemplate);
		} else if (!availableTemplates.isEmpty()) {
			selectedTemplateComboBox.getSelectionModel().select(0);
		}

		model.getSelectedTemplate().bind(selectedTemplateComboBox.getSelectionModel().selectedItemProperty());
		getChildren().add(selectedTemplateComboBox);
	}

	private void createGenerateButton() {
		final Button generate = new Button("Generate and copy last workout to clipboard");
		generate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					final File exportFile = new File(model.getExportFile().get());
					final LogReader reader = selectedLogReaderComboBox.getSelectionModel().getSelectedItem().createReader();
					final WorkoutLog log = reader.readLog(exportFile);
					model.getGenerator().selectTemplate(selectedTemplateComboBox.getSelectionModel().getSelectedItem());
					model.getGenerator().genLastWorkoutToClipboard(log, new WendlerFormula());

					if (model.getDeleteExportFile().get()) {
						exportFile.delete();
					}

					if (model.getCloseApplication().get()) {
						Platform.exit();
					}
				} catch (final Exception e) {
					e.printStackTrace();
					final Alert alert = new Alert(AlertType.ERROR, e.toString(), ButtonType.OK);
					alert.show();
				}
			}
		});
		getChildren().add(generate);
	}

	public MainWindow(Stage stage, MainWindowModel model) throws Exception {
		this.stage = stage;
		this.model = model;

		createReaderSelection();
		createExportFileChooserRow();
		createOptionsRow();
		createTemplateFileSelection();
		createGenerateButton();
	}

}
