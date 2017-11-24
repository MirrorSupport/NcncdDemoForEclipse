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
import cn.miao.ncncd.api.HeartRateApi;
import cn.miao.ncncd.api.handle.ApiCallBack;
import cn.miao.ncncd.http.entity.HeartRate;
import cn.miao.ncncd.util.ToastUtil;

/**
 * 上传心率数据界面
 */
public class HeartRateActivity extends AppCompatActivity {

    /*返回按钮*/
    private ImageButton ibClose;

    /*上传的内容*/
    private EditText etAverage;
    private EditText etMax;
    private EditText etMin;
    private EditText etIntervalTime;

    /*加入、上传、显示上传数据*/
    private Button btInsert;
    private Button btnUpload;
    private Button btnReset;
    private TextView tvShow;

    private List<HeartRate> heartRates;

    private ProgressDialog progressDialog;

    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        initBoot();
        initView();
        initData();
        initEvent();
    }

    public void initBoot() {
        heartRates = new ArrayList<HeartRate>();
        stringBuilder = new StringBuilder();
    }

    public void initView() {
        ibClose = (ImageButton) findViewById(R.id.ib_close);

        etAverage = (EditText) findViewById(R.id.et_average);
        etMax = (EditText) findViewById(R.id.et_max);
        etMin = (EditText) findViewById(R.id.et_min);
        etIntervalTime = (EditText) findViewById(R.id.et_interval_time);

        btInsert = (Button) findViewById(R.id.btn_insert);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnReset = (Button) findViewById(R.id.btn_reset);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    public void initData() {

        String intervalTime = etIntervalTime.getText().toString().trim();
        /*添加初始默认的一条数据*/
        insertData("1", "1", "1", intervalTime);

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

                String average = etAverage.getText().toString().trim();
                String max = etMax.getText().toString().trim();
                String min = etMin.getText().toString().trim();
                String intervalTime = etIntervalTime.getText().toString().trim();

                if (average.isEmpty()) {
                    ToastUtil.show(HeartRateActivity.this, "请填写平均浓度");
                    return;
                }
                if (max.isEmpty()) {
                    ToastUtil.show(HeartRateActivity.this, "请填写最大浓度");
                    return;
                }
                if (min.isEmpty()) {
                    ToastUtil.show(HeartRateActivity.this, "请填写最低浓度");
                    return;
                }
                if (intervalTime.isEmpty()) {
                    ToastUtil.show(HeartRateActivity.this, "请填写时间间隔");
                    return;
                }

                insertData(average, max, min, intervalTime);

            }
        });

        /*上传*/
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (heartRates.isEmpty()) {
                    ToastUtil.show(HeartRateActivity.this, "请加入上传数据");
                    return;
                }

                HeartRateApi.uploadHeartRate(HeartRateActivity.this, "18811427233", heartRates, new ApiCallBack() {
                    @Override
                    public void onStart() {
                        //显示ProgressDialog
                        progressDialog = ProgressDialog.show(HeartRateActivity.this, "Loading...", "Please wait...", true, false);

                    }

                    @Override
                    public void onSuccess() {
                        ToastUtil.show(HeartRateActivity.this, "数据上传成功");

                        /*改为初始化状态*/
                        heartRates.clear();
                        stringBuilder = new StringBuilder();
                        etAverage.setText("1");
                        etMax.setText("1");
                        etMin.setText("1");
                        etIntervalTime.setText("1");

                        String intervalTime = etIntervalTime.getText().toString().trim();
                        /*添加初始默认的一条数据*/
                        insertData("1", "1", "1", intervalTime);
                    }

                    @Override
                    public void onFailure(int errNo, String errMsg) {
                        ToastUtil.show(HeartRateActivity.this, errMsg);
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
                heartRates.clear();
                stringBuilder = new StringBuilder();
                etAverage.setText("1");
                etMax.setText("1");
                etMin.setText("1");
                etIntervalTime.setText("1");

                String intervalTime = etIntervalTime.getText().toString().trim();
                /*添加初始默认的一条数据*/
                insertData("1", "1", "1", intervalTime);

            }
        });
    }

    /**
     * 加入数据
     *
     * @param average
     * @param max
     * @param min
     * @param intervalTime
     */
    public void insertData(String average, String max, String min, String intervalTime) {

        long intervalTimeLong = Long.parseLong(intervalTime);
        int beginTime = (int) (System.currentTimeMillis() / 1000 - intervalTimeLong);
        HeartRate heartRate = new HeartRate();
        heartRate.setAverage(Float.parseFloat(average));
        heartRate.setMax(Float.parseFloat(max));
        heartRate.setMin(Float.parseFloat(min));
        heartRate.setBeginTime(beginTime);
        heartRate.setEndTime((int) (System.currentTimeMillis() / 1000));
        heartRates.add(heartRate);

        showContent(heartRate);
    }

    /**
     * 显示上传内容
     *
     * @param heartRate
     */
    public void showContent(HeartRate heartRate) {

        stringBuilder.insert(0, "    平均浓度:" + heartRate.getAverage() + "    最大浓度:" + heartRate.getMax() + "    最低浓度:" + heartRate.getMin() + "    收集时间间隔:" + (heartRate.getEndTime() - heartRate.getBeginTime()) + "\n");
        tvShow.setText(stringBuilder.toString());
    }
}
