package cc.ethon.logmaker.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.gen.GeneratorController;
import cc.ethon.logmaker.gui.gen.template.TemplateGeneratorController;
import cc.ethon.logmaker.gui.reader.LogReaderController;
import cc.ethon.logmaker.gui.reader.redy.csv.RedyGymLogCsvReaderController;
import cc.ethon.logmaker.gui.reader.redy.db.RedyGymLogDbReaderController;
import cc.ethon.logmaker.gui.sink.SinkController;
import cc.ethon.logmaker.gui.sink.clipboard.ClipboardSinkController;

public class MainWindowModel {

	private final static String KEY_CLOSEAPPLICATION = MainWindowModel.class.getSimpleName() + ".closeApplication";
	private final static String KEY_SELECTEDLOGREADER = MainWindowModel.class.getSimpleName() + ".selectedLogReader";
	private final static String KEY_SELECTEDGENERATOR = MainWindowModel.class.getSimpleName() + ".selectedGenerator";
	private final static String KEY_SELECTEDSINK = MainWindowModel.class.getSimpleName() + ".selectedSink";

	private final BooleanProperty closeApplication;
	private final IntegerProperty selectedLogReader;
	private final IntegerProperty selectedGenerator;
	private final IntegerProperty selectedSink;
	private final ObservableList<LogReaderController> logReaders;
	private final ObservableList<GeneratorController> generators;
	private final ObservableList<SinkController> sinks;

	public MainWindowModel(Settings settings) throws Exception {

		closeApplication = new SimpleBooleanProperty(settings.getBoolean(KEY_CLOSEAPPLICATION));
		closeApplication.addListener((obs, o, n) -> settings.setBoolean(KEY_CLOSEAPPLICATION, n));

		selectedLogReader = new SimpleIntegerProperty(settings.getInt(KEY_SELECTEDLOGREADER, 0));
		selectedLogReader.addListener((obs, o, n) -> settings.setInt(KEY_SELECTEDLOGREADER, n.intValue()));

		selectedGenerator = new SimpleIntegerProperty(settings.getInt(KEY_SELECTEDGENERATOR, 0));
		selectedGenerator.addListener((obs, o, n) -> settings.setInt(KEY_SELECTEDGENERATOR, n.intValue()));

		selectedSink = new SimpleIntegerProperty(settings.getInt(KEY_SELECTEDSINK, 0));
		selectedSink.addListener((obs, o, n) -> settings.setInt(KEY_SELECTEDSINK, n.intValue()));

		// Changing the order will cause setting to be invalid!
		logReaders = FXCollections.observableArrayList( //
				new RedyGymLogCsvReaderController(Settings.getInstance()), //
				new RedyGymLogDbReaderController(Settings.getInstance()));

		// Changing the order will cause setting to be invalid!
		generators = FXCollections.observableArrayList( //
				new TemplateGeneratorController(Settings.getInstance()));

		// Changing the order will cause setting to be invalid!
		sinks = FXCollections.observableArrayList( //
				new ClipboardSinkController());
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

	public ObservableList<LogReaderController> getLogReaders() {
		return logReaders;
	}

	public ObservableList<GeneratorController> getGenerators() {
		return generators;
	}

	public ObservableList<SinkController> getSinks() {
		return sinks;
	}

}
