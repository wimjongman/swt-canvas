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
package swt.canvas.core;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import swt.canvas.core.Layout.Cell;
import swt.canvas.core.coordinate.BaseCoordinateSystem;

public abstract class Engine {
	/**
	 * The bottom edge of the canvas
	 */
	public static int BOTTOM = 0;

	/**
	 * The left edge of the canvas
	 */
	public static int LEFT = 0;

	/**
	 * Pi
	 */
	public static final double PI = Math.PI;

	/**
	 * The right edge of the canvas
	 */
	public static int RIGHT = 0;

	/**
	 * The top edge of the canvas
	 */
	public static int TOP = 0;

	/**
	 * Two Pi
	 */
	public static final double TWO_PI = Math.PI * 2;

	protected Canvas fCanvas;
	protected Composite fCanvasCard;
	private BaseCoordinateSystem fCoordinateSystem = null;
	protected Composite fDeck;
	private Display fDisplay;

	/** The time the draw method was last called */
	protected long fLastDrawTimeStamp;

	/** The current frames per second */
	protected int fFPS = 0;
	protected int fFPSCounter = 0;
	protected int fFPSDelay = 20;

	private GC fGC;

	private int fHeight = 600;
	private int fLineWidth = 1;

	private boolean fMouseSingleClickCancel;

	protected StackLayout fStackLayout;

	protected long fTimeStamp;

	private int fWidth = 800;

	private Image fPushPopBuffer;

	private int fLastPointX, fLastPointY;

	/** between 0 (transparent) and 255 (opaque). */
	private int fAlpha = 255;

	private Layout fLayout;

	private Transform fTransform;

	public Engine() {
	}

	private void _loop() {
		if (System.currentTimeMillis() > fLastDrawTimeStamp) {
			fFPS = fFPSCounter;
			fFPSCounter = 0;
			fLastDrawTimeStamp = System.currentTimeMillis() + 1000;
		} else {
			fFPSCounter++;
		}

		if (!fCanvas.isDisposed()) {
			fCanvas.redraw();
		}
	}

	private void _mouseClick(int gx, int gy) {
		if (fMouseSingleClickCancel) {
			fMouseSingleClickCancel = false;
		} else {
			fDisplay.timerExec(100, () -> {
				if (!fMouseSingleClickCancel) {
					mouseClick(gx, gy);
				}
			});
		}
	}

	public void _mouseDoubleClick(int x, int y) {
		fMouseSingleClickCancel = true;
		mouseDoubleClick(x, y);
	}

	private BaseCoordinateSystem _newInstance(Class<? extends BaseCoordinateSystem> clazz) {
		try {
			return clazz.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE }).newInstance(fWidth, fHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BaseCoordinateSystem(fWidth, fHeight);
	}

	private void _resized() {
		fCanvas.getShell().layout();
		fHeight = fCanvas.getSize().y;
		fWidth = fCanvas.getSize().x;
		LEFT = -fWidth / 2;
		RIGHT = fWidth / 2;
		TOP = fHeight / 2;
		BOTTOM = -fHeight / 2;
		fPushPopBuffer = null;
		_setCoordinateSystem();
		resized(fWidth, fHeight);
	}

	private void _recreatePushImage() {
		if (fPushPopBuffer != null) {
			fPushPopBuffer.dispose();
			fPushPopBuffer = null;
		}
		fPushPopBuffer = new Image(fDisplay, fWidth, fHeight);
		fPushPopBuffer.getImageData().type = SWT.IMAGE_BMP;
		// fPushImage = new Image(fDisplay, fWidth, fHeight);
	}

	private void _setCoordinateSystem() {
		if (fCoordinateSystem == null || fCoordinateSystem.getClass() == BaseCoordinateSystem.class) {
			fCoordinateSystem = new BaseCoordinateSystem(fWidth, fHeight);
		} else {
			fCoordinateSystem = _newInstance(fCoordinateSystem.getClass());
		}
	}

