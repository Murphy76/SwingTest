package test1.grid.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.stream.IntStream;

import javax.swing.SwingUtilities;

import test1.grid.model.GridModel;
import test1.grid.view.GridFrame;
import test1.grid.view.GridPanel;
import test1.grid.view.MyLabel;
import test1.grid.view.PopupLabelMode;

public class GridController {

	GridFrame view;
	GridModel gridModel = new GridModel(6, 16);
	GridPanel gridPanel = new GridPanel(600, 600, gridModel);
	PopupLabelMode pum = new PopupLabelMode();

	public void initView() {
		view = new GridFrame(gridModel);
		view.addGridPanel(gridPanel);
		view.setVisible(true);
		initListeners();
		gridModel.fireIndexCgange();
		gridModel.highlightLabel();
	}

	private void initListeners() {

		// add keyListener for view
		view.addPressKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent paramKeyEvent) {

			}

			@Override
			public void keyReleased(KeyEvent paramKeyEvent) {
			}

			@Override
			public void keyPressed(KeyEvent paramKeyEvent) {
				if (paramKeyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
					gridModel.highlightLeft();
				} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
					gridModel.highlightRight();
				} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_UP) {
					gridModel.highlightUp(getDimensions());
				} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
					gridModel.highlightDown(getDimensions());
				}

			}
		});
		// Set JLabel Mouse Listeners
		MouseListener mListener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if ((MyLabel) e.getSource() != null) {
					gridModel.changeSelectedLabel((MyLabel) e.getSource());
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (SwingUtilities.isLeftMouseButton(e)) {
					gridModel.moveUnderlyingLabel((MyLabel) e.getSource(), getDimensions());
				}
			}
			@Override
			  public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					pum.setClickedLabel((MyLabel) e.getSource());
					pum.show(e.getComponent(), e.getX(), e.getY());


				}

			}
			@Override
			public void mouseExited(MouseEvent e) {
				if ((MyLabel) e.getSource() != null) {
					gridModel.mouseExitedLabel((MyLabel) e.getSource());
				}

			}

		};
		IntStream.range(0, gridModel.getPanels().length).forEach(i -> {
			gridModel.getPanels()[i].addMouseListener(mListener);
		});

		gridPanel.setComponentPopupMenu(pum);



	}

	// get panel dimension
	public int[][] getDimensions() {

		return gridPanel.getDimensions();

	}

}
