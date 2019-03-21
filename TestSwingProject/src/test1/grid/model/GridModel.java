package test1.grid.model;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import javax.swing.JLabel;

import test1.grid.observer.AbstractChangebleModel;
import test1.grid.observer.FiredProperties;
import test1.grid.view.MyLabel;

public class GridModel extends AbstractChangebleModel {

	private int col = 4;
	private int row = 4;
	int highlightIndex = 6;
	MyLabel[] panels;

	public GridModel(int highlightIndex, int col, int row) {
		this.col = col;
		this.row = row;
		this.highlightIndex = highlightIndex;
		this.panels = new MyLabel[col * row];
	}

	public void highlightLeft() {
		if (highlightIndex > 0) {
			int current = highlightIndex;
			do {
				highlightIndex--;
			} while (panels[highlightIndex] == null && highlightIndex > 0);
			if (panels[highlightIndex] == null) {
				highlightIndex = current;
			}
			firePropertyChange(FiredProperties.KEY_LEFT.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
		}
	}

	public void highlightRight() {
		if (highlightIndex < panels.length - 1) {
			int current = highlightIndex;
			do {
				highlightIndex++;
			} while (highlightIndex < (panels.length - 1) && panels[highlightIndex] == null);

			if (panels[highlightIndex] == null) {
				highlightIndex = current;
			}
			firePropertyChange(FiredProperties.KEY_RIGHT.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
		}
	}

	public void highlightUp() {
		int current = highlightIndex;
		if (highlightIndex > 0) {
			do {
				highlightIndex = highlightIndex - getRow();
				if (highlightIndex < 0) {
					highlightIndex = panels.length - (getRow() - ((highlightIndex + getRow()) % getRow())) - 1;
				}

			} while (panels[highlightIndex] == null && highlightIndex > 0);

			if (panels[highlightIndex] == null) {
				highlightIndex = current;
			}
			firePropertyChange(FiredProperties.KEY_UP.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
		}
	}

	public void highlightDown() {
		if (highlightIndex < panels.length) {
			int current = highlightIndex;

			do {
				highlightIndex = highlightIndex + getRow();
				if (highlightIndex > (panels.length - 1)) {
					highlightIndex = (((highlightIndex - getRow()) % getRow())) + 1;
				}

			} while (panels[highlightIndex] == null && highlightIndex > 0);

			if (highlightIndex == getCol() && current > highlightIndex) {
				highlightIndex = current;
			}

			if (highlightIndex < panels.length) {

				firePropertyChange(FiredProperties.KEY_DOWN.name(), panels[current], panels[highlightIndex]);
				fireIndexCgange();
			}

		}
	}

	public void fireIndexCgange() {
		firePropertyChange(FiredProperties.HIGHLIGHT_CHANGE.name(), null, highlightIndex);
	}

	public int getHighlightIndex() {
		return highlightIndex;
	}

	public void setHighlightIndex(int highlightIndex) {
		this.highlightIndex = highlightIndex;
	}

	public MyLabel[] getPanels() {
		return panels;
	}

	public void changeSelectedLabel(MyLabel jLabel) {

		firePropertyChange(FiredProperties.MOUSE_HOVER.name(), panels[highlightIndex], jLabel);
		highlightIndex = jLabel.getIdx();// getComponentIndex(highlightedLabel);
		fireIndexCgange();
	}

	private int getComponentIndex(MyLabel label) {

		OptionalInt idx = IntStream.range(0, panels.length).filter(i -> label.equals(panels[i])).findFirst();
		if (idx.isPresent()) {
			return idx.getAsInt();
		}
		return -1;

	}

	public void moveUnderlyingLabel() {

		firePropertyChange(FiredProperties.MOUSE_L_CLICK.name(), panels[highlightIndex],
				highlightIndex + getRow() > panels.length ? null : panels[highlightIndex + getRow()]);

	}

	public void highlightLabel() {
		firePropertyChange(FiredProperties.HIGHLIGHT_LABEL.name(), null, panels[highlightIndex]);

	}

	public void mouseExitedLabel(MyLabel source) {

	}

	public void swapPanels() {

		if (highlightIndex + getRow() < panels.length) {
			panels[highlightIndex] = panels[highlightIndex + getRow()];
			if (panels[highlightIndex] != null) {
				panels[highlightIndex].setIdx(highlightIndex);
			}
			panels[highlightIndex + getRow()] = null;

		} else {
			panels[highlightIndex] = null;
		}

	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
}
