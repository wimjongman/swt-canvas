/*******************************************************************************
 * Copyright (c) 2006, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Qi Liang (IBM Corporation)
*******************************************************************************/
package com.ibm.examples.chart.widget.chart;

import org.eclipse.birt.chart.model.Chart;

import com.ibm.examples.chart.data.DataSet;

/**
 * Provides the common members and the framework to build one chart.
 * 
 * @author Qi Liang
 */
public abstract class AbstractChartBuilder {

    /**
     * Font name for all titles, labels, and values.
     */
    protected final static String FONT_NAME = "MS Sans Serif";

    /**
     * Provides data for chart.
     */
    protected DataSet dataSet = null;

    /**
     * Chart instance.
     */
    protected Chart chart = null;

    /**
     * Chart title.
     */
    protected String title = null;

    /**
     * Constructs one chart builder and associate it to one data set.
     * 
     * @param dataSet
     *            data set
     */
    public AbstractChartBuilder(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Builds one chart.
     */
    public void build() {
        createChart();
        buildPlot();
        buildLegend();
        buildTitle();
        buildXAxis();
        buildYAxis();
        buildXSeries();
        buildYSeries();
    }

    /**
     * Creates chart instance.
     */
    protected abstract void createChart();

    /**
     * Builds plot.
     */
    protected void buildPlot() {

    }

    /**
     * Builds X axis.
     */
    protected void buildXAxis() {

    }

    /**
     * Builds Y axis.
     */
    protected void buildYAxis() {

    }

    /**
     * Builds X series.
     */
    protected void buildXSeries() {

    }

    /**
     * Builds Y series.
     */
    protected void buildYSeries() {

    }

    /**
     * Builds legend.
     * 
     */
    protected void buildLegend() {

    }

    /**
     * Builds the chart title.
     */
    protected void buildTitle() {
        chart.getTitle().getLabel().getCaption().setValue(title);
        chart.getTitle().getLabel().getCaption().getFont().setSize(14);
        chart.getTitle().getLabel().getCaption().getFont().setName(FONT_NAME);
    }

    /**
     * Returns the chart instance.
     * 
     * @return the chart instance
     */
    public Chart getChart() {
        return chart;
    }

}
