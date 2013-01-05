package com.komamitsu.android.openglsample;

import java.io.IOException;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements Callback {

  private Camera cm;

  public CameraPreview(Context context, AttributeSet attrs) {
    super(context, attrs);
    getHolder().addCallback(this);
    getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int height, int width) {
    if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      cm.setDisplayOrientation(90);
    }
    else {
      cm.setDisplayOrientation(0);
    }

    try {
      cm.setPreviewDisplay(surfaceHolder);
      /*
      Camera.Parameters pr = cm.getParameters();
      pr.setPictureSize(width, height);
      cm.setParameters(pr);
      */
      cm.startPreview();
    } catch (IOException e) {
      e.printStackTrace();
      cm.release();
    }
  }

  @Override
  public void surfaceCreated(SurfaceHolder arg0) {
    cm = Camera.open();
    /*
    Camera.Parameters pr = cm.getParameters();
    List<Size> ss = pr.getSupportedPictureSizes();
    Size s = ss.get(0);
    pr.setPictureSize(s.width, s.height);
    cm.setParameters(pr);
    */
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder arg0) {
    if (cm != null) {
      cm.stopPreview();
      cm.release();
    }
  }
}
