package swt.canvas.chart.birt;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.device.swt.SwtRendererImpl;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.birt.core.framework.PlatformConfig;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import swt.canvas.core.Engine;

public class ChartEngine extends Engine {

	private Image fCachedImage;
	/**
	 * The device render for rendering chart.
	 */
	protected static IDeviceRenderer render = null;
	/**
	 * The chart instance.
	 */
	protected Chart chart = null;
	/**
	 * The chart state.
	 */
	protected GeneratedChartState state = null;

	public static void main(String[] args) {
		new ChartEngine().run();
	}

	@Override
	public void draw() {
		final Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
		buildCachedImage(rect);
		drawImage(fCachedImage, 0, 0);

	}

	@Override
	public void init() {
		chart = ChartWithAxesImpl.create();
		initRenderer();
	}

	@Override
	public void resized(int width, int height) {
		disposeImage();
	}

	private void initRenderer() {
		// try {
		PlatformConfig pc = new PlatformConfig();
		pc.setProperty(PluginSettings.PROP_STANDALONE, true);
		render = new SwtRendererImpl(PluginSettings.instance(pc));
		// PluginSettings ps = PluginSettings.instance(pc);
		// render = ps.getDevice("dv.SWT");
		// } catch (ChartException ex) {
		// ex.printStackTrace();
		// }
	}

	/**
	 * Builds the chart state. This method should be call when data is changed.
	 */
	private void buildChart() {
		Bounds bo = BoundsImpl.create(0, 0, getWidth(), getHeight());
		int resolution = render.getDisplayServer().getDpiResolution();
		bo.scale(72d / resolution);
		try {
			Generator gr = Generator.instance();
			createCachedImage(new Rectangle(0, 0, getWidth(), getHeight()));
			state = gr.build(render.getDisplayServer(), chart, bo, null, null, null);
		} catch (ChartException ex) {
			ex.printStackTrace();
		}
	}

	private void buildCachedImage(final Rectangle rect) {
		if (fCachedImage == null) {
			createCachedImage(rect);
			buildChart();
			drawToCachedImage(rect);
		}
	}

	private GC createCachedImage(Rectangle size) {
		disposeImage();
		fCachedImage = new Image(getDisplay(), size.width, size.height);
		GC gc = new GC(fCachedImage);
		render.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, gc);
		return gc;
	}  
	
	/**
	 * Draws the chart onto the cached image in the area of the given
	 * <code>Rectangle</code>.
	 * 
	 * @param size the area to draw
	 */
	private void drawToCachedImage(Rectangle size) {
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

	private void disposeImage() {
		if (fCachedImage != null) {
			fCachedImage.dispose();
			fCachedImage = null;
		}
	}

}
