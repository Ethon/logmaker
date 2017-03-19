package cc.ethon.logmaker.gui;

import java.io.IOException;

import javafx.application.Platform;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;
import cc.ethon.logmaker.gen.Generator;
import cc.ethon.logmaker.gui.gen.GeneratorController;
import cc.ethon.logmaker.gui.reader.LogReaderController;
import cc.ethon.logmaker.gui.sink.SinkController;
import cc.ethon.logmaker.sink.Sink;

public class MainWindow extends VBox {

	private static class SelectedLogReaderCellFactory implements Callback<ListView<LogReaderController>, ListCell<LogReaderController>> {
		@Override
		public ListCell<LogReaderController> call(ListView<LogReaderController> view) {
			return new ListCell<LogReaderController>() {
				@Override
				protected void updateItem(LogReaderController controller, boolean arg1) {
					super.updateItem(controller, arg1);
					if (controller != null) {
						if (controller.getImage() != null) {
							setGraphic(new ImageView(controller.getImage()));
						}
						setText(controller.getName());
						setDisable(false);
					}
				}
			};
		}
	}

	private final Stage stage;
	private final MainWindowModel model;

	private ComboBox<LogReaderController> selectedLogReaderComboBox;
	private ComboBox<GeneratorController> selectedGeneratorComboBox;
	private ComboBox<SinkController> selectedSinkComboBox;
	private ComboBox<MaxEstimator> selectedFormulaComboBox;

	private void createReaderSelection() throws IOException {
		final Label label = new Label("Select the workoutlog reader");
		VBox.setMargin(label, new Insets(0, 0, 10, 0));

		selectedLogReaderComboBox = new ComboBox<LogReaderController>();
		selectedLogReaderComboBox.setItems(model.getLogReaders());
		selectedLogReaderComboBox.getSelectionModel().select(model.getSelectedLogReader().get());
		model.getSelectedLogReader().bind(selectedLogReaderComboBox.getSelectionModel().selectedIndexProperty());

		final SelectedLogReaderCellFactory cellFactory = new SelectedLogReaderCellFactory();
		selectedLogReaderComboBox.setButtonCell(cellFactory.call(null));
		selectedLogReaderComboBox.setCellFactory(cellFactory);

		final LogReaderController logReader = model.getLogReaders().get(model.getSelectedLogReader().get());
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

	private void createGeneratorSelection() {
		final Label label = new Label("Select the generator");
		VBox.setMargin(label, new Insets(0, 0, 10, 0));

		selectedGeneratorComboBox = new ComboBox<GeneratorController>();
		selectedGeneratorComboBox.setItems(model.getGenerators());
		selectedGeneratorComboBox.getSelectionModel().select(model.getSelectedGenerator().get());
		model.getSelectedGenerator().bind(selectedGeneratorComboBox.getSelectionModel().selectedIndexProperty());

		final GeneratorController generator = model.getGenerators().get(model.getSelectedGenerator().get());
		final TitledPane optionsPane = new TitledPane("Generator Options", generator.getOptionsView(stage));

		selectedGeneratorComboBox.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
			optionsPane.setContent(n.getOptionsView(stage));
		});
		VBox.setMargin(selectedGeneratorComboBox, new Insets(0, 0, 10, 0));

		final VBox generatorPart = new VBox(label, selectedGeneratorComboBox, optionsPane);
		generatorPart.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		VBox.setMargin(generatorPart, new Insets(0, 10, 10, 10));
		generatorPart.setPadding(new Insets(10));
		getChildren().add(generatorPart);
	}

	private void createSinkSelection() {
		final Label label = new Label("Select where the log is generated to");
		VBox.setMargin(label, new Insets(0, 0, 10, 0));

		selectedSinkComboBox = new ComboBox<SinkController>();
		selectedSinkComboBox.setItems(model.getSinks());
		selectedSinkComboBox.getSelectionModel().select(model.getSelectedSink().get());
		model.getSelectedSink().bind(selectedSinkComboBox.getSelectionModel().selectedIndexProperty());

		final SinkController sink = model.getSinks().get(model.getSelectedSink().get());
		final TitledPane optionsPane = new TitledPane("Sink Options", sink.getOptionsView(stage));

		selectedSinkComboBox.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
			optionsPane.setContent(n.getOptionsView(stage));
		});
		VBox.setMargin(selectedSinkComboBox, new Insets(0, 0, 10, 0));

		final VBox sinkPart = new VBox(label, selectedSinkComboBox, optionsPane);
		sinkPart.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		VBox.setMargin(sinkPart, new Insets(0, 10, 10, 10));
		sinkPart.setPadding(new Insets(10));
		getChildren().add(sinkPart);
	}

	private void createFormulaSelection() {
		final Label label = new Label("Select the formula to estimate the 1RM:");
		HBox.setMargin(label, new Insets(0, 10, 0, 0));

		selectedFormulaComboBox = new ComboBox<MaxEstimator>();
		selectedFormulaComboBox.setItems(model.getFormulas());
		selectedFormulaComboBox.getSelectionModel().select(model.getSelectedFormula().get());
		model.getSelectedFormula().bind(selectedFormulaComboBox.getSelectionModel().selectedIndexProperty());

		final HBox sinkPart = new HBox(label, selectedFormulaComboBox);
		sinkPart.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		VBox.setMargin(sinkPart, new Insets(0, 10, 10, 10));
		sinkPart.setPadding(new Insets(10));
		getChildren().add(sinkPart);
	}

	private void createGenerateButton() {
		final Label label = new Label("Select the number of workouts to generate:");
		HBox.setMargin(label, new Insets(0, 10, 0, 0));

		final Spinner<Integer> spinner = new Spinner<Integer>();
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, model.getWorkoutsToGenerate().get()));
		model.getWorkoutsToGenerate().bind(spinner.valueProperty());

		final HBox selectionPart = new HBox(label, spinner);
		VBox.setMargin(selectionPart, new Insets(0, 0, 10, 0));

		final Button generate = new Button("Generate report");
		generate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					final LogReaderController readerController = selectedLogReaderComboBox.getSelectionModel().getSelectedItem();
					final WorkoutLog log = readerController.createReader().readLog();

					final GeneratorController generatorController = selectedGeneratorComboBox.getSelectionModel().getSelectedItem();
					final Generator generator = generatorController.getGenerator();

					final MaxEstimator formula = selectedFormulaComboBox.getSelectionModel().getSelectedItem();

					final SinkController sinkController = selectedSinkComboBox.getSelectionModel().getSelectedItem();
					try (final Sink sink = sinkController.createSink()) {
						generator.generate(sink, log, formula, model.getWorkoutsToGenerate().get());
					}

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
		HBox.setMargin(generate, new Insets(0, 10, 0, 0));

		final CheckBox closeApplication = new CheckBox("Close logmaker after generation");
		closeApplication.setSelected(model.getCloseApplication().get());
		model.getCloseApplication().bind(closeApplication.selectedProperty());

		final HBox generatePart = new HBox(generate, closeApplication);

		final VBox box = new VBox(selectionPart, generatePart);
		box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		VBox.setMargin(box, new Insets(0, 10, 10, 10));
		box.setPadding(new Insets(10));
		getChildren().add(box);
	}

	public MainWindow(Stage stage, MainWindowModel model) throws Exception {
		this.stage = stage;
		this.model = model;

		createReaderSelection();
		createGeneratorSelection();
		createSinkSelection();
		createFormulaSelection();
		createGenerateButton();
	}

}
