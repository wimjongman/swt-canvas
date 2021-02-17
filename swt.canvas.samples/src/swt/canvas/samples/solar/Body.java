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
package swt.canvas.samples.solar;

import java.util.Random;

public class Body {

	int x;
	int y;
	int r;
	int d;
	private Body other;
	private int orbit;
	private double speed;
	private double alpha;

	public Body(int r, int orbit, double speed) {
		super();
		this.r = r;
		this.orbit = orbit;
		this.speed = speed;
		this.d = 0;
		setAlpha();
	}

	private void setAlpha() {
		this.alpha = new Random().nextDouble();
	}

	public Body(Body other, int r, int d, double noise) {
		super();
		this.r = r;
		this.d = d;
		this.speed = noise;
		this.orbit = other.r;
		this.other = other;
		setAlpha();
		lock();
	}

	public void lock() {
		if (other == null) {
			return;
		}
		x = other.x + d;
		y = other.y + d;
	}

	public void spin() {
		if (other == null) {
			x = (int) ((orbit + d) * Math.cos(alpha));
			y = (int) ((orbit + d) * Math.sin(alpha));
		} else {
			x = other.x + (int) ((r + d) * Math.cos(alpha));
			y = other.y + (int) ((r + d) * Math.sin(alpha));
		}
		
		alpha += this.speed;
		if(alpha > (2*Math.PI)) {
			alpha = .001;
		}
	}

}
