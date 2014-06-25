package com.app.demos.list;

import java.util.List;

import com.app.demos.util.AppCache;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridImageList extends BaseAdapter {

	private Context context;
	private List<String> imageUrls;
	
	public GridImageList (Context context, List<String> imageUrls) {
		this.context = context;
		this.imageUrls = imageUrls;
	}
	
	@Override
	public int getCount() {
		return imageUrls.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setPadding(10, 10, 10, 10);
		// get pic from remote
		Bitmap bitmap = AppCache.getCachedImage(context, imageUrls.get(position));
		imageView.setImageBitmap(bitmap);
		return imageView;
	}
	
}