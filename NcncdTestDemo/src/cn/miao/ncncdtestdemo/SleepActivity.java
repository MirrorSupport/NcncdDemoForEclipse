package cn.miao.ncncdtestdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.miao.ncncd.api.SleepApi;
import cn.miao.ncncd.api.handle.ApiCallBack;
import cn.miao.ncncd.http.entity.Sleep;
import cn.miao.ncncd.util.ToastUtil;

/**
 * 上传睡眠数据界面
 */
public class SleepActivity extends AppCompatActivity {

    /*返回按钮*/
    private ImageButton ibClose;

    /*上传的内容*/
    private EditText etTotalDuration;
    private EditText etEffectiveDuration;
    private EditText etDeepDuration;
    private EditText etLightDuration;
    private EditText etIntervalTime;

    /*加入、上传、显示上传数据*/
    private Button btInsert;
    private Button btnUpload;
    private Button btnReset;
    private TextView tvShow;

    private List<Sleep> sleeps;

    private ProgressDialog progressDialog;

    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        initBoot();
        initView();
        initData();
        initEvent();
    }

    public void initBoot() {
        sleeps = new ArrayList<Sleep>();
        stringBuilder = new StringBuilder();
    }

    public void initView() {

        ibClose = (ImageButton) findViewById(R.id.ib_close);

        etTotalDuration = (EditText) findViewById(R.id.et_totalDuration);
        etEffectiveDuration = (EditText) findViewById(R.id.et_effectiveDuration);
        etDeepDuration = (EditText) findViewById(R.id.et_deepDuration);
        etLightDuration = (EditText) findViewById(R.id.et_lightDuration);
        etIntervalTime = (EditText) findViewById(R.id.et_interval_time);

        btInsert = (Button) findViewById(R.id.btn_insert);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnReset = (Button) findViewById(R.id.btn_reset);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    public void initData() {

        String intervalTime = etIntervalTime.getText().toString().trim();
        /*添加初始默认的一条数据*/
        insertData("1", "1", "1", "1", intervalTime);

        tvShow.setMovementMethod(ScrollingMovementMethod.getInstance());

    }

    public void initEvent() {

        /*返回*/
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*加入*/
        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String totalDuration = etTotalDuration.getText().toString().trim();
                String effectiveDuration = etEffectiveDuration.getText().toString().trim();
                String deepDuration = etDeepDuration.getText().toString().trim();
                String lightDuration = etLightDuration.getText().toString().trim();
                String intervalTime = etIntervalTime.getText().toString().trim();

                if (totalDuration.isEmpty()) {
                    ToastUtil.show(SleepActivity.this, "请填写总睡眠时长");
                    return;
                }
                if (effectiveDuration.isEmpty()) {
                    ToastUtil.show(SleepActivity.this, "请填写有效睡眠时长");
                    return;
                }
                if (deepDuration.isEmpty()) {
                    ToastUtil.show(SleepActivity.this, "请填写深度睡眠时长");
                    return;
                }
                if (lightDuration.isEmpty()) {
                    ToastUtil.show(SleepActivity.this, "请填写浅睡眠时长");
                    return;
                }
                if (intervalTime.isEmpty()) {
                    ToastUtil.show(SleepActivity.this, "请填写时间间隔");
                    return;
                }

                insertData(totalDuration, effectiveDuration, deepDuration, lightDuration, intervalTime);
            }
        });

        /*上传*/
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sleeps.isEmpty()) {
                    ToastUtil.show(SleepActivity.this, "请加入上传数据");
                    return;
                }

                SleepApi.uploadSleep(SleepActivity.this, "18811427233", sleeps, new ApiCallBack() {
                    @Override
                    public void onStart() {
                        //显示ProgressDialog
                        progressDialog = ProgressDialog.show(SleepActivity.this, "Loading...", "Please wait...", true, false);

                    }

                    @Override
                    public void onSuccess() {
                        ToastUtil.show(SleepActivity.this, "数据上传成功");

                        /*改为初始化状态*/
                        sleeps.clear();
                        stringBuilder = new StringBuilder();
                        etTotalDuration.setText("1");
                        etEffectiveDuration.setText("1");
                        etDeepDuration.setText("1");
                        etLightDuration.setText("1");
                        etIntervalTime.setText("1");

                        String intervalTime = etIntervalTime.getText().toString().trim();
                         /*添加初始默认的一条数据*/
                        insertData("1", "1", "1", "1", intervalTime);
                    }

                    @Override
                    public void onFailure(int errNo, String errMsg) {
                        ToastUtil.show(SleepActivity.this, errMsg);
                    }

                    @Override
                    public void onNetError() {
                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                    }
                });
            }
        });

          /*重置*/
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /*改为初始化状态*/
                sleeps.clear();
                stringBuilder = new StringBuilder();
                etTotalDuration.setText("1");
                etEffectiveDuration.setText("1");
                etDeepDuration.setText("1");
                etLightDuration.setText("1");
                etIntervalTime.setText("1");

                String intervalTime = etIntervalTime.getText().toString().trim();
                         /*添加初始默认的一条数据*/
                insertData("1", "1", "1", "1", intervalTime);

            }
        });
    }

    /**
     * 加入数据
     *
     * @param totalDuration
     * @param effectiveDuration
     * @param deepDuration
     * @param lightDuration
     * @param intervalTime
     */
    public void insertData(String totalDuration, String effectiveDuration, String deepDuration, String lightDuration, String intervalTime) {
        long intervalTimeLong = Long.parseLong(intervalTime);
        int beginTime = (int) (System.currentTimeMillis() / 1000 - intervalTimeLong);
        Sleep sleep = new Sleep();
        sleep.setTotalDuration(Integer.parseInt(totalDuration));
        sleep.setEffectiveDuration(Integer.parseInt(effectiveDuration));
        sleep.setDeepDuration(Integer.parseInt(deepDuration));
        sleep.setLightDuration(Integer.parseInt(lightDuration));
        sleep.setBeginTime(beginTime);
        sleep.setEndTime((int) (System.currentTimeMillis() / 1000));
        sleeps.add(sleep);

        showContent(sleep);
    }

    /**
     * 显示上传内容
     *
     * @param sleep
     */
    public void showContent(Sleep sleep) {
        stringBuilder.insert(0, "    总睡眠时长:" + sleep.getTotalDuration() + "    有效睡眠时长:" + sleep.getEffectiveDuration() + "    深度睡眠时长:" + sleep.getDeepDuration() + "    浅睡眠时长:" + sleep.getLightDuration() + "    收集时间间隔:" + (sleep.getEndTime() - sleep.getBeginTime()) + "\n");
        tvShow.setText(stringBuilder.toString());
    }
}
