package com.app.demos.opengl.demo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;

public class DemoGL2dRenderer implements Renderer {
	
	static int one = 0x10000;
	
	private IntBuffer triangleBuffer;
	private IntBuffer quaterBuffer;
	private IntBuffer color1Buffer;
	private IntBuffer color2Buffer;
	
	private int[] vertices = new int[] {
		0,one,0,
		-one,-one,0,
		one,-one,0
	};
	private int[] quater = new int[] {
		one,one,0,
		-one,one,0,
		one,-one,0,
		-one,-one,0
	};
	private int[] color1 = new int[] {
		one,0,0,one,
		0,one,0,one,
		0,0,one,one
	};
	private int[] color2 = new int[] {
		one,0,0,0,
		one,one,0,0,
		one,one,one,0,
		0,one,one,0
	};

	@Override
	public void onDrawFrame(GL10 gl) {
		
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 重置当前观察矩阵
		gl.glLoadIdentity();
		
		// 三角形的ByteBuffer
		ByteBuffer vbb1 = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb1.order(ByteOrder.nativeOrder());
		triangleBuffer = vbb1.asIntBuffer();
		triangleBuffer.put(vertices);
		triangleBuffer.position(0);
		// 四方形的ByteBuffer
		ByteBuffer vbb2 = ByteBuffer.allocateDirect(quater.length * 4);
		vbb2.order(ByteOrder.nativeOrder());
		quaterBuffer = vbb2.asIntBuffer();
		quaterBuffer.put(quater);
		quaterBuffer.position(0);
		// 颜色1的ByteBuffer
		ByteBuffer cbb1 = ByteBuffer.allocateDirect(color1.length * 4);
		cbb1.order(ByteOrder.nativeOrder());
		color1Buffer = cbb1.asIntBuffer();
		color1Buffer.put(color1);
		color1Buffer.position(0);
		// 颜色2的ByteBuffer
		ByteBuffer cbb2 = ByteBuffer.allocateDirect(color2.length * 4);
		cbb2.order(ByteOrder.nativeOrder());
		color2Buffer = cbb2.asIntBuffer();
		color2Buffer.put(color2);
		color2Buffer.position(0);
		
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();
		// 允许设置顶点
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 允许设置颜色
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// 左移1.5单位，并移入屏幕6.0
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
		// 设置三角形
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, triangleBuffer);
		// 设置三角形颜色
		gl.glColorPointer(4, GL10.GL_FIXED, 0, color1Buffer);
		// 绘制三角形
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		
		// 重置当前模型的观察矩阵
		gl.glLoadIdentity();
		// 左移1.5单位，并移入屏幕6.0
		gl.glTranslatef(1.5f, 0.0f, -6.0f);
		// 设置正方形
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
		// 设置正方形颜色
		gl.glColorPointer(4, GL10.GL_FIXED, 0, color2Buffer);
		// 绘制正方形
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		// 取消颜色设置
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		// 取消顶点设置
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float ratio = (float) width / height;
		// 设置OpenGL场景的大小
		gl.glViewport(0, 0, width, height);
		// 设置投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 重置投影矩阵
		gl.glLoadIdentity();
		// 设置视口的大小
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 重置当前模型观察矩阵
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// 告诉系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// 黑色背景
		gl.glClearColor(0, 0, 0, 0);
		// 启用阴影平滑
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 设置深度缓存
		gl.glClearDepthf(1.0f);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 所做深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}
}