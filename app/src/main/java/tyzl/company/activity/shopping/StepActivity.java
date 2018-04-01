package tyzl.company.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.StepAdapter;
import tyzl.company.entity.ShoppingCartInfo;
import tyzl.company.entity.StepInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.weight.TitleView;


/****
 * 查看物流
 */
public class StepActivity extends BaseActivity {

    private TitleView titleView;
    private ShoppingCartInfo info;
    private ListView listView;
    private StepAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_vertical_stepview);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    /**
     * 跳转到 StepActivity
     *
     * @param context
     */
    public static void startActivity(Context context, ShoppingCartInfo info) {
        Intent intent = new Intent(context, StepActivity.class);
        intent.putExtra("info", info);
        context.startActivity(intent);
    }

    /**
     * 跳转到 StepActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StepActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void initView() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("查看物流");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        listView = (ListView) findViewById(R.id.listView);
        info = (ShoppingCartInfo) getIntent().getSerializableExtra("info");
    }


    @Override
    protected void setData() {
        // TODO Auto-generated method stub

        List<StepInfo> list0 = new ArrayList<>();
        list0.add(new StepInfo("2017-03-15 12:00", "感谢你在京东购物，欢迎你下次光临！"));
        list0.add(new StepInfo("2017-03-14 12:00", "配送员【包牙齿】已出发，联系电话【130-0000-0000】，感谢您的耐心等待，参加评价还能赢取好多礼物哦"));
        list0.add(new StepInfo("2017-03-13 12:00", "您的订单在京东【北京中关村大厦站】验货完成，正在分配配送员"));
        list0.add(new StepInfo("2017-03-12 12:00", "您的订单在京东【北京通州分拣中心】发货完成，准备送往京东【北京中关村大厦站】"));
        list0.add(new StepInfo("2017-03-11 12:00", "您的订单在京东【北京通州分拣中心】分拣完成"));
        list0.add(new StepInfo("2017-03-10 12:00", "您的订单在京东【华东外单分拣中心】发货完成，准备送往京东【北京通州分拣中心】"));
        list0.add(new StepInfo("2017-03-09 12:00", "打包成功"));
        list0.add(new StepInfo("2017-03-08 12:00", "扫描员已经扫描"));
        list0.add(new StepInfo("2017-03-07 12:00", "您的订单已拣货完成"));
        list0.add(new StepInfo("2017-03-06 12:00", "您的订单已打印完毕"));
        list0.add(new StepInfo("2017-03-05 12:00", "您的订单预计6月23日送达您的手中，618期间促销火爆，可能影响送货时间，请您谅解，我们会第一时间送到您的手中"));
        list0.add(new StepInfo("2017-03-04 12:00", "您的订单已经进入亚洲第一仓储中心1号库准备出库"));
        list0.add(new StepInfo("2017-03-03 12:00", "您的商品需要从外地调拨，我们会尽快处理，请耐心等待"));
        list0.add(new StepInfo("2017-03-02 12:00", "您已提交定单，等待系统确认"));

        adapter = new StepAdapter(this, R.layout.step_view_item,list0);
        listView.setAdapter(adapter);
    }


    @Override
    protected void setEvent() {

    }


    @Override
    public void onClick(View v) {

    }
}

