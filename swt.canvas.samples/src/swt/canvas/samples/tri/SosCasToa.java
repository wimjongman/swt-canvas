/*******************************************************************************
 * Copyright (c) 2021, W.S. Jongman
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     W.S. Jongman - initial API and implementation
 */
package swt.canvas.samples.tri;

import java.text.MessageFormat;
import java.text.NumberFormat;

import swt.canvas.core.Engine;
import swt.canvas.core.coordinate.BaseCoordinateSystem;
import swt.canvas.core.coordinate.Cartesian;

public class SosCasToa extends Engine {

	public static void main(String[] args) {
		new SosCasToa().setWidth(400).setHeight(600).run();
	}

	int radius = 100;
	double radians = 0.7;
	private boolean loop = false;
	NumberFormat threeDecs = NumberFormat.getInstance();

	@Override
	public void init() {
		setFPS(100);
		setCoordinateSystem(Cartesian.class);
		threeDecs.setMaximumFractionDigits(3);
		setLineWidth(1);
	}

	@Override
	public void draw() {

		int x = (int) (radius * Math.sin(radians));
		int y = (int) (radius * Math.cos(radians));

		drawText(x, y);

		String msg = "Single click to set a new corner, double click to stop/start the animation..";
		drawString(msg, gx(10), gy(getHeight() - 20));

		BaseCoordinateSystem cs = getCoordinateSystem();
		drawLine(-cs.sw(), 0, cs.sw(), 0);
		drawLine(0, cs.sh(), 0, -cs.sh());

		if (loop)
			radians += 0.005;

		if (radians > TWO_PI)
			radians = 0.01;

		drawCircle(0, 0, radius);

		drawLine(0, 0, x, y);
		drawLine(x, y, x, 0);
		drawLine(x, 0, 0, 0);
	}

	private void drawText(int y, int x) {
		int ypos = -10;
		String msg = MessageFormat.format("Radius: {0}", radius);
		drawString(msg, gx(10), gy(ypos += 20));
		double degrees = radians * (180 / PI);
		msg = MessageFormat.format("Angle Deg: {0}", threeDecs.format(degrees));
		drawString(msg, gx(10), gy(ypos += 20));
		msg = MessageFormat.format("Angle Rad: {0}", radians);
		drawString(msg, gx(10), gy(ypos += 20));
		msg = MessageFormat.format("x/y: {0}/{1}", x, y);
		drawString(msg, gx(10), gy(ypos += 20));
		msg = MessageFormat.format("FPS: {0}/{1}", getActualFPS(), getRequestedFPS());
		drawString(msg, gx(10), gy(ypos += 20));
	}

	@Override
	public void mouseClick(int x, int y) {
		radius = (int) Math.sqrt((x * x) + (y * y));
		radians = Math.atan((double) x / (double) y);

		if (y < 0) {
			radians += PI;
		}
		// hoek = (hoek + TWO_PI) / 2;

	}

	@Override
	public void mouseDoubleClick(int x, int y) {
		loop = !loop;
	}

}
