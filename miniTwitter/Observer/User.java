package miniTwitter.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import miniTwitter.Composite.UserGroup;
import miniTwitter.Subject.Subject;

public class User extends Subject implements UserGroup, Observer {
		
		//Fields
		private int id;              //User IDs are 1001, 1002,..., 2001, 2002, etc; 
		private String userName;     //1000, 2000, 3000, ... are reserved for Groups
		private List<String> messages;
		private List<User> followers;
		private String newsFeed;
		private HashMap<String, String> newsFeedMap;
		
		//Constructor
		public User() {
			id = 0;
			userName = null;
			messages = new ArrayList<String>();
			followers = new ArrayList<User>();
			newsFeedMap = new HashMap<>();
		}
		
		//UserGroup Interface Methods
		//Getters
		@Override
		public int getID() {
			return id;
		}
		
		@Override
		public String getName() {
			return userName;
		}
		
		public String getNewsFeed() {
			return newsFeed;
		}
		
		//Setters
		@Override
		public void setID(int id) {
			this.id = id;
		}
		
		@Override
		public void setName(String name) {
			userName = name;
		}
		
		//Other public methods
		public int getMessageCount() {
			return messages.size();
		}
		
		public void addMessages(String message) {
			messages.add(message);
		}
		
		public boolean containsFollower(User follower) {
			for (Observer u : observers) {
				if (follower == (User)u) {
					return true;
				}
			}
			return false;
		}
		
		//Observer Methods
		public void addFollowers(User user) {
			followers.add(user);
		}
		
		@Override
		public void update(Subject subject) { 
			if (subject instanceof User) { 
				newsFeedMap.put(((User) subject).getName(),((User) subject).getNewsFeed()); 
				displayNewsFeeds(); 
			} 
		} 
		
		//Observer internal method
		private void displayNewsFeeds() {
			//send newsFeeds to Observers' UserPanel instance, incomplete method
		}
		
}
