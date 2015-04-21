package me.zdnuist.animator.fragment;

import me.zdnuist.animator.R;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class FrameAnimFragment extends DialogFragment {

	AnimationDrawable animDrawable;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = inflater
				.inflate(R.layout.fragment_frameanim, container, false);
		ImageView show = (ImageView) v.findViewById(R.id.iv_show);
		animDrawable = (AnimationDrawable) getResources().getDrawable(
				R.animator.frameanimator1);
		show.setBackground(animDrawable);
		animDrawable.start();
		return v;
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);

		animDrawable.stop();
	}

}
