package swt.canvas.samples.flowfield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Vector;

import swt.canvas.core.Engine;

public class FlowField extends Engine {

	public static void main(String[] args) {
		new FlowField().run();
	}

	private HashMap<Point, Double> fFlowField = new LinkedHashMap<>();
	private OpenSimplex2S fNoise = new OpenSimplex2S(System.currentTimeMillis());
	int fNumberOfParticles = 20;

	private List<Vector> fParticles = new ArrayList<>();
	Vector fPreviousParticle = null;

	int fResolution = 20;
	double thirdD = 0.1;

	boolean flop = true;

	@Override
	public void draw() {
		drawString(getFPS() + "", 0, 0);
		fParticles.forEach(particle -> drawParticle(particle));
		drawFlowField();
		updateFlowField(1);
	}

	@Override
	public void mouseClick(int x, int y) {
		fParticles.add(new Vector(x, y));
	}

	private void updateFlowField(int step) {
		thirdD += .01;
		int height = getHeight();
		int width = getWidth();
		int start = new Random().nextInt(step);
		for (int iy = start; iy < height / fResolution; iy += step) {
			for (int ix = start; ix < width / fResolution; ix += step) {
				double noise = fNoise.noise3_Classic((ix + .1) / 100, (iy + .1) / 100, thirdD);
				Point key = new Point(ix, iy);
				fFlowField.remove(key);
				fFlowField.put(key, noise);
			}
		}
	}

	private void drawFlowField() {
		fFlowField.forEach((point, vector) -> {
			double angle = vector * TWO_PI;
			int y = (int) (fResolution / 2 * Math.cos(angle));
			int x = (int) (fResolution / 2 * Math.sin(angle));
			drawLine(point.x * fResolution + (fResolution / 2), point.y * fResolution + (fResolution / 2),
					point.x * fResolution + (fResolution / 2) + x, point.y * fResolution + (fResolution / 2) + y);
		});
	}

	private void drawParticle(Vector particle) {

		drawCircle((int) particle.x, (int) particle.y, 5);

		Point p = new Point((int) particle.x / fResolution, (int) particle.y / fResolution);
		double angle = fFlowField.get(p) * TWO_PI;
		int x = (int) (2 * Math.sin(angle));
		int y = (int) (2 * Math.cos(angle));
		Vector v = new Vector(x, y);
		Vector newParticle = particle.getAdded(v);
		particle.x = newParticle.x;
		particle.y = newParticle.y;
		if (isOffScreen(particle)) {
			particle.x = new Random().nextInt(getWidth() - fResolution) + fResolution / 2;
			particle.y = new Random().nextInt(getHeight() - fResolution) + fResolution / 2;
		} else {
			fPreviousParticle = particle;
		}
	}

	@Override
	public void resized(int width, int height) {
		fParticles.clear();
		for (int ix = 0; ix < fNumberOfParticles; ix++) {
			fParticles
					.add(new Vector(new Random().nextInt(200) + fResolution, new Random().nextInt(200) + fResolution));
		}
		updateFlowField(1);
	}

	@Override
	public void init() {
		setHeight(400);
		setWidth(400);
		setFPS(50);
	}

	private boolean isOffScreen(Vector particle) {
		return particle.x < fResolution || particle.x > (getWidth() - fResolution) || particle.y < fResolution
				|| particle.y > getHeight() - fResolution;
	}
}
