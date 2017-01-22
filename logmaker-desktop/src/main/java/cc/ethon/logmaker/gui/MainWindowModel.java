package cc.ethon.logmaker.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.formula.BrzykiFormula;
import cc.ethon.logmaker.formula.EpleyFormula;
import cc.ethon.logmaker.formula.LombardiFormula;
import cc.ethon.logmaker.formula.MaxEstimator;
import cc.ethon.logmaker.formula.MayhewEtAlFormula;
import cc.ethon.logmaker.formula.McGlothinFormula;
import cc.ethon.logmaker.formula.OConnerEtAlFormula;
import cc.ethon.logmaker.formula.WathanFormula;
import cc.ethon.logmaker.formula.WendlerFormula;
import cc.ethon.logmaker.gui.gen.GeneratorController;
import cc.ethon.logmaker.gui.gen.template.TemplateGeneratorController;
import cc.ethon.logmaker.gui.reader.LogReaderController;
import cc.ethon.logmaker.gui.reader.logmaker.LogmakerXmlReaderController;
import cc.ethon.logmaker.gui.reader.redy.csv.RedyGymLogCsvReaderController;
import cc.ethon.logmaker.gui.reader.redy.db.RedyGymLogDbReaderController;
import cc.ethon.logmaker.gui.sink.SinkController;
import cc.ethon.logmaker.gui.sink.clipboard.ClipboardSinkController;
import cc.ethon.logmaker.gui.sink.fileappender.FileAppenderSinkController;
import cc.ethon.logmaker.gui.sink.filewriter.FileWriterSinkController;
import cc.ethon.logmaker.gui.sink.xmlappender.XmlAppenderSinkController;

public class MainWindowModel {

	private final static String KEY_CLOSEAPPLICATION = MainWindowModel.class.getSimpleName() + ".closeApplication";
	private final static String KEY_SELECTEDLOGREADER = MainWindowModel.class.getSimpleName() + ".selectedLogReader";
	private final static String KEY_SELECTEDGENERATOR = MainWindowModel.class.getSimpleName() + ".selectedGenerator";
	private final static String KEY_SELECTEDSINK = MainWindowModel.class.getSimpleName() + ".selectedSink";
	private final static String KEY_SELECTEDFORMULA = MainWindowModel.class.getSimpleName() + ".selectedFormula";
	private final static String KEY_WORKOUTSTOGENERATE = MainWindowModel.class.getSimpleName() + ".workoutsToGenerate";

	private final BooleanProperty closeApplication;
	private final IntegerProperty selectedLogReader;
	private final IntegerProperty selectedGenerator;
	private final IntegerProperty selectedSink;
	private final IntegerProperty selectedFormula;
	private final IntegerProperty workoutsToGenerate;
	private final ObservableList<LogReaderController> logReaders;
	private final ObservableList<GeneratorController> generators;
	private final ObservableList<SinkController> sinks;
	private final ObservableList<MaxEstimator> formulas;

	public MainWindowModel(Settings settings) throws Exception {

		closeApplication = new SimpleBooleanProperty(settings.getBoolean(KEY_CLOSEAPPLICATION));
		closeApplication.addListener((obs, o, n) -> settings.setBoolean(KEY_CLOSEAPPLICATION, n));

		selectedLogReader = new SimpleIntegerProperty(settings.getInt(KEY_SELECTEDLOGREADER, 0));
		selectedLogReader.addListener((obs, o, n) -> settings.setInt(KEY_SELECTEDLOGREADER, n.intValue()));

		selectedGenerator = new SimpleIntegerProperty(settings.getInt(KEY_SELECTEDGENERATOR, 0));
		selectedGenerator.addListener((obs, o, n) -> settings.setInt(KEY_SELECTEDGENERATOR, n.intValue()));

		selectedSink = new SimpleIntegerProperty(settings.getInt(KEY_SELECTEDSINK, 0));
		selectedSink.addListener((obs, o, n) -> settings.setInt(KEY_SELECTEDSINK, n.intValue()));

		selectedFormula = new SimpleIntegerProperty(settings.getInt(KEY_SELECTEDFORMULA, 0));
		selectedFormula.addListener((obs, o, n) -> settings.setInt(KEY_SELECTEDFORMULA, n.intValue()));

		workoutsToGenerate = new SimpleIntegerProperty(settings.getInt(KEY_WORKOUTSTOGENERATE, 1));
		workoutsToGenerate.addListener((obs, o, n) -> settings.setInt(KEY_WORKOUTSTOGENERATE, n.intValue()));

		// Changing the order will cause setting to be invalid!
		logReaders = FXCollections.observableArrayList( //
				new RedyGymLogCsvReaderController(settings), //
				new RedyGymLogDbReaderController(settings), //
				new LogmakerXmlReaderController(settings));

		// Changing the order will cause setting to be invalid!
		generators = FXCollections.observableArrayList( //
				new TemplateGeneratorController(settings));

		// Changing the order will cause setting to be invalid!
		sinks = FXCollections.observableArrayList( //
				new ClipboardSinkController(), //
				new FileAppenderSinkController(settings), //
				new FileWriterSinkController(settings), //
				new XmlAppenderSinkController(settings));

		// Changing the order will cause setting to be invalid!
		formulas = FXCollections.observableArrayList( //
				new WendlerFormula(), //
				new EpleyFormula(), //
				new BrzykiFormula(), //
				new McGlothinFormula(), //
				new LombardiFormula(), //
				new MayhewEtAlFormula(), //
				new OConnerEtAlFormula(), //
				new WathanFormula());
	}

	public BooleanProperty getCloseApplication() {
		return closeApplication;
	}

	public IntegerProperty getSelectedLogReader() {
		return selectedLogReader;
	}

	public IntegerProperty getSelectedGenerator() {
		return selectedGenerator;
	}

	public IntegerProperty getSelectedSink() {
		return selectedSink;
	}

	public IntegerProperty getSelectedFormula() {
		return selectedFormula;
	}

	public IntegerProperty getWorkoutsToGenerate() {
		return workoutsToGenerate;
	}

	public ObservableList<LogReaderController> getLogReaders() {
		return logReaders;
	}

	public ObservableList<GeneratorController> getGenerators() {
		return generators;
	}

	public ObservableList<SinkController> getSinks() {
		return sinks;
	}

	public ObservableList<MaxEstimator> getFormulas() {
		return formulas;
	}

}
