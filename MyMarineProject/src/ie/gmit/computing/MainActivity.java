package ie.gmit.computing;


import ie.gmit.computing.model.Node;
import ie.gmit.computing.model.TreeStructure;
import ie.gmit.computing.preference.MyDialogPrefrence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Iterator;
import java.util.Map;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

@SuppressWarnings("deprecation")
/**
 * This is the first page that can take picture and go to more option to do something with buttons
 * @author Siyi_Kong
 *
 */
public class MainActivity extends Activity implements SurfaceHolder.Callback,OnClickListener,PictureCallback{

	static final int REQUEST_IMAGE_CAPTURE = 1;
//	TabHost tabHost;
//	TabWidget tabWidget;
	
    	    
Button add;
Button delete;
Button preference;
Button shot;
Button moreButtons;
SurfaceView surfaceView;
SurfaceHolder holder;
Camera camera;
public static int i=1;

ObjectInputStream inputStream;
ObjectOutputStream outputStream;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		TreeStructure.datas.clear();
//		Node.children.clear();
//		Node.nodesName.clear();
		
	
//		try {
//			inputStream=new ObjectInputStream(new FileInputStream(new File("tree.ser")));
//			//TreeStructure.datas=(Map<String, Node>) inputStream.readObject();
//			Node.children=(java.util.List<Node>) inputStream.readObject();
//			//Node.nodesName=(java.util.List<String>) inputStream.readObject();
//			
//			inputStream.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
		add=(Button) findViewById(R.id.add);
        delete=(Button) findViewById(R.id.delete);
        preference=(Button) findViewById(R.id.Prefer);
        shot=(Button)findViewById(R.id.press);
        shot.setId(0);
        shot.setOnClickListener(this);
        
        moreButtons=(Button)findViewById(R.id.more);
        moreButtons.setId(1);
        moreButtons.setOnClickListener(this);
        
        surfaceView=(SurfaceView)findViewById(R.id.camera);
        surfaceView.setFocusable(true);
        surfaceView.setFocusable(true);
        surfaceView.setClickable(true);
        surfaceView.setOnClickListener(this);
        holder=surfaceView.getHolder();
        
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);
       //tabHost.setOnTabChangedListener(this);
//		LayoutInflater inflater=getLayoutInflater();
//		View view=inflater.inflate(R.id.mainpage, null);
		
	}

	
//	private void dispatchTakePictureIntent() {
//	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//	    }
//	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		  final int LARGEST_WIDTH=200;
		  final int LARGEST_HEIGHT=200;
		  int bestWidth=0;
		  int bestHeight=0;
		  
		  camera=Camera.open();
		  Camera.Parameters parameters=camera.getParameters();
		  
//		  java.util.List<String> colorEffectsList=parameters.getSupportedColorEffects();//color effect
//		  Iterator<String> iterator1=colorEffectsList.iterator();
//		  
//		  while(iterator1.hasNext()){
//			  String effect=(String)iterator1.next();
//			  if(effect.equals(Camera.Parameters.EFFECT_SOLARIZE)){
//				  parameters.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);
//				  break;
//			  }
//		  }
		  
		//操作2 改变SurfaceView的大小
			java.util.List<Camera.Size> previewSizes=parameters.getSupportedPreviewSizes();
			if (previewSizes.size()>1) {
				Iterator<Camera.Size> iterator2=previewSizes.iterator();
				while (iterator2.hasNext()) {
					Camera.Size size = (Camera.Size) iterator2.next();
					if (size.width>bestWidth&&size.width<=LARGEST_WIDTH&&
						size.height>bestHeight&&size.height<=LARGEST_HEIGHT) {
						bestWidth=size.width;
						bestHeight=size.height;
					}
				}
				if (bestWidth!=0&&bestHeight!=0) {
					parameters.setPreviewSize(bestWidth, bestHeight);
					surfaceView.setLayoutParams
					(new RelativeLayout.LayoutParams(bestWidth, bestHeight));
				}
			}
			
		  camera.setDisplayOrientation(90);
		  try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			camera.release();
			e.printStackTrace();
		}
		  
		  parameters.setPictureFormat(PixelFormat.JPEG);
		  camera.setParameters(parameters);
		  
		  camera.startPreview();
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		camera.startPreview();
		
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		camera.stopPreview();
		camera.release();
	}


	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		 ContentResolver resolver=getContentResolver();
		 Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
		 MediaStore.Images.Media.insertImage(resolver, bitmap,i+"", "it is "+i);
		 i++;
		 camera.startPreview();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 switch (v.getId()) {
		case 0:
			camera.takePicture(null, null, this);
			break;

		case 1:
			Intent intent=new Intent(MainActivity.this,Second_page.class);
			startActivity(intent);
			break;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		
		super.onStart();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
//		try {
//			inputStream=new ObjectInputStream(new FileInputStream(new File("tree.ser")));
//			TreeStructure.datas=(Map<String, Node>) inputStream.readObject();
//			Node.children=(java.util.List<Node>) inputStream.readObject();
//			Node.nodesName=(java.util.List<String>) inputStream.readObject();
//			
//			inputStream.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
	
		super.onRestart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
//		try {
//			outputStream=new ObjectOutputStream(new FileOutputStream(new File("tree.ser")));
//			outputStream.writeObject(TreeStructure.datas);// write newly map in
//			outputStream.writeObject(Node.nodesName);// write nodes
//			outputStream.writeObject(Node.children);
//			
//			outputStream.flush();
//			outputStream.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		super.onStop();
	}
	@SuppressLint("SdCardPath")
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
//		if(Environment.getExternalStorageDirectory()!=null){
//			File path=new File("/sdcard/marineProject");
//			File file=new File("/sdcard/marineProject/tree.ser");
//			
//			if(!path.exists()){
//				path.mkdir();
//			}
//			
//			if(!file.exists()){
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			try {
//				outputStream=new ObjectOutputStream(new FileOutputStream(path));
//				outputStream.writeObject(Node.children);
//				
//				outputStream.flush();
//				outputStream.close();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		super.onDestroy();
	}
	/**
	 * It is called by the button add
	 * @param view
	 */
    public void add_triggered(View view){
    	Intent intent=new Intent(MainActivity.this,Add.class);
		startActivity(intent);
	}
    /**
	 * It is called by the button delete
	 * @param view
	 */
    public void delete_triggered(View view){
    	Intent intent=new Intent(MainActivity.this,Delete.class);
		startActivity(intent);
	}
    /**
	 * It is called by the button setting
	 * @param view
	 */
    public void setting_triggered(View view){
    	Intent intent=new Intent(MainActivity.this,MyDialogPrefrence.class);
		startActivity(intent);
    	
}
}
