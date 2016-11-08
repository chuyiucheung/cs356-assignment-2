package miniTwitter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import miniTwitter.Composite.UserGroup;
import miniTwitter.Observer.User;
import miniTwitter.Visitor.CheckPositiveVisitor;
import miniTwitter.Visitor.CheckTotalGroupVisitor;
import miniTwitter.Visitor.CheckTotalMessagesVisitor;
import miniTwitter.Visitor.CheckTotalUserVisitor;
import miniTwitter.Visitor.UserGroupVisitor;

public class AdminControlPanel {

	//Fields
	private static AdminControlPanel INSTANCE;
	private DefaultMutableTreeNode selectedNode;
	private String parentGroupName;
	private HashMap<String, UserGroup> userGroupMap = new HashMap<>(); 
	
	//Constructor
	private AdminControlPanel() {
		//Create and setup Window
		JFrame adminFrame = new JFrame();
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setup the content pane
		JPanel adminPanel = new JPanel(new GridBagLayout());
		adminFrame.getContentPane().add(adminPanel, BorderLayout.CENTER);
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.BOTH;
		gbConstraints.insets = new Insets(5,5,5,5);
		
		//Initiate Singleton NavTree
		NavTree userTree = NavTree.getInstance();
		JScrollPane jsp =new JScrollPane(userTree.getWidget());
		jsp.setPreferredSize(new Dimension(200, 200));
		adminFrame.add(jsp, BorderLayout.WEST);
		userTree.getWidget().getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		//Initiate root Group and put into userGroupMap hashmap
		Group root = new Group();
		root.setID(1000);
		userGroupMap.put("root", (UserGroup)root);
		
		//Initiate userIDTextField
		TextField userIDTextField = new TextField("User ID");
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		gbConstraints.gridheight = 1;
		adminPanel.add(userIDTextField.getWidget(), gbConstraints);
		
		//Initiate addUserButton
		Button addUserButton = new Button("Add User");
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 0;
		adminPanel.add(addUserButton.getWidget(), gbConstraints);
		addUserToTree(addUserButton, userIDTextField, userTree); //Calls private method to invoke EventHandler
		
		//Initiate groupIDTextField
		TextField groupIDTextField = new TextField("Group ID");
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		adminPanel.add(groupIDTextField.getWidget(), gbConstraints);
		
		//Initiate addGroupButton
		Button addGroupButton = new Button("Add Group");
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 1;
		adminPanel.add(addGroupButton.getWidget(), gbConstraints);
		addGroupToTree(addGroupButton, groupIDTextField, userTree);
		
		//Initiate openUserViewButton
		Button openUserViewButton = new Button("Open User View");
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		gbConstraints.gridwidth = GridBagConstraints.REMAINDER;
		adminPanel.add(openUserViewButton.getWidget(), gbConstraints);
		openUserView(openUserViewButton, userTree);   //Calls private method to invoke EventHandler
		
		//Initiate showUserTotalButton
		Button showUserTotalButton = new Button("Show User Total");
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 11;
		gbConstraints.gridheight = 1;
		gbConstraints.gridwidth = GridBagConstraints.RELATIVE;
		adminPanel.add(showUserTotalButton.getWidget(), gbConstraints);
		showUserTotal(showUserTotalButton, userTree); //Calls private method to invoke EventHandler
		
		//Initiate showGroupTotalButton
		Button showGroupTotalButton = new Button("Show Group Total Button");
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 11;
		adminPanel.add(showGroupTotalButton.getWidget(), gbConstraints);
		showGroupTotal(showGroupTotalButton, userTree); //Calls private method to invoke EventHandler
		
		//Initiate showMessagesTotalButton
		Button showMessagesTotalButton = new Button("Show Messages Total");
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 12;
		adminPanel.add(showMessagesTotalButton.getWidget(), gbConstraints);
		showMessagesTotal(showMessagesTotalButton, userTree); //Calls private method to invoke EventHandler
		
		//Initiate showPositiveButton
		Button showPositiveButton = new Button("Show Positive Percentage Button");
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 12;
		adminPanel.add(showPositiveButton.getWidget(), gbConstraints);
		showPositive(showPositiveButton, userTree); //Calls private method to invoke EventHandler
		
		//Display Window
		adminFrame.setTitle("MiniTwitter - Admin Control Panel");
		adminFrame.setSize(650, 300);
		adminFrame.setVisible(true);
	}
	
