package me.zdnuist.animator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ObjectAnimatorActivity extends Activity implements OnClickListener {

	public static final String TAG = "ObjectAnimatorActivity";

	Button start, start2, start3,start4;
	TextView text;

	int width;

	Context mContext;
	WindowManager wm;
	
	/**
	 * android.view.WindowManager.LayoutParams#FLAG_DISMISS_KEYGUARD
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_objectanimator);
		mContext = this;

		start = (Button) findViewById(R.id.btn_start);
		start.setOnClickListener(this);

		start2 = (Button) findViewById(R.id.btn_start2);
		start2.setOnClickListener(this);

		start3 = (Button) findViewById(R.id.btn_start3);
		start3.setOnClickListener(this);

		text = (TextView) findViewById(R.id.tv_text);

		wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);

		width = dm.widthPixels;
		text.setAlpha(0f);
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_start:
			// TextView在5秒中内从常规变换成全透明，再从全透明变换成常规
			ObjectAnimator anim = ObjectAnimator.ofFloat(text, "alpha", 1f, 0f,
					1f);
			// TextView进行一次360度的旋转
			anim = ObjectAnimator.ofFloat(text, "rotation", 0f, 360f);
			// TextView先向左移出屏幕，然后再移动回来
			float curTranslationX = text.getTranslationX();
			anim = ObjectAnimator.ofFloat(text, "translationX",
					curTranslationX, -500f, curTranslationX);
			// 将TextView在垂直方向上放大3倍再还原
			anim = ObjectAnimator.ofFloat(text, "scaleY", 1f, 3f, 1f);

			anim.setDuration(5000);
			anim.start();
			break;
		case R.id.btn_start2:
			text.setAlpha(1f);
			/*
			 * after(Animator anim) 将现有动画插入到传入的动画之后执行 after(long delay)
			 * 将现有动画延迟指定毫秒后执行 before(Animator anim) 将现有动画插入到传入的动画之前执行
			 * with(Animator anim) 将现有动画和传入的动画同时执行
			 */
			// 让TextView先从屏幕外移动进屏幕，然后开始旋转360度，旋转的同时进行淡入淡出操作
			ObjectAnimator moveIn = ObjectAnimator.ofFloat(text,
					"translationX", width + 600f, 0f);
			ObjectAnimator rotate = ObjectAnimator.ofFloat(text, "rotation",
					0f, 360f);
			ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(text, "alpha",
					1f, 0f, 1f);
			AnimatorSet animSet = new AnimatorSet();
			animSet.play(rotate).with(fadeInOut).after(moveIn);

			// 添加监听器
			moveIn.addListener(new AnimatorListener() {

				@Override
				public void onAnimationStart(Animator animation) {
					Log.d(TAG, "onAnimationStart");
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					Log.d(TAG, "onAnimationEnd");
				}

				@Override
				public void onAnimationCancel(Animator animation) {
					Log.d(TAG, "onAnimationCancel");
				}

				@Override
				public void onAnimationRepeat(Animator animation) {
					Log.d(TAG, "onAnimationRepeat");
				}

			});

			animSet.setDuration(4 * 1000);
			animSet.start();
			break;
		case R.id.btn_start3:
			Animator animator = AnimatorInflater.loadAnimator(this,
					R.animator.animator1);
			animator.setTarget(text);
			animator.start();
			break;
		}

	}

	/*
	 * 使用XML编写动画 <animator> 对应代码中的ValueAnimator <objectAnimator>
	 * 对应代码中的ObjectAnimator <set> 对应代码中的AnimatorSet
	 */

}
