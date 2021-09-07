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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.ibm.examples.chart.data.DataSet;
import com.ibm.examples.chart.widget.chart.ChartTypeConstants;

/**
 * Describes the input for chart editor. This input contains the chart type, and
 * data.
 * 
 * @author Qi Liang
 */
public class ChartEditorInput implements IEditorInput {

    /**
     * The data set containing the data for chart
     */
    private DataSet dataSet = null;

    /**
     * The chart type
     */
    private int type = ChartTypeConstants.BAR;

    /**
     * Constructor.
     * 
     * @param dataSet
     *            the data set for chart
     * @param type
     *            chart type
     */
    public ChartEditorInput(DataSet dataSet, int type) {
        this.dataSet = dataSet;
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#exists()
     */
    public boolean exists() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
     */
    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#getName()
     */
    public String getName() {
        return "Chart";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     */
    public IPersistableElement getPersistable() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     */
    public String getToolTipText() {
        return "Chart";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
        return null;
    }

    /**
     * Gets chart type.
     * 
     * @return chart type.
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the data set.
     * 
     * @return data set
     */
    public DataSet getDataSet() {
        return dataSet;
    }

}
