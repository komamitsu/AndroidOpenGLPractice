/*
 * Copyright (C) 2007 The Android Open Source Project
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * A vertex shaded cube.
 */
class Cube
{
  public Cube()
  {
    float one = 1f;
    float height = one * 1.5f;
    float width = one * 10;
    float vertices[] = {
      // 前面
      -width, -height, width,
      width, -height, width,
      -width, height, width,
      width, height, width,
      // 背面
      -width, -height, -width,
      -width, height, -width,
      width, -height, -width,
      width, height, -width,
      // 左面
      -width, -height, width,
      -width, height, width,
      -width, -height, -width,
      -width, height, -width,
      // 右面
      width, -height, -width,
      width, height, -width,
      width, -height, width,
      width, height, width,
      // 上面
      /*
      -gLen, height, gLen,
      gLen, height, gLen,
      -gLen, height, -gLen,
      gLen, height, -gLen,
      */
      // 下面
      -width, -height, width,
      -width, -height, -width,
      width, -height, width,
      width, -height, -width,
    };

    float norms[] = {
      // 前面
      0f, 0f, -1f,
      0f, 0f, -1f,
      0f, 0f, -1f,
      0f, 0f, -1f,
      // 裏面
      0f, 0f, 1f,
      0f, 0f, 1f,
      0f, 0f, 1f,
      0f, 0f, 1f,
      // 左面
      1f, 0f, 0f,
      1f, 0f, 0f,
      1f, 0f, 0f,
      1f, 0f, 0f,
      // 右面
      -1f, 0f, 0f,
      -1f, 0f, 0f,
      -1f, 0f, 0f,
      -1f, 0f, 0f,
      // 上面
      /*
      0f, -1f, 0f,
      0f, -1f, 0f,
      0f, -1f, 0f,
      0f, -1f, 0f,
      */
      // 下面
      0f, 1f, 0f,
      0f, 1f, 0f,
      0f, 1f, 0f,
      0f, 1f, 0f
    };

    // Buffers to be passed to gl*Pointer() functions
    // must be direct, i.e., they must be placed on the
    // native heap where the garbage collector cannot
    // move them.
    //
    // Buffers with multi-byte datatypes (e.g., short, int, float)
    // must have their byte order set to native order

    ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
    vbb.order(ByteOrder.nativeOrder());
    mVertexBuffer = vbb.asFloatBuffer();
    mVertexBuffer.put(vertices);
    mVertexBuffer.position(0);

    ByteBuffer nbb = ByteBuffer.allocateDirect(norms.length * 4);
    nbb.order(ByteOrder.nativeOrder());
    mNormalBuffer = nbb.asFloatBuffer();
    mNormalBuffer.put(norms);
    mNormalBuffer.position(0);
  }

  public void draw(GL10 gl)
  {
    // gl.glFrontFace(GL10.GL_CCW);
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
    gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
    // gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

    // 前面と背面のプリミティブの描画
    gl.glColor4f(0.2f, 0.2f, 0.7f, 1.0f);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);

    // 左面と右面のプリミティブの描画
    gl.glColor4f(0.2f, 0.2f, 0.7f, 1.0f);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);

    // 上面と下面のプリミティブの描画
    /*
    gl.glColor4f(0.7f, 0.7f, 1.0f, 1.0f);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
    */
    gl.glColor4f(0, 0, 0, 1.0f);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
  }

  private final FloatBuffer mVertexBuffer;
  private final FloatBuffer mNormalBuffer;
}
