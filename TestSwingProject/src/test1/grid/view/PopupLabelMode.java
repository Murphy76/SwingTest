package test1.grid.view;

import java.awt.HeadlessException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopupLabelMode extends JPopupMenu {

	MyLabel clickedLabel = null;

	public PopupLabelMode() throws HeadlessException {
		super();

		add("Set Blinking mode").addActionListener(e-> {
			((MyLabel) this.getInvoker()).setMode(LabelMode.BLINKED);


			((MyLabel) this.getInvoker()).start();
		});
		add("Set Running String mode").addActionListener(e-> {
			((MyLabel) this.getInvoker()).setMode(LabelMode.RUNNING_STR);
			((MyLabel) this.getInvoker()).start();
		});
		add("Set Running String mode").addActionListener(e-> {
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
