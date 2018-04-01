/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tyzl.company.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbToastUtil.java
 * 描述：Toast工具类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2014-07-02 下午11:52:13
 */

public class AbToastUtil {

    /**
     * 上下文.
     */
    private static Context mContext = null;

    /**
     * 显示Toast.
     */
    public static final int SHOW_TOAST = 0;
    /**
     *
     */
    public static Toast toast = null;


    /**
     * 主要Handler类，在线程中可用
     * what：0.提示文本信息
     */
    private static Handler baseHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToast(mContext, msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };


    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(Context context, String message) {
        String simpleName = context.getClass().getSimpleName();
        AbLogUtil.e("simpleName",simpleName+"");
        if (message == null) {
            return;
        }
        if (context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (message.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_LONG) {
                        if (toast != null) {
                            toast.show();
                        } else {
                            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                } else {
                    toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    oldMsg = message;
                }
            }
            oneTime = twoTime;
        }
    }

    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public static void showToast(Context context, int resId) {
        mContext = context;
        Toast.makeText(context, "" + context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
//		Toast.makeText(context, "" + context.getResources().getText(resId), Toast.LENGTH_SHORT).setGravity(Gravity.CENTER, 0, 0);
//		toast.setGravity(Gravity.CENTER, 0, 0);
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (toast != null) {
            toast.setGravity(gravity, xOffset, yOffset);
        }
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param resId 要提示的字符串资源ID，消息what值为0,
     */
    public static void showToastInThread(Context context, int resId) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", context.getResources().getString(resId));
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }


    public static void showToastInThread(Context context, String text) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }


}
