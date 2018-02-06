package cn.miao.ncncdtestdemo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.miao.ncncdtestdemo.R;

/**
 * 测试列表adapter Created by zhangzhuang on 17/11/16.
 */
public class TestAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private String[] datas;

	/* 构造函数 */
	public TestAdapter(Context context, String[] datas) {
		this.mInflater = LayoutInflater.from(context);
		this.datas = datas;
	}

	@Override
	public int getCount() {

		return datas.length;
	}

	@Override
	public Object getItem(int position) {
		return datas[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_test, null);
		}

		/* 得到各个控件的对象 */
		TextView name = (TextView) convertView.findViewById(R.id.tv_name);
		name.setText(datas[position]);

		return convertView;
	}
}