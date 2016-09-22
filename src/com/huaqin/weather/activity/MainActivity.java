package com.huaqin.weather.activity;

import java.util.ArrayList;
import java.util.List;

import com.huaqin.weather.adapter.FragAdapter;
import com.huaqin.weather.fragment.FutureWeatherFragment;
import com.huaqin.weather.fragment.HomeFragment;
import com.huaqin.weather.fragment.TravelFragment;
import com.huaqin.weather.fragment.UserFragment;
import com.huaqin.weather.wxapi.WXEntryActivity;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener
{
	public static final int SHOW_HOME = 0 ;
	public static final int SHOW_FUTURE = 1 ;
	public static final int SHOW_TRAVEL = 2 ;
	public static final int SHOW_USER = 3 ;
	public ViewPager weatherPager;
	public List<Fragment> fragments= new ArrayList<Fragment>();
	public HomeFragment homeFragment = new HomeFragment();
	public UserFragment userFragment = new UserFragment();
	public FutureWeatherFragment futureWeatherFragment = new FutureWeatherFragment();
	public TravelFragment travelFragment = new TravelFragment();
	public FragmentManager fragmentManager;  
    public FragmentTransaction fragmentTransaction;  
    public TextView homeTextView , futureTextView, travelTextView,userTextView ;
    public ImageView homeImageView , futureImageView, travelImageView , userTImageView;
    public LinearLayout homeLayout ,futureLayout , travelLayout , userLayout ;
    private final String APP_ID = "wx15127f13f8073c28";
    public IWXAPI api;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		api = WXAPIFactory.createWXAPI(getApplicationContext(),APP_ID,true); 
		initLayout();
		initView();
		initFragment();
		FragAdapter weatherAdapter = new FragAdapter(getSupportFragmentManager(),fragments);
		
		weatherPager = (ViewPager)findViewById(R.id.viewpager);
		weatherPager.setAdapter(weatherAdapter);
		weatherPager.setOffscreenPageLimit(3);
		weatherPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() 
		{
			
			@Override
			public void onPageSelected(int turn) 
			{
				// TODO Auto-generated method stub
				switch (turn) 
				{
				case 0:
					changeHomeColor();
					break; 
				case 1:
					changeFutureColor();
					break;
				case 2:
					changeTravelColor();
					break;
				case 3:
					changeUserColor();
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) 
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initLayout() 
	{
		// TODO Auto-generated method stub
		homeLayout = (LinearLayout) findViewById(R.id.bottom_home_tab);
		futureLayout = (LinearLayout) findViewById(R.id.bottom_future_tab);
		travelLayout = (LinearLayout) findViewById(R.id.bottom_travel_tab);
		userLayout = (LinearLayout) findViewById(R.id.bottom_user_tab);
		
	}

	private void initFragment() 
	{
		// TODO Auto-generated method stub
		fragments.add(homeFragment);
		fragments.add(futureWeatherFragment);
		fragments.add(travelFragment);
		fragments.add(userFragment);
	}

	private void initView() 
	{
		// TODO Auto-generated method stub
		homeTextView = (TextView) findViewById(R.id.bottom_home_text);
		futureTextView = (TextView) findViewById(R.id.bottom_future_text);
		travelTextView = (TextView) findViewById(R.id.bottom_travel_text);
		userTextView = (TextView) findViewById(R.id.bottom_user_text);
		
		homeImageView = (ImageView) findViewById(R.id.bottom_home_img);
		futureImageView = (ImageView) findViewById(R.id.bottom_future_img);
		travelImageView = (ImageView) findViewById(R.id.bottom_travel_img);
		userTImageView = (ImageView) findViewById(R.id.bottom_user_img);
		
		homeTextView.setOnClickListener(this);
		futureTextView.setOnClickListener(this);
		travelTextView.setOnClickListener(this);
		userTextView.setOnClickListener(this);
		
		homeImageView.setOnClickListener(this);
		futureImageView.setOnClickListener(this);
		travelImageView.setOnClickListener(this);
		userTImageView.setOnClickListener(this);
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// TODO Auto-generated method stub
		switch (item.getItemId()) 
		{
		case R.id.add_or_del_city:
			startActivity(new Intent(this, ChangeCityActivity.class));
			MainActivity.this.finish();
			break;
		case R.id.share_friend:
			// 设置分享的内容
			setShareContent(0);
			break;
		case R.id.share_weather:
			// 设置分享的内容
			setShareContent(1); 
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	private void setShareContent(int flag) {
		// TODO Auto-generated method stub
		if (!api.isWXAppInstalled()) {  
	        Toast.makeText(this, "您还未安装微信客户端",  
	                Toast.LENGTH_SHORT).show();  
	        return;  
	    }  
	  
	    WXWebpageObject webpage = new WXWebpageObject();  
	    webpage.webpageUrl = "http://www.weather.com.cn/";  
	    WXMediaMessage msg = new WXMediaMessage(webpage);  
	  
	    msg.title = "最好用的迷你天气";  
	    msg.description = getResources().getString(  
	            R.string.share);  
	    Bitmap thumb = BitmapFactory.decodeResource(getResources(),  
	            R.drawable.background);  
	    msg.setThumbImage(thumb);  
	    SendMessageToWX.Req req = new SendMessageToWX.Req();  
	    req.transaction = String.valueOf(System.currentTimeMillis());  
	    req.message = msg;  
	    req.scene = flag;  
	    api.sendReq(req); 
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bottom_home_img:
			weatherPager.setCurrentItem(SHOW_HOME);
			break;
		case R.id.bottom_future_img:
			weatherPager.setCurrentItem(SHOW_FUTURE);
			break;
		case R.id.bottom_travel_img:
			weatherPager.setCurrentItem(SHOW_TRAVEL);
			break;
		case R.id.bottom_user_img:
			weatherPager.setCurrentItem(SHOW_USER);
			break;
		}
	}
	public void changeHomeColor()
	{
		homeLayout.setBackgroundColor(Color.rgb(171, 205, 239));
		futureLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		travelLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		userLayout.setBackgroundColor(Color.rgb(255, 255, 255));
	}
	public void changeFutureColor()
	{
		futureLayout.setBackgroundColor(Color.rgb(171, 205, 239));
		homeLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		travelLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		userLayout.setBackgroundColor(Color.rgb(255, 255, 255));
	}
	public void changeTravelColor()
	{
		travelLayout.setBackgroundColor(Color.rgb(171, 205, 239));
		futureLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		homeLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		userLayout.setBackgroundColor(Color.rgb(255, 255, 255));
	}
	public void changeUserColor()
	{
		userLayout.setBackgroundColor(Color.rgb(171, 205, 239));
		futureLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		travelLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		homeLayout.setBackgroundColor(Color.rgb(255, 255, 255));
	}
	
}
