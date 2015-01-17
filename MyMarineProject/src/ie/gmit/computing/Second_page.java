package ie.gmit.computing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ie.gmit.computing.R.layout;
import ie.gmit.computing.model.Node;
import ie.gmit.computing.model.TreeStructure;
import ie.gmit.computing.preference.MyDialogPrefrence;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.view.ViewGroup;

/**
 * It is mainly about buttons display that every time users press one button, it shows its children in the
 * form of buttons. It can also new nodes and deleting nodes to make some changes on the button display
 * and it can set scientists' information through setting.
 * @author Siyi_Kong
 *
 */
public class Second_page extends Activity implements OnClickListener{

	Button start;
	//Button back;
	TreeStructure structure;
	public static final int BTN_WIDTH=100;
	public static final int BTN_HEIGHT=20;
	int count=0;
	 LinearLayout layout;
	 LinearLayout backLayout;
	 LinearLayout newLayout;
	 RelativeLayout.LayoutParams layoutParams;
	 
	 Button[] tempButtons=null;
	 Map<Node, Button> relations=new HashMap<Node,Button>();
	 List<Node> tempNode=new ArrayList<Node>();
	 
	 Node tempParent=null;// temperarily parent node
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_page);
		layout=(LinearLayout)findViewById(R.id.buttonsShow);
		backLayout=(LinearLayout)findViewById(R.id.backbutton);// this is used to contain "back" only
		
//		layoutParams=new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
//		layoutParams.addRule(RelativeLayout.ABOVE,);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		 
//		layout.setGravity(Gravity.CENTER_VERTICAL);
		 
		structure=new TreeStructure();
		start=new Button(getApplicationContext());
		start.setId(count);
		start.setGravity(Gravity.CENTER_VERTICAL);
		start.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
		start.setText("start");
		start.setOnClickListener(this);
		
		//add back
		Button back=new Button(getApplicationContext());
		back.setText("back");
		back.setGravity(Gravity.CENTER_VERTICAL);
		back.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		back.setId(1000);// 1000 is back
		back.setOnClickListener(this);
		
		backLayout.addView(back);
		layout.addView(start);
		
		/*******New layout**********/
		newLayout=new LinearLayout(getApplicationContext());// initiliazing the layout
		newLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		newLayout.setOrientation(LinearLayout.VERTICAL);
		newLayout.setGravity(Gravity.CENTER);
	}
	/**
	 * It is triggered by buttons on the screen, except the three button Add, delete,setting
	 */
	@Override
	public void onClick(View v) {
 
		if(v.getId()==1000){
			Log.i("id is 1000 now", v.getId()+"");
			GoToLastLayer(newLayout);
		}
		else {
			 count++; // id plus one everytime a view is clicked
			  
			  Button temp=(Button)findViewById(v.getId());
			  Log.i("id", temp.toString());
			  String nodeName=(String) temp.getText();//Null pointer
			  Node parent=TreeStructure.datas.get(nodeName);
			  
			 // tempParent=parent.getParent(); //get the parent of the node
			  searchChildren(parent,temp);// temp button as we can use 
		}
	 
	}
	
	/**
	 * To check if it has children and the number of children. If it is a leaf, jump to another page which would show 
	 * picture you have taken
	 * @param parentNode The node that is going to be searched
	 * @param button the button associated with the parentNode
	 */
	public void searchChildren(Node parentNode,Button button){
		
		if(parentNode.children.size()>0){
			Log.i("enter1","entere1");
			
			
			
			Node[] nodes=parentNode.children();// get the children of the current code
			
			if(parentNode.getParent()!=null){
				
				Iterator<Node> iterator=parentNode.getParent().children.iterator();
				
				while(iterator.hasNext()){
					Node node=iterator.next();
					tempNode.add(node);// the node that is going to be stored as last layer and has been set gone
					relations.get(node).setVisibility(View.GONE);
				}
				
			}else {
				button.setVisibility(View.GONE);
			}
			
			
			for(int i=0;i<parentNode.NumOfChild();i++){
				  
				  
				Node node=nodes[i];
				 String nodeNname=node.getName();
				 
				 Button btn=new Button(getApplicationContext());
				 btn.setText(nodeNname);
				 btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 btn.setGravity(Gravity.CENTER_VERTICAL);
				 btn.setId(count);
				 btn.setOnClickListener(this);
				 
//				 layout.removeView(start);
//				 
//	             layout.removeView(newLayout);
//	            
//				 newLayout.addView(btn);
				 
				
				 layout.addView(btn);
				 
				 count++;
				 
				 relations.put(node, btn);
			}
			newLayout=getLinearLayout();
			}else{
				//jump to another page, and it is leaf
				Bundle bundle=new Bundle();
				Intent intent=new Intent(Second_page.this,Pic_page.class);
				bundle.putString("name", parentNode.getName());
				intent.putExtras(bundle);
				startActivity(intent);// 
			}
		}
	
	/**
	 * It is used to return the last layer that was covered by the present layer
	 * @param view
	 */
	public void GoToLastLayer(View view){
		layout.removeView(view);
		
	}
	
	/**
	 * It is called by the button add
	 * @param view
	 */
	public void add_triggered(View view){
		Intent intent=new Intent(Second_page.this,Add.class);
		startActivity(intent);
	}

	/**
	 * It is called by the button delete
	 * @param view
	 */
    public void delete_triggered(View view){
    	Intent intent=new Intent(Second_page.this,Delete.class);
		startActivity(intent);
	}

    /**
	 * It is called by the button setting
	 * @param view
	 */
    public void setting_triggered(View view){
    	Intent intent=new Intent(Second_page.this,MyDialogPrefrence.class);
		startActivity(intent);
}
    /**
     * The back event is triggered when pressing physical button back
     */
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent=new Intent(Second_page.this,MainActivity.class);
		startActivity(intent);
	}
    
    /**
     * It creates a new LinearLayout
     * @return
     */
    public LinearLayout getLinearLayout(){
    	
		newLayout=new LinearLayout(getApplicationContext());// initiliazing the layout
		newLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		newLayout.setOrientation(LinearLayout.VERTICAL);
		newLayout.setGravity(Gravity.CENTER);
		return newLayout;
    }
}

