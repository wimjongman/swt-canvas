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
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
import org.eclipse.birt.chart.model.component.MarkerLine;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.MarkerLineImpl;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.type.AreaSeries;
import org.eclipse.birt.chart.model.type.impl.AreaSeriesImpl;

import com.ibm.examples.chart.data.DataSet;

/**
 * Builds area chart.
 * 
 * @author Qi Liang
 */
public class AreaChartBuilder extends AbstractChartWithAxisBuilder {

    /**
     * Constructor.
     *
     * @param dataSet data for chart
     */
    public AreaChartBuilder(DataSet dataSet) {
        super(dataSet);
        title = "Area Chart";
        xTitle = "Cities";
        yTitle = "Staff";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.AbstractChartBuilder#buildXAxis()
     */
    protected void buildXAxis() {
        xAxis = ((ChartWithAxes) chart).getPrimaryBaseAxes()[0];
        xAxis.setType(AxisType.TEXT_LITERAL);
        xAxis.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
        xAxis.getMajorGrid()
                .setLineAttributes(LineAttributesImpl
                        .create(ColorDefinitionImpl.BLUE(),
                                LineStyle.SOLID_LITERAL,
                                1));
        xAxis.getOrigin().setType(IntersectionType.MIN_LITERAL);
        xAxis.getTitle().getCaption().setValue(xTitle);
        xAxis.getTitle().setVisible(true);
        xAxis.getLabel().setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.AbstractChartBuilder#buildYAxis()
     */
    protected void buildYAxis() {
        yAxis = ((ChartWithAxes) chart).getPrimaryOrthogonalAxis(xAxis);
        yAxis.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
        yAxis.getMajorGrid()
                .setLineAttributes(LineAttributesImpl
                        .create(ColorDefinitionImpl.BLACK(),
                                LineStyle.SOLID_LITERAL,
                                1));
        yAxis.getMinorGrid().getLineAttributes().setVisible(true);
        yAxis.setPercent(false);
        yAxis.getTitle().getCaption().setValue(yTitle);
        yAxis.getTitle().setVisible(true);
        yAxis.getTitle().getCaption().getFont().setRotation(90);
        yAxis.getLabel().setVisible(true);

        MarkerLine ml = MarkerLineImpl.create(yAxis, NumberDataElementImpl
                .create(2));
        yAxis.getMarkerLines().add(ml);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.AbstractChartBuilder#buildXSeries()
     */
    protected void buildXSeries() {
        TextDataSet categoryValues = TextDataSetImpl
                .create(dataSet.getCities());

        Series seCategory = SeriesImpl.create();
        seCategory.setDataSet(categoryValues);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(0);
        xAxis.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);
    }

    /*
     *  (non-Javadoc)
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#buildYSeries()
     */
    protected void buildYSeries() {
        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(dataSet
                .getTechnitians());
        NumberDataSet orthoValuesDataSet2 = NumberDataSetImpl.create(dataSet
                .getSalers());

        AreaSeries as1 = (AreaSeries) AreaSeriesImpl.create();
        as1.setSeriesIdentifier("Series 1");
        as1.setDataSet(orthoValuesDataSet1);
        as1.setTranslucent(true);
        as1.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
        as1.getLabel().setVisible(true);

        AreaSeries as2 = (AreaSeries) AreaSeriesImpl.create();
        as2.setSeriesIdentifier("Series 2");
        as2.setDataSet(orthoValuesDataSet2);
        as2.setTranslucent(true);
        as2.getLineAttributes().setColor(ColorDefinitionImpl.PINK());
        as2.getLabel().setVisible(true);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        sdY.getSeriesPalette().update(1);
        yAxis.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(as1);
        sdY.getSeries().add(as2);

    }

}
