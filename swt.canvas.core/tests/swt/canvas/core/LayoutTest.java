package swt.canvas.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LayoutTest {

	@Test
	public void testSimpleLayout() {
		Layout l = new Layout();
		assertTrue("Expected 1, received " + l.getColumns(), l.getColumns() == 1);
	}

	@Test
	public void testTwoColumns() {
		Layout l = new Layout();
		l.createCell("left1", Layout.LEFT, Layout.CANVAS, 10);
		assertTrue("Expected 2, received " + l.getColumns(), l.getColumns() == 2);

		l = new Layout();
		l.createCell("left1", Layout.RIGHT, Layout.CANVAS, 10);
		assertTrue("Expected 2, received " + l.getColumns(), l.getColumns() == 2);
	}

	@Test
	public void testThreeColumns() {
		Layout l = new Layout();
		l.createCell("left1", Layout.LEFT, Layout.CANVAS, 10);
		l.createCell("left2", Layout.LEFT, "left1", 10);
		assertTrue("Expected 3, received " + l.getColumns(), l.getColumns() == 3);

		l = new Layout();
		l.createCell("r1", Layout.RIGHT, Layout.CANVAS, 10);
		l.createCell("r2", Layout.RIGHT, "r1", 10);
		assertTrue("Expected 3, received " + l.getColumns(), l.getColumns() == 3);

		l = new Layout();
		l.createCell("l1", Layout.LEFT, Layout.CANVAS, 10);
		l.createCell("r1", Layout.RIGHT, Layout.CANVAS, 10);
		assertTrue("Expected 3, received " + l.getColumns(), l.getColumns() == 3);
	}

	@Test
	public void testFourColumns() {
		Layout l = new Layout();
		l.createCell("left1", Layout.LEFT, Layout.CANVAS, 10);
		l.createCell("left2", Layout.LEFT, "left1", 10);
		l.createCell("left3", Layout.LEFT, "left2", 10);
		assertTrue("Expected 4, received " + l.getColumns(), l.getColumns() == 4);

		l = new Layout();
		l.createCell("r1", Layout.RIGHT, Layout.CANVAS, 10);
		l.createCell("r2", Layout.RIGHT, "r1", 10);
		l.createCell("r3", Layout.RIGHT, "r2", 10);
		assertTrue("Expected 4, received " + l.getColumns(), l.getColumns() == 4);

		l = new Layout();
		l.createCell("l1", Layout.LEFT, Layout.CANVAS, 10);
		l.createCell("r1", Layout.RIGHT, Layout.CANVAS, 10);
		l.createCell("r2", Layout.RIGHT, "r1", 10);
		assertTrue("Expected 4, received " + l.getColumns(), l.getColumns() == 4);
	}
	
	@Test
	public void testTwoRows() {
		Layout l = new Layout();
		l.createCell("t1", Layout.TOP, Layout.CANVAS, 10);
		assertTrue("Expected 2, received " + l.getRows(), l.getRows() == 2);
		
		l = new Layout();
		l.createCell("t1", Layout.BOTTOM, Layout.CANVAS, 10);
		assertTrue("Expected 2, received " + l.getRows(), l.getRows() == 2);
	}
	
	@Test
	public void testThreeRows() {
		Layout l = new Layout();
		l.createCell("c1", Layout.TOP, Layout.CANVAS, 10);
		l.createCell("c2", Layout.TOP, "c1", 10);
		assertTrue("Expected 3, received " + l.getRows(), l.getRows() == 3);
		
		l = new Layout();
		l.createCell("c1", Layout.BOTTOM, Layout.CANVAS, 10);
		l.createCell("c2", Layout.BOTTOM, "c1", 10);
		assertTrue("Expected 3, received " + l.getRows(), l.getRows() == 3);
		
		l = new Layout();
		l.createCell("c1", Layout.TOP, Layout.CANVAS, 10);
		l.createCell("c2", Layout.BOTTOM, Layout.CANVAS, 10);
		assertTrue("Expected 3, received " + l.getRows(), l.getRows() == 3);
	}
	
	@Test
	public void testFourRows() {
		Layout l = new Layout();
		l.createCell("c1", Layout.TOP, Layout.CANVAS, 10);
		l.createCell("c2", Layout.TOP, "c1", 10);
		l.createCell("c3", Layout.TOP, "c2", 10);
		assertTrue("Expected 4, received " + l.getRows(), l.getRows() == 4);
		
		l = new Layout();
		l.createCell("r1", Layout.BOTTOM, Layout.CANVAS, 10);
		l.createCell("r2", Layout.BOTTOM, "r1", 10);
		l.createCell("r3", Layout.BOTTOM, "r2", 10);
		assertTrue("Expected 4, received " + l.getRows(), l.getRows() == 4);
		
		l = new Layout();
		l.createCell("l1", Layout.BOTTOM, Layout.CANVAS, 10);
		l.createCell("r1", Layout.TOP, Layout.CANVAS, 10);
		l.createCell("r2", Layout.TOP, "r1", 10);
		assertTrue("Expected 4, received " + l.getRows(), l.getRows() == 4);
	}
}
