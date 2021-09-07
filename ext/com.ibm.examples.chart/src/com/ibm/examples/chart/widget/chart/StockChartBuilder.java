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

import org.eclipse.birt.chart.extension.datafeed.StockEntry;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.DateTimeDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.StockDataSet;
import org.eclipse.birt.chart.model.data.impl.DateTimeDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.StockDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.type.StockSeries;
import org.eclipse.birt.chart.model.type.impl.StockSeriesImpl;
import org.eclipse.birt.chart.util.CDateTime;

/**
 * Builds stock chart.
 * 
 * @author Qi Liang
 */
public class StockChartBuilder extends AbstractChartWithAxisBuilder {

    /**
     * Constructor.
     */
    public StockChartBuilder() {
        super(null);
        title = "Stock Chart";
        xTitle = "Date";
        yTitle = "Price";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildXAxis()
     */
    protected void buildXAxis() {

        // Create X axis
        xAxis = ((ChartWithAxesImpl) chart).getPrimaryBaseAxes()[0];

        // Set title
        xAxis.getTitle().setVisible(true);
        xAxis.getTitle().getCaption().setColor(ColorDefinitionImpl.BLUE());
        xAxis.getTitle().getCaption().setValue(xTitle);
        xAxis.setTitlePosition(Position.BELOW_LITERAL);

        // Set label
        xAxis.getLabel().setVisible(true);
        xAxis.getLabel().getCaption().setColor(ColorDefinitionImpl.BLUE());
        xAxis.getLabel().getCaption().getFont().setRotation(30);
        xAxis.setLabelPosition(Position.BELOW_LITERAL);

        // Set the axis type and origin type
        xAxis.setType(AxisType.DATE_TIME_LITERAL);
        xAxis.getOrigin().setType(IntersectionType.MIN_LITERAL);

        // Set major grid
        xAxis.getMajorGrid().setTickStyle(TickStyle.ABOVE_LITERAL);
        xAxis.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl
                .create(255, 196, 196));
        xAxis.getMajorGrid().getLineAttributes()
                .setStyle(LineStyle.DOTTED_LITERAL);
        xAxis.getMajorGrid().getLineAttributes().setVisible(true);

        xAxis.setCategoryAxis(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildYAxis()
     */
    protected void buildYAxis() {

        // Create Y axis
        yAxis = ((ChartWithAxesImpl) chart).getPrimaryOrthogonalAxis(xAxis);

        // Set title
        yAxis.getTitle().setVisible(true);
        yAxis.getTitle().getCaption().setValue(yTitle);
        yAxis.getTitle().getCaption().setColor(ColorDefinitionImpl.BLUE());
        yAxis.setTitlePosition(Position.LEFT_LITERAL);

        // Set label
        yAxis.getLabel().setVisible(true);
        yAxis.getLabel().getCaption().setColor(ColorDefinitionImpl.BLUE());
        yAxis.setLabelPosition(Position.LEFT_LITERAL);

        // Set scale
        yAxis.getScale().setMin(NumberDataElementImpl.create(5));
        yAxis.getScale().setMax(NumberDataElementImpl.create(20));
        yAxis.getScale().setStep(1);

        // Set major grid
        yAxis.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl
                .create(196, 196, 255));
        yAxis.getMajorGrid().getLineAttributes()
                .setStyle(LineStyle.DOTTED_LITERAL);
        yAxis.getMajorGrid().getLineAttributes().setVisible(true);
        yAxis.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);

        // Set axis type and origin type
        yAxis.setType(AxisType.LINEAR_LITERAL);
        yAxis.getOrigin().setType(IntersectionType.MIN_LITERAL);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildXSeries()
     */
    protected void buildXSeries() {

        // X series data set
        DateTimeDataSet dsDateValues = DateTimeDataSetImpl
                .create(new CDateTime[] { new CDateTime(2006, 6, 13),
                        new CDateTime(2006, 6, 14), new CDateTime(2006, 6, 15),
                        new CDateTime(2006, 6, 16), new CDateTime(2006, 6, 17),
                        new CDateTime(2006, 6, 18), new CDateTime(2006, 6, 19),
                        new CDateTime(2006, 6, 20) });

        // Create one series and assign data set to it
        Series seBase = SeriesImpl.create();
        seBase.setDataSet(dsDateValues);

        // Add the series to seriers definition
        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(1);
        xAxis.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seBase);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildYSeries()
     */
    protected void buildYSeries() {

        // Y series data set
        StockDataSet dsStockValues = StockDataSetImpl
                .create(new StockEntry[] { new StockEntry(13, 12, 15, 14),
                        new StockEntry(14, 13, 15, 15),
                        new StockEntry(15, 15, 16, 16),
                        new StockEntry(16, 14, 18, 17),
                        new StockEntry(17, 14, 17, 15),
                        new StockEntry(15, 11, 15, 12),
                        new StockEntry(12, 10, 18, 13),
                        new StockEntry(13, 13, 14, 13) });

        // Create stock seriers
        StockSeries ss = (StockSeries) StockSeriesImpl.create();
        ss.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
        ss.setDataSet(dsStockValues);

        // Add the series to seriers definition
        SeriesDefinition sdY1 = SeriesDefinitionImpl.create();
        sdY1.getSeriesPalette().update(ColorDefinitionImpl.CYAN());
        yAxis.getSeriesDefinitions().add(sdY1);
        sdY1.getSeries().add(ss);

    }

}
