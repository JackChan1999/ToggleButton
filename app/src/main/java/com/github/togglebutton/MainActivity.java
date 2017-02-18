package com.github.togglebutton;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.ViewGroup;
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
