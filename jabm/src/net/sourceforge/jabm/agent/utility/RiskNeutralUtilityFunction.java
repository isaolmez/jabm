/*
 * JABM - Java Agent-Based Modeling Toolkit
 * Copyright (C) 2011 Steve Phelps
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */
package net.sourceforge.jabm.agent.utility;

import java.io.Serializable;

public class RiskNeutralUtilityFunction extends AbstractUtilityFunction
		implements Serializable, UtilityFunction {

	protected double coefficient = 1.0;
	
	public RiskNeutralUtilityFunction() {
		super();
	}

	public double calculatePayoff(double profit) {
		return coefficient * profit;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	
}
