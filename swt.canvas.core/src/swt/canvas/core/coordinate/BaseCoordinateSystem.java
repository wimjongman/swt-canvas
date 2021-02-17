package swt.canvas.core.coordinate;

/**
 * The base class that uses the Screen Coordinate system
 */
public class BaseCoordinateSystem {

	protected int fWidth = 0;

	protected int fHeight = 0;

	public BaseCoordinateSystem(int width, int height) {
		fWidth = width;
		fHeight = height;
	}

	/**
	 * Converts the graphics system's y into the coordinate system's y
	 * 
	 * @param x the graphics system's y
	 * @return the coordinate system's y
	 */
	public int gy(int y) {
		return y;
	}

	/**
	 * Converts the graphics system's x into the coordinate system's x
	 * 
	 * @param x the graphics system's x
	 * @return the coordinate system's x
	 */
	public int gx(int x) {
		return x;
	}

	/**
	 * Converts the coordinate system's y into the graphics system's y
	 * 
	 * @param y the coordinates system's y
	 * @return the transformed y
	 */
	public int cy(int y) {
		return y;
	}

	/**
	 * Converts the coordinate system's x into the graphics system's x
	 * 
	 * @param x the coordinates system's x
	 * @return the transformed x
	 */
	public int cx(int x) {
		return x;
	}

}
