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
package com.ibm.examples.chart.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.ibm.examples.chart.data.DataSet;
import com.ibm.examples.chart.editors.ChartEditorInput;

/**
 * Creates one chart in editor. 
 * 
 * @author Qi Liang
 */
public class SampleAction extends Action {

    private IWorkbenchWindow window;

    /**
     * Chart type
     */
    private int type;

    /**
     * The constructor.
     */
    public SampleAction(IWorkbenchWindow window, int type) {
        this.window = window;
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.IAction#run()
     */
    public void run() {
        try {
            IEditorInput input = new ChartEditorInput(DataSet.getInstance(),
                    type);
            window.getActivePage().openEditor(input,
                    "com.ibm.examples.chart.editors.SampleEditor");
        } catch (PartInitException e) {
            e.printStackTrace();
        }
    }

}