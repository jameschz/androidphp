package com.app.demos.opengl.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class GLUtil {

	public static IntBuffer getIntBuffer(int[] source){
		ByteBuffer bb = ByteBuffer.allocateDirect(source.length * 4);
  		bb.order(ByteOrder.nativeOrder());
  		IntBuffer vertices = bb.asIntBuffer();
  		vertices.put(source);
  		vertices.position(0);
  		return vertices;
	}
}