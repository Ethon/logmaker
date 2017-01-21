package cc.ethon.logmaker.gui.reader.redy.csv;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RedyGymLogCsvReaderView extends VBox {

	private final Stage stage;
	private final RedyGymLogCsvReaderModel model;

	private void createFilePickerRow() {
		final Label label = new Label("Chosen CSV export:");
		HBox.setMargin(label, new Insets(0, 10, 10, 0));

		final TextField textField = new TextField();
		textField.textProperty().bind(model.getFile());
		HBox.setHgrow(textField, Priority.ALWAYS);
		HBox.setMargin(textField, new Insets(0, 10, 10, 0));

		final Button button = new Button("Choose export");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select Export File");
				final File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					model.getFile().setValue(file.getAbsolutePath());
				}
			}
		});
		HBox.setMargin(button, new Insets(0, 0, 10, 0));

		final HBox hbox = new HBox();
		hbox.getChildren().addAll(label, textField, button);
		setMargin(hbox, new Insets(10));
		getChildren().add(hbox);
	}

	private void createDeleteExportFileRow() {
		final CheckBox deleteExportFile = new CheckBox("Delete export after generation");
		deleteExportFile.setSelected(model.getDeleteFile().get());
		model.getDeleteFile().bind(deleteExportFile.selectedProperty());
		setMargin(deleteExportFile, new Insets(0, 10, 10, 10));
		getChildren().add(deleteExportFile);
	}

	public RedyGymLogCsvReaderView(Stage stage, RedyGymLogCsvReaderModel model) {
		this.stage = stage;
		this.model = model;

		createFilePickerRow();
		createDeleteExportFileRow();
	}

}
