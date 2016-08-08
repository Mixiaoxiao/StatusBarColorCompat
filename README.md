StatusBarColorCompat
===============

Change the StatusBarColor dynamically, works on Android 4.4+.

简单优雅地动态改变状态栏颜色，支持安卓4.4+。

Screenshots 
-----
* The left part is KITKAT, and right is LOLLIPOP. 左边是4.4，右边是5.0+。
![StatusBarColorCompat](https://raw.github.com/Mixiaoxiao/StatusBarColorCompat/master/GIF.gif) 


Sample APK
-----

[StatusBarColorCompat.apk](https://raw.github.com/Mixiaoxiao/StatusBarColorCompat/master/StatusBarColorCompat.apk)

Usage 
-----

* Just copy the [StatusBarColorCompat.java](https://raw.github.com/Mixiaoxiao/statusbarcolorcompat/master/StatusBarColorCompat/src/com/mixiaoxiao/statusbarcolorcompat/StatusBarColorCompat.java) in your project
* Use 
```java
StatusBarColorCompat.setContentViewWithStatusBarColorByColorPrimaryDark(Activity activity, int layoutResID) 
```
instead of
```java
Activity.setContentView(int layoutResID) 
```
* StatusBarColorCompat will fetch the colorPrimaryDark(in android.R.attr.colorPrimaryDark or R.color.colorPrimaryDark) automatically and set the color to the StatusBar. 

懒人用法
-----

* 把 [StatusBarColorCompat.java](https://raw.github.com/Mixiaoxiao/statusbarcolorcompat/master/StatusBarColorCompat/src/com/mixiaoxiao/statusbarcolorcompat/StatusBarColorCompat.java) 加入你的工程
* 用StatusBarColorCompat提供的方法
```java
StatusBarColorCompat.setContentViewWithStatusBarColorByColorPrimaryDark(Activity activity, int layoutResID) 
```
来替换之前的
```java
Activity.setContentView(int layoutResID) 
```
* StatusBarColorCompat 会自动取得ColorPrimary(in android.R.attr.colorPrimaryDark or R.color.colorPrimaryDark) 并设置为状态栏颜色。

Extra 
-----

* setStatusBarColor, use this line to change the statusBarColor dynamically
```java
StatusBarColorCompat.setStatusBarColor(Activity activity, int statusBarColor) {
```

* setContentViewFitsSystemWindows, KITKAT compat
```java
StatusBarColorCompat.setContentViewFitsSystemWindows(Activity activity, int layoutResID)
```

其他 
-----

* setStatusBarColor, 使用这个方法来动态改变状态栏颜色
```java
StatusBarColorCompat.setStatusBarColor(Activity activity, int statusBarColor) {
```

* setContentViewFitsSystemWindows, 主要是对KITKAT进行兼容，不影响padding，根节点是merge也可
```java
StatusBarColorCompat.setContentViewFitsSystemWindows(Activity activity, int layoutResID)
```

Features 特性
-----

* 简单、优雅，最低化对原代码的侵入性
* LOLLIPOP+ :使用原生的api
* KITKAT :不影响原根布局的padding，不影响软键盘弹出，支持动态变色

Developed By
------------

Mixiaoxiao - <xiaochyechye@gmail.com> or <mixiaoxiaogogo@163.com>



License
-----------

    Copyright 2016 Mixiaoxiao

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
