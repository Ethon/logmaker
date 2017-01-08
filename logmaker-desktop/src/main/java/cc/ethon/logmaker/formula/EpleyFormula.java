package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public class EpleyFormula implements MaxEstimator {

	@Override
	public String getName() {
		return "Epley";
	}

	@Override
	public double estimate(Set set) {
		if (set.getReps() == 0) {
			return 0;
		}
		if (set.getReps() == 1) {
			return set.getWeight();
		}
		return set.getWeight() * (1.0 + set.getReps() * 1.0 / 30.0);
	}

	@Override
	public String toString() {
		return getName();
	}

}
