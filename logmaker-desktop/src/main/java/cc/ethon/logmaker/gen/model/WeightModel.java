package cc.ethon.logmaker.gen.model;

import java.text.DecimalFormat;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

public class WeightModel {

	private static final String DEFAULT_FORMAT = "$KGkg";
	private static final DecimalFormat WEIGHT_FORMATTER = new DecimalFormat("0.##");

	private static final int GRAMS_PER_KILOGRAM = 1000;

	private final int grams;

	public WeightModel(int grams) {
		super();
		this.grams = grams;
	}

	public int getGrams() {
		return grams;
	}

	public int getKilograms() {
		return grams / GRAMS_PER_KILOGRAM;
	}

	public int getGramsOfKilogram() {
		return getGrams() - getKilograms() * GRAMS_PER_KILOGRAM;
	}

	public TemplateMethodModelEx getFormat() {
		return (args) -> {
			final boolean valid = args.size() <= 1;
			if (!valid) {
				throw new IllegalArgumentException("Wrong usage of `format([String])`");
			}

			String string = !args.isEmpty() ? ((SimpleScalar) args.get(0)).getAsString() : DEFAULT_FORMAT;
			string = string.replace("$G", String.valueOf(getGrams()));
			string = string.replace("$g", String.valueOf(getGramsOfKilogram()));
			string = string.replace("$kg", String.valueOf(getKilograms()));
			string = string.replace("$KG", WEIGHT_FORMATTER.format(1.0 * getGrams() / GRAMS_PER_KILOGRAM));
			return string;
		};
	}

}
