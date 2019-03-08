package test1.grid.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.stream.IntStream;

import javax.swing.JLabel;

import test1.grid.model.GridModel;
import test1.grid.view.GridFrame;
import test1.grid.view.GridPanel;

public class GridController {

	GridFrame view ;
	GridModel gridModel = new GridModel(6,16);
	GridPanel gridPanel = new GridPanel(600,600,gridModel);

	public void initView () {
		view = new GridFrame(gridModel);
		view.addGridPanel(gridPanel);
		view.setVisible(true);
		initListeners();
		gridModel.fireIndexCgange();
		gridModel.highlightLabel();
	}

	private void initListeners() {

		//add keyListener for view
		view.addPressKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent paramKeyEvent) {

			}

			@Override
			public void keyReleased(KeyEvent paramKeyEvent) {
			}

			@Override
			public void keyPressed(KeyEvent paramKeyEvent) {
				if (paramKeyEvent.getKeyCode()==KeyEvent.VK_LEFT) {
					gridModel.highlightLeft();
				}else if (paramKeyEvent.getKeyCode()==KeyEvent.VK_RIGHT) {
					gridModel.highlightRight();
				}else if (paramKeyEvent.getKeyCode()==KeyEvent.VK_UP) {
					gridModel.highlightUp(getDimensions());
				}else if (paramKeyEvent.getKeyCode()==KeyEvent.VK_DOWN) {
					gridModel.highlightDown(getDimensions());
				}



			}
		});
		//Set JLabel Mouse Listeners
		MouseListener mListener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if ((JLabel) e.getSource() != null) {
					gridModel.changeSelectedLabel((JLabel) e.getSource());
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				gridModel.moveUnderlyingLabel((JLabel) e.getSource(), getDimensions());

			}

		};
		IntStream.range(0, gridModel.getPanels().length).forEach(i -> {
			gridModel.getPanels()[i].addMouseListener(mListener);
		});

	}
	//get panel dimension
	public int[][] getDimensions() {

		return gridPanel.getDimensions();

	}


}
