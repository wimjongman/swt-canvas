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

import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;

import com.ibm.examples.chart.data.DataSet;

/**
 * Builds multi-bar chart.
 * 
 * @author Qi Liang
 */
public class MultiBarChartBuilder extends BarChartBuilder {

    /**
     * Constructor.
     * 
     * @param dataSet
     *            data for chart
     */
    public MultiBarChartBuilder(DataSet dataSet) {
        super(dataSet);
        title = "Multi-Bar Chart";
        xTitle = "Cities";
        yTitle = "Staff";

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.BarChartBuilder#buildYSeries(float[])
     */
    protected void buildYSeries() {

        // Create two data set
        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(dataSet
                .getTechnitians());
        NumberDataSet orthoValuesDataSet2 = NumberDataSetImpl.create(dataSet
                .getSalers());

        // Create series 1
        BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
        bs1.setDataSet(orthoValuesDataSet1);
        bs1.setRiserOutline(null);

        // Create series 2
        BarSeries bs2 = (BarSeries) BarSeriesImpl.create();
        bs2.setDataSet(orthoValuesDataSet2);
        bs2.setRiserOutline(null);

        // Add two series to one series definition
        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        yAxis.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(bs1);
        sdY.getSeries().add(bs2);
    }

}
