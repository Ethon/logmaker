package cc.ethon.logmaker.gui;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.WendlerFormula;
import cc.ethon.logmaker.gen.ClipboardSink;
import cc.ethon.logmaker.gen.TemplateGenerator;
import cc.ethon.logmaker.gui.reader.LogReaderController;
import cc.ethon.logmaker.gui.reader.redy.csv.RedyGymLogCsvReaderController;
import cc.ethon.logmaker.gui.reader.redy.db.RedyGymLogDbReaderController;

public class MainWindow extends VBox {

	private final Stage stage;
	private final MainWindowModel model;

	private ComboBox<LogReaderController> selectedLogReaderComboBox;
	private ComboBox<String> selectedTemplateComboBox;

	private void createReaderSelection() throws IOException {
		// Changing the order will cause setting to be invalid!
		final ObservableList<LogReaderController> logReaders = FXCollections.observableArrayList( //
				new RedyGymLogCsvReaderController(Settings.getInstance()), //
				new RedyGymLogDbReaderController(Settings.getInstance()));

		final Label label = new Label("Select the workoutlog reader");
		VBox.setMargin(label, new Insets(0, 0, 10, 0));

		selectedLogReaderComboBox = new ComboBox<LogReaderController>();
		selectedLogReaderComboBox.setItems(logReaders);
		selectedLogReaderComboBox.getSelectionModel().select(model.getSelectedLogReader().get());
		model.getSelectedLogReader().bind(selectedLogReaderComboBox.getSelectionModel().selectedIndexProperty());

		final LogReaderController logReader = logReaders.get(model.getSelectedLogReader().get());
		final TitledPane optionsPane = new TitledPane("Reader Options", logReader.getOptionsView(stage));

		selectedLogReaderComboBox.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
			optionsPane.setContent(n.getOptionsView(stage));
		});
		VBox.setMargin(selectedLogReaderComboBox, new Insets(0, 0, 10, 0));

		final VBox readerPart = new VBox(label, selectedLogReaderComboBox, optionsPane);
		readerPart.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		VBox.setMargin(readerPart, new Insets(10));
		readerPart.setPadding(new Insets(10));
		getChildren().add(readerPart);
	}

	private void createOptionsRow() {
		final CheckBox closeApplication = new CheckBox("Close logmaker after generation");
		closeApplication.setSelected(model.getCloseApplication().get());
		model.getCloseApplication().bind(closeApplication.selectedProperty());
		HBox.setMargin(closeApplication, new Insets(10));

		final HBox hbox = new HBox();
		hbox.getChildren().addAll(closeApplication);
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
					final LogReaderController readerController = selectedLogReaderComboBox.getSelectionModel().getSelectedItem();
					final WorkoutLog log = readerController.createReader().readLog();

					final TemplateGenerator generator = model.getGenerator();
					generator.selectTemplate(selectedTemplateComboBox.getSelectionModel().getSelectedItem());
					generator.generate(new ClipboardSink(), log, new WendlerFormula(), 1);

					readerController.postProcess();
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
		createOptionsRow();
		createTemplateFileSelection();
		createGenerateButton();
	}

}
