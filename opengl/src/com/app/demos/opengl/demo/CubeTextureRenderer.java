package com.app.demos.opengl.demo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;

public class CubeTextureRenderer implements Renderer {
	
	private boolean mTranslucentBackground;
	private CubeTexture mCubeTexture;
	private float mAngle;

	public CubeTextureRenderer(boolean useTranslucentBackground, Bitmap texture) {
		mTranslucentBackground = useTranslucentBackground;
		mCubeTexture = new CubeTexture();
	}

	public void onDrawFrame(GL10 gl) {
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 设置当前矩阵模式（视景矩阵）
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 重置当前观察矩阵
		gl.glLoadIdentity();
		// 设置物体位置
		gl.glTranslatef(0, 0, -3.0f);
		// 设置旋转方式
		gl.glRotatef(mAngle, 0, 1, 0);
		gl.glRotatef(mAngle * 0.25f, 1, 0, 0);
		// 允许设置顶点和颜色
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// 绘制立方体
		mCubeTexture.draw(gl);
		// 角度增量
		mAngle += 1.2f;
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		// 设置投影矩阵
		float ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		// 重置不雅察矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// 全局配置
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 设置背景
		if (mTranslucentBackground) {
			gl.glClearColor(0, 0, 0, 0);
		} else {
			gl.glClearColor(1, 1, 1, 1);
		}
		// 初始化纹理
		mCubeTexture.init(gl);
	}

}
