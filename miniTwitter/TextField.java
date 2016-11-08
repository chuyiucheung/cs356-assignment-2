package miniTwitter;

import javax.swing.JTextField;

public class TextField {
	
	//Field
	private JTextField field;
	
	//Methods
	//Constructor
	public TextField(String label) {
		field = new JTextField();
		field.setText(label);
	}
	
	//Getter
	public JTextField getWidget() {
		// TODO Auto-generated method stub
		return field;
	}

}
