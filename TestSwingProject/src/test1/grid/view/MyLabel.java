package test1.grid.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class MyLabel extends JLabel implements ActionListener {

	private final Timer timer = new Timer(500, this);
	private int idx;
	private LabelMode mode;
	private int index;
	private String runningStr = "dfsdfg bwe w4eh wetrh werth";
	private Color blinkColor = Color.GREEN;
	private Color regularColor = Color.LIGHT_GRAY;


	public MyLabel() {
		super();
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, Font.BOLD));


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
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (LabelMode.RUNNING_STR.equals(mode)){
        index++;
        if (index > getRunningStr().length() - 15) {
            index = 0;
        }
        setText(getRunningStr().substring(index, index + 15));
    	} else if (	LabelMode.BLINKED.equals(mode)){
    		if (regularColor.equals(getForeground())) {
    			setForeground(blinkColor);

    		}else {
    			setForeground(regularColor);
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
