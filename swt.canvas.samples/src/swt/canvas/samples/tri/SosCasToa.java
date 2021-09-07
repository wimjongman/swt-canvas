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
import swt.canvas.core.coordinate.Cartesian;

public class SosCasToa extends Engine {

	public static void main(String[] args) {
		new SosCasToa().setWidth(400).setHeight(600).run();
	}

	int radius = 100;
	double angle = 0.1;
	private boolean loop = false;
	NumberFormat twoDecs = NumberFormat.getInstance();

	@Override
	public void init() {
		setFPS(100);
		setCoordinateSystem(Cartesian.class);
		twoDecs.setMaximumFractionDigits(2);
		setLineWidth(1);
	}

	@Override
	public void draw() {

		int y = (int) (radius * Math.cos(angle));
		int x = (int) (radius * Math.sin(angle));

		String msg = MessageFormat.format("Radius {0}, angle {1}, x {2}, y {3} ", radius, twoDecs.format(angle), x, y);
		drawString(msg, getWidth() + 10, getHeight() + 10);

		drawLine(-(getWidth() / 2), 0, (getWidth() / 2), 0);
		drawLine(0, (getHeight() / 2), 0, -(getHeight() / 2));

		if (loop)
			angle += 0.005;

		if (angle > TWO_PI)
			angle = 0.01;

		drawCircle(0, 0, radius);
//		drawCircle(0, 0, x);
//		drawCircle(0, 0, y);

		drawLine(0, 0, x, y);
		drawLine(x, y, x, 0);
		drawLine(x, 0, 0, 0);
	}

	@Override
	public void mouseClick(int x, int y) {
		radius = (int) Math.sqrt((x * x) + (y * y));
		angle = Math.atan((double) x / (double) y);

		if (y < 0) {
			angle += PI;
		}
		// hoek = (hoek + TWO_PI) / 2;

	}

	@Override
	public void mouseDoubleClick(int x, int y) {
		loop = !loop;
	}

}
