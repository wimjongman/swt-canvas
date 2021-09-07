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
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.MarkerType;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.type.LineSeries;
import org.eclipse.birt.chart.model.type.impl.LineSeriesImpl;

import com.ibm.examples.chart.data.DataSet;

/**
 * Builds line chart.
 * 
 * @author Qi Liang
 */
public class LineChartBuilder extends AbstractChartWithAxisBuilder {

    /**
     * Constructor.
     * 
     * @param dataSet
     *            data for chart
     */
    public LineChartBuilder(DataSet dataSet) {
        super(dataSet);
        title = "Line Chart";
        xTitle = "Cities";
        yTitle = "Technitians";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildXAxis()
     */
    protected void buildXAxis() {
        xAxis = ((ChartWithAxes) chart).getPrimaryBaseAxes()[0];

        xAxis.getTitle().setVisible(true);
        xAxis.getTitle().getCaption().setValue(xTitle);
        xAxis.getTitle().getCaption().getFont().setBold(false);
        xAxis.getTitle().getCaption().getFont().setSize(11);
        xAxis.getTitle().getCaption().getFont().setName(FONT_NAME);

        xAxis.getLabel().setVisible(true);
        xAxis.getLabel().getCaption().getFont().setSize(8);
        xAxis.getLabel().getCaption().getFont().setName(FONT_NAME);

        xAxis.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);

        xAxis.setType(AxisType.TEXT_LITERAL);
        xAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildYAxis()
     */
    protected void buildYAxis() {
        yAxis = ((ChartWithAxes) chart).getPrimaryOrthogonalAxis(xAxis);

        yAxis.getTitle().setVisible(true);
        yAxis.getTitle().getCaption().setValue(yTitle);
        yAxis.getTitle().getCaption().getFont().setBold(false);
        yAxis.getTitle().getCaption().getFont().setRotation(90);
        yAxis.getTitle().getCaption().getFont().setSize(11);
        yAxis.getTitle().getCaption().getFont().setName(FONT_NAME);

        yAxis.getLabel().setVisible(true);
        yAxis.getLabel().getCaption().getFont().setSize(8);
        yAxis.getLabel().getCaption().getFont().setName(FONT_NAME);

        yAxis.getMajorGrid().getLineAttributes().setVisible(true);
        yAxis.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl
                .GREY());
        yAxis.getMajorGrid().getLineAttributes()
                .setStyle(LineStyle.DASHED_LITERAL);
        yAxis.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);

        yAxis.setType(AxisType.LINEAR_LITERAL);
        yAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
        
        yAxis.getScale().setStep(1.0);
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

        // Apply the color palette
        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(1);

        xAxis.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);
    }

    protected void buildYSeries() {

        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(dataSet
                .getTechnitians());

        LineSeries ls = (LineSeries) LineSeriesImpl.create();
        ls.setDataSet(orthoValuesDataSet1);
        ls.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
        ls.getMarkers().forEach(m -> m.setType(MarkerType.CIRCLE_LITERAL));
        ls.getLabel().setVisible(true);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        yAxis.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(ls);
    }

}
