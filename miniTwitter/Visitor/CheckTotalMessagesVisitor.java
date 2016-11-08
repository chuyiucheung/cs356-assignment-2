package miniTwitter.Visitor;

import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import miniTwitter.Group;
import miniTwitter.Composite.UserGroup;
import miniTwitter.Observer.User;

public class CheckTotalMessagesVisitor implements UserGroupVisitor {

	//UserGroupVisitor interface methods
	//This method is called when a User makes a visit
	@Override
	public void userVisit(HashMap<String, UserGroup> map, UserGroup component) {
		User currentUser = (User)component;
		int count = currentUser.getMessageCount();    //Counting messages sent by the User
		if (count <= 1) {
			JOptionPane.showMessageDialog(null, "This User has "+count+" message.");
		} else {
			JOptionPane.showMessageDialog(null, "This User has "+count+" messages.");
		}
	}

	//This method is called when a Group makes a visit
	@Override
	public void groupVisit(HashMap<String, UserGroup> map, UserGroup component) {
		Group currentGroup = (Group)component;
		List<UserGroup> childList = currentGroup.getChild();
		int count = 0;                  //Counting messages sent by Users in the Group
		for (UserGroup u : childList) {
			if (u.getID() % 1000 != 0){
				User user = (User)u;
				count += user.getMessageCount();
			}
		}
		if (count <= 1) {
			JOptionPane.showMessageDialog(null, "This group contains "+count+" message.");
		} else {
			JOptionPane.showMessageDialog(null, "This group contains "+count+" messages.");
		}
	}
	

}
