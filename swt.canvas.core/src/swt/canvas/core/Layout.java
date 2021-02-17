package swt.canvas.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * This class describes the layout of the window in terms of Cells. Every layout
 * starts with one Cell which is the cell containing the canvas. Cells can be
 * added relative to the "canvas" cell or to other cells already placed in the
 * layout.
 * 
 */
public class Layout {

	/** The name of the canvas cell */
	public static final String CANVAS = "canvas";

	/** Above the relative */
	public static final int TOP = 1;
	/** Below the relative */
	public static final int BOTTOM = 2;
	/** Right of the relative */
	public static final int RIGHT = 3;
	/** Left of relative */
	public static final int LEFT = 4;

	public class Cell {
		private String fName;
		private Cell fRelative;
		private int fPosition;
		protected int fColumn;
		protected int fRow;

		private Cell(String name, int position, Cell relative) {
			fName = name;
			fPosition = position;
			fRelative = relative;
		}

		public int getColumn() {
			return fColumn;
		}

		public int getRow() {
			return fRow;
		}
		
		public String getName() {
			return fName;
		}

		public Cell getRelative() {
			return fRelative;
		}

		public int getPosition() {
			return fPosition;
		}

		@Override
		public String toString() {
			return "Cell [Name=" + fName + ", Relative=" + fRelative + "]";
		}

	}

	Cell fCanvasCell = new Cell(CANVAS, 0, null);
	Map<String, Cell> fCells = new HashMap<>();

	/**
	 * Creates a new layout with one central canvas cell. Call createCell to add
	 * more cells to the layout.
	 */
	public Layout() {
		fCells.put(CANVAS, fCanvasCell);
	}

	/**
	 * Creates a cell in this layout which is placed relative to a Cell with the
	 * passed name.
	 * 
	 * @param name     the name of the new cell
	 * @param position the position relative to another cell
	 * @param relative the name of a relative cell, use {@link #CANVAS} for the
	 *                 canvas cell
	 * @param weight   the weight of the width or height of this cell as a
	 *                 percentage (depends on position).
	 * @return this Layout object
	 */
	public Layout createCell(String name, int position, String relative, int weight) {
		if (CANVAS.equals(name) || name == null || !fCells.containsKey(relative) || name.equals(relative)) {
			throw new RuntimeException("Invalid cell name: " + name);
		}
		if (weight < 1 || weight > 99) {
			throw new RuntimeException("Invalid percentage : " + weight);
		}

		if (fCells.containsKey(name)) {
			fCells.remove(name);
		}
		fCells.put(name, new Cell(name, position, fCells.get(relative)));
		
		return this;
	}

	/**
	 * Return the cell with the given name
	 * 
	 * @param name the cell to find
	 * @return the cell with this name, or null if not found.
	 */
	public Cell getCell(String name) {
		return fCells.get(name);
	}

	/**
	 * @return the number of columns in this layout
	 */
	public int getColumns() {
		return 1 + getNeighbours(getCell(CANVAS), LEFT).size() + getNeighbours(getCell(CANVAS), RIGHT).size();
	}

	/**
	 * @return the number of rows in this layout
	 */
	public int getRows() {
		return 1 + getNeighbours(getCell(CANVAS), TOP).size() + getNeighbours(getCell(CANVAS), BOTTOM).size();
	}

	/**
	 * Lists all cells 
	 * @param pCell
	 * @param pos
	 * @return
	 */
	public List<Cell> getNeighbours(Cell pCell, int pos) {
		Cell cell = pCell;
		List<Cell> result = new ArrayList<>();

		while (cell != null) {
			Cell theCell = cell;
			cell = fCells.values().stream()
					.filter(c -> c.getRelative() != null && c.getRelative().equals(theCell) && c.fPosition == pos)
					.findFirst().orElse(null);
			if (cell != null)
				result.add(cell);
		}
		return result;
	}

//	public Cell getCell(int row, int col) {
//		int rows = getRows();
//		int cols = getColumns();
//		
//		if(row > rows || col > cols) {
//			return null;
//		}
//		
//		Map<Integer, Cell> columns = new HashMap<>();
//		for(int ix = 0; ix < cols; ix++) {
//			getNeighbours(fCanvasCell, ix)
//		}
//		columns.put(null, fCanvasCell)
//
//		return null;
//	}

}
