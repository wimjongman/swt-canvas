package swt.canvas.core;

import org.eclipse.swt.graphics.Transform;

public class Transformer {

	private Engine fEngine;

	public Transformer(Engine engine) {
		fEngine = engine;
	}

	private Transform fTransform;

	/**
	 * Specifies an amount to displace objects within the coordinate system. The
	 * offsetX parameter specifies left/right translation, and the offsetY parameter
	 * specifies up/down translation.
	 * 
	 * Transformations are cumulative and apply to everything that happens after and
	 * subsequent calls to the function accumulates the effect. For example, calling
	 * translate(50, 0) and then translate(20, 0) is the same as translate(70, 0).
	 * 
	 * Previous transformations are reset based on the reset parameter.
	 * 
	 * @param offsetX x displacement
	 * @param offsetY y displacement
	 * @param reset   true to reset all previous translations
	 * 
	 * @return this
	 * 
	 */
	public Transformer translate(float offsetX, float offsetY, boolean reset) {
		reset(reset);
		fTransform = getTransform();
		fTransform.translate(offsetX, offsetY);
		fEngine.getGC().setTransform(fTransform);
		return this;
	}
	
	
	/**
	 * Specifies an amount to displace objects within the coordinate system. The
	 * offsetX parameter specifies left/right translation, and the offsetY parameter
	 * specifies up/down translation.
	 * <p>
	 * Transformations are cumulative and apply to everything that happens after.
	 * Subsequent calls to the function accumulates the effect. For example, calling
	 * translate(50, 0) and then translate(20, 0) is the same as translate(70, 0).
	 * <p>
	 * Previous transformations are reset.
	 * 
	 * @param offsetX x displacement
	 * @param offsetY y displacement
	 * 
	 * @return this
	 * 
	 */
	public Transformer translate(float offsetX, float offsetY) {
		return translate(offsetX, offsetY, true);
	}

	/**
	 * @return the current active transform object
	 */
	public Transform getTransform() {
		if (fTransform == null) {
			fTransform = new Transform(fEngine.getDisplay());
		}
		return fTransform;
	}

	private void reset(boolean restart) {
		if (restart == true) {
			dispose();
		}
	}

	public void dispose() {
		if (fTransform != null) {
			fTransform.dispose();
			fTransform = null;
		}
	}
}
