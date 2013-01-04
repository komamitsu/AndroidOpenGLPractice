package com.komamitsu.android.openglsample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Implement a simple rotation control.
 * 
 */
public class MyGlSurfaceView extends GLSurfaceView implements OnKeyEventListener {
  public MyGlSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mRenderer = new CubeRenderer();
    setRenderer(mRenderer);
    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
  }

  public MyGlSurfaceView(Context context) {
    super(context);
    mRenderer = new CubeRenderer();
    setRenderer(mRenderer);
    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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
      gl.glTranslatef(0, 0, -3.0f);
      gl.glRotatef(mAngleX, 0, 1, 0);
      gl.glRotatef(mAngleY, 1, 0, 0);

      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

      mCube.draw(gl);
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
      gl.glFrustumf(-ratio, ratio, -1, 1, 0.5f, 50);
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

      gl.glClearColor(1, 1, 1, 1);
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
      gl.glEnable(GL10.GL_LIGHTING);
      gl.glEnable(GL10.GL_LIGHT0);
      gl.glEnable(GL10.GL_COLOR_MATERIAL);
      gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
      gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
      gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
      gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
      gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);
    }

    private final Cube mCube;
    public float mAngleX;
    public float mAngleY;
  }

  private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
  private final float TRACKBALL_SCALE_FACTOR = 36.0f;
  private final CubeRenderer mRenderer;
  private float mPreviousX;
  private float mPreviousY;

  @Override
  public void onKeyEvent(EventType type) {
    switch (type) {
    case TOP:
      break;

    case BOTTOM:
      break;

    case LEFT:
      mRenderer.mAngleX -= 0.2 * TRACKBALL_SCALE_FACTOR;
      requestRender();
      break;

    case RIGHT:
      mRenderer.mAngleX += 0.2 * TRACKBALL_SCALE_FACTOR;
      requestRender();
      break;
    }
  }
}