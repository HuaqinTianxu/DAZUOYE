package com.huaqin.weather.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

import com.huaqin.weather.activity.R;
import com.huaqin.weather.model.FutureWeather;
import com.huaqin.weather.model.RealTimeWeather;
import com.huaqin.weather.utils.GetJsonData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment
{
    public TextView dateView , weekView, tempView , windView , humidityView , cityNameView ,weatherView;
    public TextView tomoWeatherView, tomoTempView, tomoWindView, afterWeatherView, afterTempView, afterWindView;
    public List<FutureWeather> list = new ArrayList<FutureWeather>();
    public DataThread dataThread;
    public GetJsonData jsonData = new GetJsonData();
    public Handler handler;
    public RealTimeWeather realTimeWeather = new RealTimeWeather();
    public static final int GET_DATA_SUCCESS = 0x1234;
    public ImageView todayImageView,tomoImageView,afterImageView;
    public String result = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.home_layout,container,false);
		
		dateView = (TextView) view.findViewById(R.id.date_y);
		weekView = (TextView) view.findViewById(R.id.week_text);
		tempView = (TextView) view.findViewById(R.id.temp_text);
		weatherView = (TextView) view.findViewById(R.id.weather_text);
		windView = (TextView) view.findViewById(R.id.wind_text);
		humidityView = (TextView) view.findViewById(R.id.hum_text);
		cityNameView = (TextView) view.findViewById(R.id.city_text);
		
		tomoTempView = (TextView) view.findViewById(R.id.tomo_temp_text);
		tomoWeatherView = (TextView) view.findViewById(R.id.tomo_weather_text);
		tomoWindView = (TextView) view.findViewById(R.id.tomo_wind_text);
		afterTempView = (TextView) view.findViewById(R.id.after_temp_text);
		afterWeatherView = (TextView) view.findViewById(R.id.after_weather_text);
		afterWindView = (TextView) view.findViewById(R.id.after_wind_text);
		
		todayImageView = (ImageView) view.findViewById(R.id.weather_image);
		tomoImageView = (ImageView) view.findViewById(R.id.tomo_weather_image);
		afterImageView = (ImageView) view.findViewById(R.id.after_weather_image);
		
		dataThread = new DataThread();
		//dataThread.start();
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) 
			{
				// TODO Auto-generated method stub
				if(msg.what == GET_DATA_SUCCESS)
				{
					SetRealTimeWeather();
				}				
			}
			
		};
		return view;
	}

	public class DataThread extends Thread
	{

		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
		    GetRealTimeWeather();
			handler.sendEmptyMessage(GET_DATA_SUCCESS);
		}
		
	}
	public void GetRealTimeWeather()
	{
		Bundle bundle = new Bundle();
		bundle = getActivity().getIntent().getExtras();
		String cityString = null;
		if(bundle!=null)
		{
			cityString = bundle.getString("city");
		}
		
		if(cityString==null)
		{
			result = jsonData.getRequest("上海");
		}
		else
		{
			result = jsonData.getRequest(cityString);
		}
		
		try
		{
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
			
			realTimeWeather.setTemp(jsonObject.getJSONObject("result")
					.getJSONObject("sk").getString("temp"));
			realTimeWeather.setWindDirection(jsonObject.getJSONObject("result")
					.getJSONObject("sk").getString("wind_direction"));
			realTimeWeather.setWindStrength(jsonObject.getJSONObject("result")
					.getJSONObject("sk").getString("wind_strength"));
			realTimeWeather.setHumidity(jsonObject.getJSONObject("result")
					.getJSONObject("sk").getString("humidity"));
			realTimeWeather.setTime(jsonObject.getJSONObject("result")
					.getJSONObject("sk").getString("time"));
			
			realTimeWeather.setCity(jsonObject.getJSONObject("result")
					.getJSONObject("today").getString("city"));
		    realTimeWeather.setDate(jsonObject.getJSONObject("result")
		    		.getJSONObject("today").getString("date_y"));
		    realTimeWeather.setTodaytemp(jsonObject.getJSONObject("result")
		    		.getJSONObject("today").getString("temperature"));
		    realTimeWeather.setWeather(jsonObject.getJSONObject("result")
		    		.getJSONObject("today").getString("weather"));
		    realTimeWeather.setTodaytemp(jsonObject.getJSONObject("result")
		    		.getJSONObject("today").getString("temperature"));
		    realTimeWeather.setWeek(jsonObject.getJSONObject("result")
		    		.getJSONObject("today").getString("week"));
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void SetRealTimeWeather()
	{
		dateView.setText(realTimeWeather.getDate()+realTimeWeather.getTime()+"发布");
		weekView.setText(realTimeWeather.getWeek());
		tempView.setText(realTimeWeather.getTodaytemp());
		weatherView.setText(realTimeWeather.getWeather());
		windView.setText(realTimeWeather.getWindDirection()+realTimeWeather.getWindStrength());
		humidityView.setText("湿度"+realTimeWeather.getHumidity());
		cityNameView.setText(realTimeWeather.getCity());
		
	    tomoTempView.setText(list.get(1).getTemperature());
	    tomoWeatherView.setText(list.get(1).getWeather());
	    tomoWindView.setText(list.get(1).getWind());
	    
	    afterTempView.setText(list.get(2).getTemperature());
	    afterWeatherView.setText(list.get(2).getWeather());
	    afterWindView.setText(list.get(2).getWind());
	    
	    setWeatherImage();
	    
	}
	private void setWeatherImage() {
		// TODO Auto-generated method stub
		String todayWeather = realTimeWeather.getWeather();
		String tomoWeather = list.get(1).getWeather();
		String afterTomoWeather = list.get(2).getWeather();
	    if(todayWeather.equals("睛"))
	    {
	    	todayImageView.setImageResource(R.drawable.sunny);
	    }
	    else if(todayWeather.equals("多云"))
	    {
	    	todayImageView.setImageResource(R.drawable.cloudy);
	    }
	    else if(todayWeather.equals("小雨")||todayWeather.equals("中雨")||todayWeather.equals("大雨")||todayWeather.equals("阵雨")||todayWeather.equals("阵雨转小雨"))
	    {
	    	todayImageView.setImageResource(R.drawable.rain);
	    }
	    else if(todayWeather.equals("雷阵雨"))
	    {
	    	todayImageView.setImageResource(R.drawable.storm);
	    }
	    else if(todayWeather.equals("多云转睛")||todayWeather.equals("睛转多云"))
	    {
	    	todayImageView.setImageResource(R.drawable.sunnyorcloudy);
	    }
	    else if(todayWeather.equals("多云转小雨")||todayWeather.equals("小雨转多云"))
	    {
	    	todayImageView.setImageResource(R.drawable.sunnyorrain);
	    }
	    else 
	    {
	    	todayImageView.setImageResource(R.drawable.snow);
	    }
	    //
	    if(tomoWeather.equals("睛"))
	    {
	    	tomoImageView.setImageResource(R.drawable.sunny);
	    }
	    else if(tomoWeather.equals("多云"))
	    {
	    	tomoImageView.setImageResource(R.drawable.cloudy);
	    }
	    else if(tomoWeather.equals("小雨")||tomoWeather.equals("中雨")||tomoWeather.equals("大雨"))
	    {
	    	tomoImageView.setImageResource(R.drawable.rain);
	    }
	    else if(tomoWeather.equals("雷阵雨"))
	    {
	    	tomoImageView.setImageResource(R.drawable.storm);
	    }
	    else if(tomoWeather.equals("多云转睛")||tomoWeather.equals("睛转多云"))
	    {
	    	tomoImageView.setImageResource(R.drawable.sunnyorcloudy);
	    }
	    else if(tomoWeather.equals("多云转小雨")||tomoWeather.equals("小雨转多云"))
	    {
	    	tomoImageView.setImageResource(R.drawable.sunnyorrain);
	    }
	    else 
	    {
	    	tomoImageView.setImageResource(R.drawable.snow);
	    }
	    //
	    if(afterTomoWeather.equals("睛"))
	    {
	    	afterImageView.setImageResource(R.drawable.sunny);
	    }
	    else if(afterTomoWeather.equals("多云"))
	    {
	    	afterImageView.setImageResource(R.drawable.cloudy);
	    }
	    else if(afterTomoWeather.equals("小雨")||afterTomoWeather.equals("中雨")||afterTomoWeather.equals("大雨"))
	    {
	    	afterImageView.setImageResource(R.drawable.rain);
	    }
	    else if(afterTomoWeather.equals("雷阵雨"))
	    {
	    	afterImageView.setImageResource(R.drawable.storm);
	    }
	    else if(afterTomoWeather.equals("多云转睛")||afterTomoWeather.equals("睛转多云"))
	    {
	    	afterImageView.setImageResource(R.drawable.sunnyorcloudy);
	    }
	    else if(afterTomoWeather.equals("多云转小雨")||afterTomoWeather.equals("小雨转多云"))
	    {
	    	afterImageView.setImageResource(R.drawable.sunnyorrain);
	    }
	    else 
	    {
	    	afterImageView.setImageResource(R.drawable.snow);
	    }
	}
}