	/**
	 * Create contents of the window.
	 * 
	 * @param shell
	 */
	protected void createContents(Shell shell) {
		shell.setSize(fWidth, fHeight);
		shell.setText("SWT Application");

		int columns = fLayout.getColumns();
		shell.setLayout(new GridLayout(columns, false));

		fDeck = new Composite(shell, SWT.NONE);
		fStackLayout = new StackLayout();
		fDeck.setLayout(fStackLayout);
		fDeck.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		fCanvasCard = new Composite(fDeck, SWT.BORDER);
		fCanvasCard.setLayout(new FillLayout(SWT.HORIZONTAL));

		fCanvas = new Canvas(fCanvasCard, SWT.DOUBLE_BUFFERED);
		fCanvas.setBackground(fCanvas.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		fCanvas.setForeground(fCanvas.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		fCanvas.addListener(SWT.MouseUp, e -> _mouseClick(gx(e.x), gy(e.y)));
		fCanvas.addListener(SWT.MouseDoubleClick, e -> _mouseDoubleClick(gx(e.x), gy(e.y)));

		List<Cell> neighbours = fLayout.getNeighbours(fLayout.getCell(Layout.CANVAS), Layout.RIGHT);
		for (Cell cell : neighbours) {
			Composite parent = new Composite(shell, SWT.NONE);
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			parent.setLayoutData(gridData);
			parent.setLayout(new FillLayout());
			createCell(cell, parent);
		}

		neighbours = fLayout.getNeighbours(fLayout.getCell(Layout.CANVAS), Layout.BOTTOM);
		int left = columns - neighbours.size() + 1;
		for (Cell cell : neighbours) {
			Composite parent = new Composite(shell, SWT.NONE);
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, left, 1);
			parent.setLayoutData(gridData);
			if (left > 1) {
				left = 1;
			}
			createCell(cell, parent);
		}

		fStackLayout.topControl = fCanvasCard;
		fDeck.layout();

	}

	/**
	 * This method creates the paint listener.
	 */
	protected void createPaintListener() {
		fCanvas.addListener(SWT.Paint, e -> {
			e.gc.setAdvanced(true);
			e.gc.setAntialias(SWT.ON);
			fGC = e.gc;
			fGC.setLineWidth(fLineWidth);
			fGC.setAlpha(fAlpha);
			draw();
			disposeTransform();
		});
	}

	private void disposeTransform() {
		if(fTransform != null) {
			fTransform.dispose();
		}
	}

	public void translate(float x, float y) {
		disposeTransform();
		fTransform = new Transform(fDisplay);
		fTransform.translate(x, y);
		fGC.setTransform(fTransform);
	}

	/**
	 * Converts the current coordinates system's x into the graphics system's x
	 * 
	 * @param x the current coordinates system's x
	 * @return the transformed x
	 */
	public int cx(int x) {
		return fCoordinateSystem.cx(x);
	}

	/**
	 * Converts the current coordinates system's y into the graphics system's y
	 * 
	 * @param y the current coordinates system's y
	 * @return the transformed y
	 */
	public int cy(int y) {
		return fCoordinateSystem.cy(y);
	}

	/**
	 * Draw the canvas.
	 * 
	 */
	public abstract void draw();

	/**
	 * Draws a circle by delegating to {@link #drawOval(int, int, int, int)}.
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @return
	 * 
	 * @return this object, for fluent method chaining.
	 */
	public Engine drawCircle(int x, int y, int radius) {
		drawOval(x, y, radius * 2, radius * 2);
		return this;
	}

	/**
	 * Draws a line from x1, y1 to x2, y2.
	 * 
	 * Remembers the last point x2 and y2 to be used in subsequent line drawing
	 * operations.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * 
	 * @return this object, for fluent method chaining.
	 */
	public Engine drawLine(int x1, int y1, int x2, int y2) {
		fGC.drawLine(cx(x1), cy(y1), cx(x2), cy(y2));
		fLastPointX = x2;
		fLastPointY = y2;
		return this;
	}

	/**
	 * Draws a line from the last point to x and y. The last point is determined by
	 * any previous line draw operation and is 0,0 if no previous line was drawn..
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return this object, for fluent method chaining.
	 */
	public Engine drawLine(int x, int y) {
		drawLine(fLastPointX, fLastPointY, x, y);
		return this;
	}

	/**
	 * Draws an oval with x and y as the center.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 * @return this object, for fluent method chaining.
	 */
	public Engine drawOval(final int x, final int y, final int width, final int height) {
		fGC.drawOval(cx(x - width / 2), cy(y + height / 2), width, height);
		return this;
	}

	/**
	 * Draws a rectangle where x,y is the bottom left corner of the rectangle.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * 
	 * @return this object, for fluent method chaining.
	 */
	public Engine drawRectangle(final int x, final int y, final int width, final int height) {
		fGC.drawRectangle(cx(x), cy(y), width, -height);
		return this;
	}

	/**
	 * Draws a rounded rectangle where x,y is the bottom left corner of the
	 * rectangle.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * 
	 * @return this object, for fluent method chaining.
	 */
	public Engine drawRectangle(final int x, final int y, final int width, final int height, final int arcWidth,
			final int arcHeight) {
		fGC.drawRoundRectangle(cx(x), cy(y), width, -height, arcWidth, arcHeight);
		return this;
	}

	/**
	 * Draws a point at x,y.
	 * 
	 * @param x
	 * @param y
	 * @return
	 * 
	 * @return this object, for fluent method chaining.
	 */
	public Engine drawPoint(int x, int y) {
		fGC.drawPoint(x, y);
		return this;
	}

	/**
	 * Draws a string on x,y on the current coordinate system
	 * 
	 * @param string
	 * @param x
	 * @param y
	 * @return
	 * 
	 * @return this object, for fluent method chaining.
	 */
	public Engine drawString(String string, int x, int y) {
		fGC.drawString(string, cx(x), cy(y));
		return this;
	}

	/**
	 * @return the current FPS
	 */
	public int getFPS() {
		return fFPS;
	}

	/**
	 * @return the current height of the canvas
	 */
	public int getHeight() {
		return fHeight;
	}

	/**
	 * @return the current width of the canvas
	 */
	public int getWidth() {
		return fWidth;
	}

	/**
	 * Converts the graphics system's x into the current coordinates system's x
	 * 
	 * @param x the graphics system's x
	 * @return the current coordinates system's x
	 */
	public int gx(int x) {
		return fCoordinateSystem.gx(x);
	}

	/**
	 * Converts the graphics system's y into the current coordinates system's y
	 * 
	 * @param x the graphics system's y
	 * @return the current coordinates system's y
	 */
	public int gy(int y) {
		return fCoordinateSystem.gy(y);
	}

	/**
	 * Initialisation.
	 */
	public abstract void init();

	/**
	 * Override this method if you are interested to capture the location of a mouse
	 * click.
	 * 
	 * @param x current coordinates system's x
	 * @param y current coordinates system's y
	 */
	public void mouseClick(int x, int y) {
	}

	/**
	 * Override this method if you are interested to capture the location of a mouse
	 * double click.
	 * 
	 * @param x current coordinates system's x
	 * @param y current coordinates system's y
	 */
	public void mouseDoubleClick(int x, int y) {
	}

	/**
	 * Override this method if you want to be notified of canvas resize events. The
	 * default implementation does nothing, so calling super is a waste of time
	 * (literally). This method is called once immediately after the {@link #init()}
	 * method is called but before {@link #draw()} is called for the first time.
	 * 
	 * Subsequent calls are done when the canvas is resized.
	 * 
	 * @param width  the new width
	 * @param height the new height
	 */
	public void resized(int width, int height) {

	}

	/**
	 * Starts the game loop.
	 */
	public void run() {
		fDisplay = Display.getDefault();
		Shell shell = new Shell(fDisplay);
		init();
		fLayout = getLayout();
		createContents(shell);
		shell.open();
		shell.layout();
		_resized();
		createPaintListener();
		shell.addListener(SWT.Resize, e -> _resized());
		fLastDrawTimeStamp = System.currentTimeMillis() + 1000;
		while (!shell.isDisposed()) {
			fTimeStamp = System.currentTimeMillis() + fFPSDelay;
			while (fTimeStamp > System.currentTimeMillis())
				fDisplay.readAndDispatch();
			_loop();
		}
	}

	public final void setCoordinateSystem(Class<? extends BaseCoordinateSystem> system) {
		fCoordinateSystem = _newInstance(system);
	}

	/**
	 * Sets the frame rate or fps. This is the number of times per second the engine
	 * tries to call the draw routine. Please note that a high frame rate also means
	 * you have to be quick in the draw routine. The FPS may deteriorate if too much
	 * time is spent in the draw routine.
	 * 
	 * @param fps the frame rate in frames per second.
	 * @return
	 */
	public Engine setFPS(int fps) {
		fFPSDelay = 1000 / fps;
		return this;
	}

	/**
	 * The height of the window.
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		fHeight = height;
	}

	/**
	 * Sets the width of the line (stroke).
	 * 
	 * @param width the line width
	 */
	public void setLineWidth(final int width) {
		fLineWidth = width;
	}

	/**
	 * The width of the window
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		fWidth = width;
	}

	/**
	 * Pushes the current content of the canvas into a buffer.
	 * 
	 * @see #popCanvas()
	 */
	public void pushCanvas() {
		if (fPushPopBuffer == null)
			_recreatePushImage();
		fGC.copyArea(fPushPopBuffer, 0, 0);
	}

	/**
	 * Restores the canvas that was previously pushed.
	 * 
	 * @see #pushCanvas()
	 */
	public void popCanvas() {
		if (fPushPopBuffer != null) {
			fGC.drawImage(fPushPopBuffer, 0, 0);
		}
	}

	/**
	 * 
	 * Sets the alpha value which must be between 0 (transparent) and 255 (opaque).
	 * 
	 * @param alpha the new alpha value
	 */
	public Engine setAlpha(int alpha) {
		if (alpha >= 0 && alpha <= 255) {
			fAlpha = alpha;
		}
		return this;
	}

	/**
	 * @return the current alpha value
	 * @see #setAlpha(int)
	 */
	public int getAlpha() {
		return fAlpha;
	}

	/**
	 * Returns a {@link Layout} object that describes the layout of the window in
	 * terms of cells relative to the canvas cell.
	 * <p>
	 * The default implementation returns a layout with one cell (the canvas)
	 * 
	 * @return the layout of the window
	 */
	public Layout getLayout() {
		return new Layout();
	}

	/**
	 * Create the UI of the passed cell with the passed composite as parent.
	 * 
	 * @param parent the parent composite
	 */
	public void createCell(Cell cell, Composite parent) {
	}
}
