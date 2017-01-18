package cc.ethon.logmaker.gen.model;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class DistanceModel {

	private static final String DEFAULT_FORMAT = "$kmkm $mm";

	private static final int METERS_PER_KILOMETER = 1000;

	private final int meters;

	public DistanceModel(int meters) {
		super();
		this.meters = meters;
	}

	public int getMeters() {
		return meters;
	}

	public int getKilometers() {
		return meters / METERS_PER_KILOMETER;
	}

	public int getMetersOfKilometer() {
		return getMeters() - getKilometers() * METERS_PER_KILOMETER;
	}

	public TemplateMethodModelEx getFormatDistance() {
		return (args) -> {
			try {
				String string = !args.isEmpty() ? ((SimpleScalar) args.get(0)).getAsString() : DEFAULT_FORMAT;
				string = string.replace("$M", String.valueOf(getMeters()));
				string = string.replace("$m", String.valueOf(getMetersOfKilometer()));
				string = string.replace("$km", String.valueOf(getKilometers()));
				return string;
			} catch (final Exception e) {
				throw new TemplateModelException("Usage: formatDistance([formatString])", e);
			}
		};
	}

}
