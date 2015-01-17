package ie.gmit.computing.util;

import ie.gmit.computing.Add;
import ie.gmit.computing.model.Node;
import ie.gmit.computing.model.TreeStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.util.Log;
import android.widget.Toast;

/**
 * It is a helper class which can be used to get and set children node values
 * @author Siyi_Kong
 *
 */
public final class Tool {

//	public static Map<integer,List<Node>> saveState=new HashMap<integer,List<Node>>();// to store the nodes of one layer
	
	/**
	 * It can return all children of a specific node
	 * @param node
	 * @return
	 */
	public static List<String> getAllChildren(Node node){
		Log.i("entered", node.getName());
		List<String> list=new ArrayList<>();
		list.clear();// to clear the data last time
		Node[] nodes=node.children();
		
		for(int i=0;i<nodes.length;i++){
			String name=nodes[i].getName();
			list.add(name);
		}
		return list;
	}
	
	/**
	 * It is used to add a new node after a specified parent node
	 * @param newNode
	 * @param ParentNode
	 * @return
	 */
	public static boolean addChildren(String newNode,String ParentNode){
		Node parent=null;
		if(nodeDuplicated(newNode)){
			return false;
		}else{
			parent=TreeStructure.datas.get(ParentNode);
			Node newnode=new Node(newNode, parent);
			TreeStructure.datas.put(newNode, newnode);
			parent.addChild(newnode);
		}
		return true;
	}
	/**
	 * It is used to add a new node in the middle of  a specified parent node and its child node
	 * @param newNode
	 * @param ParentNode
	 * @param childNode
	 * @return
	 */
	public static boolean addChildrenInBetween(String newNode,String ParentNode,String childNode){
		
		if(nodeDuplicated(newNode)){
			return false;
		}else {
			Node parent=TreeStructure.datas.get(ParentNode);
			Node child=TreeStructure.datas.get(childNode);
			Node newnode=new Node(newNode, parent);
			child.setParent(newnode);
		}
		return true;
	}
	
	/**
	 * It is used to judge if the node you are going to add is duplicated
	 * @param node
	 * @return
	 */
	public static boolean nodeDuplicated(String node){
		Iterator<String> nodes=Node.nodesName.iterator();
		while(nodes.hasNext()){
			String names=nodes.next();
			if(names==node)
				return true;
		}
		return false;
	}
	
	/**
	 * It is used to judge if the node you are going to add is duplicated
	 * @param node
	 * @return
	 */
	public static void deleteNode(String NodeName){
		Node node=TreeStructure.datas.get(NodeName);
		Node parent=node.getParent();
		
		Node[] nodes=node.children();
		
		for(int i=0;i<nodes.length;i++){
			nodes[i].setParent(parent);
			parent.addChild(nodes[i]);
		}
		
		parent.removeChild(node);
		Node.nodesName.remove(node);
		TreeStructure.datas.remove(node);
	}
	
}
