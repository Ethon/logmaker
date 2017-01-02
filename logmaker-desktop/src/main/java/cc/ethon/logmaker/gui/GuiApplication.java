package cc.ethon.logmaker.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;

public class GuiApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("logmaker");

		final MainWindow mainWindow = new MainWindow(primaryStage, new MainWindowModel(Settings.getInstance()));
		primaryStage.setScene(new Scene(mainWindow, 600, 250));
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		Settings.getInstance().save();
		super.stop();
	}

}
