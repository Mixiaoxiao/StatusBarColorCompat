package com.mixiaoxiao.statusbarcolorcompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class StatusBarColorCompat {

	private static void log(String msg) {
		Log.d("StatusBarColorCompat", msg);
	}

	public static final boolean IS_KITKAT = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
			&& (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP);
	public static final boolean IS_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	/** 只在KITKAT下有用到 **/
	public static final int KITKAT_STATUSBAR_HEIGHT = IS_KITKAT ? getStatusBarHeight() : 0;
	private static final String KITKAT_FAKE_STATUSBAR_BACKGROUND_VIEW_TAG = "KITKAT_FAKE_STATUSBAR_BACKGROUND_VIEW_TAG";

	public static void setContentViewWithStatusBarColorByColorPrimaryDark(Activity activity, int layoutResID) {
		setContentViewFitsSystemWindows(activity, layoutResID);
		setStatusBarColor(activity, getColorPrimaryDark(activity));
	}

	
	/**
	 * 主要是在KITKAT下把根布局设置setFitsSystemWindows，
	 * 来解决软键盘弹出的问题
	 * 会再套一层FrameLayout，避免merge问题和
	 * @param activity
	 * @param layoutResID
	 */
	public static void setContentViewFitsSystemWindows(Activity activity, int layoutResID) {
		if (IS_KITKAT) {
			// 要在根布局加setFitsSystemWindows，来解决软键盘的问题
			// 如果merge是根标签，必须指定ViewGroup root和attachToRoot=true
			// 否则android.view.InflateException: <merge /> can be used only with a
			// valid ViewGroup root and attachToRoot=true
			// 目前暂时未找到优雅的解决merge的方法
			// 情况一，只有一个child,就不是merge，直接把原来的View传给activity即可，
			// 但是如果把这个child设置了setFitsSystemWindows，其padding会重置为0
			// 情况二，count>1说明layout的根节点是merge，需要把这个套一层的contentContainer传给activity
			// 综上所述，在KITKAT下再套一个setFitsSystemWindows的FrameLayout即可
			FrameLayout contentContainer = new FrameLayout(activity);
			LayoutInflater.from(activity).inflate(layoutResID, contentContainer, true);
			// if(contentContainer.getChildCount() == 1){
			// View realContent = contentContainer.getChildAt(0);
			// realContent.setFitsSystemWindows(true);
			// contentContainer.removeViewAt(0);
			// activity.setContentView(realContent);
			// }else{
			contentContainer.setFitsSystemWindows(true);
			activity.setContentView(contentContainer);
			// }
		} else {
			activity.setContentView(layoutResID);
		}

	}
	/**
	 * 设置状态栏颜色
	 * 5.0以上用原生的window.setStatusBarColor
	 * 4.4上用贴一个fakeStatusBarBackgroundView的方式
	 * 其实5.0的window.setStatusBarColor也是把贴的statusBarBackgroundView设置颜色
	 * @param activity
	 * @param statusBarColor
	 */
	@SuppressLint("NewApi")
	public static void setStatusBarColor(final Activity activity, final int statusBarColor) {
		if (IS_LOLLIPOP) {
			final Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			if (window.getStatusBarColor() != statusBarColor) {
				window.setStatusBarColor(statusBarColor);
				log("LOLLIPOP window.getStatusBarColor() != statusBarColor, so update");
			} else {
				log("LOLLIPOP window.getStatusBarColor() == statusBarColor, so ingore");
			}
		} else if (IS_KITKAT) {
			final Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 在这里设置setSoftInputMode是没有效果的，要在super.onCreate之前设置才有效果
			// window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
			// | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			ViewGroup decorView = (ViewGroup) window.getDecorView();
			View fakeStatusBarBackgroundView = null;
			// findViewWithTag会遍历所有的，我们在这里只遍历decorView的child即可
			final int decorChildCount = decorView.getChildCount();
			for (int i = 0; i < decorChildCount; i++) {
				View child = decorView.getChildAt(i);
				Object childTag = child.getTag();
				if (childTag != null) {
					if (KITKAT_FAKE_STATUSBAR_BACKGROUND_VIEW_TAG.equals(childTag)) {
						fakeStatusBarBackgroundView = child;
						log("find the fakeStatusBarBackgroundView by tag");
						break;
					}
				}
			}
			if (fakeStatusBarBackgroundView == null) {
				fakeStatusBarBackgroundView = new View(activity);
				log("NOT find the fakeStatusBarBackgroundView by tag, so new one");
				fakeStatusBarBackgroundView.setTag(KITKAT_FAKE_STATUSBAR_BACKGROUND_VIEW_TAG);
				decorView.addView(fakeStatusBarBackgroundView, ViewGroup.LayoutParams.MATCH_PARENT,
						KITKAT_STATUSBAR_HEIGHT);
			}
			fakeStatusBarBackgroundView.setBackgroundColor(statusBarColor);
		}
	}
	/**
	 * 取ColorPrimaryDark的值
	 * 5.0以上优先去取android.R.attr.colorPrimaryDark 
	 * 其他情况取R.color.colorPrimaryDark（如果有的话）
	 * @param activity
	 * @return
	 */
	@SuppressLint("InlinedApi")
	private static int getColorPrimaryDark(Activity activity) {
		int statusBarColor = Color.TRANSPARENT;
		if (IS_LOLLIPOP) {
			TypedArray a = activity.getTheme().obtainStyledAttributes(new int[] { android.R.attr.colorPrimaryDark });
			if (a.hasValue(0)) {
				statusBarColor = a.getColor(0, Color.TRANSPARENT);
			} else {
				statusBarColor = getColorPrimaryDarkFromResources(activity);
			}
			a.recycle();
		} else if (IS_KITKAT) {
			statusBarColor = getColorPrimaryDarkFromResources(activity);
		}
		return statusBarColor;
	}

	private static int getColorPrimaryDarkFromResources(Activity activity) {
		final Resources res = activity.getResources();
		final int colorPrimaryDarkResId = res.getIdentifier("colorPrimaryDark", "color", activity.getPackageName());
		if (colorPrimaryDarkResId > 0) {
			return res.getColor(colorPrimaryDarkResId);
		}
		return Color.TRANSPARENT;
	}

	public static int getStatusBarHeight() {
		final Resources res = Resources.getSystem();
		int id = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
		if (id > 0) {// 一般不可能没有这个id，除非这个厂商是傻逼。
			return res.getDimensionPixelSize(id);
		}
		// 如果没有取到，则取25dp，貌似6.0以上是24dp？这个暂时懒得管了
		return (int) (res.getDisplayMetrics().density * 25f + 0.5f);
	}

}
