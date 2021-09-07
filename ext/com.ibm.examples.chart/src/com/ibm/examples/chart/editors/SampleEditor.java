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
package com.ibm.examples.chart.editors;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.ibm.examples.chart.actions.ImageGenerator;
import com.ibm.examples.chart.actions.SaveAction;
import com.ibm.examples.chart.widget.ChartCanvas;
import com.ibm.examples.chart.widget.ChartWithToolTipCanvas;
import com.ibm.examples.chart.widget.chart.AbstractChartBuilder;
import com.ibm.examples.chart.widget.chart.AreaChartBuilder;
import com.ibm.examples.chart.widget.chart.BarChartBuilder;
import com.ibm.examples.chart.widget.chart.BarChartWithToolTipBuilder;
import com.ibm.examples.chart.widget.chart.ChartTypeConstants;
import com.ibm.examples.chart.widget.chart.LineChartBuilder;
import com.ibm.examples.chart.widget.chart.MultiBarChartBuilder;
import com.ibm.examples.chart.widget.chart.PieChartBuilder;
import com.ibm.examples.chart.widget.chart.ScatterChartBuilder;
import com.ibm.examples.chart.widget.chart.StackedChartBuilder;
import com.ibm.examples.chart.widget.chart.StockChartBuilder;
import com.ibm.examples.chart.widget.chart.TestChartBuilder;

/**
 * The editor to show chart.
 * 
 * @author Qi Liang
 */
public class SampleEditor extends EditorPart {

    public SampleEditor() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    public void doSave(IProgressMonitor monitor) {
        // do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#doSaveAs()
     */
    public void doSaveAs() {
        // do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorPart#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     */
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setSite(site);
        setInput(input);

        if (input != null) {
            switch (((ChartEditorInput) input).getType()) {
                case ChartTypeConstants.BAR:
                    setPartName("Bar Chart");
                    break;
                case ChartTypeConstants.MULTI_BAR:
                    setPartName("Multi Bar Chart");
                    break;
                case ChartTypeConstants.LINE:
                    setPartName("Line Chart");
                    break;
                case ChartTypeConstants.PIE:
                    setPartName("Pie Chart");
                    break;
                case ChartTypeConstants.STACKED:
                    setPartName("Stacked Chart");
                    break;
                case ChartTypeConstants.SCATTER:
                    setPartName("Scatter Chart");
                    break;
                case ChartTypeConstants.STOCK:
                    setPartName("Stock Chart");
                    break;
                case ChartTypeConstants.AREA:
                    setPartName("Area Chart");
                    break;
                case ChartTypeConstants.SAMPLE:
                    setPartName("Sample Chart");
                    break;
                case ChartTypeConstants.BAR_WITH_TOOL_TIP:
                    setPartName("Bar Chart with Tool Tip");
                    break;
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#isDirty()
     */
    public boolean isDirty() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
     */
    public boolean isSaveAsAllowed() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {

        Chart chart = createChart();
        ChartEditorInput input = (ChartEditorInput) getEditorInput();

        ChartCanvas canvas = null;
        if (input.getType() == ChartTypeConstants.BAR_WITH_TOOL_TIP)
            canvas = new ChartWithToolTipCanvas(parent, SWT.NONE);
        else
            canvas = new ChartCanvas(parent, SWT.NONE);
        canvas.setChart(chart);
        canvas.setSize(800, 600);
        
        MenuManager manager = new MenuManager();
        SaveAction action = new SaveAction(getSite().getShell(), chart);
        manager.add(action);
        
        Menu menu = manager.createContextMenu(canvas);
        canvas.setMenu(menu);
    }

    /**
     * Creates one chart instance with the chart type.
     * 
     * @return the chart to show
     */
    private Chart createChart() {
        AbstractChartBuilder builder = null;

        ChartEditorInput input = (ChartEditorInput) getEditorInput();
        switch (input.getType()) {
            case ChartTypeConstants.BAR:
                builder = new BarChartBuilder(input.getDataSet());
                break;
            case ChartTypeConstants.MULTI_BAR:
                builder = new MultiBarChartBuilder(input.getDataSet());
                break;
            case ChartTypeConstants.LINE:
                builder = new LineChartBuilder(input.getDataSet());
                break;
            case ChartTypeConstants.PIE:
                builder = new PieChartBuilder(input.getDataSet());
                break;
            case ChartTypeConstants.STACKED:
                builder = new StackedChartBuilder(input.getDataSet());
                break;
            case ChartTypeConstants.SCATTER:
                builder = new ScatterChartBuilder(input.getDataSet());
                break;
            case ChartTypeConstants.STOCK:
                builder = new StockChartBuilder();
                break;
            case ChartTypeConstants.AREA:
                builder = new AreaChartBuilder(input.getDataSet());
                break;
            case ChartTypeConstants.SAMPLE:
                builder = new TestChartBuilder(input.getDataSet());
                break;
            case ChartTypeConstants.BAR_WITH_TOOL_TIP:
                builder = new BarChartWithToolTipBuilder(input.getDataSet());
                break;
            default:
                throw new RuntimeException("Unknow requested chart type.");
        }
        builder.build();
        Chart chart = builder.getChart();

        return chart;
    }



    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // Do nothing.
    }
}
