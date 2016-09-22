package com.huaqin.weather.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class ChangeCityActivity extends Activity implements OnClickListener{
	private String City[] = { "北京", "天津", "上海", "重庆", "河北", "石家庄", "张家口", "廊坊",
			"南京", "武汉", "合肥", "安庆", "临沂", "沈阳", "哈尔滨", "杭州", "六安", "南昌",
			"大连", "佳木斯", "镇江", "温州", "宿州", "阜阳", "蚌埠", "淮南", "滁州", "铜陵",
			"池州", "厦门", "景德镇", "济南", "香港", "澳门", "台湾", "泰州" };
	private ImageView scBackImg;
	// 输入框
	private EditText scCityEd;
	// 搜索框
	private Button scCityBtn;
	// 城市列表的GridView
	private GridView scGridView;
	// GridView的适配器
	private SimpleAdapter adapter;
	// 城市名
	private String myCity;
	private List<Map<String, Object>> data;
	private Map<String, Object> map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_city_activity);
		initView();
		getData();
		addData();
	}
	private void addData() {
		// TODO Auto-generated method stub
		adapter = new SimpleAdapter(this, data, R.layout.city_item,
				new String[] { "city" }, new int[] { R.id.city });
		scGridView.setAdapter(adapter);
	}
	private void initView() {
		// TODO Auto-generated method stub
		scBackImg = (ImageView) findViewById(R.id.scBackImg);
		scBackImg.setOnClickListener(this);
		scCityBtn = (Button) findViewById(R.id.scCityBtn);
		scCityBtn.setOnClickListener(this);
		scCityEd = (EditText) findViewById(R.id.scCityEd);
		scGridView = (GridView) findViewById(R.id.scGridView);
		data = new ArrayList<Map<String, Object>>();
		
		scCityEd.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (scCityEd.getText().toString().length() == 0) 
				{
					scCityBtn.setVisibility(View.GONE);
				} 
				else 
				{
					scCityBtn.setVisibility(View.VISIBLE);
				}
			}
		});
		scGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				map = (Map<String, Object>) adapter.getItem(position);
				String city = map.get("city").toString();
				scCityEd.setText(city);
			}
			
		});
	}
	private void getData() {
		for (int i = 0; i < City.length; i++) {
			map = new HashMap<String, Object>();
			map.put("city", City[i]);
			data.add(map);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.scBackImg:
			this.finish();
			break;
		case R.id.scCityBtn:
			String cityName = scCityEd.getText().toString();
			Intent intent = new Intent(this,MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("city",cityName);
			intent.putExtras(bundle);
			startActivity(intent);
			ChangeCityActivity.this.finish();
			break;
		}
	}
}
