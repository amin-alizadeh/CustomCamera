package com.customcamera;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CCMain extends Activity implements OnClickListener, Camera.PreviewCallback {

	private CameraUtilities camUtil;
	private Camera cam;
	private CameraPreview mPreview;
	private Button captureButton;
	private PictureCallback mPicture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ccmain);
		
		captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(this);
		
		mPicture = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				captureButton.setText("Picture size: " + data.length);
			}
		};
		
		camUtil = new CameraUtilities(this);
		
		cam = camUtil.getFrontCamera();
		/*CameraInfo camInfo = camUtil.getFrontCameraInfo();
		Camera.Parameters parameters = cam.getParameters();
		parameters.set("orientation", "portrait");
		cam.setParameters(parameters);*/
		
		mPreview = new CameraPreview(this, cam);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ccmain, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.button_capture:
			
			if (camUtil.cameraExists()) {
				cam.takePicture(null, null, mPicture);
				
			} else {
				Toast.makeText(this, "Camera was not detectd!", Toast.LENGTH_SHORT).show();				
			}
			
			break;

		default:
			
			break;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		
		Toast.makeText(this, newConfig.orientation, Toast.LENGTH_LONG).show();
	
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		Size size = camera.getParameters().getPreviewSize();
		
		
	}
	
	
}
