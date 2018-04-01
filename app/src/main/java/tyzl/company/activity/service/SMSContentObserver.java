package tyzl.company.activity.service;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tyzl.company.utils.AbStringUtil;


//用来观察系统里短消息的数据库变化  ”表“内容观察者,只要信息数据库发生变化，都会触发该ContentObserver 派生类
public class SMSContentObserver extends ContentObserver {
    private static String TAG = "SMSContentObserver";
    private int MSG_OUTBOXCONTENT = 2;
    private String patternCoder = "\\d{6}";     //正则表达式
    private String strContent;                  //验证码内容
    private Context mContext;
    private Handler mHandler;                  //更新UI线程

    public SMSContentObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    /**
     * 当所监听的Uri发生改变时，就会回调此方法
     *
     * @param selfChange 此值意义不大 一般情况下该回调值false
     */
    @Override
    public void onChange(boolean selfChange) {
        try {
            //查询收件箱里的内容
            Uri outSMSUri = Uri.parse("content://sms/inbox");
            Cursor c = mContext.getContentResolver().query(outSMSUri, null, null, null, "date desc");
            if (c != null) {
                c.moveToNext();
                if (c.moveToFirst()) {
                    String message = c.getString(c.getColumnIndex("body"));      // 短信内容
//                    String phone = c.getString(c.getColumnIndex("address"));     //短信来源号码
                    c.close();
                    if (checkString(message)) {
                        String code = AbStringUtil.filterNumber(message);
                        if (!TextUtils.isEmpty(code)) {
                            strContent = code;
                        }
                        mHandler.sendEmptyMessage(MSG_OUTBOXCONTENT);
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    //指定一个号码，只读取与他有关的新消息。
    private boolean checkPhone(String phone) {
        // TODO Auto-generated method stub
        String c_phone = "10690260615079";           //可修改成不同的号码，看个人情况而定。
        if (c_phone.equals(phone))
            return true;
        return false;
    }

    //指定一关键字符串，用来确认短信内容是我们想要去提取的
    private boolean checkString(String message) {
        if (TextUtils.isEmpty(message))
            return false;
        Pattern p = Pattern.compile("您的验证码是");          //这个关键字符串可以自定义
        Matcher matcher = p.matcher(message);
        if (matcher.find())
            return true;
        return false;
    }

    //匹配短信中的四个数字（验证码）
    private String patternCode(String patternContent) {
        if (TextUtils.isEmpty(patternContent))
            return null;
        Pattern p = Pattern.compile(patternCoder);
        Matcher matcher = p.matcher(patternContent);
        if (matcher.find())
            return matcher.group();
        return null;
    }

    public String getstrContent() {
        return strContent;
    }

}