package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public interface MaxEstimator {

	public String getName();

	public double estimate(Set set);

}
