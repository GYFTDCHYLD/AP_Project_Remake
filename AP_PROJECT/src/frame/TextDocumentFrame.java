package frame;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

public class TextDocumentFrame extends JInternalFrame{
	private JTextArea document;
	
	
	public void intializeComponent() {
		document = new JTextArea();
		
	}
	public void addComponentsToWindow(){
		this.add(document);
	}
	
	public void setWindowsProperties() {
		this.setSize(300, 300); 
		this.setVisible(true); 
	}
	public TextDocumentFrame() {
		super("TextDocument",true,true,true,true);
		intializeComponent() ;
		addComponentsToWindow();
		setWindowsProperties();
	}
	
}