	//Singleton pattern, initiate INSTANCE only if INSTANCE has not been initiated
	public static AdminControlPanel getInstance() {
		if (INSTANCE == null) {
			synchronized (AdminControlPanel.class) {
				if (INSTANCE == null) {
					INSTANCE = new AdminControlPanel();
				}
			}
		}
		return INSTANCE;
	}
	
	//Getter
	//To retrieve an User object from the HashMap userGroupMap
	public UserGroup getUser(String user) {
		return userGroupMap.get(user);
	}
	
	//Internal Methods
	//This method specifies the behavior when addUserButton is pressed
	private void addUserToTree(Button button, TextField textField, NavTree navTree) {
		//listening to tree selection
		navTree.getWidget().addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)navTree.getWidget().getLastSelectedPathComponent();
		    /* if nothing is selected */ 
		        if (node == null) return;
		    /* retrieve the node that was selected */ 
		        Object nodeInfo = node.getUserObject();
		    /* React to the node selection. */
		        selectedNode = node;
		        parentGroupName = nodeInfo.toString();
		    }
		});
		//listening to button click
		button.getWidget().addActionListener(new ActionListener() {
        	public void actionPerformed (ActionEvent e) {
        		if (!userGroupMap.containsKey(textField.getWidget().getText())) { //if username is not in used already, do
        			User newUser = new User();                                    //create new user
        			newUser.setName(textField.getWidget().getText());             //set username
        			UserGroup temp = userGroupMap.get(parentGroupName);           //calculates and assign ID
        			Group parentGroup = (Group)temp;
        			int parentID = parentGroup.getID();
        			int childCount = parentGroup.getChildCount();
        			newUser.setID(parentID + childCount + 1);
        			parentGroup.addUserGroup((UserGroup)newUser);                 //udpate composite list
        			userGroupMap.put(textField.getWidget().getText(), (UserGroup)newUser);  //put UserGroup object into hashmap
        			navTree.addUser(selectedNode, new DefaultMutableTreeNode(textField.getWidget().getText()));  //create & display leaf
        			navTree.getWidget().updateUI();
        		}
			}
        });
	}
	
	//This method specifies the behavior when addGroupButton is pressed
	private void addGroupToTree(Button button, TextField textField, NavTree navTree) {
		navTree.getWidget().addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)navTree.getWidget().getLastSelectedPathComponent();
		    /* if nothing is selected */ 
		        if (node == null) return;
		    /* retrieve the node that was selected */ 
		        Object nodeInfo = node.getUserObject();
		    /* React to the node selection. */
		        selectedNode = node;
		        parentGroupName = nodeInfo.toString();
		    }
		});
		button.getWidget().addActionListener(new ActionListener() {
        	public void actionPerformed (ActionEvent e) {
        		if (!userGroupMap.containsKey(textField.getWidget().getText())) { //if group name is not in used already, do
        			Group newGroup = new Group();                                 //create new group
        			newGroup.setName(textField.getWidget().getText());            //set group name
        			UserGroup temp = userGroupMap.get(parentGroupName);           //calculates and assign ID
        			Group parentGroup = (Group)temp;
        			int parentID = parentGroup.getID();
        			newGroup.setID(parentID + 1000);
        			parentGroup.addUserGroup((UserGroup)newGroup);                 //udpate composite list
        			userGroupMap.put(textField.getWidget().getText(), (UserGroup)newGroup);  //put UserGroup object into hashmap
        			navTree.addGroup(selectedNode, new DefaultMutableTreeNode(textField.getWidget().getText()));
    				navTree.getWidget().updateUI();
        		}
			}
        });
	}
	
	//This method specifies the behavior when addGroupButton is pressed
	private void openUserView(Button button, NavTree navTree) {
		navTree.getWidget().addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)navTree.getWidget().getLastSelectedPathComponent();
		    /* if nothing is selected */ 
		        if (node == null) return;
		    /* retrieve the node that was selected */ 
		        Object nodeInfo = node.getUserObject();
		    /* React to the node selection. */
		        selectedNode = node;
		        parentGroupName = nodeInfo.toString();
		    }
		});
		button.getWidget().addActionListener(new ActionListener() {
        	public void actionPerformed (ActionEvent e) {
        		User selectedUser = (User)userGroupMap.get(selectedNode.toString());
        		if(selectedUser.getID() % 1000 != 0){
        			new UserPanel(selectedUser, INSTANCE);
        		}
			}
        });
	}
	
	//This method specifies the behavior when showUserTotalButton is pressed
	private void showUserTotal(Button button, NavTree navTree) {
		navTree.getWidget().addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)navTree.getWidget().getLastSelectedPathComponent();
		    /* if nothing is selected */ 
		        if (node == null) return;
		    /* retrieve the node that was selected */ 
		        Object nodeInfo = node.getUserObject();
		    /* React to the node selection. */
		        selectedNode = node;
		        parentGroupName = nodeInfo.toString();
		    }
		});
		button.getWidget().addActionListener(new ActionListener() {
        	public void actionPerformed (ActionEvent e) {
        		UserGroupVisitor v = new CheckTotalUserVisitor();
        		UserGroup selectedUserGroup = userGroupMap.get(selectedNode.toString());
        		if (selectedUserGroup.getID() % 1000 != 0) {
        			v.userVisit(userGroupMap, selectedUserGroup);
        		} else {
        			v.groupVisit(userGroupMap, selectedUserGroup);
        		}
			}
        });
	}
	
	//This method specifies the behavior when showGroupTotalButton is pressed
		private void showGroupTotal(Button button, NavTree navTree) {
			navTree.getWidget().addTreeSelectionListener(new TreeSelectionListener() {
			    public void valueChanged(TreeSelectionEvent e) {
			        DefaultMutableTreeNode node = (DefaultMutableTreeNode)navTree.getWidget().getLastSelectedPathComponent();
			    /* if nothing is selected */ 
			        if (node == null) return;
			    /* retrieve the node that was selected */ 
			        Object nodeInfo = node.getUserObject();
			    /* React to the node selection. */
			        selectedNode = node;
			        parentGroupName = nodeInfo.toString();
			    }
			});
			button.getWidget().addActionListener(new ActionListener() {
	        	public void actionPerformed (ActionEvent e) {
	        		UserGroupVisitor v = new CheckTotalGroupVisitor();
	        		UserGroup selectedUserGroup = userGroupMap.get(selectedNode.toString());
	        		if (selectedUserGroup.getID() % 1000 != 0) {
	        			v.userVisit(userGroupMap, selectedUserGroup);
	        		} else {
	        			v.groupVisit(userGroupMap, selectedUserGroup);
	        		}
				}
	        });
		}
		
		//This method specifies the behavior when showMessagesTotalButton is pressed
		private void showMessagesTotal(Button button, NavTree navTree) {
			navTree.getWidget().addTreeSelectionListener(new TreeSelectionListener() {
			    public void valueChanged(TreeSelectionEvent e) {
			        DefaultMutableTreeNode node = (DefaultMutableTreeNode)navTree.getWidget().getLastSelectedPathComponent();
			    /* if nothing is selected */ 
			        if (node == null) return;
			    /* retrieve the node that was selected */ 
			        Object nodeInfo = node.getUserObject();
			    /* React to the node selection. */
			        selectedNode = node;
			        parentGroupName = nodeInfo.toString();
			    }
			});
			button.getWidget().addActionListener(new ActionListener() {
	        	public void actionPerformed (ActionEvent e) {
	        		UserGroupVisitor v = new CheckTotalMessagesVisitor();
	        		UserGroup selectedUserGroup = userGroupMap.get(selectedNode.toString());
	        		if (selectedUserGroup.getID() % 1000 != 0) {
	        			v.userVisit(userGroupMap, selectedUserGroup);
	        		} else {
	        			v.groupVisit(userGroupMap, selectedUserGroup);
	        		}
				}
	        });
		}
		
		//This method specifies the behavior when showUserTotalButton is pressed
		private void showPositive(Button button, NavTree navTree) {
			navTree.getWidget().addTreeSelectionListener(new TreeSelectionListener() {
			    public void valueChanged(TreeSelectionEvent e) {
			        DefaultMutableTreeNode node = (DefaultMutableTreeNode)navTree.getWidget().getLastSelectedPathComponent();
			    /* if nothing is selected */ 
			        if (node == null) return;
			    /* retrieve the node that was selected */ 
			        Object nodeInfo = node.getUserObject();
			    /* React to the node selection. */
			        selectedNode = node;
			        parentGroupName = nodeInfo.toString();
			    }
			});
			button.getWidget().addActionListener(new ActionListener() {
	        	public void actionPerformed (ActionEvent e) {
	        		UserGroupVisitor v = new CheckPositiveVisitor();
	        		UserGroup selectedUserGroup = userGroupMap.get(selectedNode.toString());
	        		if (selectedUserGroup.getID() % 1000 != 0) {
	        			v.userVisit(userGroupMap, selectedUserGroup);
	        		} else {
	        			v.groupVisit(userGroupMap, selectedUserGroup);
	        		}
				}
	        });
		}
}