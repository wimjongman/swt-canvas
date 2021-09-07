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

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public class SaveAction extends Action {

	private Shell parent;

	private Chart chart;

	public SaveAction(Shell parent, Chart chart) {
		super("Save as PNG File...");
		this.parent = parent;
		this.chart = chart;
	}

	public void run() {
		FileDialog dialog = new FileDialog(parent, SWT.SAVE);
		String filename = dialog.open();
		if (filename != null) {
			ImageGenerator.generateImage(chart, filename);
		}
	}

}
