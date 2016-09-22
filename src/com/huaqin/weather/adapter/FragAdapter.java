package com.huaqin.weather.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragAdapter extends FragmentPagerAdapter 
{

	private List<Fragment> mFragments;
	
	public FragAdapter(FragmentManager fm,List<Fragment> fragments) 
	{
		super(fm);
		// TODO Auto-generated constructor stub
		mFragments=fragments;
	}
	
	public FragAdapter(FragmentManager fm)
	{
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position) 
	{
		// TODO Auto-generated method stub
		
		return mFragments.get(position);
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return mFragments.size();
	}
	
}
