package cc.ethon.logmaker.gui.reader;

import java.io.File;
import java.util.Map;

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

public abstract class SingleFileReaderView extends VBox {

	protected static final int CHOSEN_FILE = 1;
	protected static final int CHOOSE_FILE = 2;
	protected static final int SELECT_FILE = 3;
	protected static final int DELETE_FILE = 4;

	private final Stage stage;
	private final SingleFileReaderModel model;
	private final Map<Integer, String> strings;

	protected abstract Map<Integer, String> createStrings();

	private void createFilePickerRow() {
		final Label label = new Label(strings.get(CHOSEN_FILE));
		HBox.setMargin(label, new Insets(0, 10, 10, 0));

		final TextField textField = new TextField();
		textField.textProperty().bind(model.getFile());
		HBox.setHgrow(textField, Priority.ALWAYS);
		HBox.setMargin(textField, new Insets(0, 10, 10, 0));

		final Button button = new Button(strings.get(CHOOSE_FILE));
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle(strings.get(SELECT_FILE));
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

	private void createDeleteBackupFileRow() {
		final CheckBox deleteBackupFile = new CheckBox(strings.get(DELETE_FILE));
		deleteBackupFile.setSelected(model.getDeleteFile().get());
		model.getDeleteFile().bind(deleteBackupFile.selectedProperty());
		setMargin(deleteBackupFile, new Insets(0, 10, 10, 10));
		getChildren().add(deleteBackupFile);
	}

	public SingleFileReaderView(Stage stage, SingleFileReaderModel model) {
		this.stage = stage;
		this.model = model;
		this.strings = createStrings();

		createFilePickerRow();
		createDeleteBackupFileRow();
	}

}
