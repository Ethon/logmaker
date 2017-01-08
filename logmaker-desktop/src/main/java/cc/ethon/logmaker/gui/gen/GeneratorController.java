package cc.ethon.logmaker.gui.gen;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.gen.Generator;

public abstract class GeneratorController {

	public abstract String getName();

	public abstract Generator getGenerator();

	public abstract Node getOptionsView(Stage stage);

	@Override
	public String toString() {
		return getName();
	}

}
