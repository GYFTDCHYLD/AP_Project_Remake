package driver;

import java.awt.EventQueue;

import frame.ServerWindow;

public class Driver {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ServerWindow();
			}
		});
		
	}
}
