package main;

import java.awt.EventQueue;

import frame.ServerWindow;

public class MicroStarServerDriver {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ServerWindow(8000);// port number
			}
		});
		
	}
}
