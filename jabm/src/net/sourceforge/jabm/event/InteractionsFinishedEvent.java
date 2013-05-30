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
package net.sourceforge.jabm.event;

import java.io.Serializable;

import net.sourceforge.jabm.Simulation;

/**
 * An event that is fired when agents have finished interacting.
 * 
 * @author Steve Phelps
 *
 */
public class InteractionsFinishedEvent extends SimulationEvent implements
		Serializable {

	public InteractionsFinishedEvent(Simulation model) {
		super(model);
	}

	public InteractionsFinishedEvent() {
		super();
	}
	
	

}
