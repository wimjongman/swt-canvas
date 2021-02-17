package swt.canvas.samples.solar;

import java.util.ArrayList;
import java.util.List;

import swt.canvas.core.Engine;
import swt.canvas.core.coordinate.CartesianCoordinateSystem;

public class SolarSystem extends Engine {

	public static void main(String[] args) {
		new SolarSystem().run();
	}

	List<Body> bodies = new ArrayList<>();

	@Override
	public void init() {
		setCoordinateSystem(CartesianCoordinateSystem.class);
		setFPS(120);
		bodies.add(new Body(20, 190, .01));
		bodies.add(new Body(bodies.get(0), 5, 50, .005));
		bodies.add(new Body(bodies.get(1), 3, 20, .01));
		bodies.add(new Body(bodies.get(2), 1, 10, .1));
//		bodies.add(new Body(10, 90));
//		bodies.add(new Body(bodies.get(3), 6, 40));
//		bodies.add(new Body(30, 300));
//		bodies.add(new Body(bodies.get(5), 10, 40));
//		bodies.add(new Body(25, 380));
//		bodies.add(new Body(10, 400));
//		bodies.add(new Body(5, 500));
	}

	@Override
	public void draw() {

		drawCircle(0, 0, 50);
		drawString(fFPS + "", LEFT, TOP);

//		drawLine(-(getWidth() / 2), 0, (getWidth() / 2), 0);
//		drawLine(0, (getHeight() / 2), 0, -(getHeight() / 2));

		bodies.forEach(body -> {
			drawCircle(body.x, body.y, body.r);
			body.spin();
		});
	}
}
