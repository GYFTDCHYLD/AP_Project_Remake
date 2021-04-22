package main;

import frame.MainWindow;
import network.Client;

public class driver {

	public static void main(String[] args) {
		new Client(new MainWindow()); 

	}

}
