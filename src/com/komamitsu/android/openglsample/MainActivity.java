/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.komamitsu.android.openglsample;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.komamitsu.android.openglsample.OnKeyEventListener.EventType;

/**
 * Wrapper activity demonstrating the use of {@link GLSurfaceView}, a view that
 * uses OpenGL drawing into a dedicated surface.
 * 
 * Shows: + How to redraw in response to user input.
 */
public class MainActivity extends Activity {
  protected static final String TAG = MainActivity.class.getSimpleName();
  private static final int BUTTON_EVENT_INDEX_TOP = 0;
  private static final int BUTTON_EVENT_INDEX_BOTTOM = 1;
  private static final int BUTTON_EVENT_INDEX_LEFT = 2;
  private static final int BUTTON_EVENT_INDEX_RIGHT = 3;
  private volatile boolean[] buttonPusheEvents;
  private volatile ExecutorService buttonMonitorExecutor;
  private static final List<Pair<Integer, Integer>> BUTTON_EVENT_REGISTER_MAPPING =
      Arrays.asList(
          new Pair<Integer, Integer>(R.id.button_top, BUTTON_EVENT_INDEX_TOP),
          new Pair<Integer, Integer>(R.id.button_bottom, BUTTON_EVENT_INDEX_BOTTOM),
          new Pair<Integer, Integer>(R.id.button_left, BUTTON_EVENT_INDEX_LEFT),
          new Pair<Integer, Integer>(R.id.button_right, BUTTON_EVENT_INDEX_RIGHT)
          );

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Create our Preview view and set it as the content of our
    // Activity
    setContentView(R.layout.activity_main);
    mGLSurfaceView = (MyGlSurfaceView) findViewById(R.id.glsurface);
    mGLSurfaceView.requestFocus();
    mGLSurfaceView.setFocusableInTouchMode(true);

    for (Pair<Integer, Integer> idAndIndex : BUTTON_EVENT_REGISTER_MAPPING) {
      Integer id = idAndIndex.first;
      final Integer index = idAndIndex.second;
      findViewById(id).setOnTouchListener(new OnTouchListener() {
        @Override
        public boolean onTouch(View arg0, MotionEvent ev) {
          switch (ev.getAction()) {
          case MotionEvent.ACTION_DOWN:
            buttonPusheEvents[index] = true;
            break;
          case MotionEvent.ACTION_UP:
            buttonPusheEvents[index] = false;
            break;
          }
          return true;
        }
      });
    }
  }

  @Override
  protected void onResume() {
    // Ideally a game should implement onResume() and onPause()
    // to take appropriate action when the activity looses focus
    super.onResume();
    mGLSurfaceView.onResume();

    stopButtonMonitor();
    clearPushEvents();

    buttonMonitorExecutor = Executors.newSingleThreadExecutor();
    buttonMonitorExecutor.execute(new Runnable() {

      @Override
      public void run() {
        while (buttonMonitorExecutor != null && !buttonMonitorExecutor.isShutdown()) {
          if (buttonPusheEvents[BUTTON_EVENT_INDEX_LEFT]) {
            mGLSurfaceView.onKeyEvent(EventType.LEFT);
          }

          if (buttonPusheEvents[BUTTON_EVENT_INDEX_RIGHT]) {
            mGLSurfaceView.onKeyEvent(EventType.RIGHT);
          }

          try {
            Thread.sleep(1000 / 30);
          } catch (InterruptedException e) {
          }
        }
      }
    });
  }

  private void stopButtonMonitor() {
    if (buttonMonitorExecutor != null) {
      buttonMonitorExecutor.shutdownNow();
    }
    buttonMonitorExecutor = null;
  }

  private void clearPushEvents() {
    buttonPusheEvents = new boolean[] { false, false, false, false };
  }

  @Override
  protected void onPause() {
    // Ideally a game should implement onResume() and onPause()
    // to take appropriate action when the activity looses focus
    super.onPause();
    mGLSurfaceView.onPause();

    stopButtonMonitor();
    clearPushEvents();
  }

  private MyGlSurfaceView mGLSurfaceView;
}
