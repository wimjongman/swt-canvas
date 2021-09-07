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
package swt.canvas.core.coordinate;

/**
 * Maps the graphics coordinates to the Cartesian coordinates.
 *
 */
public class Cartesian extends BaseCoordinateSystem {

	public Cartesian(int width, int height) {
		super(width, height);
	}

	/**
	 * Converts the graphics system's y into the Cartesian y
	 * 
	 * @param x the graphics system's y
	 * @return the Cartesian y
	 */
	@Override
	public int gy(int y) {
		return getSectionHeight() - y;
	}

	/**
	 * Converts the graphics system's x into the Cartesian x
	 * 
	 * @param x the graphics system's x
	 * @return the Cartesian x
	 */
	@Override
	public int gx(int x) {
		return -getSectionWidth() + x;
	}

	/**
	 * Converts the Cartesian y into the graphics system's y
	 * 
	 * @param y the Cartesian y
	 * @return the transformed y
	 */
	@Override
	public int cy(int y) {
		return getSectionHeight() - y;
	}

	/**
	 * Converts the Cartesian x into the graphics system's x
	 * 
	 * @param x the Cartesian x
	 * @return the transformed x
	 */
	@Override
	public int cx(int x) {
		return getSectionWidth() + x;
	}

	@Override
	public int getSectionWidth() {
		return (fWidth / 2);
	}

	@Override
	public int getSectionHeight() {
		return (fHeight / 2);
	}
}
