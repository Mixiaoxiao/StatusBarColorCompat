package com.mixiaoxiao.statusbarcolorcompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		super.onCreate(savedInstanceState);
	}

	public void setContentViewWithStatusBarColorByColorPrimaryDark(int layoutResID) {
		StatusBarColorCompat.setContentViewWithStatusBarColorByColorPrimaryDark(this, layoutResID);
	}

	public void onClickChangeStatusBarColorToTransparent(View v) {
		StatusBarColorCompat.setStatusBarColor(this, Color.TRANSPARENT);
	}

	public void onClickChangeStatusBarColorToMaterialLightBlue(View v) {
		StatusBarColorCompat.setStatusBarColor(this, getResources().getColor(R.color.material_lightblue_500));
	}

	public void onClickChangeStatusBarColorToMaterialTeal(View v) {
		StatusBarColorCompat.setStatusBarColor(this, getResources().getColor(R.color.material_teal_500));
	}

	public void onClickChangeStatusBarColorToMaterialPink(View v) {
		StatusBarColorCompat.setStatusBarColor(this, getResources().getColor(R.color.material_pink_500));
	}

	public void onClickSecondActivity(View v) {
		startActivity(new Intent(this, SecondActivity.class));
	}
}
