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
class Bullet
{
  public Bullet()
  {
    float one = 1.5f;
    float vertices[] = {
      // 前面
      -one, -one, 0,
      +one, -one, 0,
      0, +one, 0,
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
  }

  public void draw(GL10 gl)
  {
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);

    // 前面と背面のプリミティブの描画
    gl.glColor4f(1f, 1f, 0, 1.0f);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);
  }

  private final FloatBuffer mVertexBuffer;
}
