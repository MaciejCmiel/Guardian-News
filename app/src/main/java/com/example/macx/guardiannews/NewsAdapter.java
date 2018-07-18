package com.example.macx.guardiannews;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MacX on 2017-11-07.
 */

public class NewsAdapter extends ArrayAdapter<News>
{

	public NewsAdapter(Activity context, List<News> newsList)
	{
		super(context, 0, newsList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		ViewHolder holder;

		// Check if the existing view is being reused, otherwise inflate the view
		if(convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		News currentNews = getItem(position);

		holder.sectionName.setText(currentNews.getSectionName());
		holder.webTitle.setText(currentNews.getWebTitle());
		holder.webPublicationDate.setText(currentNews.getWebPublicationDate());

		return convertView;
	}

	static class ViewHolder
	{
		@BindView(R.id.section_name)
		TextView sectionName;
		@BindView(R.id.web_title)
		TextView webTitle;
		@BindView(R.id.web_publication_date)
		TextView webPublicationDate;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
