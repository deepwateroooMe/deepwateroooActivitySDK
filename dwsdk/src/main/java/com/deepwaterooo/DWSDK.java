package com.deepwaterooo;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deepwaterooo.dwsdk.R;
import com.deepwaterooo.dwsdk.UnityasrEventCallback;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

// 安卓SDK与unity游戏端最底层的双向桥接层:

// 那么这里桥接层两个类实现了双向调用,就可以达到互通目的了
// 但是可以去测试,游戏端可以借助桥接方法调用这个类里所定义的方法,实现游戏调用安卓端
// 得好好想下: 为什么这个类会找不到,这就是必须得打.jar包的原因 ?
// public class DWSDK extends AppCompatActivity { // 暂只设一个按钮,但是SDK这端的浓缩,这个按钮可转至游戏界面
// 这里就是急于模仿造成的: 必须得有活动上下文才能成为一个真正的活动呀!!!    
// public class DWSDK { // 暂只设一个按钮,但是SDK这端的浓缩,这个按钮可转至游戏界面
// 两个改成是类似的,可能会绕过这个问题,可是目的意义是什么呢? 活动的原生安卓SDK视图 与 兼容视图的区别 ?
public class DWSDK extends UnityPlayerActivity { // 暂只设一个按钮,但是SDK这端的浓缩,这个按钮可转至游戏界面
    private static final String TAG = "MainActivity";

// 自身的引用: 会跳至安卓SDK的一端,就不需要下面的这个索引了
    public static Activity mActivity;

    private static UnityasrEventCallback mCallback;
    private static AudioManager mAudioManager;
        
    // public static UnityPlayer mUnityPlayer; // 我觉得我还是不需要这个,只需要把它销毁
    // public static DWUnityActivity instance; // 相当于是单例模式
// DWUnityActivity extends AppCompatActivity:
    // public static UnityPlayer mUnityPlayer; // 只需要拿到它的视图并销毁就可以了
    // public static DWUnityActivity instance; // 相当于是单例模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (DWUnityActivity.mActivity != null) { // 再想想它是否是会自动销毁掉: 在安卓活动切换的时候,生命周期函数会自动销毁前活动的,包括视图,所以我应该是不需要管的,除非我需要有什么特殊处理
        //     ((ViewGroup)DWUnityActivity.mActivity.getParent()).removeView(mUnityPlayer);
        // }
        setContentView(R.layout.activity_main);
         mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // mActivity = this;
    //     // 手动适配 UnityPlayer 显示示图的过程        
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
    //         // setContentView(mUnityPlayer);
    //         setContentView(R.layout.activity_unity);
    // // 想要加载的游戏界面的布局: 可以游戏的过程中动态添加
    //         LinearLayout ll_unity_container = (LinearLayout) findViewById(R.id.ll_unity_container);
    // // 说的是:基类的静态成员变量,但是它是可以拿到视图显示的,它拿到的应该是游戏端的视图,嵌套在安卓界面中(一个按钮)       
    //         View unity_view = mUnityPlayer.getView(); // <<<<<<<<<<<<<<<<<<<< 感觉这里可能就是那个前不着村后不着店的死槛
    //         ll_unity_container.addView(unity_view);
    //         // mUnityPlayer.requestFocus();
    }

    public static void SendUnityMessage(String methodName, String parameter) {
        Log.d(TAG, "SendUnityMessage() ");
// 这里的特殊性是,所有的交接口方法都是持在这一个特殊物件上的,所以永远是这个物件
// 只要是调用U3D包的这个方法,就可以,不必一定要继承自UnityPlayerActivity        
        UnityPlayer.UnitySendMessage("MenuViewPanel", methodName, parameter != null ? parameter : ""); // for tmp
    }

    public void click(View view){
        Log.d(TAG, "click() ");
        // 点击跳转到 UnityActivity
        Intent intent = new Intent(this, DWUnityActivity.class);
        startActivity(intent);
    }
// // 这里,把调音量相关的方法包装进来
// // 大概读取的时候,是不需要权限的;但如果要设置当前值,可能就需要权限.把权限检查放在设置函数里
//     public int getMaxVolume() {
//         int max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
//         Log.d(TAG, "getMaxVolume() max: " + max);
//         mCallback.onIntValReady(max, "max");
//         return max;
//     }
//     public static int getCurrentVolume() {
//         int current = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
//         Log.d(TAG, "getCurrentVolume() current: " + current);
//         mCallback.onIntValReady(current, "cur");
//         return current;
//     }
//     public void setCurrentVolume(int val) {
//         Log.d(TAG, "setCurrentVolume() val: " + val);
// // 大概读取的时候,是不需要权限的;但如果要设置当前值,可能就需要权限.把权限检查放在设置函数里
//         NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//         if (Build.VERSION.SDK_INT >= VERSION_CODES.N
//             && !mNotificationManager.isNotificationPolicyAccessGranted()) {
//             Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//             startActivity(intent);
//         }
//         mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, val, AudioManager.FLAG_PLAY_SOUND);
// // TODO: 需要监听系统广播，需要得知是否设置成功了        
//     }
    public int add(int x, int y) { // 好像这个方法必须是静态的,那回去再试一下先前的项目
        Log.d(TAG, "add() ");
        String v = (x+y) + "";
        DWSDK.SendUnityMessage("onAddResultReady", v);
        return  x+y;
    }
    
    // 获取接口内容
    public void setCallback(UnityasrEventCallback callback){
        Log.d("@@@", "UnityBatteryEventCallback setCallback start ");
        mCallback = callback;
        Log.d("@@@", "UnityBatteryEventCallback setCallback end ");
        mCallback.Test1("I love my dear cousin~! Must marry him");
        mCallback.Speechcontent(617);
    }
}