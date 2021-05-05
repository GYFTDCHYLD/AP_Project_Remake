package main;

import frame.MainWindow;

public class MicroStarClientDriver {

	public static void main(String[] args) {
		new MainWindow("192.168.1.16", 8000); // host address and port number

	}
}
