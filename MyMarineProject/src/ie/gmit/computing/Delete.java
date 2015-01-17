package ie.gmit.computing;

import java.util.ArrayList;
import java.util.List;

import ie.gmit.computing.model.Node;
import ie.gmit.computing.model.TreeStructure;
import ie.gmit.computing.util.Tool;
import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * It is delete page that handles the removal of nodes
 * @author Siyi_Kong
 *
 */
public class Delete extends Activity{

	Button save;
	Spinner spinner;
	ArrayAdapter<String> arrayAdapter;
	List<String> parent=new ArrayList<>();
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(ie.gmit.computing.R.layout.delete);
		save=(Button)findViewById(ie.gmit.computing.R.id.delete_save);
		spinner=(Spinner)findViewById(ie.gmit.computing.R.id.parent_spiner_delete);
		
		parent.addAll(Node.nodesName);
		arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,parent);
		//arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner.setAdapter(arrayAdapter);
		
      spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			name=arrayAdapter.getItem(position);
			Toast.makeText(getApplicationContext(), name+" is selected", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	});
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Tool.deleteNode(name);
				Toast.makeText(getApplicationContext(), name+" is deleted", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
