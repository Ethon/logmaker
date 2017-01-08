package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public class MayhewEtAlFormula implements MaxEstimator {

	@Override
	public String getName() {
		return "Mayhew et al.";
	}

	@Override
	public double estimate(Set set) {
		if (set.getReps() == 0) {
			return 0;
		}
		if (set.getReps() == 1) {
			return set.getWeight();
		}
		return 100.0 * set.getWeight() / (52.2 + 41.9 * Math.pow(Math.E, -0.055 * set.getReps()));
	}

	@Override
	public String toString() {
		return getName();
	}

}
