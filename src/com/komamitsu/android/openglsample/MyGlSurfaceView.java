package com.komamitsu.android.openglsample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.AttributeSet;

/**
 * Implement a simple rotation control.
 * 
 */
public class MyGlSurfaceView extends GLSurfaceView implements OnKeyEventListener {
  private static final String TAG = MyGlSurfaceView.class.getSimpleName();

  private void init() {
    setEGLConfigChooser(8, 8, 8, 8, 16, 0);
    getHolder().setFormat(PixelFormat.TRANSLUCENT);
    setRenderer(mRenderer);
    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    setZOrderOnTop(true);
  }

  public MyGlSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public MyGlSurfaceView(Context context) {
    super(context);
    init();
  }

  /**
   * Render a cube.
   */
  private class CubeRenderer implements GLSurfaceView.Renderer {
    public CubeRenderer() {
      mCube = new Cube();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
      /*
       * Usually, the first thing one might want to do is to clear
       * the screen. The most efficient way of doing this is to use
       * glClear().
       */

      gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

      /*
       * Now we're ready to draw some 3D objects
       */

      gl.glMatrixMode(GL10.GL_MODELVIEW);
      gl.glLoadIdentity();
      // gl.glRotatef(0, 0, 1, 0);
      // gl.glRotatef(0, 1, 0, 0);
      // gl.glTranslatef(0, 0, 0);

      GLU.gluLookAt(gl, mPosX, mPosY, mPosZ,
          mPosX + (float) Math.sin(Math.toRadians(mAngleX)),
          mPosY,
          mPosZ + (float) Math.cos(Math.toRadians(mAngleX)), 0f, 1f, 0f);

      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
      gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

      mCube.draw(gl);

      if (bullet != null) {
        gl.glPushMatrix();
        gl.glTranslatef(bulletX, bulletY, bulletZ);
        gl.glRotatef(bulletAngle, 0, 0, 1);
        gl.glRotatef(mAngleX, 0, 1, 0);
        bullet.draw(gl);
        gl.glPopMatrix();

        bulletX += (float) Math.sin(Math.toRadians(mAngleX)) * 0.2;
        bulletY += 0.03;
        bulletZ += (float) Math.cos(Math.toRadians(mAngleX)) * 0.2;
        bulletAngle += 10;

        if (bulletX > 50 || bulletZ > 50) {
          bullet = null;
        }
        else {
          requestRender();
        }
      }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
      gl.glViewport(0, 0, width, height);

      /*
       * Set our projection matrix. This doesn't have to be done
       * each time we draw, but usually a new projection needs to
       * be set when the viewport is resized.
       */

      float ratio = (float) width / height;
      gl.glMatrixMode(GL10.GL_PROJECTION);
      gl.glLoadIdentity();
      // gl.glFrustumf(-ratio, ratio, -0.8f, 1, 0.5f, 50);
      GLU.gluPerspective(gl, 50f, ratio, 0.2f, 50f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
      /*
       * By default, OpenGL enables features that improve quality
       * but reduce performance. One might want to tweak that
       * especially on software renderer.
       */
      gl.glDisable(GL10.GL_DITHER);

      /*
       * Some one-time OpenGL initialization can be made here
       * probably based on features of this particular context
       */
      gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

      // gl.glClearColor(1, 1, 1, 1);
      gl.glDisable(GL10.GL_CULL_FACE);
      gl.glShadeModel(GL10.GL_SMOOTH);
      gl.glEnable(GL10.GL_DEPTH_TEST);
      gl.glEnable(GL10.GL_LIGHTING);
      gl.glEnable(GL10.GL_LIGHT0);
      gl.glDepthFunc(GL10.GL_LEQUAL);

      float[] lightAmbient = new float[] { 0.2f, 0.3f, 0.6f, 1.0f };// 光源アンビエント
      float[] lightDiffuse = new float[] { 0.2f, 0.3f, 0.6f, 1.0f };// 光源ディフューズ
      float[] lightPos = new float[] { 0, 0, 0, 1 }; // 光源位置
      float[] matAmbient = new float[] { 0.6f, 0.6f, 0.6f, 1.0f };// マテリアルアンビエント
      float[] matDiffuse = new float[] { 0.6f, 0.6f, 0.6f, 1.0f };// マテリアルディフューズ

      // ライティングの指定
      // gl.glEnable(GL10.GL_COLOR_MATERIAL); // avoiding SIGSEGV
      gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
      gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
      gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
      gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
      // gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);
    }

    private final Cube mCube;
    public float mAngleX = 30f;
    public float mAngleY = 0f;
    public float mPosX = 0f;
    public float mPosY = 0f;
    public float mPosZ = -3f;

    public Bullet bullet;
    public float bulletX;
    public float bulletY;
    public float bulletZ;
    public float bulletAngle;
  }

  private final float TRACKBALL_SCALE_FACTOR = 15.0f;
  private final CubeRenderer mRenderer = new CubeRenderer();

  @Override
  public void onKeyEvent(EventType type) {
    // Log.d(TAG, "onKeyEvent(start): type=" + type + ", mAngleX=" +
    // mRenderer.mAngleX + ", mPosZ=" + mRenderer.mPosZ + ", mPosX=" +
    // mRenderer.mPosX);

    switch (type) {
    case TOP:
      mRenderer.mPosZ += Math.cos(Math.toRadians(mRenderer.mAngleX)) * 0.2;
      mRenderer.mPosX += Math.sin(Math.toRadians(mRenderer.mAngleX)) * 0.2;
      requestRender();
      break;

    case BOTTOM:
      mRenderer.mPosZ -= Math.cos(Math.toRadians(mRenderer.mAngleX)) * 0.2;
      mRenderer.mPosX -= Math.sin(Math.toRadians(mRenderer.mAngleX)) * 0.2;
      /*
      mRenderer.bullet = new Bullet();
      mRenderer.ballX = mRenderer.mPosX;
      mRenderer.ballY = mRenderer.mPosY;
      mRenderer.ballZ = mRenderer.mPosZ;
      */
      requestRender();
      break;

    case LEFT:
      mRenderer.mAngleX += 0.2 * TRACKBALL_SCALE_FACTOR;
      requestRender();
      break;

    case RIGHT:
      mRenderer.mAngleX -= 0.2 * TRACKBALL_SCALE_FACTOR;
      requestRender();
      break;

    case FIRE:
      mRenderer.bullet = new Bullet();
      mRenderer.bulletX = mRenderer.mPosX;
      mRenderer.bulletY = mRenderer.mPosY;
      mRenderer.bulletZ = mRenderer.mPosZ;
      requestRender();
      break;
    }
  }
}