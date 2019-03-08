package test1.grid.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import test1.grid.model.GridModel;
import test1.grid.observer.FiredProperties;

public class GridPanel extends JPanel implements PropertyChangeListener{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final int ROWS_COUNT = 4;
	private final int COLUMNS_COUNT = 4;
	public final Color REGUILAR_COLOR = Color.blue;
	public final Color HIGHLIGHT_COLOR = Color.green;

	GridModel gridModel;

	public GridPanel(int windowWidth, int windowHeight, GridModel model) {
		this.gridModel = model;
		this.setSize(windowWidth, windowHeight);

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints gblc = new GridBagConstraints();
		gblc.fill = GridBagConstraints.BOTH;
		gblc.anchor = GridBagConstraints.CENTER;
		gblc.weightx = 0.1;
		gblc.weighty = 0.1;

		Font labelFont = this.getFont();
		Font myFont = new Font(labelFont.getName(), Font.PLAIN, 30);

		for (int i = 0; i < gridModel.getPanels().length; i++) {
			gridModel.getPanels()[i] = new JLabel();
			setLabelBorder(gridModel.getPanels()[i], REGUILAR_COLOR);
			gridModel.getPanels()[i].setFont(myFont);
			gridModel.getPanels()[i].setText(String.valueOf(i));
			gridModel.getPanels()[i].setHorizontalAlignment(SwingConstants.CENTER);
			gblc.gridx = i % COLUMNS_COUNT;
			gblc.gridy = i / ROWS_COUNT;

			gbl.setConstraints(gridModel.getPanels()[i], gblc);
			this.add(gridModel.getPanels()[i]);

		}


		 gridModel.addPropertyChangeListener(this);
	}


	protected void removeLabel(JLabel labelRoRemove) {
		this.remove(labelRoRemove);
		this.revalidate();
		this.repaint();

	}

	public void setLabelBorder(JLabel label, Color color, int thickness) {
		label.setBorder(BorderFactory.createLineBorder(color, thickness));
	}

	public void setLabelBorder(JLabel label, Color color) {
		try {
			label.setBorder(BorderFactory.createLineBorder(color, 3));
		} catch (NullPointerException e) {
			// just skip it
		}
	}

	private int getComponentIndex(Component[] array, JLabel label) {

		OptionalInt idx = IntStream.range(0, array.length).filter(i -> label.equals(gridModel.getPanels()[i])).findFirst();
		if (idx.isPresent()) {
			return idx.getAsInt();
		}
		return -1;

	}

	public int[][] getDimensions() {

		return ((GridBagLayout) this.getLayout()).getLayoutDimensions();

	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if (FiredProperties.MOUSE_CLICK.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					int[][] dim = (int[][]) evt.getNewValue();
					int col = (int)evt.getOldValue() % dim[0].length;
					int row = (int)evt.getOldValue() / dim[0].length;
					int cols = dim[0].length;
					int rows = dim[1].length;

					GridBagConstraints gblcMoveDown;
					GridBagConstraints gblcMoveUp;
					JLabel moveDown;
					JLabel moveUp;
					for (int i = row; i < rows; i++) {

						moveDown = gridModel.getPanels()[col + cols * i];
						if (moveDown == null) {
							break;
						}
						gblcMoveDown = ((GridBagLayout)getLayout()).getConstraints(moveDown) ;
						remove(moveDown);
						if ((col + cols * (i + 1)) >= gridModel.getPanels().length) {
							gridModel.getPanels()[col + cols * i] = null;
							break;
						}
						moveUp = gridModel.getPanels()[col + cols * (i + 1)];
						if (moveUp != null) {
							gblcMoveUp = ((GridBagLayout)getLayout()).getConstraints(moveUp) ;
							remove(moveUp);
							add(moveUp, gblcMoveDown);
							add(moveDown, gblcMoveUp);
							gridModel.getPanels()[col + cols * i] = moveUp;
							gridModel.getPanels()[col + cols * (i + 1)] = moveDown;
						} else {
							gridModel.getPanels()[col + cols * i] = null;
						}

					}
					gridModel.highlightLabel();
					validate();
					repaint();
				}
			});
		}

	}

}
