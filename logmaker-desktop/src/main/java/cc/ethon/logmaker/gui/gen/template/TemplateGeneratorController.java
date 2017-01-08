package cc.ethon.logmaker.gui.gen.template;

import java.io.IOException;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gen.Generator;
import cc.ethon.logmaker.gen.TemplateGenerator;
import cc.ethon.logmaker.gui.gen.GeneratorController;

public class TemplateGeneratorController extends GeneratorController {

	private final TemplateGenerator generator;
	private final TemplateGeneratorModel model;

	public TemplateGeneratorController(Settings settings) throws IOException {
		model = new TemplateGeneratorModel(settings);
		generator = new TemplateGenerator(settings.getTemplatesDir());
		if (model.getSelectedTemplate().get() != null) {
			generator.selectTemplate(model.getSelectedTemplate().get());
		}

		model.getAvailableTemplates().addAll(generator.getAvailableTemplates());
		model.getSelectedTemplate().addListener((obs, o, n) -> generator.selectTemplate(n));
	}

	@Override
	public String getName() {
		return "Template Generator";
	}

	@Override
	public Generator getGenerator() {
		return generator;
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new TemplateGeneratorView(model);
	}

}
