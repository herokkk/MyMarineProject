package ie.gmit.computing.model;

import java.util.HashMap;
import java.util.Map;
/**
 * It is used to initialize the tree structure 
 * @author Siyi_Kong
 *
 */
public class TreeStructure {

	public static Map<String, Node> datas=new HashMap<String,Node>();
	/**
	 * The constructor for initializing the nodes
	 */
	public TreeStructure(){
		Node root=new Node("start",null);
		datas.put("start", root);
		
		Node solid=new Node("solid",root);
	    root.addChild(solid);
	    datas.put("solid", solid);
	    
	    Node flexible=new Node("flexible",root);
	    root.addChild(flexible);
	    datas.put("flexible", flexible);
	    
	    Node hard=new Node("hard",solid);
	    solid.addChild(hard);
	    datas.put("hard", hard);
	    
	    Node squashed=new Node("can be squashed",solid);
	    //squashed.setImage(new ImageIcon("me.png"));
	    solid.addChild(squashed);
	    datas.put("can be squashed", squashed);
	    
	    Node Filamentous=new Node("Filamentous",flexible);
	    flexible.addChild(Filamentous);
	    datas.put("Filamentous", Filamentous);
	    
	    Node large2D=new Node("Large2D SurfaceArea",flexible);
	    flexible.addChild(large2D);
	    datas.put("Large2D SurfaceArea",large2D);
	    
	    Node Smooth=new Node("Smooth edges",hard);
	    hard.addChild(Smooth);
	    datas.put("Smooth edges",Smooth);
	    
	    Node irregular=new Node("irregular edges",hard);
	    hard.addChild(irregular);
	    datas.put("irregular edges",irregular);
	    
	    Node styrene=new Node("styrene",squashed);
	    squashed.addChild(styrene);
	    datas.put("styrene",styrene);
	    
	    Node fibre=new Node("fibre",Filamentous);
	    Filamentous.addChild(fibre);
	    datas.put("fibre",fibre);
	    
	    Node film=new Node("film",large2D);
	    large2D.addChild(film);
	    datas.put("film",film);
	    
	    Node fragment=new Node("fragment",irregular);
	    irregular.addChild(fragment);
	    datas.put("fragment",fragment);
	    
	    Node edges=new Node("edges",fragment);
	    fragment.addChild(edges);
	    datas.put("edges",edges);
	    
	    Node all=new Node("All Argular",edges);
	    edges.addChild(all);
	    datas.put("All Argular",all);
	    
	    Node most=new Node("Most Argular",edges);
	    edges.addChild(most);
	    datas.put("Most Argular",most);
	    
	    Node mostrounded=new Node("Most rounded",edges);
	    edges.addChild(mostrounded);
	    datas.put("Most rounded",mostrounded);
	    
	    Node allrounded=new Node("All rounded",edges);
	    edges.addChild(allrounded);
	    datas.put("All rounded",allrounded);
	    
	    Node resin=new Node("resin",Smooth);
	    Smooth.addChild(resin);
	    datas.put("resin",resin);
	    
	    Node cylindrical=new Node("cylindrical",resin);
	    resin.addChild(cylindrical);
	    datas.put("cylindrical",cylindrical);
	    
	    Node rounded=new Node("rounded",resin);
	    resin.addChild(rounded);
	    datas.put("rounded",rounded);
	    
	    Node longg=new Node("long",cylindrical);
	    cylindrical.addChild(longg);
	    datas.put("long",longg);
	    
	   
	    Node flat=new Node("flat",cylindrical);
	    cylindrical.addChild(flat);
	    datas.put("flat",flat);
	    
	    Node oval=new Node("oval",rounded);
	    rounded.addChild(oval);
	    datas.put("oval",oval);
	    
	    Node sphere=new Node("sphere",rounded);
	    rounded.addChild(sphere);
	    datas.put("sphere",sphere);
	}
}
