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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.swt.graphics.Point;

public class ImageGenerator {

	public static void generateImage(Chart chart, String filename) {
		Graphics gc = null;
		try {
			PluginSettings ps = PluginSettings.instance();
			IDeviceRenderer render = ps.getDevice("dv.PNG");
			render.setProperty(IDeviceRenderer.FILE_IDENTIFIER, filename);
			// Create the AWT image to output
			Point size = new Point(800, 600);
			Image cachedImage = new BufferedImage(size.x, size.y,
					BufferedImage.TYPE_INT_RGB);
			
			gc = cachedImage.getGraphics();
			render.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, gc);

			Bounds bo = BoundsImpl.create(0, 0, size.x, size.y);
			int resolution = render.getDisplayServer().getDpiResolution();
			bo.scale(72d / resolution);
			

			Generator gr = Generator.instance();
			GeneratedChartState state = gr.build(render.getDisplayServer(),
					chart, bo, null, null, null);
			gr.render(render, state);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (gc != null)
				gc.dispose();
		}
	}
}
