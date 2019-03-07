package test1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MyContainer extends JPanel {

	private final int ROWS_COUNT = 4;
	private final int COLUMNS_COUNT = 4;
	public final Color REGUILAR_COLOR = Color.blue;
	public final Color HIGHLIGHT_COLOR = Color.green;

	public int panelsCount = 16;
	int highlightIndex = 6;
	JLabel[] panels = new JLabel[panelsCount];
	SwingTemplate st;

	public MyContainer(int windowWidth, int windowHeight, SwingTemplate st) {
		this.setSize(windowWidth, windowHeight);
		this.st = st;
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints gblc = new GridBagConstraints();
		gblc.fill = GridBagConstraints.BOTH;
		gblc.anchor = GridBagConstraints.CENTER;
		gblc.weightx = 0.1;
		gblc.weighty = 0.1;


		Font labelFont = this.getFont();
		Font myFont = new Font(labelFont.getName(), Font.PLAIN, 30);

		for (int i = 0; i < panelsCount; i++) {
			panels[i] = new JLabel();
			setLabelBorder(panels[i], REGUILAR_COLOR);
			panels[i].setFont(myFont);
			panels[i].setText(String.valueOf(i));
			panels[i].setHorizontalAlignment(SwingConstants.CENTER);
			panels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if ((JLabel) e.getSource() != null) {
						changeSelectedLabel((JLabel) e.getSource());
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					moveUnderlyingLabel(e.getPoint(), (JLabel) e.getSource());

				}
			});
			gblc.gridx = i % COLUMNS_COUNT;
			gblc.gridy = i / ROWS_COUNT;

			gbl.setConstraints(panels[i], gblc);
			this.add(panels[i]);

			if (i == highlightIndex) {
				setLabelBorder(panels[i], HIGHLIGHT_COLOR);

			}
		}

		sendInterfaceData();

	}

	protected void removeLabel(JLabel labelRoRemove) {
		this.remove(labelRoRemove);
		this.revalidate();
		this.repaint();

	}

	protected void moveUnderlyingLabel(Point point, JLabel source) {
		GridBagLayout gbl = (GridBagLayout) this.getLayout();
		int[][] dim = gbl.getLayoutDimensions();
		int col = highlightIndex % dim[0].length;
		int row = highlightIndex / dim[0].length;
		int cols = dim[0].length;
		int rows = dim[1].length;

		GridBagConstraints gblcMoveDown;
		GridBagConstraints gblcMoveUp;
		JLabel moveDown;
		JLabel moveUp;
		for (int i = row; i < rows; i++) {

			moveDown = panels[col + cols * i];
			if (moveDown == null) {
				break;
			}
			gblcMoveDown = gbl.getConstraints(moveDown);
			this.remove(moveDown);
			if ((col + cols * (i + 1)) >= panels.length) {
				panels[col + cols * i] = null;
				break;
			}
			moveUp = panels[col + cols * (i + 1)];
			if (moveUp != null) {
				gblcMoveUp = gbl.getConstraints(moveUp);
				this.remove(moveUp);
				this.add(moveUp, gblcMoveDown);
				this.add(moveDown, gblcMoveUp);
				panels[col + cols * i] = moveUp;
				panels[col + cols * (i + 1)] = moveDown;
			} else {
				panels[col + cols * i] = null;
			}

		}

		this.validate();
		this.repaint();
	}

	private void sendInterfaceData() {
		for (int i = 0; i < panelsCount; i++) {
			if (panels[i] != null) {
				if (i == highlightIndex) {
					setLabelBorder(panels[i], HIGHLIGHT_COLOR, 3);
				} else {
					setLabelBorder(panels[i], REGUILAR_COLOR);
				}
			}
		}
		st.setTitle("Selected index is " + String.valueOf(highlightIndex));
	}

	public void keyLeft() {
		if (highlightIndex > 0)
			highlightIndex--;

		sendInterfaceData();
	}

	public void keyRight() {
		if (highlightIndex < panelsCount - 1)
			highlightIndex++;
		sendInterfaceData();
	}

	private void changeSelectedLabel(JLabel label) {
		setLabelBorder(panels[highlightIndex], REGUILAR_COLOR);
		setLabelBorder(label, HIGHLIGHT_COLOR, 3);
		if (getComponentIndex(panels, label) >= 0) {
			highlightIndex = getComponentIndex(panels, label);
		}

	}

	public void setLabelBorder(JLabel label, Color color, int thickness) {
		label.setBorder(BorderFactory.createLineBorder(color, thickness));
	}

	public void setLabelBorder(JLabel label, Color color) {
		try {
			label.setBorder(BorderFactory.createLineBorder(color, 3));
		} catch (NullPointerException e) {
			//just skip it
		}
	}

	private int getComponentIndex(Component[] array, JLabel label) {

		OptionalInt idx = IntStream.range(0, array.length).filter(i -> label.equals(panels[i])).findFirst();
		if (idx.isPresent()) {
			return idx.getAsInt();
		}
		return -1;

	}

}
