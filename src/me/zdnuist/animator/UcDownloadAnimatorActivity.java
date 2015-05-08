package me.zdnuist.animator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UcDownloadAnimatorActivity extends Activity implements
		OnClickListener {
	
	public static final String TAG = "UcDownloadAnimatorActivity";

	public final static int NOTIFYCATION_ID = 0x100001;

	public final static String ACTION = "me.zdnuist.broadcoast.action";

	private NotificationManager notificationManager;

	private Button notificationShow;

	private Notification notification;

	AnimationDrawable animDrawable;

	AlarmManager alarmManager;

	PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ucdownload);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent();
		intent.setAction(ACTION);
		pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		notificationShow = (Button) findViewById(R.id.btn_show_notifi);
		notificationShow.setOnClickListener(this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION);

		registerReceiver(receiver, filter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		unregisterReceiver(receiver);

		alarmManager.cancel(pendingIntent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show_notifi:
//			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//					System.currentTimeMillis(), 200, pendingIntent);
			
			new Thread(new DownloadTask("http://121.199.50.162/soft/apk/gaode.apk",this)).start();
			break;
		}
	}

	private void showNotification(int drawable, int progress) {

		// RemoteViews remoteViews = new RemoteViews(getPackageName(),
		// R.layout.notification_ucdownload);

		Notification.Builder mBuilder = new Notification.Builder(this);
		// animDrawable = (AnimationDrawable) getResources().getDrawable(
		// R.animator.uc_download_notification);
		mBuilder.setSmallIcon(drawable)
				.setTicker("Updated Notification")
				.setLargeIcon(
						BitmapFactory.decodeResource(getResources(),
								R.drawable.ic_launcher)).setContentIntent(null)
				.setProgress(100, progress, false).setOngoing(true);

		notification = mBuilder.build();
		notificationManager.notify(NOTIFYCATION_ID, notification);
	}

	int i = 1;
	int j = 0;
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && intent.getAction().equals(ACTION)) {
				if (i > 9) {
					i = 1;
				}

				int id = 0;
				switch (i) {
				case 1:
					id = R.drawable.notification_dling1;
					break;
				case 2:
					id = R.drawable.notification_dling2;
					break;
				case 3:
					id = R.drawable.notification_dling3;
					break;

				case 7:
					id = R.drawable.notification_dling7;
					break;
				case 8:
					id = R.drawable.notification_dling8;
					break;
				case 9:
					id = R.drawable.notification_dling9;
					break;

				case 4:
					id = R.drawable.notification_dling4;
					break;
				case 5:
					id = R.drawable.notification_dling5;
					break;
				case 6:
					id = R.drawable.notification_dling6;
					break;
				}

				j = intent.getIntExtra("progress", 0);
				if (j == 100) {
					id = R.drawable.notification_dled;
					showNotification(id, j);
					alarmManager.cancel(pendingIntent);
				} else {

					showNotification(id, j);

					i++;
					// j++;
				}

			}
		}
	};

	static class DownloadTask implements Runnable {

		private String webUrl;
		private Context context;

		public DownloadTask(String webUrl, Context context) {
			this.webUrl = webUrl;
			this.context = context;
		}

		@Override
		public void run() {
			Log.d(TAG, " begin download");
			HttpURLConnection urlConnection = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				URL url = new URL(webUrl);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setConnectTimeout(5 * 10000);
				urlConnection.setDoInput(true);
				urlConnection.setRequestMethod("GET");
				urlConnection.setReadTimeout(5000);
				int responseCode = urlConnection.getResponseCode();
				Log.d(TAG, "response code : " + responseCode);
				if (responseCode == HttpURLConnection.HTTP_OK) {
				int totalLength = urlConnection.getContentLength();
				
				inputStream = urlConnection.getInputStream();
				
				byte[] buffer = new byte[4 * 1024 * 1024];
				int len = 0;
				
				Intent intent = new Intent();
				intent.setAction(ACTION);
				
				makeKSDir();
				outputStream = new FileOutputStream(IMAGE_SAVE_PATH + File.separator + webUrl.substring(webUrl.lastIndexOf("/")+1)); 
				int progress = 0;
				while((len = inputStream.read(buffer))!=-1){
					outputStream.write(buffer,0,len);
					progress += len;
					Log.d(TAG, "progress:" + ((progress*100)/totalLength));
					intent.putExtra("progress", ((progress*100)/totalLength));
					context.sendBroadcast(intent);
					
					Thread.sleep(300);
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(outputStream!=null)
						outputStream.close();
					if(inputStream != null)
						inputStream.close();
					if(urlConnection!=null)
						urlConnection.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static String IMAGE_SAVE_PATH = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "zdnuist"
			+ File.separator + "cache";

	public static void makeKSDir() {
		File dir = new File(IMAGE_SAVE_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

}
