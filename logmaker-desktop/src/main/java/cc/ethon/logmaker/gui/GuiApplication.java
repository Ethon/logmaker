package cc.ethon.logmaker.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;

public class GuiApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			primaryStage.setTitle("logmaker");
			final MainWindow mainWindow = new MainWindow(primaryStage, new MainWindowModel(Settings.getInstance()));
			primaryStage.setScene(new Scene(mainWindow, 600, 250));
			primaryStage.show();
		} catch (final Exception e) {
			e.printStackTrace();
			final Alert alert = new Alert(AlertType.ERROR, e.toString(), ButtonType.OK);
			alert.show();
			Platform.exit();
		}
	}

	@Override
	public void stop() throws Exception {
		Settings.getInstance().save();
		super.stop();
	}

}
