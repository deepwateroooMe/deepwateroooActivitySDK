package com.deepwaterooo;// 因为你还会有个真正的小SDK,这里主要方便同一个大包裹下能够找到类 ?

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.deepwaterooo.dwsdk.R;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

// 安卓SDK与unity游戏端最底层的双向桥接层:
    // DWUnityActivity: 主要负责安卓SDK端调用游戏底层桥接方法,实现安卓端向游戏端的调用
    // 同时,实现游戏活动 与 安卓活动的无缝无按钮切换
// DWUnityActivity: extends UnityPlayerActivity或者自定义,都没有关系,主要逻辑要连通  // <<<<<<<<<< 这里可能说得不对
// 这个类有个更大的作用是把安卓SDK中的安卓活动,转化为游戏端的活动, 所以需要再模仿这个例子实现一遍

public class DWUnityActivity extends UnityPlayerActivity { // 有个bug: 找不到DWSDK,换另一种方法试
// public class DWUnityActivity extends AppCompatActivity { // 感觉这里面有什么地方没有适配好,无法实例化启动程序
    private final String TAG = "DWUnityActivity";

    // public static UnityPlayer mUnityPlayer;
    // public static DWUnityActivity instance; // 相当于是单例模式
    // // 自身的引用:
    // public static Activity mActivity;
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_unity);
// 想要加载的游戏界面的布局: 可以游戏的过程中动态添加
        LinearLayout ll_unity_container = (LinearLayout) findViewById(R.id.ll_unity_container);
// 说的是:基类的静态成员变量,但是它是可以拿到视图显示的,它拿到的应该是游戏端的视图,嵌套在安卓界面中(一个按钮)       
        View unity_view = mUnityPlayer.getView(); // <<<<<<<<<<<<<<<<<<<< 感觉这里可能就是那个前不着村后不着店的死槛
        ll_unity_container.addView(unity_view);
//        mActivity = this;
        
// // 手动适配 UnityPlayer 显示示图的过程        
//         requestWindowFeature(1);
//         super.onCreate(bundle);
//         if (mUnityPlayer == null) {
//             getWindow().setFormat(2);
//             mUnityPlayer = new UnityPlayer(this);
//             instance = this;
//         } else {
//             ((ViewGroup)mUnityPlayer.getParent()).removeView(mUnityPlayer);
//             UnityPlayer.currentActivity = this;
//             instance = (DWUnityActivity)UnityPlayer.currentActivity;
//         }
//         setContentView(mUnityPlayer); // 这里直接设置成游戏或是安卓视图,不再两端各带一个按钮
//         mUnityPlayer.requestFocus();
// //         setContentView(R.layout.activity_unity);
// // // 想要加载的游戏界面的布局: 可以游戏的过程中动态添加
// //         LinearLayout ll_unity_container = (LinearLayout) findViewById(R.id.ll_unity_container);
// // // 说的是:基类的静态成员变量,但是它是可以拿到视图显示的,它拿到的应该是游戏端的视图,嵌套在安卓界面中(一个按钮)       
// //         View unity_view = mUnityPlayer.getView(); // <<<<<<<<<<<<<<<<<<<< 感觉这里可能就是那个前不着村后不着店的死槛
// //         ll_unity_container.addView(unity_view);
    }

    public void click(View view){
        Log.d(TAG, "click() ");
        callMainActivity();
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        // 添加返回键返回 MainActivity
        if (i == KeyEvent.KEYCODE_BACK){
            callMainActivity();
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void callMainActivity(){
        Log.d(TAG, "callMainActivity() ");
        Intent intent = new Intent(this, com.deepwaterooo.DWSDK.class);
        startActivity(intent);
        finish();
    }
}