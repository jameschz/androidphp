package com.app.demos.list;

import java.util.List;
import java.util.Map;

import com.app.demos.R;
import com.app.demos.util.AppCache;
import com.app.demos.util.AppFilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandList {
	
	final public static int TEXT_VIEW = 1;
	final public static int IMAGE_VIEW = 2;
	
	private LayoutInflater layout = null;
	private Integer dividerId = R.color.divider1;
	private ExpandList.OnItemClickListener itemClickListener = null;
	
	private Context context = null;
	private List<? extends Map<String, ?>> dataList = null;
	private int resourceId = -1;
	private String[] colKeys = {};
	private int[] tplKeys = {};
	private int[] types = {};
	
	public ExpandList (Context context, List<? extends Map<String, ?>> data, int resource, String[] cols, int[] tpls, int[] types) {
		// layout
		this.context = context;
		this.layout = LayoutInflater.from(context);
		// data
		this.resourceId = resource;
		this.dataList = data;
		this.colKeys = cols;
		this.tplKeys = tpls;
		this.types = types;
	}
	
	public View getView () {
		return layout.inflate(resourceId, null);
	}
	
	public void setDivider (Integer dividerId) {
		this.dividerId = dividerId;
	}
	
	public void setOnItemClickListener (ExpandList.OnItemClickListener listener) {
		itemClickListener = listener;
	}
	
	public void render (ViewGroup vg) {
		int dataPos = 0;
		int dataSize = dataList.size();
		for (Map<String, ?> data : dataList) {
			View v = getView();
			// render main
			for (int i = 0; i < colKeys.length; i++) {
				String colKey = colKeys[i];
				int tplKey = tplKeys[i];
				int type = types[i];
				switch (type) {
					case ExpandList.TEXT_VIEW :
						TextView tv = (TextView) v.findViewById(tplKey);
						AppFilter.setHtml(tv, data.get(colKey).toString());
						break;
					case ExpandList.IMAGE_VIEW :
						ImageView iv = (ImageView) v.findViewById(tplKey);
						Bitmap img = AppCache.getImage(data.get(colKey).toString());
						if (iv != null) {
							if (img != null) {
								iv.setImageBitmap(img);
								iv.setVisibility(View.VISIBLE);
							} else {
								iv.setImageBitmap(null);
								iv.setVisibility(View.GONE);
							}
						}
						break;
					default :
						break;
				}

			}
			// add click callback
			if (itemClickListener != null) {
				final int pos = dataPos;
				v.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						itemClickListener.onItemClick(v, pos);
					}
				});
			}
			vg.addView(v);
			// count data
			dataPos++;
			// render divider
			if (dataPos < dataSize) {
				View d = new TextView(context, null);
				d.setBackgroundResource(dividerId);
				d.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
				vg.addView(d);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// 
	
	abstract public interface OnItemClickListener {
		abstract public void onItemClick(View view, int pos);
	}
}