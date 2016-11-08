package miniTwitter;

import javax.swing.JButton;

public class Button {
	
	//Fields
	private JButton button;
	
	//Methods
	//Constructor
	public Button(String label) {
		button = new JButton();
		button.setText(label);
	}
	
	//Getter
	public JButton getWidget() {
		return button;
	}
	
}
