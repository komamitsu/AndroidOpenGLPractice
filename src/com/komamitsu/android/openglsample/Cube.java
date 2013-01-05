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
    float one = 2.5f;
    float gLen = one * 6;
    float vertices[] = {
      // 前面
      -gLen, -one, gLen,
      gLen, -one, gLen,
      -gLen, one, gLen,
      gLen, one, gLen,
      // 背面
      -gLen, -one, -gLen,
      -gLen, one, -gLen,
      gLen, -one, -gLen,
      gLen, one, -gLen,
      // 左面
      -gLen, -one, gLen,
      -gLen, one, gLen,
      -gLen, -one, -gLen,
      -gLen, one, -gLen,
      // 右面
      gLen, -one, -gLen,
      gLen, one, -gLen,
      gLen, -one, gLen,
      gLen, one, gLen,
      // 上面
      -gLen, one, gLen,
      gLen, one, gLen,
      -gLen, one, -gLen,
      gLen, one, -gLen,
      // 下面
      -gLen, -one, gLen,
      -gLen, -one, -gLen,
      gLen, -one, gLen,
      gLen, -one, -gLen,
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
      0f, -1f, 0f,
      0f, -1f, 0f,
      0f, -1f, 0f,
      0f, -1f, 0f,
      // 下面
      0f, 1f, 0f,
      0f, 1f, 0f,
      0f, 1f, 0f,
      0f, 1f, 0f
    };
    float colors[] = {
      // 前面
      0f, 0f, -1f, 1f,
      0f, 0f, -1f, 1f,
      0f, 0f, -1f, 1f,
      0f, 0f, -1f, 1f,
      // 裏面
      0f, 0f, 1f, 1f,
      0f, 0f, 1f, 1f,
      0f, 0f, 1f, 1f,
      0f, 0f, 1f, 1f,
      // 左面
      1f, 0f, 0f, 1f,
      1f, 0f, 0f, 1f,
      1f, 0f, 0f, 1f,
      1f, 0f, 0f, 1f,
      // 右面
      -1f, 0f, 0f, 1f,
      -1f, 0f, 0f, 1f,
      -1f, 0f, 0f, 1f,
      -1f, 0f, 0f, 1f,
      // 上面
      0f, -1f, 0f, 1f,
      0f, -1f, 0f, 1f,
      0f, -1f, 0f, 1f,
      0f, -1f, 0f, 1f,
      // 下面
      0f, 1f, 0f, 1f,
      0f, 1f, 0f, 1f,
      0f, 1f, 0f, 1f,
      0f, 1f, 0f, 1f,
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

    ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
    cbb.order(ByteOrder.nativeOrder());
    mColorBuffer = cbb.asFloatBuffer();
    mColorBuffer.put(colors);
    mColorBuffer.position(0);
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
    gl.glColor4f(0.7f, 0.7f, 1.0f, 1.0f);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
    gl.glColor4f(0, 0, 0.2f, 1.0f);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
  }

  private final FloatBuffer mVertexBuffer;
  private final FloatBuffer mNormalBuffer;
  private final FloatBuffer mColorBuffer;
}
