package tyzl.company.fragment.mainFragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.ShoppingCarRecyclerViewAdapter;
import tyzl.company.entity.Respond;
import tyzl.company.entity.Results;
import tyzl.company.entity.ShopCarModel;
import tyzl.company.entity.ShopCartInfo;
import tyzl.company.entity.Status;
import tyzl.company.global.Config;
import tyzl.company.main.BaseFragment;
import tyzl.company.pullview.AbPullToRefreshView;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.utils.MyShareSdk;
import tyzl.company.volley.RequestListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by geek on 2016/11/1.
 * 购物车
 */
public class ShoppingFragment extends BaseFragment implements  SwipeRefreshLayout.OnRefreshListener ,AbPullToRefreshView.OnHeaderRefreshListener,AbPullToRefreshView.OnFooterLoadListener {

    private View view;
    private FragmentActivity mActivity;
    private List<ShopCartInfo>  list=new ArrayList<>();
    private Button go_pay;
  //  private SwipeRefreshLayout swipe;
    private TextView tv_edite,tv_allprice;
    private View settlementView,editeView;
    private RecyclerView recyclerView;
    private View layout_all_pay,layout_all_edite;
    private ImageView img_pay_all,img_edite_all;
    private  ShoppingCarRecyclerViewAdapter adapter;
    private Button btn_delete,btn_share;
    private  ImageView img_networkException,img_noData;
    private View layout_Loding;
    private ProgressBar progressBar;
    private MyShareSdk shareSdk;
    private AbPullToRefreshView abPullToRefreshView;
    private int nextPage=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_shopping_car, container, false);
        mActivity = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {
        Log.e("tag",toJson());
       Respond respond=  parse(toJson(),new TypeToken<Respond<Results>>(){});
       // Respond respond=  parse(toJson(),Respond);

        if (respond==null){
            Log.e("tag","respond==null");
          } else {
            Log.e("tag",respond.getStatus());
            Log.e("tag",respond.getDate());
            List<Results>  list= respond.getResults();
            for (Results results:list){
                Log.e("tag",results.getCurrentCity());
            }
            /**
             * 1
             */
        }


    }
    static Gson gson=new Gson();
