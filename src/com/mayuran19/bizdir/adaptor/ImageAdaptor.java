package com.mayuran19.bizdir.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayuran19.bizdir.R;

public class ImageAdaptor extends BaseAdapter {
	private Context context;
	private final String[] categories;

	public ImageAdaptor(Context context, String[] categories) {
		this.context = context;
		this.categories = categories;
	}

	@Override
	public int getCount() {
		return categories.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from mobile.xml
			gridView = inflater.inflate(
					R.layout.biz_category_grid_view_decorator, null);

			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(categories[position]);

			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);

			imageView.setImageResource(R.drawable.ic_launcher);

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}

}
