# ToggleButton 滑动开关

# 项目概述

滑动开关是一个纯粹的自定义控件，上面的按钮会随着我们的左右滑动而滑动，并且在状态改变时通知用户，效果如下图1-9 所示，这也是应用中设置某些状态信息时最常见的控件，因此，我们有必要学习关于如何
自定义一个这样的滑动开关。

![](https://github.com/JackChen1999/ToggleButton/blob/master/art/b1.png) ![](https://github.com/JackChen1999/ToggleButton/blob/master/art/b2.png)

# 滑动开关UI

布局文件为activity_main.xml，代码如下：res/layout/activity_main.xml

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:itheima="http://schemas.android.com/apk/res/com.itheima.togglebuttondemo"
                xmlns:tools="http://schemas.android.com/tools"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
    <com.itheima.togglebuttondemo.view.ToggleButton
        android:id="@+id/togglebutton"
        android:layout_width="wrap_content"
        android:layout_centerInParent="true"
        itheima:SwitchBtnBackgroud="@drawable/switch_background"
        itheima:SlidBtnBackgroud="@drawable/slide_button_background"
        itheima:CurrentState="false"
        android:layout_height="wrap_content"/>
</RelativeLayout>
```

在activity_main.xml 布局中引入如下命名空间：
xmlns:itheima="http://schemas.android.com/apk/res/com.itheima.togglebuttondemo",com.itheima.togglebuttondemo 是包名，itheima 是自定义的命名控件名，可以任取名字，也可以使用类名。
上面的布局主要是引入com.itheima.togglebuttondemo.view.ToggleButton 类和自定义属性的使用。添加自定义属性，在values 目录下创建attrs.xml 文件，具体代码如文件所示：res/values/attrs.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <declare-styleable name="ToggleButton">
        <!-- 滑动开关背景图片属性-->
        <attr
            name="SwitchBtnBackgroud"
            format="reference"/>
        <!-- 滑动块背景图片属性-->
        <attr
            name="SlidBtnBackgroud"
            format="reference"/>
        <!-- 滑动开关的状态-->
        <attr
            name="CurrentState"
            format="boolean"/>
    </declare-styleable>
</resources>
```

attrs.xml 文件目录结构如下图所示：

![](https://github.com/JackChen1999/ToggleButton/blob/master/art/b3.png)

# 滑动开关业务逻辑实现

下拉选择框activity 界面，MainActivity.java 代码如下：com/itheima/MySwitch/MainActivity

```java
public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ToggleButton togglebutton = (ToggleButton) findViewById(R.id.togglebutton);
		//设置滑动开关的背景图片
		// togglebutton.setSwitchBtnBackgroudResource(R.drawable.switch_background);
		//设置滑动块的背景图片
		// togglebutton.setSlidBtnBackgroudResource(R.drawable.slide_button_background);
		//设置滑动开关的默认状态
		// togglebutton.setCurrentState(true);
		//设置滑动开关状态改变监听
		Togglebutton.setToggleBtnStateChangeListener(new ToggleBtnStateChangeListener() {
			@Override
			public void onToggleBtnStateChange(boolean currentState) {
				if (currentState) {
					Toast.makeText(getApplicationContext(), "开关打开", 0).show();
				}else{
					Toast.makeText(getApplicationContext(), "开关关闭", 0).show();
				}
			}
		});
	}
}
```
自定义的滑动开关ToggleButton 类的实现，具体代码如文件所示：com/itheima/MySwitch/MainActivity

```java
public class ToggleButton extends View {
	private Bitmap  switchBitmap;//滑动开关的背景图片
	private Bitmap  slidBitmap;//滑动块的背景图片
	private boolean currentState;
	private int     currentX;//手指触摸点的X 值
	private boolean isTouching = false;
	private ToggleBtnStateChangeListener mToggleBtnStateChangeListener;//状态改变监听器
	//在xml 中引用该控件时，调用该方法
	public ToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		String namespace = "http://schemas.android.com/apk/res/com.itheima.togglebuttondemo";
		currentState = attrs.getAttributeBooleanValue(namespace, "CurrentState", false);
		int switchBtnBackgroudId =
				attrs.getAttributeResourceValue(namespace, "SwitchBtnBackgroud", -1);
		int slidBtnBackgroudId =
				attrs.getAttributeResourceValue(namespace, "SlidBtnBackgroud", -1);
		setSwitchBtnBackgroudResource(switchBtnBackgroudId);
		setSlidBtnBackgroudResource(slidBtnBackgroudId);
	}
	//在代码中创建该控件时，调用该构造方法
	public ToggleButton(Context context) {
		super(context);
	}
	//设置滑动开关的背景图片
	public void setSwitchBtnBackgroudResource(int switchBackground) {
		switchBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);
	}
	// 为了可以高度自定义和增强可扩展性，我们可以给其创建一个自定义控件底部背景了一个方法
	// 设置滑动块的背景图片
	public void setSlidBtnBackgroudResource(int slideButtonBackground) {
		slidBitmap = BitmapFactory.decodeResource(getResources(), slideButtonBackground);
	}
	//设置滑动开关的默认状态
	public void setCurrentState(boolean b) {
		currentState = b;
	}
	// 1、测量滑动开关的宽高
	// 测量控件的宽高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(switchBitmap.getWidth(), switchBitmap.getHeight());
	}
	// 2、绘制，画出我们的滑动开关
	//canvas:画布，将图形绘制在canvas，才能显示到屏幕上
	@Override
	protected void onDraw(Canvas canvas) {
		//绘制滑动开关的背景图片
		canvas.drawBitmap(switchBitmap, 0, 0, null);
		//绘制滑动块的背景图片
		if(isTouching){//手指触摸的时候，根据currentx 的值来绘制滑动块
			//根据手指的X 值，来绘制滑动块图片
			int left = currentX - slidBitmap.getWidth()/2;
			if(left < 0){//设置左边界
				left = 0;
			}else if(left > (switchBitmap.getWidth() - slidBitmap.getWidth())){//设置右边界
				left = switchBitmap.getWidth() - slidBitmap.getWidth();
			}
			canvas.drawBitmap(slidBitmap, left, 0, null);
		}else{ // 手指离开控件的时候，根据状态来绘制滑动块
			// 根据状态值，来绘制滑动块
			if(currentState){ //当前为true，开关打开，滑动块显示在最右边
				canvas.drawBitmap(slidBitmap,switchBitmap.getWidth() - slidBitmap.getWidth(),
						0, null);
			}else{//当前为false，开关关闭，滑动块显示在最左边
				canvas.drawBitmap(slidBitmap, 0, 0, null);
			}
		}
	}
	//当控件被触摸后，会调用该方法
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN://手指按下
				isTouching = true;
				currentX = (int) event.getX();
				break;
			case MotionEvent.ACTION_MOVE://手指滑动
				isTouching= true;
				currentX = (int) event.getX();
				break;
			case MotionEvent.ACTION_UP://手指抬起
				isTouching = false;
				currentX = (int) event.getX();
				int center = switchBitmap.getWidth()/2;
				//当滑动块中心点大于滑动开关背景图片的中心线时，显示到右边，当前状态为true
				boolean state = currentState;
				currentState = currentX > center;
				if(mToggleBtnStateChangeListener !=null&&state != currentState ){
					mToggleBtnStateChangeListener.onToggleBtnStateChange(currentState);
				}
				break;
			default:
				break;
		}
		invalidate(); //强制让控件重新绘制，ondraw；
		return true; //自己处理触摸事件
	}
	public void setToggleBtnStateChangeListener(ToggleBtnStateChangeListenerlistener){
		this.mToggleBtnStateChangeListener = listener;
	}
	// 定义滑动开关状态改变的回调接口
	public interface ToggleBtnStateChangeListener{
		void onToggleBtnStateChange(boolean currentState);
	}
}
```
运行程序，效果图如图1-11 所示。

![](https://github.com/JackChen1999/ToggleButton/blob/master/art/b4.png) ![](https://github.com/JackChen1999/ToggleButton/blob/master/art/b5.png)

# 知识点总结

1．通过setMeasuredDimension 方法，来设置自定义控件的宽高，见ToggleButton 类第42 行
2．View 可以通过invalidate()方法强制让自己重新绘制，见ToggleButton 类第96 行
3．View 通过实现onTouchEvent 方法来处理手指触摸事件，见ToggleButton 类第72 行

# 自定义控件之自定义属性

当我们使用自定义属性来自定义控件时，一般分为以下几个步骤进行设置：

1. 在res 文件的values 里面创建attrs.xml，见文件【1-10】attrs.xml
2. 在attrs.xml，里面定义我们需要的属性，见文件【1-10】attrs.xml 代码
3. 在布局文件中使用自定义的属性，注意要添加命名空间，见文件【1-9】activity_main.xml 第2 行
4. 在构造方法中来获取设置的属性数据，见文件【1-9】见ToggleButton 类第8~19 行