package com.github.togglebutton;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ToggleButton toggleButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		
		toggleButton.setSlideBackgroudResource(R.drawable.slide_button_background);
		toggleButton.setSwitchBackgroudResource(R.drawable.switch_background);
		toggleButton.setToggleState(ToggleButton.ToggleState.Open);
		
		toggleButton.setOnToggleStateChangeListener(new ToggleButton.OnToggleStateChangeListner() {
			@Override
			public void onToggleStateChange(ToggleButton.ToggleState state) {
				Toast.makeText(MainActivity.this, state== ToggleButton.ToggleState.Open?"开启":"关闭", 0).show();
			}
		});
	}
}
