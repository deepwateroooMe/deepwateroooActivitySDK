package com.deepwaterooo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.deepwaterooo.DWSDK;
import com.squarepanda.sdk.activities.bluetooth.BluetoothBaseActivity;
import com.squarepanda.sdk.beans.PlayerDO;
import com.squarepanda.sdk.utils.PlayerUtil;
import com.unity3d.player.UnityPlayer;

// public class DWUnityActivity extends BluetoothBaseActivity {
public class DWUnityActivity extends AppCompatActivity {
    public static final String TAG = "DWUnityActivity";

    // private static BluetoothBaseActivity _instance;

    public static UnityPlayer mUnityPlayer;
    public static DWUnityActivity instance;

    private boolean _isScreenLocked;
    private boolean _fromBackground;

    public DWUnityActivity() {
        _isScreenLocked = false;
        _fromBackground = false;
    }
    protected void onCreate(Bundle savedInstanceState) {
        _isScreenLocked = false;
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        if (mUnityPlayer == null) {
            getWindow().setFormat(2);
            mUnityPlayer = new UnityPlayer(this);
            instance = this;
        } else {
            ((ViewGroup)mUnityPlayer.getParent()).removeView(mUnityPlayer);
            UnityPlayer.currentActivity = this;
            instance = (DWUnityActivity)UnityPlayer.currentActivity;
        }
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }
    protected void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1005)
            DWSDK.SendUnityMessage("UnlockPermissionResponse", "1");
        else if (resultCode == 1007)
            DWSDK.SendUnityMessage("UnlockPermissionResponse", "0");
    }
    protected void onSuccessLogoutEvent() {
        DWSDK.SendUnityMessage("SuccessLogout", "1");
    }
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();
        if(!_isScreenLocked) {
            if(_fromBackground) {
                if(DWSDK.IsLoggedIn())
                    PlayerUtil.startSelectPlayerActivity(instance, true, 1);
                _fromBackground = false;
            }
        } else {
            _isScreenLocked = false;
        }
    }
    public void gamePaused(boolean b) {
        DWSDK.SendUnityMessage("_onSDKScreenOpen", "");
        _isScreenLocked = b;
        _fromBackground = true;
    }
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == 2)
            return mUnityPlayer.injectEvent(event);
        else
            return super.dispatchKeyEvent(event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
    public void batteryLevel(String s) {
        DWSDK.SendUnityMessage("_onBatteryLevel", s);
    }
    public void availableServices() {
    }
    public void firmwareUpdateStatus(int i) {
    }
    protected void didNavigatesToMainMenu() {
        DWSDK.SendUnityMessage("_onSDKScreenClose", "");
    }
    public void didFinishSdkUserConfiguration() {
        DWSDK.SendUnityMessage("OnZPadFinishSDKUserConfig", "");
    }
    public void didfinishSDKscreenflow() {
        DWSDK.SendUnityMessage("_onSDKReady", "");
    }
}
// public class DWUnityActivity extends AppCompatActivity {
//     private final String TAG = "DWUnityActivity";

    
// // 这是继续练习 回 字的四样写法,........    
//     public static UnityPlayer mUnityPlayer; // <<<<<<<<<<<<<<<<<<<< mUnityPlayer UnityPlayerActivity
//     public static DWUnityActivity instance; // <<<<<<<<<< 相当于是,unity游戏端的实例 reference

//     public DWUnityActivity() {}

//     protected void onCreate(Bundle savedInstanceState) { // 这里是需要理解得最透彻的,我觉得
//         requestWindowFeature(1);
//         super.onCreate(savedInstanceState);
//         if (mUnityPlayer == null) {
//             getWindow().setFormat(2);
//             mUnityPlayer = new UnityPlayer(this); // <<<<<<<<<< 
//             instance = this;
//         } else {
//             ((ViewGroup)mUnityPlayer.getParent()).removeView(mUnityPlayer);
//             UnityPlayer.currentActivity = this;
//             instance = (DWUnityActivity)UnityPlayer.currentActivity;
//         }
//         setContentView(mUnityPlayer); // <<<<<<<<<< 就是,游戏界面在安卓端的实现,就是这个样子的了 ?
//         mUnityPlayer.requestFocus();
//     }
//     protected void onDestroy() {
//         mUnityPlayer.quit(); // <<<<<<<<<< 
//         super.onDestroy();
//     }

// // // 安卓SDK调用unity中的方法: 现在是没有搞明白,为什么安卓SDK中就可以这么调用unity游戏端的这些私有方法
// //     public void batteryLevel(String s) {
// //         DWSDK.SendUnityMessage("_onBatteryLevel", s);
// //     }
// //     public void availableServices() {}
// //     protected void didNavigatesToMainMenu() { // 为什么会需要使用这个方法 ?
// //         DWSDK.SendUnityMessage("_onSDKScreenClose", "");
// //     }
// //     public void didFinishSdkUserConfiguration() {
// //         DWSDK.SendUnityMessage("OnZPadFinishSDKUserConfig", "");
// //     }
// //     public void didfinishSDKscreenflow() {
// //         DWSDK.SendUnityMessage("_onSDKReady", "");
// //     }
// //     public void didSelectedChild(PlayerDO player) { // 这里,我大概可以改装一个 onUserLogin之类的回调给游戏端
// //         PlayerUtil.setSelectedPlayer(instance, player);
// //         DWSDK.SendUnityMessage("_onProfileSelected", "");
// //         DWSDK.SendUnityMessage("_onSDKScreenClose", "");
// //     }
//     protected void onPause() {
//         super.onPause();
//         mUnityPlayer.pause();
//     }
//     protected void onResume() {
//         super.onResume();
//         mUnityPlayer.resume();
//     }
//     public void gamePaused(boolean b) { 
//         DWSDK.SendUnityMessage("_onSDKScreenOpen", ""); // 安卓SDK调用unity中的方法, 使安卓与游戏两端可以无缝衔接 ?    
//     }

    
// // 这个方法不管用    
//     private Activity _unityActivity; 
//     Activity getActivity() {
//         if (_unityActivity == null) {
//             try {
//                 Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
//                 Activity activity = (Activity) classtype.getDeclaredField("currentActivity").get(classtype);
//                 _unityActivity = activity;
//             } catch (ClassNotFoundException e) {
     
//             } catch (IllegalAccessException e) {
     
//             } catch (NoSuchFieldException e) {
     
//             }
//         }
//         return _unityActivity;
//     }
     
//     boolean callUnity(String gameObjectName, String functionName, String args){
//         Log.d(TAG, "callUnity() ");
//         try {
//             Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
//             Method method =classtype.getMethod("UnitySendMessage", String.class,String.class,String.class);
//             method.invoke(classtype,gameObjectName,functionName,args);
//             return true;
//         } catch (ClassNotFoundException e) {
     
//         } catch (NoSuchMethodException e) {
     
//         } catch (IllegalAccessException e) {
     
//         } catch (InvocationTargetException e) {
     
//         }
//         return false;
//     }
     
//     public boolean showToast(String content){
//         Log.d(TAG, "showToast() content: " + content);
//         Toast.makeText(getActivity(),content,Toast.LENGTH_SHORT).show();
//         // 这里是主动调用Unity中的方法，该方法之后unity部分会讲到
//         callUnity("Main Camera","FromAndroid", "hello unity, i'm android");
//         return true;
//     }
// }

// tac log1.log | awk '!flag; /FATAL EXCEPTION: main/{flag = 1};' | tac > cur.log
// tac log1.log | awk '!flag; /08:54:00.00/{flag = 1};' | tac > cur.log
