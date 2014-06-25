package com.app.demos.opengl.demo;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;

import com.app.demos.opengl.util.GLUtil;
import com.app.demos.opengl.util.GLImage;

public class CubeTexture extends Cube {
	
	int one = 0x10000;
	int[] vertice = new int[]{
			-one,-one,one,
			one,-one,one,
			one,one,one,
			-one,one,one,
			
			-one,-one,-one,
			-one,one,-one,
			one,one,-one,
			one,-one,-one,
			
			-one,one,-one,
			-one,one,one,
			one,one,one,
			one,one,-one,
			
			-one,-one,-one,
			one,-one,-one,
			one,-one,one,
			-one,-one,one,
			
			one,-one,-one,
			one,one,-one,
			one,one,one,
			one,-one,one,
			
			-one,-one,-one,
			-one,-one,one,
			-one,one,one,
			-one,one,-one,
	};
	int[] normal = new int[]{
			0,0,one,
			0,0,one,
			0,0,one,
			0,0,one,
			
			0,0,one,
			0,0,one,
			0,0,one,
			0,0,one,
			
			0,one,0,
			0,one,0,
			0,one,0,
			0,one,0,
			
			0,-one,0,
			0,-one,0,
			0,-one,0,
			0,-one,0,
			
			one,0,0,
			one,0,0,
			one,0,0,
			one,0,0,
			
			-one,0,0,
			-one,0,0,
			-one,0,0,
			-one,0,0,
	};
	// 纹理映射数据
	int[] texCoord = new int[]{
		one,0,0,0,0,one,one,one,	
		0,0,0,one,one,one,one,0,
		one,one,one,0,0,0,0,one,
		0,one,one,one,one,0,0,0,
		0,0,0,one,one,one,one,0,
		one,0,0,0,0,one,one,one,
	};
	IntBuffer vertices=GLUtil.getIntBuffer(vertice);
	IntBuffer normals=GLUtil.getIntBuffer(normal);
	IntBuffer texCoords=GLUtil.getIntBuffer(texCoord);
	ByteBuffer indices1 = ByteBuffer.wrap(new byte[]{
			0,1,3,2,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
	});
	ByteBuffer indices2 = ByteBuffer.wrap(new byte[]{
			0,0,0,0,
			4,5,7,6,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
	});
	ByteBuffer indices3 = ByteBuffer.wrap(new byte[]{
			0,0,0,0,
			0,0,0,0,
			8,9,11,10,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
	});
	ByteBuffer indices4 = ByteBuffer.wrap(new byte[]{
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			12,13,15,14,
			0,0,0,0,
			0,0,0,0,
	});
	ByteBuffer indices5 = ByteBuffer.wrap(new byte[]{
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			16,17,19,18,
			0,0,0,0,
	});
	ByteBuffer indices6 = ByteBuffer.wrap(new byte[]{
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			20,21,23,22,
	});
	// 纹理数组
	int [] texture;
	//光线参数
	FloatBuffer lightAmbient = FloatBuffer.wrap(new float[]{0.5f,0.5f,0.5f,1.0f}); 
	FloatBuffer lightDiffuse = FloatBuffer.wrap(new float[]{1.0f,1.0f,1.0f,1.0f}); 
	FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{0.0f,0.0f,2.0f,1.0f}); 
	
	public CubeTexture() {
		
	}
	
	public void init(GL10 gl) {
		// 纹理相关
		IntBuffer textureBuffer = IntBuffer.allocate(6);
		gl.glGenTextures(6, textureBuffer);
		texture = textureBuffer.array();
				
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mImageSkin, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST); 
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mImageSkin, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mImageSkin, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST); 
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[3]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mImageSkin, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST); 
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[4]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mImageSkin, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST); 
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[5]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mImageSkin, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST); 
		
		gl.glClearDepthf(1.0f);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		// 灯源相关
		gl.glColor4f(1.0f,1.0f,1.0f,0.5f);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);
		gl.glEnable(GL10.GL_LIGHT1);
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_LIGHTING);
	}
	
	@Override
	public void draw(GL10 gl) {
		// 绘制立方体
		gl.glNormalPointer(GL10.GL_FIXED, 0, normals);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertices);
		gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, texCoords);

		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// 绘制四边形
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4,  GL10.GL_UNSIGNED_BYTE, indices1);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 8,  GL10.GL_UNSIGNED_BYTE, indices2);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 12,  GL10.GL_UNSIGNED_BYTE, indices3);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[3]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 16,  GL10.GL_UNSIGNED_BYTE, indices4);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[4]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 20,  GL10.GL_UNSIGNED_BYTE, indices5);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[5]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 24,  GL10.GL_UNSIGNED_BYTE, indices6);
		
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
}