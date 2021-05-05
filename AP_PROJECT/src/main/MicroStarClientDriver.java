package main;

import frame.MainWindow;

public class MicroStarClientDriver {

	public static void main(String[] args) {
		new MainWindow("127.0.0.1", 8000); // host address and port number

	}
}
