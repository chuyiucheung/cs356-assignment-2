package miniTwitter.Visitor;

import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import miniTwitter.Group;
import miniTwitter.Composite.UserGroup;

public class CheckTotalGroupVisitor implements UserGroupVisitor {
	
	//UserGroup interface methods
	//This method is called when a User makes a visit
	@Override
	public void userVisit(HashMap<String, UserGroup> map, UserGroup component) {
		JOptionPane.showMessageDialog(null, "Please click on a group to access this feature!");
	}
	
	//This method is called when a Group makes a visit
	@Override
	public void groupVisit(HashMap<String, UserGroup> map, UserGroup component) {
		Group currentGroup = (Group)component;
		List<UserGroup> childList = currentGroup.getChild();
		int count = 0;                        //Counting groups
		for (UserGroup u : childList) {
			if (u.getID() % 1000 == 0){
				count++;
			}
		}
		if (count <= 1) {
			JOptionPane.showMessageDialog(null, "This group contains "+count+" sub-group.");
		} else {
			JOptionPane.showMessageDialog(null, "This group contains "+count+" sub-groups.");
		}
	}
}
