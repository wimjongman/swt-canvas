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
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.DataPoint;
import org.eclipse.birt.chart.model.attribute.DataPointComponentType;
import org.eclipse.birt.chart.model.attribute.LegendItemType;
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
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;

import com.ibm.examples.chart.data.DataSet;

/**
 * Builds one sample bar chart.
 * 
 * @author Qi Liang
 */
public class TestChartBuilder extends AbstractChartWithAxisBuilder {

    /**
     * Constructor.
     * 
     * @param dataSet
     *            data for chart
     */
    public TestChartBuilder(DataSet dataSet) {
        super(dataSet);
        title = "Bar Chart";
        xTitle = "Cities";
        yTitle = "Technicians";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#build()
     */
    public void build() {

        // 1. Create chart instance
        chart = ChartWithAxesImpl.create();
        chart.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL);

        // 2. Create plot
        chart.getPlot().setBackground(ColorDefinitionImpl.ORANGE());
        chart.getPlot().getClientArea().setBackground(ColorDefinitionImpl
                .YELLOW());

        // 3. Create legend
        chart.getLegend().setItemType(LegendItemType.CATEGORIES_LITERAL);
        chart.getLegend().setVisible(true);

        // 4. Set title
        chart.getTitle().getLabel().getCaption().setValue(title);
        chart.getTitle().getLabel().getCaption().getFont().setSize(14);
        chart.getTitle().getLabel().getCaption().getFont().setName(FONT_NAME);

        // 5. Create axises
        xAxis = ((ChartWithAxes) chart).getPrimaryBaseAxes()[0];
        xAxis.getTitle().setVisible(true);
        xAxis.getTitle().getCaption().setValue(xTitle);

        yAxis = ((ChartWithAxes) chart).getPrimaryOrthogonalAxis(xAxis);
        yAxis.getTitle().setVisible(true);
        yAxis.getTitle().getCaption().setValue(yTitle);
        yAxis.getScale().setStep(1.0);

        // 6. Create X series
        TextDataSet categoryValues = TextDataSetImpl
                .create(dataSet.getCities());

        Series seCategory = SeriesImpl.create();
        seCategory.setDataSet(categoryValues);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(1);

        xAxis.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);

        // 7. Create Y series
        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(dataSet
                .getTechnitians());

        BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
        bs1.setDataSet(orthoValuesDataSet1);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        yAxis.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(bs1);

        DataPoint dp = bs1.getDataPoint();
        dp.getComponents().clear();
        dp.setPrefix("(");
        dp.setSuffix(")");
        dp.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.BASE_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0.00")));
        dp.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0.00")));
    }
}
