package test1.grid.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

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
	private int index;
	public String s =  "Tomorrow, and tomorrow, and tomorrow, "
	        + "creeps in this petty pace from day to day, "
	        + "to the last syllable of recorded time; ... "
	        + "It is a tale told by an idiot, full of "
	        + "sound and fury signifying nothing.";

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
			gridModel.getPanels()[i] = new MyLabel();
			setLabelBorder(gridModel.getPanels()[i], REGUILAR_COLOR);
			gridModel.getPanels()[i].setFont(myFont);
			((MyLabel) gridModel.getPanels()[i]).setIdx(i);
			gridModel.getPanels()[i].setText(String.valueOf(i));
			gridModel.getPanels()[i].setHorizontalAlignment(SwingConstants.CENTER);
			gblc.gridx = i % COLUMNS_COUNT;
			gblc.gridy = i / ROWS_COUNT;

			gbl.setConstraints(gridModel.getPanels()[i], gblc);
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

	private int getComponentIndex(Component[] array, MyLabel label) {

		OptionalInt idx = IntStream.range(0, array.length).filter(i -> label.equals(gridModel.getPanels()[i]))
				.findFirst();
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
		if (FiredProperties.MOUSE_L_CLICK.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					int[][] dim = (int[][]) evt.getNewValue();
					int col = (int) evt.getOldValue() % dim[0].length;
					int row = gridModel.getHighlightIndex() / dim[0].length;
					int cols = dim[0].length;
					int rows = dim[1].length;

					GridBagConstraints gblcMoveDown;
					GridBagConstraints gblcMoveUp;
					MyLabel moveDown;
					MyLabel moveUp;

					moveDown = gridModel.getPanels()[col + cols * row];
					gblcMoveDown = ((GridBagLayout) getLayout()).getConstraints(moveDown);
					remove(moveDown);
					add(new MyLabel(), gblcMoveDown);
					if ((col + cols * (row + 1)) < gridModel.getPanels().length) {
						moveUp = gridModel.getPanels()[col + cols * (row + 1)];
						if (moveUp != null) {
							gblcMoveUp = ((GridBagLayout) getLayout()).getConstraints(moveUp);
							add(moveUp, gblcMoveDown);
							moveUp.setIdx(moveDown.getIdx());
							add(new MyLabel(), gblcMoveUp);
						}
					}
					gridModel.swapPanels(col + cols * row, col + cols * (row + 1));

					gridModel.highlightLabel();
					validate();
					repaint();
				}
			});
		}

	}



}
