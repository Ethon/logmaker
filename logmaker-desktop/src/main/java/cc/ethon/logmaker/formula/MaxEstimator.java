package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public interface MaxEstimator {

	public String getName();

	public double estimate(Set set);

	default int compareSets(Set set1, Set set2) {
		return new Double(estimate(set1)).compareTo(estimate(set2));
	}

}
