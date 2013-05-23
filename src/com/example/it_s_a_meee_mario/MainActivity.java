/*
 * @author: Maria Formisano
 * @github name: formisano
 */
package com.example.it_s_a_meee_mario;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		blinkAnimation();
		runningMario();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void blinkAnimation(){
		TextView myText = (TextView) findViewById(R.id.HelloWorldTextView);

		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(250); //You can manage the time of the blink with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		anim.setFillAfter(true);
		myText.startAnimation(anim);
	}
	
	private void runningMario(){
		final ImageView mario1 = (ImageView)findViewById(R.id.mario1);
		final int []imageArray={R.drawable.mario1, R.drawable.mario2};
		final Handler handler = new Handler();
		final Animation run = new TranslateAnimation(-90.0f, 1280.0f, 0.0f, 0.0f);
		run.setDuration(3000);
		run.setRepeatCount(Animation.INFINITE);
		run.setFillAfter(true);
		mario1.startAnimation(run);
		
		Runnable runnable = new Runnable() {
			int i = 0;
			public void run() {
				mario1.setImageResource(imageArray[i]);
				i++;
				if(i > imageArray.length-1) i = 0;
				handler.postDelayed(this, 50);  //for interval...
			}
		};
		handler.postDelayed(runnable, 0); //for initial delay...
	}
	
}
	


