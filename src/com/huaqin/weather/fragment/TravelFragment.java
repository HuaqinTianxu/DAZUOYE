package com.huaqin.weather.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.huaqin.weather.activity.R;
import com.huaqin.weather.model.RealTimeWeather;
import com.huaqin.weather.model.TravelMessage;
import com.huaqin.weather.utils.GetJsonData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TravelFragment extends Fragment
{
	public TextView travelTextView , dressTextView, uvTextView, exerciseTextView , washTextView;
	public GetJsonData jsonData = new GetJsonData();
	public TravelMessage travelMessage = new TravelMessage();
	public static final int GET_DATA_SUCCESS = 0x3333;
	public Handler handler;
	//public 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.travel_layout, container,false);
		travelTextView = (TextView)view.findViewById(R.id.travel_text);
		dressTextView = (TextView)view.findViewById(R.id.dress_text);
		uvTextView = (TextView)view.findViewById(R.id.uv_text);
		exerciseTextView = (TextView)view.findViewById(R.id.exercise_text);
		washTextView = (TextView)view.findViewById(R.id.wash_text);
		TravelThread thread = new TravelThread();
		//thread.start();
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) 
			{
				// TODO Auto-generated method stub
				if(msg.what == GET_DATA_SUCCESS)
				{	
					travelTextView.setText("出行指数: "+travelMessage.getTravelIndex());
					dressTextView.setText("穿衣指数: "+travelMessage.getDressingIndex());
					uvTextView.setText("紫外线指数: "+travelMessage.getUvIndex());
					exerciseTextView.setText("锻练指数: "+travelMessage.getExerciseIndex());
					washTextView.setText("洗车指数: "+travelMessage.getWashIndex());
					
				}				
			}
			
		};
		return view;
	}
	public class TravelThread extends Thread
	{

		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			GetTravelMessage();
			handler.sendEmptyMessage(GET_DATA_SUCCESS);
		}

		private void GetTravelMessage() 
		{
			// TODO Auto-generated method stub
			
			try 
			{
				String result = jsonData.getRequest("上海");
				JSONObject jsonObject = new JSONObject(result);
				travelMessage.setDressingIndex(jsonObject.getJSONObject("result")
						.getJSONObject("today").getString("dressing_index"));
				travelMessage.setDressingAdvice(jsonObject.getJSONObject("result")
						.getJSONObject("today").getString("dressing_advice"));
				travelMessage.setUvIndex(jsonObject.getJSONObject("result")
						.getJSONObject("today").getString("uv_index"));
				travelMessage.setWashIndex(jsonObject.getJSONObject("result")
						.getJSONObject("today").getString("wash_index"));
				travelMessage.setTravelIndex(jsonObject.getJSONObject("result")
						.getJSONObject("today").getString("travel_index"));
				travelMessage.setExerciseIndex(jsonObject.getJSONObject("result")
						.getJSONObject("today").getString("exercise_index"));
				
			} 
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
