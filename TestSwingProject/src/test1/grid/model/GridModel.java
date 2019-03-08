package test1.grid.model;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import javax.swing.JLabel;

import test1.grid.observer.AbstractChangebleModel;
import test1.grid.observer.FiredProperties;

public class GridModel extends AbstractChangebleModel {

	int highlightIndex = 6;
	JLabel[] panels;

	public GridModel(int highlightIndex, int panelsCount) {
		this.highlightIndex = highlightIndex;
		this.panels = new JLabel[panelsCount];
	}

	public void highlightLeft() {
		if (highlightIndex > 0) {
			int current = highlightIndex;
			highlightIndex--;
			firePropertyChange(FiredProperties.KEY_LEFT.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
		}
	}

	public void highlightRight() {
		if (highlightIndex < panels.length - 1) {
			int current = highlightIndex;
			highlightIndex++;
			firePropertyChange(FiredProperties.KEY_RIGHT.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
		}
	}

	public void highlightUp(int[][] dimensions) {
		int current = highlightIndex;
		int newIndex = highlightIndex - dimensions[0].length;
		if (newIndex >= 0) {
			highlightIndex = newIndex;
			firePropertyChange(FiredProperties.KEY_UP.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
		}
	}

	public void highlightDown(int[][] dimensions) {
		int current = highlightIndex;
		int newIndex = highlightIndex + dimensions[0].length;
		if (newIndex < panels.length) {
			highlightIndex = newIndex;
			firePropertyChange(FiredProperties.KEY_DOWN.name(), panels[current], panels[highlightIndex]);
			fireIndexCgange();
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

	public JLabel[] getPanels() {
		return panels;
	}

	public void changeSelectedLabel(JLabel highlightedLabel) {

		firePropertyChange(FiredProperties.MOUSE_HOVER.name(), panels[highlightIndex], highlightedLabel);
		highlightIndex = getComponentIndex(highlightedLabel);
		fireIndexCgange();
	}

	private int getComponentIndex(JLabel label) {

		OptionalInt idx = IntStream.range(0, panels.length).filter(i -> label.equals(panels[i])).findFirst();
		if (idx.isPresent()) {
			return idx.getAsInt();
		}
		return -1;

	}

	public void moveUnderlyingLabel(JLabel source, int[][] dimension) {
		firePropertyChange(FiredProperties.MOUSE_CLICK.name(), highlightIndex, dimension);

	}

	public void highlightLabel() {
		firePropertyChange(FiredProperties.HIGHLIGHT_LABEL.name(), null, panels[highlightIndex]);

	}
}
