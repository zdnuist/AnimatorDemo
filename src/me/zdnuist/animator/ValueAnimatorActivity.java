package me.zdnuist.animator;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ValueAnimatorActivity extends Activity{
	
	public static final String TAG = "ValueAnimatorActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_valueanimator);
		//从0平滑过渡到1
		ValueAnimator anim = ValueAnimator.ofFloat(0.0f,1f);
		anim.setDuration(500); //时长500ms
		
		anim.addUpdateListener(new AnimatorUpdateListener(){

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float currentValue = (float) animation.getAnimatedValue();
				Log.d(TAG, "current value is " + currentValue);
			}
			
		});
		
		anim.start();
	}

}
