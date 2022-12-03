package com.deepwaterooo;

import com.squarepanda.sdk.beans.ParentInfoDO;
import com.squarepanda.sdk.beans.PlayerDO;
import com.squarepanda.sdk.networklayer.NetworkUtil;
import com.squarepanda.sdk.utils.PlayerUtil;
import com.squarepanda.sdk.utils.Util;
import com.unity3d.player.UnityPlayer;

// 这个类提供 全静态 公用 方法,可以供unity游戏端调用SDK中的实现, 相当于包装的公用接口API 供游戏端调用,其实现可以在SDK更底层(比如非中介安卓SDK中)
public class DWSDK {

    public static final String TAG = "DWSDK"; // spu
    public static final String UnityGameobjectName = "Deepwaterooo";
    public static final int REQUEST_CODE_MANAGE_PLAYER = 0;
    public static final int REQUEST_CODE_SELECT_PLAYER = 1;
    private static String LEDStatus = "0,0,0,0,0,0,0,0";

    public DWSDK() {}

    public static void SendUnityMessage(String methodName, String parameter) {
// 这里的特殊性是,所有的交接口方法都是持在这一个特殊物件上的,所以永远是这个物件        
        UnityPlayer.UnitySendMessage("Deepwaterooo", methodName, parameter != null ? parameter : "");
    }

// // TODO: 就是下面的几个方法没太弄明白, 这应该是java 7 里面的神仙写法,改天有机会再学这块儿,先把它跳过去    
//     public static void ShowAlertWarning(String title, String msg, String btnText, final String methodName) {
//         Util.showAlertWarning(DeepwateroooUnityActivity.mUnityPlayer.getContext(), title, msg, btnText,
//                               new android.view.View.OnClickListener(methodName) {
//                                   final String val$methodName = null;
//                                   public void onClick(View v) {
//                                       DWSDK.SendUnityMessage(methodName, "");
//                                   }
//                                       {
//                                           methodName = s;
//                                           super();
//                                       }
//                               }
//             );
//     }
//     public static void ShowAlert(String title, String msg, String btnText1, String btnText2, String methodName1, String methodName2) {
//         Util.showAlert(DeepwateroooUnityActivity.mUnityPlayer.getContext(), title, msg, Text1, Text2, new android.view.View.OnClickListener(methodName1) {
//                 final String val$methodName1;
//                 public void onClick(View v) {
//                     DWSDK.SendUnityMessage(methodName1, "");
//                 }
//                     {
//                         super();
//                         methodName1 = s;
//                     }
//             }, new android.view.View.OnClickListener(methodName2) {
//                     final String val$methodName2;
//                     public void onClick(View v) {
//                         DWSDK.SendUnityMessage(methodName2, "");
//                     }
//                         {
//                             methodName2 = s;
//                             super();
//                         }
//                 });
//     }
    public static void KeepAppAlive() {
        Util.keepAppAlive();
    }
    public static boolean IsInternetConnected() {
        return NetworkUtil.checkInternetConnection(com.deepwaterooo.DWUnityActivity.mUnityPlayer.getContext());
    }
    public static void UploadFileWithName(String data, String name) {
    PlayerDO player = PlayerUtil.getSelectedPlayer(com.deepwaterooo.DWUnityActivity.instance);
        if(player == null) {
            return;
        } else {
            String n = (new StringBuilder()).append(player.getId()).append("_").append(name).append(".json").toString();
            byte d[] = data.getBytes();
//            NetworkUtil.uploadFile(com.deepwaterooo.DWUnityActivity.instance, new ApiCallListener() {
//                    public void onResponse(Object o) {
//                    }
//                    public void onFailure(Object o) {
//                    }
//                }
//                , d, n);
            return;
        }
    }
    public static void DownloadFileWithName(String name) {
        PlayerDO player = PlayerUtil.getSelectedPlayer(com.deepwaterooo.DWUnityActivity.instance);
        if(player == null) {
            return;
        } else {
            String n = (new StringBuilder()).append(player.getId()).append("_").append(name).append(".json").toString();
//            NetworkUtil.downloadFile(com.deepwaterooo.DWUnityActivity.instance, new ApiCallListener() {
//                    public void onResponse(Object o) {
//                        ResponseBody r = (ResponseBody)o;
//                        try
//                        {
//                            byte b[] = r.bytes();
//                            String retrieved = new String(b);
//                            DWSDK.SendUnityMessage("_onLoadFileSuccess", retrieved);
//                        }
//                        catch(IOException e)
//                        {
//                            e.printStackTrace();
//                            DWSDK.SendUnityMessage("_onLoadFileFail", "");
//                        }
//                    }
//                    public void onFailure(Object o) {
//                        DWSDK.SendUnityMessage("_onLoadFileFail", "");
//                    }
//                }
//                , n);
//            return;
        }
    }
    public static void Init() {
        PlayerUtil.startSplashScreenActivity(com.deepwaterooo.DWUnityActivity.instance);
    }
    public static void StartSplashScreenActivity() {
        PlayerUtil.startSplashScreenActivity(com.deepwaterooo.DWUnityActivity.instance);
    }
    public static void StartGameActivity() {}

    // public static void ManagePlayers() {
    //     PlayerUtil.startManagePlayerActivity(DWUnityActivity.instance, 0);
    // }
    public static boolean IsLoggedIn() {
        ParentInfoDO info = PlayerUtil.getParentInfo(com.deepwaterooo.DWUnityActivity.instance);
        return info != null;
    }
    public static void GetProfileURL() {
        String url = PlayerUtil.getSelectedPlayer(com.deepwaterooo.DWUnityActivity.instance).getProfileURL();
        SendUnityMessage("profileURLResponse", url);
    }
    public static void Terms() {
        PlayerUtil.showTermsNconditions(com.deepwaterooo.DWUnityActivity.instance);
    }
    public static void Privacy() {
        PlayerUtil.showPrivacyPolicy(com.deepwaterooo.DWUnityActivity.instance);
    }
    public static void Credits() {
        PlayerUtil.showCredits(com.deepwaterooo.DWUnityActivity.instance);
    }
    public static void Logout() {
//        PlayerUtil.logoutUser(com.deepwaterooo.DWUnityActivity.instance);
    }

    
//     public static final String TAG = "DWSDK"; 
//     public static final String UnityGameobjectName = "DWSDK"; 

//     public DWSDK() {}

// // 定义了这个静态方法,在本类中也被多次用到,用于调用 unity,实现了SDK 对游戏端的调用或说事件通知
//     public static void SendUnityMessage(String methodName, String parameter) {
//         Log.d(TAG, "SendUnityMessage() ");
//         UnityPlayer.UnitySendMessage(UnityGameobjectName, methodName, parameter != null ? parameter : ""); // <<<<<<<<<< bug
//     }
//     // UnityPlayer.UnitySendMessage(
//     //     "Unity Object Name", // Unity Object Name 为 Unity 中场景对象的名称
//     //     "MethodName",        // MethodName 为该对象绑定的脚本中的方法。 message 为发送的内容
//     //     "message")

//     public static void showToast(String s) { // 这个方法应该是需要非静态,要不然拿不到context 
//         Log.d(TAG, "showToast() s: " + s);
// //        Toast.makeText(this, "Unity 调用了安卓,发起土司", Toast.LENGTH_SHORT).show();
//     }

//     public static void Init() {
//         Log.d(TAG, "Init() ");
//    }

//     public static int add(int x, int y) {
//         String v = (x + y) + "";
//         Log.d(TAG, "add() v: " + v);
//         DWSDK.SendUnityMessage("onAdd", v);
//         return x + y;
//     }
}