// Gson gson=new Gson();
   // TypeToken typeToken1=  new TypeToken<ShopCarModel>(){};
    public String toJson(){
        Status status=new Status();
        status.setStatus("success");
        status.setError("0");
        status.setDate("2016-12-12");
        List<Results> list=new ArrayList<>();
        for (int i=0;i<2;i++){
            Results results=new Results();
            results.setCurrentCity("上海");
            list.add(results);
        }
        status.setResults(list);
//        ArrayList<ShopCarModel> arrayList=new ArrayList<ShopCarModel>();
//        for (int i=0;i<2;i++){
//            ShopCarModel shopCarModel=new ShopCarModel();
//            shopCarModel.setGoods_name("冰箱");
//            shopCarModel.setGoods_num(12);
//            arrayList.add(shopCarModel);
//        }
       // gson.toJson(arrayList);
       return  gson.toJson(status);



    }
    public   <T> T parse(String result,TypeToken typeToken){

      return gson.fromJson(result,typeToken.getType());
    }

    @Override
    protected void initView() {
        abPullToRefreshView=(AbPullToRefreshView)view.findViewById(R.id.refresh_pull);
        tv_edite=(TextView)view.findViewById(R.id.tv_edite);
        tv_allprice=(TextView)view.findViewById(R.id.tv_allprice);
        settlementView=view.findViewById(R.id.settlement_layout);
        editeView=view.findViewById(R.id.edite_layout);
       // swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        recyclerView=(RecyclerView)view.findViewById(R.id.rv);
        go_pay = (Button) view.findViewById(R.id.go_pay);
        btn_delete=(Button)view.findViewById(R.id.btn_delete);
        btn_share=(Button)view.findViewById(R.id.btn_share);
        layout_all_pay=view.findViewById(R.id.layout_all_pay);
        layout_all_edite=view.findViewById(R.id.layout_all_edite);
        img_pay_all=(ImageView)view.findViewById(R.id.img_pay_all) ;
        img_edite_all=(ImageView)view.findViewById(R.id.img_edite_all) ;
        layout_Loding=view.findViewById(R.id.loading_relative);
        img_networkException=(ImageView)view.findViewById(R.id.network_img);
        img_noData=(ImageView)view.findViewById(R.id.nodata_img);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
       // swipe.setOnRefreshListener(this);
       // swipe.setColorSchemeResources(R.color.button_color);
       // swipe.setBackgroundResource(R.color.white);
       // swipe.setEnabled(false);
        layout_all_pay.setTag(false);
        layout_all_pay.setOnClickListener(this);
        layout_all_edite.setTag(false);
        layout_all_edite.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);


    }


    @Override
    protected void setData() {
        //AbSwipeRefresh.setSwipeRefresh(context, swipe, this);
        /**
         *
         */
       // layout_Loding.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new   LinearLayoutManager(mActivity);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

       // recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//               if (newState==RecyclerView.SCROLL_STATE_IDLE){
//                   Log.e("tag","停止滚动");
//
//               }else if (newState==RecyclerView.SCROLL_STATE_DRAGGING){
//                   Log.e("tag","拖拽");
//
//               }else if(newState==RecyclerView.SCROLL_STATE_SETTLING){
//                   Log.e("tag","自动滚动开始");
//
//               }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

        for (int i=0;i<30;i++){
            ShopCartInfo shoppingCartInfo=new ShopCartInfo();
//            shoppingCartInfo.setDiscrip("姿兰气垫cc霜"+ String.valueOf(i));
//            shoppingCartInfo.setService("专柜联保售后"+ String.valueOf(i));
//            shoppingCartInfo.setColor("颜色: 白色  尺码: 33"+ String.valueOf(i));
//            shoppingCartInfo.setPrice("10.18");
//            shoppingCartInfo.setNumber("3");
            list.add(shoppingCartInfo);

        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new ShoppingCarRecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterCallBackListner(new ShoppingCarRecyclerViewAdapter.OnAdapterCallBackListener() {
            @Override
            public void getAllPriceListner(String allPrice, boolean isShowSelect) {
                SpannableString spanText = new SpannableString("合计: "+allPrice);
                spanText.setSpan(new ForegroundColorSpan(Color.RED), 4,
                        spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_allprice.setText(spanText);
                if (isShowSelect){
                    layout_all_pay.setTag(true);
                    img_pay_all.setBackgroundResource(R.mipmap.shopping_select);
                    layout_all_edite.setTag(true);
                    img_edite_all.setBackgroundResource(R.mipmap.shopping_select);
                }else {
                    layout_all_pay.setTag(false);
                    img_pay_all.setBackgroundResource(R.mipmap.shopping_normal);
                    layout_all_edite.setTag(false);
                    img_edite_all.setBackgroundResource(R.mipmap.shopping_normal);
                }

            }
        });
        AbRefreshUtil.hintViewWithRecyclerAdapter(false, adapter, layout_Loding, img_noData, img_networkException,progressBar);
     // sendHttp(true);

    }

    private void sendHttp(final boolean isFirstLoad) {
        if (isFirstLoad){
            nextPage=1;
        }else {
            nextPage++;
        }
        params.put("nextPage", String.valueOf(nextPage));
        http.post(context, Config.CART_LIST_URL, params,progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json);
                abPullToRefreshView.onHeaderRefreshFinish();
                abPullToRefreshView.onFooterLoadFinish();
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<ShopCartInfo>>() {};
                    list = (List<ShopCartInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    if (isFirstLoad){
                       // nextPage=1;
                        adapter.set(list);
                        if (list.size()<10){abPullToRefreshView.setLoadMoreEnable(false);}
                    }else {
                        //nextPage++;
                        if (list==null||list.size()==0){
                            AbToastUtil.showToast(context, R.string.load_finish);
                            nextPage--;
                            return;
                        }
                        adapter.addAll(list);
                    }

//                    //fromJson   将json转化为对象.
//                  //  orderSumInfo = (OrderSumInfo) AbJsonUtil.fromJsonMall(json, "total_price", OrderSumInfo.class);
//                    if (AbStringUtil.isListEmpty(cartList)) {
//
//                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
              AbRefreshUtil.hintViewWithRecyclerAdapter(false, adapter, layout_Loding, img_noData, img_networkException,progressBar);
               // AbRefreshUtil.hintView(adapter, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                abPullToRefreshView.onHeaderRefreshFinish();
                abPullToRefreshView.onFooterLoadFinish();
                if (isFirstLoad){
                    AbRefreshUtil.hintViewWithRecyclerAdapter(true, adapter, layout_Loding, img_noData, img_networkException,progressBar);
                }else {
                    nextPage--;
                }
               // swipe.setRefreshing(false);
               // AbRefreshUtil.hintViewWithRecyclerAdapter(true, adapter, layout_Notice, layout_Nodata, networkException);

                AbToastUtil.showToast(context, message);
            }
        });
    }

    @Override
    protected void setEvent() {
        go_pay.setOnClickListener(this);
        tv_edite.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      if(v.equals(tv_edite)){
            String tvString=tv_edite.getText().toString() ;
            if ("编辑".equals(tvString)){
                tv_edite.setText("完成");
                settlementView.setVisibility(View.GONE);
            }else if ("完成".equals(tvString)){
                tv_edite.setText("编辑");
                settlementView.setVisibility(View.VISIBLE);
            }
        }else if(v.equals(layout_all_pay)){
          //结算页
          if((boolean)layout_all_pay.getTag()){
              adapter.quiteSelectAll();
          }else{
              adapter.selectAll();
          }
        }else if (v.equals(layout_all_edite)){
          //编辑页
          if((boolean)layout_all_edite.getTag()){
              adapter.quiteSelectAll();
          }else{
              adapter.selectAll();
          }
      }else  if(v.equals(btn_delete)){
          if (adapter.getItemCount()==0){
              AbToastUtil.showToast(mActivity,"购物车是空的，快去添加吧");
              return;
          }
          if (!adapter.isItemSelect()){
              AbToastUtil.showToast(mActivity,"你还没有选择商品哦");
              return;
          }
          AlertDialog.Builder alert=new AlertDialog.Builder(mActivity);
          alert.setTitle("确认删除所选商品吗?")
                  .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
//                          recyclerView.scrollToPosition(0);
                          adapter.removeItem();
                      }
                  }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

              }
          }).show();

      }else if(v.equals(btn_share)){
       final WindowManager.LayoutParams params= mActivity.getWindow().getAttributes();
          params.alpha=0.7f;
          mActivity.getWindow().setAttributes(params);
          PopupWindow popupWindow=new PopupWindow();
          View shareView=LayoutInflater.from(mActivity).inflate(R.layout.share_dailog,null);
          View wechat_friends=shareView.findViewById(R.id.wechat_friends);
          View wechat_circle=shareView.findViewById(R.id.wechat_circle);
          View qq_friends=shareView.findViewById(R.id.qq_friends);
          View sinaweibo_friends=shareView.findViewById(R.id.sinaweibo_friends);
          View qq_zone=shareView.findViewById(R.id.qq_zone);
          View qrcode=shareView.findViewById(R.id.qrcode);
          wechat_friends.setOnClickListener(this);
          wechat_circle.setOnClickListener(this);
          qq_friends.setOnClickListener(this);
          sinaweibo_friends.setOnClickListener(this);
          qq_zone.setOnClickListener(this);
          qrcode.setOnClickListener(this);
          popupWindow.setContentView(shareView);
          popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
          popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
          popupWindow.setFocusable(true);
          popupWindow.setAnimationStyle(R.style.my_popshow_anim_style);
          ColorDrawable dw = new ColorDrawable(0xb0000000);
          popupWindow.setBackgroundDrawable(dw);
          popupWindow.showAtLocation(view.findViewById(R.id.main), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
          popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
              @Override
              public void onDismiss() {
                  params.alpha=1f;
                  mActivity.getWindow().setAttributes(params);
              }
          });
      }else {
          if (shareSdk == null) {
              shareSdk = new MyShareSdk(context);
          }
          int id=v.getId();
          if (id==R.id.wechat_friends){
              shareSdk.showShare("Wechat", "小诸葛","http://www.xzgjf.com/", "content");
          }else  if (id==R.id.wechat_circle){
              shareSdk.showShare("WechatMoments", "小诸葛","http://www.xzgjf.com/", "content");
          }else  if (id==R.id.qq_friends){
              shareSdk.showShare("QQ", "小诸葛","http://www.xzgjf.com/", "content");
          }else  if (id==R.id.sinaweibo_friends){
              shareSdk.showShare("SinaWeibo", "小诸葛","http://www.xzgjf.com/", "content");
          }else  if (id==R.id.qq_zone){
              shareSdk.showShare("QZone", "小诸葛","http://www.xzgjf.com/", "content");
          }else  if (id==R.id.qrcode){
              AbToastUtil.showToast(mActivity,"二维码");
          }
      }
    }
    @Override
    public void onRefresh() {

        //swipe.setRefreshing(false);
    }


    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
       sendHttp(false);
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
     sendHttp(true);
    }
}
