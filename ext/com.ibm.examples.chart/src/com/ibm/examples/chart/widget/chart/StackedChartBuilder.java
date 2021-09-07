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

import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.RiserType;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.GradientImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;

import com.ibm.examples.chart.data.DataSet;

/**
 * Builds stacked chart.
 * 
 * @author Qi Liang
 */
public class StackedChartBuilder extends AbstractChartWithAxisBuilder {

    /**
     * Constructor.
     * 
     * @param dataSet
     *            data for chart
     */
    public StackedChartBuilder(DataSet dataSet) {
        super(dataSet);
        title = "Statcked Chart";
        xTitle = "Cities";
        yTitle = "Staff";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildPlot()
     */
    protected void buildPlot() {
        ((ChartWithAxes) chart).setUnitSpacing(25);
        chart.getBlock().setBackground(ColorDefinitionImpl.WHITE());
        Plot p = chart.getPlot();
        p.getClientArea().setBackground(GradientImpl.create(ColorDefinitionImpl
                .create(255, 235, 255), ColorDefinitionImpl.create(255,
                255,
                225), -35, false));

        p.getClientArea().getInsets().set(8, 8, 8, 8);
        p.getOutline().setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildXAxis()
     */
    protected void buildXAxis() {
        xAxis = ((ChartWithAxes) chart).getPrimaryBaseAxes()[0];
        xAxis.setType(AxisType.TEXT_LITERAL);

        xAxis.getLabel().setBackground(ColorDefinitionImpl
                .create(255, 255, 255));
        xAxis.getLabel().getCaption().getFont().setRotation(25);

        xAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
        xAxis.setTitlePosition(Position.BELOW_LITERAL);
        xAxis.setLabelPosition(Position.BELOW_LITERAL);
        xAxis.getTitle().getCaption().setValue(xTitle);
        xAxis.getTitle().setVisible(true);
        
        xAxis.getOrigin().setType(IntersectionType.MIN_LITERAL);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildYAxis()
     */
    protected void buildYAxis() {
        yAxis = ((ChartWithAxes) chart).getPrimaryOrthogonalAxis(xAxis);

        yAxis.setLabelPosition(Position.LEFT_LITERAL);
        yAxis.setTitlePosition(Position.LEFT_LITERAL);
        yAxis.getTitle().getCaption().setValue(yTitle);
        yAxis.getTitle().setVisible(true);

        yAxis.setType(AxisType.LINEAR_LITERAL);
        yAxis.getLabel().getCaption().getFont().setRotation(37);
        
        yAxis.getScale().setStep(5.0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildXSeries()
     */
    protected void buildXSeries() {

        TextDataSet categoryValues = TextDataSetImpl
                .create(dataSet.getCities());

        Series seCategory = SeriesImpl.create();
        seCategory.setDataSet(categoryValues);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(1);
        xAxis.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildYSeries()
     */
    protected void buildYSeries() {

        // Create the data set for technitians and salers
        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(dataSet
                .getTechnitians());

        NumberDataSet orthoValuesDataSet2 = NumberDataSetImpl.create(dataSet
                .getSalers());

        // Create technitian series
        BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
        bs1.setSeriesIdentifier("Technitians");
        bs1.setDataSet(orthoValuesDataSet1);
        bs1.setRiserOutline(null);
        bs1.setRiser(RiserType.RECTANGLE_LITERAL);
        bs1.setStacked(true);

        // Create saler series
        BarSeries bs2 = (BarSeries) BarSeriesImpl.create();
        bs2.setSeriesIdentifier("Salers");
        bs2.setDataSet(orthoValuesDataSet2);
        bs2.setRiserOutline(null);
        bs2.setRiser(RiserType.RECTANGLE_LITERAL);
        bs2.setStacked(true);

        // Add two series to one series definition
        SeriesDefinition sdY1 = SeriesDefinitionImpl.create();
        sdY1.getSeriesPalette().update(0);
        yAxis.getSeriesDefinitions().add(sdY1);

        sdY1.getSeries().add(bs1);
        sdY1.getSeries().add(bs2);

    }

}
