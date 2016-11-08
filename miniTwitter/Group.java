package miniTwitter;

import java.util.ArrayList;
import java.util.List;
import miniTwitter.Composite.UserGroup;

public class Group implements UserGroup {
	
	//Fields
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();
	private String groupName;
	private int id;                //Group IDs are 1000, 2000, 3000, depending on the distance from root
	
	//UserGroup Interface Methods
	//Getters
	@Override
	public String getName() {
		return groupName;
	}

	@Override
	public int getID() {
		return id;
	}
	
	//Setters
	@Override
	public void setName(String name) {
		groupName = name;
	}
	
	@Override
	public void setID(int id) {
		this.id = id;
	}
	
	//Composite Methods
	public void addUserGroup(UserGroup component) {
		userGroups.add(component);
	}

	public List<UserGroup> getChild() {
		return userGroups;
	}
	
	public int getChildCount() {
		return userGroups.size();
	}
}
