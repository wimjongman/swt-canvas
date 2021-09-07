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
package com.ibm.examples.chart;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.ibm.examples.chart.actions.SampleAction;
import com.ibm.examples.chart.widget.chart.ChartTypeConstants;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchWindow window = null;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui.IWorkbenchWindow)
	 */
	protected void makeActions(IWorkbenchWindow window) {
		this.window = window;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.jface.action.IMenuManager)
	 */
	protected void fillMenuBar(IMenuManager menuBar) {

		// Menu Sample
		MenuManager manager = new MenuManager("Sample", "sample");

		SampleAction action = new SampleAction(window, ChartTypeConstants.BAR);
		action.setText("Bar Chart");
		manager.add(action);

		action = new SampleAction(window, ChartTypeConstants.MULTI_BAR);
		action.setText("Multi-Bar Chart");
		manager.add(action);

		action = new SampleAction(window, ChartTypeConstants.LINE);
		action.setText("Line Chart");
		manager.add(action);

		action = new SampleAction(window, ChartTypeConstants.PIE);
		action.setText("Pie Chart");
		manager.add(action);

		action = new SampleAction(window, ChartTypeConstants.STACKED);
		action.setText("Stacked Chart");
		manager.add(action);

		action = new SampleAction(window, ChartTypeConstants.SCATTER);
		action.setText("Scatter Chart");
		manager.add(action);

		action = new SampleAction(window, ChartTypeConstants.STOCK);
		action.setText("Stock Chart");
		manager.add(action);

		action = new SampleAction(window, ChartTypeConstants.AREA);
		action.setText("Area Chart");
		manager.add(action);

		action = new SampleAction(window, ChartTypeConstants.BAR_WITH_TOOL_TIP);
		action.setText("Bar Chart With Tool Tip");
		manager.add(action);

		menuBar.add(manager);

		// Menu Test
		manager = new MenuManager("Test", "test");
		action = new SampleAction(window, ChartTypeConstants.SAMPLE);
		action.setText("Sample Chart");
		manager.add(action);

		menuBar.add(manager);
	}

}
