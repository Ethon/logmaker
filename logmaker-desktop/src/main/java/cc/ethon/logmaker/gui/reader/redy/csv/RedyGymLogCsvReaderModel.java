package cc.ethon.logmaker.gui.reader.redy.csv;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import cc.ethon.logmaker.Settings;

public class RedyGymLogCsvReaderModel {

	private static final String KEY_EXPORTFILE = RedyGymLogCsvReaderModel.class.getSimpleName() + ".exportFile";
	private static final String KEY_DELETEEXPORTFILE = RedyGymLogCsvReaderModel.class.getSimpleName() + ".deleteExportFile";

	private final SimpleStringProperty exportFile;
	private final BooleanProperty deleteExportFile;

	public RedyGymLogCsvReaderModel(Settings settings) {
		exportFile = new SimpleStringProperty();
		if (settings.getSettingByKey(KEY_EXPORTFILE) != null) {
			exportFile.set(settings.getSettingByKey(KEY_EXPORTFILE));
		}
		exportFile.addListener((obs, o, n) -> settings.setSettingByKey(KEY_EXPORTFILE, n));

		deleteExportFile = new SimpleBooleanProperty();
		deleteExportFile.set("true".equals(settings.getSettingByKey(KEY_DELETEEXPORTFILE)));
		deleteExportFile.addListener((obs, o, n) -> settings.setSettingByKey(KEY_DELETEEXPORTFILE, Boolean.toString(n)));
	}

	public StringProperty getExportFile() {
		return exportFile;
	}

	public BooleanProperty getDeleteExportFile() {
		return deleteExportFile;
	}

}
