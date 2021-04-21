package frame;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

public class SpreadSheetDocumentFrame extends JInternalFrame{
	private JTextArea document;
	
	
	public void intializeComponent() {
		document = new JTextArea("");
		
	}
	public void addComponentsToWindow(){
		this.add(document);
	}
	
	public void setWindowsProperties() {
		this.setSize(300, 300); 
		this.setVisible(true); 
	}
	public SpreadSheetDocumentFrame() {
		super("SpreadSheet",true,true,true,true);
		intializeComponent() ;
		addComponentsToWindow();
		setWindowsProperties();
	}
	
	
}
