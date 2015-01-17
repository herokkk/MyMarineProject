package ie.gmit.computing.preference;

import ie.gmit.computing.Add;
import ie.gmit.computing.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * The class is to build desired DialogPreference to set some information for scientists
 * @author Siyi_Kong
 *
 */
public class MyDialogPrefrence extends PreferenceActivity{

	Context context=null;
	EditTextPreference shipname;
	EditTextPreference scientistname;
	EditTextPreference email;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		
		Button button=new Button(getApplicationContext());
		button.setText("Update");
		button.setTextColor(Color.BLACK);
		setListFooter(button);
		
		shipname=(EditTextPreference)findPreference("shipname");
		shipname.setPositiveButtonText("Okay");
		shipname.setNegativeButtonText("Cancel");
		
		
		scientistname=(EditTextPreference)findPreference("scientistname");
		scientistname.setPositiveButtonText("Okay");
		scientistname.setNegativeButtonText("Cancel");
		
		
		
		email=(EditTextPreference)findPreference("email");
		email.setPositiveButtonText("Okay");
		email.setNegativeButtonText("Cancel");
		
		/*******To avoid users forgetting to update**********/
		shipname.setSummary(shipname.getText());
		scientistname.setSummary(scientistname.getText());
		email.setSummary(email.getText());
		/*******To avoid users forgetting to update**********/
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final ProgressDialog progressDialog=ProgressDialog.show(MyDialogPrefrence.this,"Notification","Updating...");
				progressDialog.show();
				new Thread(){
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							// TODO: handle exception
						}finally{
							progressDialog.dismiss();
						}
					};
					
				}.start();
				
				shipname.setSummary(shipname.getText());
				scientistname.setSummary(scientistname.getText());
				email.setSummary(email.getText());
			}
		});
	}
}
