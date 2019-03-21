package test1.grid.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.SwingUtilities;

import test1.grid.model.GridModel;
import test1.grid.view.GridFrame;
import test1.grid.view.GridPanel;
import test1.grid.view.LabelMode;
import test1.grid.view.MyLabel;
import test1.grid.view.PopupLabelMode;

public class GridController {

	private int col = 4;
	private int row = 4;
	GridFrame view;
	GridModel gridModel = new GridModel(6, col, row);
	GridPanel gridPanel = new GridPanel(600, 600, gridModel);

	PopupLabelMode pum = new PopupLabelMode();

	public void initView() {
		view = new GridFrame(gridModel);
		view.addGridPanel(gridPanel);
		view.setVisible(true);
		initListeners();
		gridModel.fireIndexCgange();
		gridModel.highlightLabel();

		setLabelRandomMode();

	}

	private void setLabelRandomMode() {
		Random rnd = new Random();
		int tmp = rnd.nextInt(col * row);
		gridModel.getPanels()[tmp].setMode(LabelMode.BLINKED);
		gridModel.getPanels()[tmp].start();
		tmp = rnd.nextInt(col * row);
		gridModel.getPanels()[tmp].setMode(LabelMode.RUNNING_STR);
		gridModel.getPanels()[tmp].start();

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
					gridModel.highlightUp();
				} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
					gridModel.highlightDown();
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
					gridModel.moveUnderlyingLabel();
				}
			}


			@Override
			public void mouseExited(MouseEvent e) {
				if ((MyLabel) e.getSource() != null) {
					gridModel.mouseExitedLabel((MyLabel) e.getSource());
				}

			}

		};
		//init listeners for Labels
		IntStream.range(0, gridModel.getPanels().length).forEach(i -> {
			gridModel.getPanels()[i].addMouseListener(mListener);
		});
		//init context menu for Labels
		IntStream.range(0, gridModel.getPanels().length).forEach(i -> {
			gridModel.getPanels()[i].setComponentPopupMenu(pum);
		});


	}


}
