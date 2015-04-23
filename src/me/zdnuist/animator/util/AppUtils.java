package me.zdnuist.animator.util;

import android.app.Activity;
import android.view.WindowManager;

public class AppUtils {

	private AppUtils() {
	}

	/**
	 * 屏幕解锁
	 * 
	 * @param mContext
	 */
	public static void screanUnLock(Activity mContext) {

		mContext.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

	}

}
