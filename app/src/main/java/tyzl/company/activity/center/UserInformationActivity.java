package tyzl.company.activity.center;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import tyzl.company.R;
import tyzl.company.entity.UserInfomationInfo;
import tyzl.company.glide.GlideImgManager;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.utils.DialogUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.TitleView;


/**
 * 用户个人信息
 * Created by hjy on 2017/3/9.
 */

public class UserInformationActivity extends BaseActivity implements GlideImgManager.OnGlideSuccessListener {

    private TitleView titleView;
    private UserInformationActivity mActivity;
    private RelativeLayout said;
    private RelativeLayout head_img;
    private static final int TAKE_IMAGE_REQUEST_CODE = 101;//拍照
    private static final int REQUEST_CODE_GALLERY = 200;//打开相册
    private static final int REQUEST_CODE_EDIT = 201;//裁剪

    private FunctionConfig config;
    private GalleryFinal.OnHanlderResultCallback onCallback;
    private ImageView mImageView;
    private View view;
    private View main;
    private TextView taking_pictures;
    private TextView choose_local;
    private PopupWindow window;
    private RelativeLayout time;
    private TextView startDateTime;
    private TextView cancel;
    private RelativeLayout gender;
    private TextView tv_gender;
    private ChooseGenderActivity chooseGenderActivity;
    private UserInfomationInfo info;
    private TextView tv_said;
    private String timeStr;
    private int mYear;
    private int mMonth, mDay;
    final int DATE_DIALOG = 1;
    private View loading;
    private ProgressBar roundProgress;
    private GlideImgManager glideImgManager;
    private DatePickerDialog dialog;
    private final String IMAGE_STORE_FILE_NAME = "IMG_%s.jpg";
    private File mImageStoreDir;
    private File mImageStoreCropDir;
    private String mImagePath;//拍摄照片路径


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_information);
        mActivity = this;
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("个人信息");
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
     * 跳转到UserInformationActivity
     */

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserInformationActivity.class);
        context.startActivity(intent);
    }



    @Override

    protected void initView() {
        said = (RelativeLayout) findViewById(R.id.said);
        head_img = (RelativeLayout) findViewById(R.id.head_img);
        mImageView = (ImageView) findViewById(R.id.imageView);
        main = findViewById(R.id.main);
        time = (RelativeLayout) findViewById(R.id.time);
        startDateTime = (TextView) findViewById(R.id.set_time);
        gender = (RelativeLayout) findViewById(R.id.gender);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_said = (TextView) findViewById(R.id.tv_said);
        chooseGenderActivity = new ChooseGenderActivity();
        glideImgManager = new GlideImgManager();
        glideImgManager.setOnGlideSuccessListener(this);
        initTime();
        dialog = new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressTitle = null;
        getUserInformationHttp();
    }

    /**
     * 从本地获取图片
     */
    private void getPhoto() {
        dismissWindow();
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, config, onCallback);
    }

    /**
     * 拍照
     */
    private void takingPhoto() {
        dismissWindow();
        openCamera();
    }


    @Override
    protected void setData() {
        mImageStoreDir = new File(Environment.getExternalStorageDirectory(), "/DCIM/RxGalleryFinal/");
        mImageStoreCropDir = new File(mImageStoreDir, "crop");
        if (!mImageStoreCropDir.exists()) {
            mImageStoreCropDir.mkdirs();
        }
        onCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
                if (requestCode == REQUEST_CODE_GALLERY) {
                    PhotoInfo info = resultList.get(0);
                    String photoPath = info.getPhotoPath().toString();
                    GalleryFinal.openEdit(REQUEST_CODE_EDIT, photoPath, onCallback);
                } else if (requestCode == REQUEST_CODE_EDIT) {
                    PhotoInfo photoInfo = resultList.get(0);
                    String photoPath = photoInfo.getPhotoPath().toString();
                    compress(photoPath);
                }

            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                AbToastUtil.showToast(context,errorMsg);

            }
        };
        config = new FunctionConfig.Builder().setMutiSelectMaxSize(1).build();

    }

    /**
     * 压缩图片
     *
     * @param photoPath
     */
    private void compress(String photoPath) {
        loading = View.inflate(this, R.layout.up_loading_img, null);
        roundProgress = (ProgressBar) loading.findViewById(R.id.loading_bar);
        DialogUtil.showAlertDialog(loading);
        File file = new File(photoPath);
        AbLogUtil.e("file1", file.length() + "");
        Luban.compress(context, file)       // 初始化Luban，并传入要压缩的图片
                .putGear(Luban.THIRD_GEAR)      // 设定压缩模式，默认 THIRD_GEAR
                .launch(new OnCompressListener() {  // 启动压缩并设置监听
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        AbLogUtil.e("file2", file.length() + "");
                        upLoadImg(file);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * 下载文件
     * @param file
     */

    private void upLoadImg(File file) {
        if (file.exists()) {
            http.upLoadPost(Config.URL_UPICON, "head_pic_img", file, params, new RequestListener() {
                @Override
                public void requestSuccess(String json) {
                    AbLogUtil.e("response", json);
                    if (AbJsonUtil.isSuccess(json)) {
                        getUserInformationHttp();
                    }
                }

                @Override
                public void requestError(String message) {
                    AbLogUtil.e("onFailure", message);
                }

                @Override
                public void inProgress(float progress, long total, int id) {
                    super.inProgress(progress, total, id);
//                    progress = progress * 100;
//                    roundProgress.setProgress((int) progress);
                }
            });
        } else {
            AbToastUtil.showToast(context, "文件不存在!");
        }
    }

    /**
     * 弹框消失
     */
    private void dismissWindow() {
        if (window != null) {
            window.dismiss();
        }
    }

    /**
     * 显示的弹框
     */
    private void showPopWindow() {
//        // TODO Auto-generated method stub
        view = LayoutInflater.from(context).inflate(R.layout.upload_photos_popwindow, null);
        taking_pictures = (TextView) view.findViewById(R.id.taking_pictures);
        choose_local = (TextView) view.findViewById(R.id.local);
        cancel = (TextView) view.findViewById(R.id.cancel);
        choose_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });
        taking_pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takingPhoto();
            }
        });

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        backgroundAlpha(0.5f);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    //初始化系统的当前时间
    private void initTime() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void setEvent() {
        said.setOnClickListener(this);
        head_img.setOnClickListener(this);
        time.setOnClickListener(this);
        gender.setOnClickListener(this);

    }

    /**
     * 获取个人信息
     */
    private void getUserInformationHttp() {
        http.post(Config.URL_GETUSERINFO, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json", json);
                if (AbJsonUtil.isSuccess(json)) {
                    info = (UserInfomationInfo) AbJsonUtil.fromJson(json, UserInfomationInfo.class);
                    if (info != null) {
                        progressTitle = null;
                        setValue();
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });


    }

    /**
     * 设置个人资料的一些信息
     */
    private void setValue() {
        tv_gender.setText(info.getSex());
        tv_said.setText(info.getNickname());
        startDateTime.setText(info.getBirthday());
        glideImgManager.glideLoader(info.gethead_pic(), R.drawable.ic_gf_camera, R.drawable.ic_gf_camera, mImageView, 0);
    }

    /**
     * 设置日期的对话框
     *
     * @param id
     * @return
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return dialog;
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */

    public void display() {
        setBirthdayTimeHttp();

    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();

        }
    };

    /**
     * 设置出生日期的时间
     */
    private void setBirthdayTimeHttp() {
        timeStr = (mYear + "-" + (mMonth + 1) + "-" + mDay);
        params.put("birthday", timeStr);
        http.post(Config.URL_UPUSERINFO, params, "正在设置...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    AbLogUtil.e("json", json);
                    info = (UserInfomationInfo) AbJsonUtil.fromJson(json, UserInfomationInfo.class);
                    if (info != null) {
                        AbToastUtil.showToast(context, "设置成功");
                        startDateTime.setText(timeStr);
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.equals(said)) {
            ChangeSaidActivity.startActivity(context);
        } else if (v.equals(head_img)) {
            showPopWindow();
        } else if (v.equals(time)) {
            showDialog(DATE_DIALOG);
        } else if (v.equals(gender)) {
            if (info != null) {
                ChooseGenderActivity.startActivity(context, info.getSex());
            } else {
                ChooseGenderActivity.startActivity(context);
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onGlideSuccessListener() {
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            DialogUtil.dismiss();
            return false;
        }
    });

    /**
     * @Author:Karl-dujinyang 兼容7.0打开路径问题
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(this.getPackageManager()) != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            String filename = String.format(IMAGE_STORE_FILE_NAME, dateFormat.format(new Date()));
            File fileImagePath = new File(mImageStoreDir, filename);
            mImagePath = fileImagePath.getAbsolutePath();
            if (mImagePath != null) {
                /*获取当前系统的android版本号*/
                int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentApiVersion < 24) {
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagePath));
                    startActivityForResult(captureIntent, TAKE_IMAGE_REQUEST_CODE);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, mImagePath);
                    Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(captureIntent, TAKE_IMAGE_REQUEST_CODE);
                }
            }
        } else {
            AbToastUtil.showToast(this, "相机不可用");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            GalleryFinal.openEdit(REQUEST_CODE_EDIT, mImagePath, onCallback);
        }
    }


}
