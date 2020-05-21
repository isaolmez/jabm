/*
 * JABM - Java Agent-Based Modeling Toolkit
 * Copyright (C) 2013 Steve Phelps
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
package net.sourceforge.jabm.report;

import java.util.Map;
import net.sourceforge.jabm.event.SimEvent;

public abstract class AbstractReport implements Report {

    protected ReportVariables reportVariables;

    public AbstractReport(ReportVariables reportVariables) {
        super();
        this.reportVariables = reportVariables;
    }

    public AbstractReport() {
        super();
    }

    @Override
    public Map<Object, Number> getVariableBindings() {
        return reportVariables.getVariableBindings();
    }

    public ReportVariables getReportVariables() {
        return reportVariables;
    }

    /**
     * Configure the variables that will be updated by this report.
     *
     * @see ReportVariables
     */
    public void setReportVariables(ReportVariables reportVariables) {
        this.reportVariables = reportVariables;
    }

    @Override
    public void eventOccurred(SimEvent event) {
        reportVariables.eventOccurred(event);
    }

    @Override
    public String getName() {
        return getClass().getName();
    }


}
