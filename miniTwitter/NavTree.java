package miniTwitter;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class NavTree {
	
	//Fields
	private static NavTree INSTANCE;
	private DefaultTreeModel treeMod;
	private JTree tree;
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

	//Methods
	//Private constructor
	private NavTree() {
		treeMod = new DefaultTreeModel(root);
		tree = new JTree();
		tree.setModel(treeMod);
		tree.setVisible(true);
		tree.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	//Singleton Pattern, initiate INSTANCE only if it hasn't been initiated
	public static NavTree getInstance() {
		if (INSTANCE == null) {
			synchronized (NavTree.class) {
				if (INSTANCE == null) {
					INSTANCE = new NavTree();
				}
			}
		}
		return INSTANCE;
	}
	
	//Getters
	public JTree getWidget() {
		return tree;
	}

	public DefaultTreeModel getTreeMod() {
		return treeMod;
	}
	
	//Setter
	public void setTreeMod(DefaultTreeModel treeMod) {
		this.treeMod = treeMod;
	}
	
	//Tree Operation Methods
	public void addUser(DefaultMutableTreeNode parentGroup, DefaultMutableTreeNode childUser) {
		parentGroup.add(childUser);
		childUser.setAllowsChildren(false);
	}
	
	public void addGroup(DefaultMutableTreeNode parentGroup, DefaultMutableTreeNode childGroup) {
		parentGroup.add(childGroup);
		childGroup.setAllowsChildren(true);
	}
	
}
