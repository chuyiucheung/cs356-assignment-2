package miniTwitter;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class TextArea{
	
	//Fields
	private JTextArea area;
	
	//Constructor
	public TextArea(String text, int row, int col) {
		area = new JTextArea(text, row, col);
		area.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	//Getter
	public JTextArea getWidget() {
		return area;
	}
	
}
