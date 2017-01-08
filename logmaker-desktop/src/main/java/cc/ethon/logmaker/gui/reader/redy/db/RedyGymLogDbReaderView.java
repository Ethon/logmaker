package cc.ethon.logmaker.gui.reader.redy.db;

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

public class RedyGymLogDbReaderView extends VBox {

	private final Stage stage;
	private final RedyGymLogDbReaderModel model;

	private void createFilePickerRow() {
		final Label label = new Label("Chosen backup:");
		HBox.setMargin(label, new Insets(0, 10, 10, 0));

		final TextField textField = new TextField();
		textField.textProperty().bind(model.getBackupFile());
		HBox.setHgrow(textField, Priority.ALWAYS);
		HBox.setMargin(textField, new Insets(0, 10, 10, 0));

		final Button button = new Button("Choose backup");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select Backup File");
				final File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					model.getBackupFile().setValue(file.getAbsolutePath());
				}
			}
		});
		HBox.setMargin(button, new Insets(0, 0, 10, 0));

		final HBox hbox = new HBox();
		hbox.getChildren().addAll(label, textField, button);
		setMargin(hbox, new Insets(10));
		getChildren().add(hbox);
	}

	private void createDeleteBackupFileRow() {
		final CheckBox deleteBackupFile = new CheckBox("Delete backup after generation");
		deleteBackupFile.setSelected(model.getDeleteBackupFile().get());
		model.getDeleteBackupFile().bind(deleteBackupFile.selectedProperty());
		setMargin(deleteBackupFile, new Insets(0, 10, 10, 10));
		getChildren().add(deleteBackupFile);
	}

	public RedyGymLogDbReaderView(Stage stage, RedyGymLogDbReaderModel model) {
		this.stage = stage;
		this.model = model;

		createFilePickerRow();
		createDeleteBackupFileRow();
	}

}
