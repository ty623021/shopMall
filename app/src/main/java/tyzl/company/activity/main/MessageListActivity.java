package tyzl.company.activity.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import tyzl.company.R;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.weight.TitleView;

/**
 * Created by hjy on 2017/3/8.
 */

public class MessageListActivity extends BaseActivity {

    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_list);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("消息");
        titleView.hiddenSearchButton();
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 跳转到MessageListActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MessageListActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
