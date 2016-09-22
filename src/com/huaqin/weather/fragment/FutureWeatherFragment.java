package com.huaqin.weather.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.achartengine.GraphicalView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.huaqin.weather.activity.R;
import com.huaqin.weather.model.FutureWeather;
import com.huaqin.weather.utils.ChartService;
import com.huaqin.weather.utils.GetJsonData;
import com.huaqin.weather.utils.TrendView;

import android.R.raw;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FutureWeatherFragment extends Fragment
{
	public GetJsonData jsonData = new GetJsonData();
	public List<FutureWeather> list = new ArrayList<FutureWeather>();
	public Handler handler ;
	public String[] tempString = new String[14];
	public String[] lowTemp = new String[7];
	public String[] highTemp = new String[7];
	public GraphicalView tempView,highView;
	public ChartService tService,highService;
	public int count = 0 ;
	public List<Integer> highTemperature = new ArrayList<Integer>();
	public List<Integer> lowTemperature = new ArrayList<Integer>();
	public TextView futureOneDay,futureTwoDay,futureThreeDay,futureFourDay ;
	public TextView futureOneWeather,futureTwoWeather,futureThreeWeather,futureFourWeather ;
	public TextView futureOneWind,futureTwoWind,futureThreeWind,futureFourWind;
	public TextView futureOneTemp,futureTwoTemp,futureThreeTemp,futureFourTemp;
	private TrendView trendview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fut_weather_layout, container,false);
		initview(view);
		
	    
		
		CurveThread curveThread = new CurveThread();
		curveThread.start();
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what == 0x1234)
				{
					SetCurve();										
					for(int i=1;i<5;i++)
					{
						lowTemperature.add(Integer.valueOf(lowTemp[i]));
						highTemperature.add(Integer.valueOf(highTemp[i]));
						
					}
					trendview.setBitmap();
					trendview.setTemperature(highTemperature, lowTemperature);
					SetText();
					
					
				}
			}
			
		};
		return view;
	}
	private void initview(View view) {
		// TODO Auto-generated method stub
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		trendview = (TrendView)view.findViewById(R.id.trendView);
	    trendview.setWidthHeight(width, height);
	    
	    futureOneDay = (TextView)view.findViewById(R.id.day_one);
	    futureTwoDay = (TextView)view.findViewById(R.id.day_two);
	    futureThreeDay = (TextView)view.findViewById(R.id.day_three);
	    futureFourDay = (TextView)view.findViewById(R.id.day_four);
	    
	    futureOneWeather = (TextView)view.findViewById(R.id.weather_name_one);
	    futureTwoWeather = (TextView)view.findViewById(R.id.weather_name_two);
	    futureThreeWeather = (TextView)view.findViewById(R.id.weather_name_three);
	    futureFourWeather = (TextView)view.findViewById(R.id.weather_name_four);
	    
	    futureOneTemp = (TextView)view.findViewById(R.id.temp_one);
	    futureTwoTemp = (TextView)view.findViewById(R.id.temp_two);
	    futureThreeTemp = (TextView)view.findViewById(R.id.temp_three);
	    futureFourTemp = (TextView)view.findViewById(R.id.temp_four);
	    
	    futureOneWind = (TextView)view.findViewById(R.id.wind_one);
	    futureTwoWind = (TextView)view.findViewById(R.id.wind_two);
	    futureThreeWind = (TextView)view.findViewById(R.id.wind_three);
	    futureFourWind = (TextView)view.findViewById(R.id.wind_four);
	}
	public class CurveThread extends Thread 
	{

		@Override
		public void run() 
		{
			GetFutureWeather();
			handler.sendEmptyMessage(0x1234);
		}

	}
	public void SetCurve()
	{
		for(int i=0; i<list.size();i++)
		{
			getNumbers(list.get(i).getTemperature());
		}
		int j=0,k=0;
		for(int i=0; i<tempString.length;i++)
		{
			if(i%2==0)
			{
				lowTemp[j++] = tempString[i];
			}
			else 
			{
				highTemp[k++] = tempString[i];
			}
		}
		
	}
	
	private void SetText() {
		// TODO Auto-generated method stub
		futureOneDay.setText(list.get(1).getWeek());
		futureTwoDay.setText(list.get(2).getWeek());
		futureThreeDay.setText(list.get(3).getWeek());
		futureFourDay.setText(list.get(4).getWeek());
		
		futureOneWeather.setText(list.get(1).getWeather());
		futureTwoWeather.setText(list.get(2).getWeather());
		futureThreeWeather.setText(list.get(3).getWeather());
		futureFourWeather.setText(list.get(4).getWeather());
		
		futureOneWind.setText(list.get(1).getWind());
		futureTwoWind.setText(list.get(2).getWind());
		futureThreeWind.setText(list.get(3).getWind());
		futureFourWind.setText(list.get(4).getWind());
		
		futureOneTemp.setText(list.get(1).getTemperature());
		futureTwoTemp.setText(list.get(2).getTemperature());
		futureThreeTemp.setText(list.get(3).getTemperature());
		futureFourTemp.setText(list.get(4).getTemperature());
	}
	
	public void getNumbers(String content) {  
        Pattern pattern = Pattern.compile("\\d+");  
        Matcher matcher = pattern.matcher(content);  
        
        while (matcher.find()) {  
           tempString[count++] = matcher.group();
        }  
    } 
	private void GetFutureWeather() 
	{
		// TODO Auto-generated method stub
		try 
		{
			String result = jsonData.getRequest("上海");
			JSONObject jsonObject = new JSONObject(result);
			
			String futureResult = jsonObject.getJSONObject("result").getString("future");
			
			JSONArray futureArray = new JSONArray(futureResult);
			for(int i=0; i<futureArray.length(); i++)
			{
				JSONObject futureData = (JSONObject) futureArray.get(i);
				FutureWeather futureWeather = new FutureWeather();
				futureWeather.setTemperature(futureData.getString("temperature"));
				futureWeather.setWeather(futureData.getString("weather"));
				futureWeather.setWeek(futureData.getString("week"));
				futureWeather.setWind(futureData.getString("wind"));
				futureWeather.setDate(futureData.getString("date"));
				
				list.add(futureWeather);
			}
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
