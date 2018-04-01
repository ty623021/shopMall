package tyzl.company.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.SearchAdapter;
import tyzl.company.entity.SearchInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.weight.TitleView;

/**
 * Created by hjy on 2016/11/5.
 * 搜索
 */
public class SearchActivity extends BaseActivity {

    private TitleView titleView;
    private List<SearchInfo> list = new ArrayList<>();
    private TagFlowLayout search_history;
    private TagFlowLayout search_hot;
    private ListView listView;
    private SearchAdapter adapter;
    private RelativeLayout search_relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    /**
     * 跳转到 SearchActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleView.showSearchEditButton(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (!AbStringUtil.isEmpty(string)) {
                    search_relative.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    changeData(string);
                } else {
                    listView.setVisibility(View.GONE);
                    search_relative.setVisibility(View.VISIBLE);
                }
            }
        });

        titleView.setRightTextButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.showRightTextButton();
    }

    @Override
    protected void initView() {
        search_relative = (RelativeLayout) findViewById(R.id.search_relative);
        search_history = (TagFlowLayout) findViewById(R.id.search_history);
        search_hot = (TagFlowLayout) findViewById(R.id.search_hot);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new SearchAdapter(context,R.layout.search_item_layout, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void setData() {
        final ArrayList<String> history_data = new ArrayList();
        for (int i = 1; i < 10; i++) {
            history_data.add("手机" + (i * 100));
        }
        final ArrayList<String> hot_data = new ArrayList();
        for (int i = 1; i < 8; i++) {
            hot_data.add("笔记本" + (i * 50));
        }
        search_history.setAdapter(new TagAdapter<String>(history_data) {
            @Override
            public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s) {
                TextView bt = (TextView) View.inflate(context, R.layout.search_hos_item, null);
                bt.setText(s);
                return bt;
            }
        });
        search_hot.setAdapter(new TagAdapter<String>(hot_data) {
            @Override
            public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s) {
                TextView bt = (TextView) View.inflate(context, R.layout.search_hos_item, null);
                bt.setText(s);
                return bt;
            }
        });

        search_history.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                titleView.setSearchEditContent(history_data.get(position));
                return true;
            }

        });

        search_hot.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                titleView.setSearchEditContent(hot_data.get(position));
                return true;
            }

        });
    }

    private void changeData(String string) {
        if (list.size() > 0) {
            list.clear();
        }
        for (int i = 0; i < 10; i++) {
            SearchInfo info = new SearchInfo();
            info.setName(string + (i + 10));
            list.add(info);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}
