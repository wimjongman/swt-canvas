package swt.canvas.samples.chart;

import java.util.ArrayList;
import java.util.List;

import swt.canvas.core.Engine;
import swt.canvas.core.coordinate.CartesianQuadrantI;

public class Bar extends Engine {

	public static void main(String[] args) {
		new Bar().run();
	}

	private List<Column> fColumns = new ArrayList<>();

	@Override
	public void draw() {
		int colWidth = getWidth() / fColumns.size();
		int border = 0;
		if (colWidth > 2) {
			border = 2;
		}
		int pos = 0;
		for (Column column : fColumns) {
			System.out.print(" " + (pos * colWidth));
			translate(pos * colWidth, 0);
			drawLine(colWidth / 2, 0, colWidth / 2, column.fValue);
			drawRectangle(0, 0, colWidth - border, column.fValue);
			pos++;
		}
		System.out.println();
	}

	@Override
	public void init() {
		setCoordinateSystem(CartesianQuadrantI.class);
		addColumn("Bar1", 10, 80);
		addColumn("Bar2", 20, 120);
		addColumn("Bar3", 30, 60);
		addColumn("Bar4", 40, 100);
		addColumn("Bar5", 50, 90);
	}

	private void addColumn(String pName, int pColor, int pValue) {
		fColumns.add(new Column(pName, pColor, pValue));
	}

	public class Column {
		int fColor;
		int fValue;
		String fName;

		public Column(String pName, int pColor, int pValue) {
			fName = pName;
			fColor = pColor;
			fValue = pValue;
		}

		public void setColor(int pColor) {
			fColor = pColor;
		}

		public void setName(String pName) {
			fName = pName;
		}

		public void setValue(int pValue) {
			fValue = pValue;
		}
	}

}
