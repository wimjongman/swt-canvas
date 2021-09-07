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

import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.DataPoint;
import org.eclipse.birt.chart.model.attribute.DataPointComponentType;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.MarkerType;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.DataPointComponentImpl;
import org.eclipse.birt.chart.model.attribute.impl.JavaNumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.type.ScatterSeries;
import org.eclipse.birt.chart.model.type.impl.ScatterSeriesImpl;

import com.ibm.examples.chart.data.DataSet;

/**
 * Builds scatter chart.
 * 
 * @author Qi Liang
 */
public class ScatterChartBuilder extends AbstractChartWithAxisBuilder {

    /**
     * Constructor.
     * 
     * @param dataSet
     *            data for chart
     */
    public ScatterChartBuilder(DataSet dataSet) {
        super(dataSet);
        title = "Scatter Chart";
        xTitle = "Cities";
        yTitle = "Staff";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.AbstractChartBuilder#buildXAxis()
     */
    protected void buildXAxis() {
        xAxis = ((ChartWithAxesImpl) chart).getPrimaryBaseAxes()[0];

        xAxis.getTitle().getCaption().setValue(xTitle);
        xAxis.getLabel().getCaption().setColor(ColorDefinitionImpl.GREEN()
                .darker());
        xAxis.getTitle().setVisible(true);

        xAxis.setType(AxisType.TEXT_LITERAL);

        xAxis.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
        xAxis.getMajorGrid().getLineAttributes()
                .setStyle(LineStyle.DOTTED_LITERAL);
        xAxis.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl
                .GREY());
        xAxis.getMajorGrid().getLineAttributes().setVisible(true);

        xAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.AbstractChartBuilder#buildYAxis()
     */
    protected void buildYAxis() {
        yAxis = ((ChartWithAxesImpl) chart).getPrimaryOrthogonalAxis(xAxis);

        yAxis.getTitle().setVisible(true);
        yAxis.getTitle().getCaption().setValue(yTitle);

        yAxis.setType(AxisType.LINEAR_LITERAL);

        yAxis.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
        yAxis.getMajorGrid().getLineAttributes()
                .setStyle(LineStyle.DOTTED_LITERAL);
        yAxis.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl
                .GREY());
        yAxis.getMajorGrid().getLineAttributes().setVisible(true);

        yAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
        
        yAxis.getScale().setStep(1.0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.AbstractChartBuilder#buildXSeries()
     */
    protected void buildXSeries() {
        TextDataSet categoryValues = TextDataSetImpl
                .create(dataSet.getCities());

        Series seBase = SeriesImpl.create();
        seBase.setDataSet(categoryValues);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        xAxis.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seBase);
    }

    protected void buildYSeries() {

        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(dataSet
                .getTechnitians());

        ScatterSeries ss = (ScatterSeries) ScatterSeriesImpl.create();
        ss.setSeriesIdentifier("Cities");
        ss.getMarkers().forEach(m -> m.setType(MarkerType.CIRCLE_LITERAL));
        DataPoint dp = ss.getDataPoint();
        dp.getComponents().clear();
        dp.setPrefix("(");
        dp.setSuffix(")");
        dp.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.BASE_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0")));
        dp.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0")));
        ss.getLabel().getCaption().setColor(ColorDefinitionImpl.RED());
        ss.getLabel().setBackground(ColorDefinitionImpl.CYAN());
        ss.getLabel().setVisible(true);
        ss.setDataSet(orthoValuesDataSet1);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        yAxis.getSeriesDefinitions().add(sdY);
        sdY.getSeriesPalette().update(ColorDefinitionImpl.BLACK());
        sdY.getSeries().add(ss);
    }

}
