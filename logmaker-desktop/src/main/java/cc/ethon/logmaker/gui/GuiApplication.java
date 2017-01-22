package cc.ethon.logmaker.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;

public class GuiApplication extends Application {

	private void installSizeChangeListener(Stage primaryStage, MainWindow window) {
		// we need to adjust values to native window border
		final double borderWidth = window.getWidth() - primaryStage.getScene().getWidth() + 1;
		final double borderHeight = window.getHeight() - primaryStage.getScene().getHeight() + 1;

		final InvalidationListener resizeListener = new InvalidationListener() {
			@Override
			public void invalidated(Observable o) {
				final Bounds bounds = window.getLayoutBounds();
				if (bounds.getWidth() + borderWidth > primaryStage.getWidth()) {
					primaryStage.setWidth(bounds.getWidth() + borderWidth + 1);
				}
				if (bounds.getHeight() + borderHeight > primaryStage.getHeight()) {
					primaryStage.setHeight(bounds.getHeight() + borderHeight + 1);
				}
			}
		};

		window.widthProperty().addListener(resizeListener);
		window.heightProperty().addListener(resizeListener);
	}

	private void setIcon(Stage primaryStage) {
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/logmaker-icon-128.png")));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			primaryStage.setTitle("logmaker");
			setIcon(primaryStage);
			final MainWindow mainWindow = new MainWindow(primaryStage, new MainWindowModel(Settings.getInstance()));
			final Scene scene = new Scene(mainWindow, 650, 800);
			primaryStage.setScene(scene);
			primaryStage.show();
			installSizeChangeListener(primaryStage, mainWindow);
		} catch (final Exception e) {
			e.printStackTrace();
			final Alert alert = new Alert(AlertType.ERROR, e.toString(), ButtonType.OK);
			alert.showAndWait();
			Platform.exit();
		}
	}

	@Override
	public void stop() throws Exception {
		Settings.getInstance().save();
		super.stop();
	}

}
