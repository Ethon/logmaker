package cc.ethon.logmaker.gui;

import java.io.File;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.gen.DefaultGenerator;
import cc.ethon.logmaker.gen.Generator;
import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.redy.db.RedyGymLogDbReader;

public class MainWindow extends VBox {

	private final Stage stage;
	private final MainWindowModel model;

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

	private void createGenerateButton() {
		final Button generate = new Button("Generate and copy last workout to clipboard");
		generate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					final File exportFile = new File(model.getExportFile().get());
					final LogReader reader = new RedyGymLogDbReader();
					final WorkoutLog log = reader.readLog(exportFile);
					final Generator gen = new DefaultGenerator();
					gen.genLastWorkoutToClipboard(log);

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

	public MainWindow(Stage stage, MainWindowModel model) {
		this.stage = stage;
		this.model = model;

		createExportFileChooserRow();
		createOptionsRow();
		createGenerateButton();
	}

}
