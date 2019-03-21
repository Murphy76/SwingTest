package test1.grid.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class MyLabel extends JLabel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Timer timer = new Timer(500, this);
	private int idx;
	private LabelMode mode;
	private int index;
	private String runningStr = "text....";
	private Color blinkColor = Color.yellow;
	private Color regularColor ;


	public MyLabel() {
		super();
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, Font.BOLD));
		regularColor = getBackground();
		setMaximumSize(new Dimension(150, 150));

	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public LabelMode getMode() {
		return mode;
	}
	public void setMode(LabelMode mode) {
		this.mode = mode;
	}

    public void start() {
        timer.start();
        setOpaque(true);
    }

    public void stop() {
        timer.stop();
        setOpaque(false);
		this.revalidate();
		this.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (LabelMode.RUNNING_STR.equals(mode)){
        index++;
        if (index > getRunningStr().length() - 5) {
            index = 0;
        }
        setText(getRunningStr().substring(index, index + 5));
    	} else if (	LabelMode.BLINKED.equals(mode)){

    		if (regularColor.getRGB() == getBackground().getRGB()) {
    			setOpaque(true);
    			setBackground(blinkColor);

    		}else {
    			setOpaque(false);
    			setBackground(regularColor);
    		}
    	}
    }
	public String getRunningStr() {
		return runningStr;
	}
	public void setRunningStr(String runningStr) {
		this.runningStr = runningStr;
	}

}
