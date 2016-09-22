package com.huaqin.weather.model;

public class RealTimeWeather {
	private String temp ;
	private String windDirection;
	private String windStrength;
	private String humidity;
	private String time;
	private String city;
	private String Todaytemp;
	private String weather;
	private String week;
	private String date;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTodaytemp() {
		return Todaytemp;
	}
	public void setTodaytemp(String todaytemp) {
		Todaytemp = todaytemp;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getWindStrength() {
		return windStrength;
	}
	public void setWindStrength(String windStrength) {
		this.windStrength = windStrength;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
