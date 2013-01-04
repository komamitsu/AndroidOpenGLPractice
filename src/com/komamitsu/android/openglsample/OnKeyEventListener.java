package com.komamitsu.android.openglsample;

public interface OnKeyEventListener {
  public enum EventType {
    TOP, BOTTOM, LEFT, RIGHT
  }

  public void onKeyEvent(EventType type);
}
