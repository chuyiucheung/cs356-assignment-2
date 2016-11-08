package miniTwitter.Visitor;

import java.util.HashMap;
import miniTwitter.Composite.UserGroup;

public interface UserGroupVisitor {
	
	//Visitor pattern methods
	public void userVisit(HashMap<String, UserGroup> map, UserGroup component);
	public void groupVisit(HashMap<String, UserGroup> map, UserGroup component);

}
