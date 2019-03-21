package test1.grid.view;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class PopupLabelMode extends JPopupMenu {

	MyLabel clickedLabel = null;

	public PopupLabelMode() throws HeadlessException {
		super();

		add("Set Blinking mode").addActionListener(e -> {
			((MyLabel) this.getInvoker()).setMode(LabelMode.BLINKED);

			((MyLabel) this.getInvoker()).start();
		});
		add("Set Running String mode").addActionListener(e -> {
			String text = JOptionPane.showInputDialog(null, "Input running text (5 char min)");
			if (text != null && text != "" && text.length()>5 ) {
				((MyLabel) this.getInvoker()).setRunningStr(text);
				((MyLabel) this.getInvoker()).setMode(LabelMode.RUNNING_STR);
				((MyLabel) this.getInvoker()).start();
			}else {
				JOptionPane.showMessageDialog(null, "Text is emtpu or too short. 5 letters min", "", JOptionPane.WARNING_MESSAGE);
			}
		});
		add("Set Common Label mode").addActionListener(e -> {
			((MyLabel) this.getInvoker()).setMode(LabelMode.COMMON);
			((MyLabel) this.getInvoker()).stop();
		});

	}

	public MyLabel getClickedLabel() {
		return clickedLabel;
	}

	public void setClickedLabel(MyLabel clickedLabel) {
		this.clickedLabel = clickedLabel;
	}

}
