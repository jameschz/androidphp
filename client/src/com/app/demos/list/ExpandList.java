package com.app.demos.list;

import java.util.List;
import java.util.Map;

import com.app.demos.R;
import com.app.demos.util.AppFilter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ExpandList {
	
	private LayoutInflater layout = null;
	private Integer dividerId = R.color.divider1;
	private ExpandList.OnItemClickListener itemClickListener = null;
	
	private Context context = null;
	private List<? extends Map<String, ?>> dataList = null;
	private int resourceId = -1;
	private String[] dataKeys = {};
	private int[] tplKeys = {};
	
	public ExpandList (Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		// layout
		this.context = context;
		this.layout = LayoutInflater.from(context);
		// data
		this.resourceId = resource;
		this.dataList = data;
		this.dataKeys = from;
		this.tplKeys = to;
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
			for (int i = 0; i < dataKeys.length; i++) {
				String dataKey = dataKeys[i];
				int tplKey = tplKeys[i];
				TextView tv = (TextView) v.findViewById(tplKey);
				AppFilter.setHtml(tv, data.get(dataKey).toString());
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