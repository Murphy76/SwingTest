package test1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Swing Program Template
@SuppressWarnings("serial")
public class SwingTemplate extends JFrame implements KeyListener {
   // Name-constants to define the various dimensions
   public static final int WINDOW_WIDTH = 600;
   public static final int WINDOW_HEIGHT = 600;
   // ......

   // private variables of UI components
   // ......
   MyContainer container;
   /** Constructor to setup the UI components */
   public SwingTemplate() {
      Container cp = this.getContentPane();

      // Content-pane sets layout
      // cp.setLayout(new ....Layout());

      // Allocate the UI components
      // .....

      // Content-pane adds components
      container = new MyContainer(WINDOW_WIDTH, WINDOW_HEIGHT, this);
      cp.add(container);
      addKeyListener(this);

      // Source object adds listener
      // .....

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit when close button clicked
      //setTitle("......"); // "this" JFrame sets title
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);  // or pack() the components
      setVisible(true);   // show it
   }

   /** The entry main() method */
   public static void main(String[] args) {
      // Run GUI codes in the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new SwingTemplate();  // Let the constructor do the job
         }
      });
   }

@Override
public void keyPressed(KeyEvent arg0) {
	if (arg0.getKeyCode()==KeyEvent.VK_LEFT) {
		container.keyLeft();
	}else if (arg0.getKeyCode()==KeyEvent.VK_RIGHT) {
		container.keyRight();
	}else if (arg0.getKeyCode()==KeyEvent.VK_UP) {
		container.pressedKeyUp();
	}else if (arg0.getKeyCode()==KeyEvent.VK_DOWN) {
		container.pressedKeyDown();
	}

}

@Override
public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub

}

@Override
public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub

}
}