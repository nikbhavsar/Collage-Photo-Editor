package com.example.nikhar.photoeditor.galary;

import android.app.Activity;
import android.os.Bundle;

public class ParentActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	
		syso3("onCreate");
	}
	
	

	@Override
	protected void onRestart() 
	{
		/*// TODO Auto-generated method stub
		if(Define.isFromBackground && LoginActivity.isLoggedin)
		{
			//Toast.makeText(getApplicationContext(), "App is From Background", Toast.LENGTH_LONG).show();
			
			Define.reloadWholeAppData(getApplicationContext());
		
			Define.isIntent=false;
			Define.isFromBackground=false;
			Define.isFinish=false;
		
		}*/
		syso3("onRestart");
		super.onRestart();
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		syso3("onResume");
		super.onResume();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		syso3("onStart");
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		syso3("onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		syso3("onDestroy");
		super.onDestroy();
	}
	
	void syso3(String name)
	{
		System.out.println("3 Method== "+name);
	}
	
	

}
