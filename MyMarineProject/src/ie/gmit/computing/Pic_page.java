package ie.gmit.computing;

import ie.gmit.computing.model.Node;
import ie.gmit.computing.preference.MyDialogPrefrence;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;





import java.util.concurrent.ExecutionException;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * It is the last page that wiil shows the leaf node, selecting picture and save all the informations
 * to SD card
 * @author Siyi_Kong
 *
 */
public class Pic_page extends Activity{

	private final String IMAGE_TYPE="image/*";
	private final int IMAGE_CODE=0;
	
	Button add;
	Button delete;
	Button settings;
	
	Button select;
	Button cancel;
	
	Bitmap picBitmap=null;
	String selectedNodeName;
	Intent intent;
	Bundle bundle;
	TextView nodeTextView;
	
	ImageView imageView;
	
	LocationManager locationManager;
	LocationListener locationListener;
	
	 double latitude;
	 double longtitude;
	 double altitude;
	 String time;
	 
	 SimpleDateFormat simpleDateFormat;
	 Date date;
	 
	 String msg;
	 
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_shown);
		add=(Button)findViewById(R.id.pic_add);
		delete=(Button)findViewById(R.id.pic_delete);
		settings=(Button)findViewById(R.id.pic_Prefer);
		select=(Button)findViewById(R.id.select);
		cancel=(Button)findViewById(R.id.backFromImage);
	
		nodeTextView=(TextView)findViewById(R.id.imageName);
		imageView=(ImageView)findViewById(R.id.imageshow);
		
		intent=getIntent();
		bundle=intent.getExtras();
		selectedNodeName=bundle.getString("name");
		
		
		nodeTextView.setText(selectedNodeName);
		
		 simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
		 date=new Date(System.currentTimeMillis());
		
		locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		String provider=LocationManager.GPS_PROVIDER;
		Location location=locationManager.getLastKnownLocation(provider);
		
		getLocation(location);// get info
        locationListener=new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				
				getLocation(location);
				
				
			}
		};
		
		locationManager.requestLocationUpdates(provider, 2000, 0, locationListener);
		

		getPicture();
		
		select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
	           msg="Longtitude:"+longtitude+"\n"
						+"Altitude:"+altitude+"\n"
						+"Latitude:"+latitude+"\n"+
						"Time: "+time+"\n";
	            
				AlertDialog alertDialog=new AlertDialog.Builder(Pic_page.this)
				.setTitle("Information").setMessage(msg).setPositiveButton("Save", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								try {
									ProgressDialog dialog2=new ProgressDialog(Pic_page.this);
									dialog2.setTitle("Notification");
									dialog2.setMessage("Saving datas and pic to SD card");
									dialog2.show();
									
									Thread.sleep(2000);
									
									if(saveInfo()){
										dialog2.dismiss();
										Toast.makeText(Pic_page.this, "Saved", Toast.LENGTH_SHORT).show();
									}else{
										dialog2.dismiss();
										Toast.makeText(Pic_page.this, "Failed, please try agagin!", Toast.LENGTH_SHORT)
										.show();
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								return;
							}
						}).create();
				
	
				alertDialog.show();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   return;
			}
		});
	}
	
	/**
	 * To get the lastest information about location
	 * @param location
	 */
	public void getLocation(Location location){
		altitude=location.getAltitude();
		longtitude=location.getLongitude();
		latitude=location.getLatitude();
		time=simpleDateFormat.format(new Date(location.getTime()));
		
	}
	
	/**
	 * This is used to save all relevant info ie,GPS coordinates
	 * @throws Exception
	 */
	public boolean saveInfo() throws Exception{
		if(Environment.MEDIA_MOUNTED.endsWith(Environment.getExternalStorageState())){
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
                File sdCardDir = Environment.getExternalStorageDirectory();
                File saveFile = new File(sdCardDir, "info.txt");  
                FileOutputStream outStream = new FileOutputStream(saveFile);  
                outStream.write(msg.getBytes());  // write datas into sd card
                outStream.close();  
            }
			return true;
		}
		return false;
	}
	/**
	 * This is used to get picture you have taken in the main page 
	 * @return
	 */
	public Bitmap getPicture(){
		
		Bitmap bitmap = null;
		
		Intent getimage=new Intent(Intent.ACTION_GET_CONTENT);
		getimage.setType(IMAGE_TYPE);
		startActivityForResult(getimage, IMAGE_CODE);
		return bitmap;
	}
	
	/**
	 * It is used to set the image by the picture you have taken
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	  if(resultCode!=RESULT_OK){
		  Log.e("Tag", "activity results error");
		 return;
	  }
	  
	  ContentResolver resolver=getContentResolver();
	  
	  if(requestCode==IMAGE_CODE){
		  
		  Uri uri=data.getData();
		  Log.i("Tag", "after uri");
		  try {
			picBitmap=MediaStore.Images.Media.getBitmap(resolver, uri);
		   imageView.setImageBitmap(picBitmap);
		   Log.i("Tag", "after set");
		  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	}
	/**
	 * It is called by the button add
	 * @param view
	 */
	 public void add_triggered(View view){
	    	Intent intent=new Intent(Pic_page.this,Add.class);
			startActivity(intent);
		}

	 /**
		 * It is called by the button delete
		 * @param view
		 */
	    public void delete_triggered(View view){
	    	Intent intent=new Intent(Pic_page.this,Delete.class);
			startActivity(intent);
		}

	    /**
		 * It is called by the button setting
		 * @param view
		 */
	    public void setting_triggered(View view){
	    	Intent intent=new Intent(Pic_page.this,MyDialogPrefrence.class);
			startActivity(intent);
	}
}
