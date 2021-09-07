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

import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;

import com.ibm.examples.chart.data.DataSet;

/**
 * Builds pie chart.
 * 
 * @author Qi Liang
 */
public class PieChartBuilder extends AbstractChartBuilder {

    SeriesDefinition sdX = null;

    /**
     * Constructor.
     * 
     * @param dataSet
     *            data for chart
     */
    public PieChartBuilder(DataSet dataSet) {
        super(dataSet);
        title = "Pie Chart";
    }

    protected void createChart() {
        chart = ChartWithoutAxesImpl.create();
    }

    protected void buildPlot() {
        chart.setSeriesThickness(25);
        chart.getBlock().setBackground(ColorDefinitionImpl.WHITE());
        Plot p = chart.getPlot();
        p.getClientArea().setBackground(null);
        p.getClientArea().getOutline().setVisible(true);
        p.getOutline().setVisible(true);
    }

    protected void buildLegend() {
        Legend lg = chart.getLegend();
        lg.getText().getFont().setSize(16);
        lg.getOutline().setVisible(true);
    }

    protected void buildXSeries() {

        TextDataSet categoryValues = TextDataSetImpl
                .create(dataSet.getCities());

        Series seCategory = SeriesImpl.create();
        seCategory.setDataSet(categoryValues);

        // Apply the color palette
        sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(1);

        ((ChartWithoutAxes) chart).getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);
    }

    protected void buildYSeries() {

        NumberDataSet orthoValuesDataSet = NumberDataSetImpl.create(dataSet
                .getTechnitians());

        PieSeries sePie = (PieSeries) PieSeriesImpl.create();
        sePie.setDataSet(orthoValuesDataSet);
        sePie.setSeriesIdentifier("Cities");

        SeriesDefinition sdCity = SeriesDefinitionImpl.create();
        sdX.getSeriesDefinitions().add(sdCity);
        sdCity.getSeries().add(sePie);

    }

}
