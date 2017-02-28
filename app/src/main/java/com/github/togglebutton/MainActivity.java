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
/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   AllenIverson
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：ToggleButton
 * Package_Name：com.github.togglebutton
 * Version：1.0
 * time：2016/2/28 16:42
 * des ：滑动开关
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
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
