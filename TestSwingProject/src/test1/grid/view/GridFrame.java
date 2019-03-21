package test1.grid.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import test1.grid.model.GridModel;
import test1.grid.observer.FiredProperties;

public class GridFrame extends JFrame implements PropertyChangeListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	public final Color REGUILAR_COLOR = Color.blue;
	public final Color HIGHLIGHT_COLOR = Color.green;

	GridModel gridModel;
	// ......

	// private variables of UI components
	// ......


	/** Constructor to setup the UI components */
	public GridFrame(GridModel gridModel2) {
		this.gridModel = gridModel2;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit when close button clicked
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // or pack() the components
		gridModel2.addPropertyChangeListener(this);
	}



	public void addGridPanel(Component gridPane) {
		Container cp = this.getContentPane();
		cp.add(gridPane);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if (FiredProperties.KEY_LEFT.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setLabelBorder((JLabel) evt.getOldValue(), REGUILAR_COLOR);
					setLabelBorder((JLabel) evt.getNewValue(), HIGHLIGHT_COLOR, 3);

				}
			});
		}
		if (FiredProperties.KEY_RIGHT.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setLabelBorder((JLabel) evt.getOldValue(), REGUILAR_COLOR);
					setLabelBorder((JLabel) evt.getNewValue(), HIGHLIGHT_COLOR, 3);

				}
			});
		}
		if (FiredProperties.KEY_UP.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setLabelBorder((JLabel) evt.getOldValue(), REGUILAR_COLOR);
					setLabelBorder((JLabel) evt.getNewValue(), HIGHLIGHT_COLOR, 3);

				}
			});
		}
		if (FiredProperties.KEY_DOWN.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setLabelBorder((JLabel) evt.getOldValue(), REGUILAR_COLOR);
					setLabelBorder((JLabel) evt.getNewValue(), HIGHLIGHT_COLOR, 3);

				}
			});
		}
		if (FiredProperties.HIGHLIGHT_CHANGE.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setTitle("Selected index is " + String.valueOf(evt.getNewValue()));

				}
			});
		}
		if (FiredProperties.MOUSE_HOVER.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setLabelBorder((JLabel) evt.getOldValue(), REGUILAR_COLOR);
					setLabelBorder((JLabel) evt.getNewValue(), HIGHLIGHT_COLOR, 3);

				}
			});
		}

		if (FiredProperties.HIGHLIGHT_LABEL.name().equals(propertyName)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setLabelBorder((JLabel) evt.getNewValue(), HIGHLIGHT_COLOR, 3);

				}
			});
		}

	}

	public void addPressKeyListener(KeyListener keyListener) {
		addKeyListener(keyListener);

	}

	public void setLabelBorder(JLabel label, Color color, int thickness) {
		if (label != null) {
			label.setBorder(BorderFactory.createLineBorder(color, thickness));
		}
	}

	public void setLabelBorder(JLabel label, Color color) {
		try {
			label.setBorder(BorderFactory.createLineBorder(color, 3));
		} catch (NullPointerException e) {
			// just skip it
		}
	}

}
