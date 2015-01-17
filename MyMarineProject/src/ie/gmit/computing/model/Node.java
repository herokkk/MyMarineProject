package ie.gmit.computing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * It is an entity class that describe a node
 * @author Siyi_Kong
 *
 */
public class Node implements Serializable{
	private static final long serialVersionUID = 1L;
	private Node parent;
	public  List<Node> children=new ArrayList<Node>();
	public static List<String> nodesName=new ArrayList<>();
	public static  List<Node> children2=new ArrayList<Node>();
	private ImageIcon image=null;
	private String name;
	
	/**
	 * Constructor
	 * @param name The name of a new node
	 * @param parent The parent node of the new node
	 */
public Node(String name,Node parent) {
		super();
		this.parent = parent;
		this.name=name;
		
	}

/**
 * To set the parent for the node
 * @param parent
 */
public void setParent(Node parent){
	this.parent=parent;
}

/**
 * Get the parent for the node
 * @param parent
 */
 public Node getParent(){
	 return this.parent;
 }
 
 /**
  * Add a new child node on the node
  * @param parent
  */
 public void addChild(Node child){
	 if(!nodesName.contains("start")){
		 nodesName.add("start");
	 }
	 if(!nodesName.contains(child.getName()))
	 nodesName.add(child.getName());
	 
	 children.add(child);
 }
 
 /**
  * Remove a child node from the node
  * @param child
  */
 public void removeChild(Node child){
	 children.remove(child);
 }
 
 /**
  * insert a node in between of this and existing
  * @param next The next Node that is going to be added in between of this and existing node
  * @param existing The child node of the current node
  */
 public void insertChild(Node next,Node existing){
	 existing.setParent(next);
	 next.setParent(this);
 }
 
 /**
  * It sets current node as the parent of newNode 
  * @param newNode The node that is going to be a child
  */
 public void insertAfterNode(Node newNode){
	 newNode.setParent(this);
 }
 public Node[] children(){
	Node[] temp=new Node[children.size()];
	for(int i=0;i<children.size();i++){
		temp[i]=children.get(i);
	}
	return temp;
 }
 /**
  * To judge if it has a image associated with
  * @return
  */
 public boolean hasImage(){
	 return this.image!=null;
 }
 /**
  * Judge if it is a leaf
  * @return
  */
 public boolean isLeaf(){
	 return children.size()==0;
 }
 /**
  * Judge if it is root
  * @return
  */
 public boolean isRoot(){
	 return this.parent==null;
 }
 /**
  * Set the image for the node
  * @return
  */
 public void setImage(ImageIcon image) {
	this.image = image;
}
/**
  * Set the name for the node
  * @return
 */
 public void setName(String name) {
	this.name = name;
}
 /**
  * Get the name of the node
  * @return
  */
 public String getName() {
	return name;
}
 /**
  * Counts the number of children of this node
  * @return
  */
 public int NumOfChild(){
	 return children.size();
 }
 
}