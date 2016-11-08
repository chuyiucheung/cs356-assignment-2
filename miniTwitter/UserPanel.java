package miniTwitter;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import miniTwitter.Composite.UserGroup;
import miniTwitter.Observer.User;

public class UserPanel {
	
	//Fields
	private UserGroup user;
	private AdminControlPanel adminInstance;
	
	//Constructor
	public UserPanel(UserGroup user, AdminControlPanel instance) {
		this.user = user;
		this.adminInstance = instance;
		//Create and setup Window
		JFrame userFrame = new JFrame();
			
		//Setup the content pane
		JPanel userPanel = new JPanel(new GridBagLayout());
		userFrame.getContentPane().add(userPanel, BorderLayout.NORTH);
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.BOTH;
		gbConstraints.insets = new Insets(5,5,5,5);
		
		//Initiate userIDTextField
		TextField userIDTextField = new TextField("Username");
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		userPanel.add(userIDTextField.getWidget(), gbConstraints);
		
		//Initiate followUserButton
		Button followUserButton = new Button("Follow User");
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 0;
		userPanel.add(followUserButton.getWidget(), gbConstraints);
		
		//Initiate followListTextArea
		TextArea followListTextArea = new TextArea("List View (Currently Following)", 7, 1);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.gridwidth = GridBagConstraints.REMAINDER;
		userPanel.add(followListTextArea.getWidget(), gbConstraints);
		followUser(followUserButton, userIDTextField, followListTextArea); //Calls private method to invoke EventHandler
		
		//Initiate tweetTextField
		TextField tweetTextField = new TextField("Tweet Message");
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		gbConstraints.gridwidth = GridBagConstraints.RELATIVE;
		userPanel.add(tweetTextField.getWidget(), gbConstraints);
		
		//Initiate postTweetButton
		Button postTweetButton = new Button("Post Tweet");
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 2;
		userPanel.add(postTweetButton.getWidget(), gbConstraints);
		
		//Initiate newsFeedsTextArea
		TextArea newsFeedsTextArea = new TextArea("List View (News Feed)", 7, 1);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		gbConstraints.gridwidth = GridBagConstraints.REMAINDER;
		userPanel.add(newsFeedsTextArea.getWidget(), gbConstraints);
		displayNewsFeed(postTweetButton, tweetTextField, newsFeedsTextArea); //Calls private method to invoke EventHandler
		
		//Display Window
		userFrame.setTitle(user.getName()+"(ID: "+user.getID()+")");
		userFrame.setSize(300, 400);
		userFrame.setVisible(true);
	}

	//Internal Methods
	//This method specifies the behavior when followUserButton is pressed
	private void followUser(Button button, TextField textField, TextArea textArea) {
		button.getWidget().addActionListener(new ActionListener() {
        	public void actionPerformed (ActionEvent e) {
        		User currentUser = (User)user;
        		User currentFollower = (User)adminInstance.getUser(textField.getWidget().getText());
        		if (!currentUser.containsFollower(currentFollower)                             //if the follower is not already in follower list
        				&& adminInstance.getUser(textField.getWidget().getText()) != null      //and the follower exists
        				&& currentUser != currentFollower) {                                   //and the follower is not the user
        			currentUser.addFollowers(currentFollower);
        			currentUser.attach(currentFollower);
        			textArea.getWidget().append("\n"+textField.getWidget().getText());
        		}
			}
        });
	}

	//This method specifies the behavior when postTweetButton is pressed
	private void displayNewsFeed(Button button, TextField textField, TextArea textArea) {
		button.getWidget().addActionListener(new ActionListener() {
        	public void actionPerformed (ActionEvent e) {
        		User currentUser = (User)user;
        		currentUser.addMessages(textField.getWidget().getText());
        		textArea.getWidget().append("\n"+textField.getWidget().getText());
			}
        });
	}

	
}
