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
package com.ibm.examples.chart.widget;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * The canvas to show chart.
 * 
 * @author Qi Liang
 */
public class ChartCanvas extends Canvas {

	/**
	 * The device render for rendering chart.
	 */
	protected IDeviceRenderer render = null;

	/**
	 * The chart instantce.
	 */
	protected Chart chart = null;

	/**
	 * The chart state.
	 */
	protected GeneratedChartState state = null;

	/**
	 * The image which caches the chart image to improve drawing performance.
	 */
	private Image cachedImage = null;

	/**
	 * Constructs one canvas containing chart.
	 * 
	 * @param parent a composite control which will be the parent of the new
	 *               instance (cannot be null)
	 * @param style  the style of control to construct
	 */
	public ChartCanvas(Composite parent, int style) {
		super(parent, style);

		// initialize the SWT rendering device
		try {
			PluginSettings ps = PluginSettings.instance();
			render = ps.getDevice("dv.SWT");
		} catch (ChartException ex) {
			ex.printStackTrace();
		}

		addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {

				Composite co = (Composite) e.getSource();
				final Rectangle rect = co.getClientArea();

				if (cachedImage == null) {
					createCachedImage(rect);
					buildChart();
					drawToCachedImage(rect);
				}
				e.gc.drawImage(cachedImage, 0, 0, cachedImage.getBounds().width, cachedImage.getBounds().height, 0, 0,
						rect.width, rect.height);

			}
		});

		addControlListener(new ControlAdapter() {

			public void controlResized(ControlEvent e) {
				buildChart();
				disposeImage();
			}
		});
	}

	/**
	 * Builds the chart state. This method should be call when data is changed.
	 */
	private void buildChart() {
		Point size = getSize();
		Bounds bo = BoundsImpl.create(0, 0, size.x, size.y);
		int resolution = render.getDisplayServer().getDpiResolution();
		bo.scale(72d / resolution);
		try {
			Generator gr = Generator.instance();
			createCachedImage(new Rectangle(size.x, size.y, size.x, size.y));
			state = gr.build(render.getDisplayServer(), chart, bo, null, null, null);
		} catch (ChartException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Draws the chart onto the cached image in the area of the given
	 * <code>Rectangle</code>.
	 * 
	 * @param size the area to draw
	 */
	public void drawToCachedImage(Rectangle size) {
		GC gc = null;
		try {
			gc = createCachedImage(size);

			Generator gr = Generator.instance();

			gr.render(render, state);
		} catch (ChartException ex) {
			ex.printStackTrace();
		} finally {
			if (gc != null)
				gc.dispose();
		}
	}

	private GC createCachedImage(Rectangle size) {
		GC gc;
		disposeImage();
		cachedImage = new Image(Display.getCurrent(), size.width, size.height);

		gc = new GC(cachedImage);
		render.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, gc);
		return gc;
	}

	private void disposeImage() {
		if (cachedImage != null) {
			cachedImage.dispose();
			cachedImage = null;
		}
	}

	/**
	 * Returns the chart which is contained in this canvas.
	 * 
	 * @return the chart contained in this canvas.
	 */
	public Chart getChart() {
		return chart;
	}

	/**
	 * Sets the chart into this canvas. Note: When the chart is set, the cached
	 * image will be dopped, but this method doesn't reset the flag
	 * <code>cachedImage</code>.
	 * 
	 * @param chart the chart to set
	 */
	public void setChart(Chart chart) {
		disposeImage();

		this.chart = chart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	public void dispose() {
		disposeImage();
		super.dispose();
	}

}
