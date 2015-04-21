package me.zdnuist.animator;

import me.zdnuist.animator.fragment.FrameAnimFragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 帧动画
 * @author zdnuist
 *
 */
public class FrameAnimatorActivity extends Activity implements OnClickListener{
	
	Button start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_frameanimator);
		
		start = (Button) findViewById(R.id.btn_start);
		start.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.btn_start:
			FrameAnimFragment fragment = new FrameAnimFragment();
			fragment.show(getFragmentManager(), "frameFragment");
			break;
		}
	}

}
