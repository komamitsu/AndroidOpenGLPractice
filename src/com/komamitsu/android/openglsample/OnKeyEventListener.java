package com.komamitsu.android.openglsample;

public interface OnKeyEventListener {
  public enum EventType {
    TOP, BOTTOM, LEFT, RIGHT, FIRE
  }

  public void onKeyEvent(EventType type);
}
