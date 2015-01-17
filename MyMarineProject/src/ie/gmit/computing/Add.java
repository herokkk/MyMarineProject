package ie.gmit.computing;

import ie.gmit.computing.model.Node;
import ie.gmit.computing.model.TreeStructure;
import ie.gmit.computing.util.Tool;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * It is the Add page that is to add new nodes after or in between of two nodes
 * @author Siyi_Kong
 *
 */
public class Add extends Activity implements OnClickListener{

	Button save;
	Button cancel;
	
	EditText newNode;
	
	Spinner parentSpinner;
	Spinner childrenSpinner;
	
	List<String> parent=new ArrayList<>();
	List<String> childrenList=new ArrayList<>();
	
	ArrayAdapter<String> adapter;
	ArrayAdapter<String> adapterChildren;
	
	String selectedParent="";
	String selectedChildren="";

	Node node=null;
	private int state=1; // the state is the state of radio button, 1 is left, 2 is right
	
	RadioButton radioButton1;
	RadioButton radioButton2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		parentSpinner=(Spinner)findViewById(R.id.parent_spiner);
		childrenSpinner=(Spinner)findViewById(R.id.children_spiner);
		newNode=(EditText)findViewById(R.id.newNode);
		save=(Button)findViewById(R.id.toAdd);
		cancel=(Button)findViewById(R.id.toCancel);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
		if(state==1){
			childrenSpinner.setVisibility(View.INVISIBLE);
		}
		parent.addAll(Node.nodesName);
		
		adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, parent);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		parentSpinner.setAdapter(adapter);

		
		parentSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
                childrenList.clear();// clear all previous datas
				selectedParent=adapter.getItem(position);
				
				node=TreeStructure.datas.get(selectedParent);
			
				generateChildrenSpinner(node);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		childrenSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectedChildren=adapterChildren.getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * It reacts the changes made on two radio button and switch to a specific mode for adding 
	 * @param view
	 */
	public void OnRadioButtonClicked(View view){
		boolean checked=((RadioButton)view).isChecked();
		
		switch (view.getId()) {
		case R.id.insert_after:
			if(checked){
				state=1;
				radioButton2=(RadioButton)findViewById(R.id.insert_between);
				radioButton2.setChecked(false);
				childrenSpinner.setVisibility(View.INVISIBLE);// invisible if check
			}
			break;

		case R.id.insert_between:
            if(checked){
            	state=2;
            	radioButton1=(RadioButton)findViewById(R.id.insert_after);
            	radioButton1.setChecked(false);
            	childrenSpinner.setVisibility(View.VISIBLE);
			}
			break;
		}
	}
	
	
	/**
	 * When users choose a parent node, it is to figure out the children the parent node has
	 * @param node
	 */
	public void generateChildrenSpinner(Node node){
		
		childrenList.addAll(Tool.getAllChildren(node));
		
		adapterChildren=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, childrenList);
		adapterChildren.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		childrenSpinner.setAdapter(adapterChildren);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.toAdd:
			if(state==1){
				if(newNode.getText().toString()!="" && selectedParent!="")
				{
					String Node=newNode.getText().toString();
					if(Tool.addChildren(Node, selectedParent)){
						//显示出一个alert对话框，等几秒跳转
						selectedParent="";
						popUpWindow();
						Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(getApplicationContext(), "Sorry,the new node you entered is duplicated", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.
					makeText(getApplicationContext(), "Please enter the new node and the target node ", Toast.LENGTH_SHORT)
					.show();;
				}
			}else if(state==2){
				if(newNode.getText().toString()!="" && selectedParent!="" && selectedChildren!=""){
					String Node=newNode.getText().toString();
					if(Tool.addChildrenInBetween(Node, selectedParent, selectedChildren)){
						popUpWindow();
						Intent intent=new Intent(Add.this,Second_page.class);
						startActivity(intent);
					}else {
						Toast.makeText(getApplicationContext(), "Sorry,the new node you entered is duplicated", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.
					makeText(getApplicationContext(), "Please enter the new node and the parent and child node ", Toast.LENGTH_SHORT)
					.show();;
				}
			}
			
			break;

		case R.id.toCancel:
			Intent intent=new Intent(Add.this,Second_page.class);
			startActivity(intent);
			break;
		}
	}
	
	/**
	 * A progressDialog will be popped up for verifying the state
	 */
	public void popUpWindow(){
		final ProgressDialog progressDialog=ProgressDialog.show(Add.this,"Notification","Wait a second!");
		progressDialog.show();
		new Thread(){
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					progressDialog.dismiss();
				}
			};
			
		}.start();
		
		
//		Intent intent=new Intent(Add.this,Second_page.class);
//		startActivity(intent);
	}

	}
	

