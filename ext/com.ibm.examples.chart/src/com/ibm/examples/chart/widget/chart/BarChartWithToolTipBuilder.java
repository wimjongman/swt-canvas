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

import org.eclipse.birt.chart.model.attribute.ActionType;
import org.eclipse.birt.chart.model.attribute.DataPoint;
import org.eclipse.birt.chart.model.attribute.DataPointComponentType;
import org.eclipse.birt.chart.model.attribute.TriggerCondition;
import org.eclipse.birt.chart.model.attribute.impl.DataPointComponentImpl;
import org.eclipse.birt.chart.model.attribute.impl.JavaNumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.attribute.impl.TooltipValueImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.ActionImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TriggerImpl;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;

import com.ibm.examples.chart.data.DataSet;

/**
 * Builds bar chart with tool tip.
 * 
 * @author Qi Liang
 */
public class BarChartWithToolTipBuilder extends BarChartBuilder {

    /**
     * Constructor.
     * 
     * @param dataSet
     *            data for chart
     */
    public BarChartWithToolTipBuilder(DataSet dataSet) {
        super(dataSet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.BarChartBuilder#buildYSeries()
     */
    protected void buildYSeries() {
        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(dataSet
                .getTechnitians());

        BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
        bs1.setDataSet(orthoValuesDataSet1);
        bs1.setRiserOutline(null);

        DataPoint dp = bs1.getDataPoint();
        dp.getComponents().clear();
        dp.setPrefix("(");
        dp.setSuffix(")");

        // Chart doesn't support format specifier for text, so we use
        // JavaNumberFormatSpecifierImpl here.
        dp.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.BASE_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0")));
        dp.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0")));

        // Add the mouse over trigger
        bs1.getTriggers().add(TriggerImpl
                .create(TriggerCondition.ONMOUSEOVER_LITERAL, ActionImpl
                        .create(ActionType.SHOW_TOOLTIP_LITERAL,
                                TooltipValueImpl.create(500, null))));

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        yAxis.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(bs1);
    }

}
