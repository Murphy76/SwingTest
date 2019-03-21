package test1.grid.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import test1.grid.model.GridModel;
import test1.grid.observer.FiredProperties;

public class GridPanel extends JPanel implements PropertyChangeListener {

	/**
	 *
	 */

	private static final long serialVersionUID = 1L;
	public final Color REGUILAR_COLOR = Color.blue;
	public final Color HIGHLIGHT_COLOR = Color.green;
	GridModel gridModel;

	public GridPanel(int windowWidth, int windowHeight, GridModel gridModel2) {

		this.gridModel = gridModel2;
		this.setSize(windowWidth, windowHeight);

		GridLayout gbl = new GridLayout(gridModel2.getRow(), gridModel2.getCol());
		this.setLayout(gbl);

		Font labelFont = this.getFont();
		Font myFont = new Font(labelFont.getName(), Font.PLAIN, 30);

		for (int i = 0; i < gridModel.getPanels().length; i++) {
			gridModel.getPanels()[i] = new MyLabel();
			setLabelBorder(gridModel.getPanels()[i], REGUILAR_COLOR);
			gridModel.getPanels()[i].setFont(myFont);
			((MyLabel) gridModel.getPanels()[i]).setIdx(i);
			gridModel.getPanels()[i].setText(String.valueOf(i));
			gridModel.getPanels()[i].setHorizontalAlignment(SwingConstants.CENTER);
			((MyLabel) gridModel.getPanels()[i]).setToolTipText("Use context menu to set the label mode.");
			((MyLabel) gridModel.getPanels()[i]).setLayout(new FlowLayout());
			this.add(gridModel.getPanels()[i]);

		}

		gridModel.addPropertyChangeListener(this);
	}

	protected void removeLabel(MyLabel labelRoRemove) {
		this.remove(labelRoRemove);
		this.revalidate();
		this.repaint();

	}

	public void setLabelBorder(MyLabel label, Color color, int thickness) {
		label.setBorder(BorderFactory.createLineBorder(color, thickness));
	}

	public void setLabelBorder(MyLabel label, Color color) {
		try {
			label.setBorder(BorderFactory.createLineBorder(color, 3));
		} catch (NullPointerException e) {
			// just skip it
		}
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if (FiredProperties.MOUSE_L_CLICK.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					MyLabel moveDown=(MyLabel) evt.getOldValue();
					MyLabel moveUp=(MyLabel) evt.getNewValue();
					remove(moveDown);
					if (moveUp!=null) {
						remove(moveUp);
						add(moveUp, gridModel.getHighlightIndex());
						add(new MyLabel(), gridModel.getHighlightIndex()+ gridModel.getRow());
					}else {
						add(new MyLabel(), gridModel.getHighlightIndex());
					}

					gridModel.swapPanels();
					gridModel.highlightLabel();
					validate();
					repaint();
				}
			});
		}

	}

}
