package com.zg.www.pullrefreshgridview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.zg.www.pullrefreshgridview.MyGridView.OnRefreshListener;

public class MainActivity extends Activity {

	private List<String> data;
	private BaseAdapter adapter;
	private LinearLayout head;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		data = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			data.add("试试看看效果哈！");
		}

		final MyGridView gridView = (MyGridView) findViewById(R.id.gridView);
		
		head = (LinearLayout) findViewById(R.id.head);

	       LayoutParams p = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT, Gravity.CENTER);

	       //获取PullToRefreshGridView里面的head布局

	       head.addView(gridView.getView(), p);
		
		adapter = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView tv = new TextView(getApplicationContext());
				tv.setText(data.get(position));
				return tv;
			}

			public long getItemId(int position) {
				return 0;
			}

			public Object getItem(int position) {
				return null;
			}

			public int getCount() {
				return data.size();
			}
		};
		gridView.setAdapter(adapter);

		gridView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						data.add("刷新后添加的内容");
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						gridView.onRefreshComplete();
					}

				}.execute(null);
			}
		});
	}
}