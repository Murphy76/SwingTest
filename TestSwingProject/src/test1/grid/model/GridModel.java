package test1.grid.model;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import javax.swing.JLabel;

import test1.grid.observer.AbstractChangebleModel;
import test1.grid.observer.FiredProperties;
import test1.grid.view.MyLabel;

public class GridModel extends AbstractChangebleModel {

	int highlightIndex = 6;
	MyLabel[] panels;

	public GridModel(int highlightIndex, int panelsCount) {
		this.highlightIndex = highlightIndex;
		this.panels = new MyLabel[panelsCount];
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
			} while (panels[highlightIndex] == null && highlightIndex <= panels.length);
			if (panels[highlightIndex] == null) {
				highlightIndex = current;
			}
			firePropertyChange(FiredProperties.KEY_RIGHT.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
		}
	}

	public void highlightUp(int[][] dimensions) {
		int current = highlightIndex;
		if (highlightIndex > 0) {
			do {
				highlightIndex = highlightIndex - dimensions[0].length;
				if (highlightIndex < 0) {
					highlightIndex = panels.length
							- (dimensions[0].length - ((highlightIndex + dimensions[0].length) % dimensions[0].length))
							- 1;
					System.out.println(highlightIndex);
				}

			} while (panels[highlightIndex] == null && highlightIndex > 0);

			if (panels[highlightIndex] == null) {
				highlightIndex = current;
			}
			firePropertyChange(FiredProperties.KEY_UP.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
		}
	}

	public void highlightDown(int[][] dimensions) {
		if (highlightIndex < panels.length -1) {
			int current = highlightIndex;

			do {
				highlightIndex = highlightIndex + dimensions[0].length;
				if (highlightIndex > panels.length -1) {
					highlightIndex =  (((highlightIndex - dimensions[0].length) % dimensions[0].length))	+ 1;
					System.out.println(highlightIndex);
				}

			} while (panels[highlightIndex] == null && highlightIndex > 0);



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
		System.out.println(jLabel.getIdx());
		fireIndexCgange();
	}

	private int getComponentIndex(MyLabel label) {

		OptionalInt idx = IntStream.range(0, panels.length).filter(i -> label.equals(panels[i])).findFirst();
		if (idx.isPresent()) {
			return idx.getAsInt();
		}
		return -1;

	}

	public void moveUnderlyingLabel(MyLabel source, int[][] dimension) {
		firePropertyChange(FiredProperties.MOUSE_L_CLICK.name(), highlightIndex, dimension);

	}

	public void highlightLabel() {
		firePropertyChange(FiredProperties.HIGHLIGHT_LABEL.name(), null, panels[highlightIndex]);

	}

	public void mouseExitedLabel(MyLabel source) {

	}

	public void swapPanels(int toRemove, int moveUp) {

		if (moveUp < panels.length) {
			panels[toRemove] = panels[moveUp];
			panels[moveUp] = null;
		}

	}
}
