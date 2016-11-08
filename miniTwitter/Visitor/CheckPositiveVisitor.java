package miniTwitter.Visitor;

import java.util.HashMap;
import javax.swing.JOptionPane;
import miniTwitter.Composite.UserGroup;

public class CheckPositiveVisitor implements UserGroupVisitor {

	//GroupUserVisitor interface methods
	//This method is called when a User makes a visit
	@Override
	public void userVisit(HashMap<String, UserGroup> map, UserGroup component) {
		JOptionPane.showMessageDialog(null, "This feature will be added soon!");		
	}

	//This method is called when a Group makes a visit
	@Override
	public void groupVisit(HashMap<String, UserGroup> map, UserGroup component) {
		JOptionPane.showMessageDialog(null, "This feature will be added soon!");	
	}

}
