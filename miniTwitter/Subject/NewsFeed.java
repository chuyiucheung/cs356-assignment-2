package miniTwitter.Subject;

import miniTwitter.Composite.UserGroup;

public class NewsFeed extends Subject {
	
	//Fields
	private UserGroup user;
	private String newsFeed;
	
	//Observer pattern methods
	public NewsFeed(UserGroup user, String news) {
		this.user = user;
		newsFeed = news;
	}
	
	public String getNewsFeed() {
		return newsFeed;
	}
	
	public UserGroup getUser() {
		return user;
	}
}
