package com.customcamera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build;

public class CameraUtilities {
	
	private Context context;
	private int camCount = 0;
	private boolean camExists = false;
	private int frontCamIdx = 1, backCamIdx = 0;
	private Camera frontCam = null;
	private Camera backCam = null;
	
	public CameraUtilities(Context context) {
		this.context = context;
		this.camExists = checkCameraHardware();
		if (this.camExists) {
			this.camCount = cameraCount();
			findFrontBackCams();
		} else {
			this.camCount = 0;
		}
	}

	private void findFrontBackCams() {
		for (int camIdx = 0; camIdx < camCount; camIdx++) {
			Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
			Camera.getCameraInfo(camIdx, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				this.frontCamIdx = camIdx;
			} else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				this.backCamIdx = camIdx;
			}
		}
	}

	/** Check if this device has a camera */
	private boolean checkCameraHardware() {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	private int cameraCount() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return Camera.getNumberOfCameras();
		} else if (camExists){
			return 1;
		} else {
			return 0;
		}
	}

	/** A safe way to get an instance of the Camera object. */
	private Camera getCameraInstance(int i) {
		Camera c = null;
		try {
			c = Camera.open(i); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}
	
	private CameraInfo getCameraInfo(int idx) {
		CameraInfo camInfo = null;
		Camera.getCameraInfo(idx, camInfo);
		return camInfo;	
	}
	
	public boolean cameraExists() {
		return this.camExists;
	}

	public int getCameraCount() {
		return this.camCount;
	}
	
	public Camera getFrontCamera() {
		if (this.frontCam == null) {
			this.frontCam = getCameraInstance(frontCamIdx);
		}
		return this.frontCam;
	}

	public Camera getBackCamera() {
		if (this.backCam == null) {
			this.backCam = getCameraInstance(backCamIdx);
		}
		return backCam;
	}
	
	public CameraInfo getBackCameraInfo() {
		return getCameraInfo(backCamIdx);
	}
	
	public CameraInfo getFrontCameraInfo() {
		return getCameraInfo(frontCamIdx);
	}
	
}
