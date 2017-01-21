package cc.ethon.logmaker.gui.reader.redy.db;

import java.io.File;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.reader.LogReaderController;
import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.redy.db.RedyGymLogDbReader;

public class RedyGymLogDbReaderController extends LogReaderController {

	private final RedyGymLogDbReaderModel model;

	public RedyGymLogDbReaderController(Settings settings) {
		model = new RedyGymLogDbReaderModel(settings);
	}

	@Override
	public String getName() {
		return "Redy Gym Log Database Backup Reader";
	}

	@Override
	public LogReader createReader() {
		return new RedyGymLogDbReader(new File(model.getFile().get()));
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new RedyGymLogDbReaderView(stage, model);
	}

	@Override
	public void postProcess() {
		if (model.getDeleteFile().get()) {
			final File file = new File(model.getFile().get());
			if (file.exists()) {
				file.delete();
			}
		}
	}

}
