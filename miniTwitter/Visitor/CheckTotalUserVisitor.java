package miniTwitter.Visitor;

import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import miniTwitter.Group;
import miniTwitter.Composite.UserGroup;

public class CheckTotalUserVisitor implements UserGroupVisitor {

	//UserGroup interface methods
	//This method is called when a User makes this visit
	@Override
	public void userVisit(HashMap<String, UserGroup> map, UserGroup component) {
		JOptionPane.showMessageDialog(null, "Please click on a group to access this feature!");	
		
	}
	
	//This method is called when a Group makes this visit
	@Override
	public void groupVisit(HashMap<String, UserGroup> map, UserGroup component) {
		Group currentGroup = (Group)component;
		List<UserGroup> childList = currentGroup.getChild();
		int count = 0;
		for (UserGroup u : childList) { //Counting users in the tree
			if (u.getID() % 1000 != 0){
				count++;
			}
		}
		if (count <= 1) {
			JOptionPane.showMessageDialog(null, "This group contains "+count+" user.");
		} else {
			JOptionPane.showMessageDialog(null, "This group contains "+count+" users.");	
		}
	}

}
