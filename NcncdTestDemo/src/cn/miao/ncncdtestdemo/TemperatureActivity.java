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
import cn.miao.ncncd.api.TemperatureApi;
import cn.miao.ncncd.api.handle.ApiCallBack;
import cn.miao.ncncd.http.entity.Temperature;
import cn.miao.ncncd.util.ToastUtil;

/**
 * 上传体温数据界面
 */
public class TemperatureActivity extends AppCompatActivity {

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

    private List<Temperature> temperatures;

    private ProgressDialog progressDialog;

    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        initBoot();
        initView();
        initData();
        initEvent();
    }

    public void initBoot() {
        temperatures = new ArrayList<Temperature>();
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
                    ToastUtil.show(TemperatureActivity.this, "请填写平均浓度");
                    return;
                }
                if (max.isEmpty()) {
                    ToastUtil.show(TemperatureActivity.this, "请填写最大浓度");
                    return;
                }
                if (min.isEmpty()) {
                    ToastUtil.show(TemperatureActivity.this, "请填写最小浓度");
                    return;
                }
                if (intervalTime.isEmpty()) {
                    ToastUtil.show(TemperatureActivity.this, "请填写时间间隔");
                    return;
                }

                insertData(average, max, min, intervalTime);
            }
        });

        /*上传*/
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (temperatures.isEmpty()) {
                    ToastUtil.show(TemperatureActivity.this, "请加入上传数据");
                    return;
                }

                TemperatureApi.uploadTemperature(TemperatureActivity.this, "18811427233", temperatures, new ApiCallBack() {
                    @Override
                    public void onStart() {
                        //显示ProgressDialog
                        progressDialog = ProgressDialog.show(TemperatureActivity.this, "Loading...", "Please wait...", true, false);

                    }

                    @Override
                    public void onSuccess() {
                        ToastUtil.show(TemperatureActivity.this, "数据上传成功");

                        /*改为初始化状态*/
                        temperatures.clear();
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
                        ToastUtil.show(TemperatureActivity.this, errMsg);
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
                temperatures.clear();
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
     * 加入数据并显示上传内容
     *
     * @param average
     * @param max
     * @param min
     * @param intervalTime
     */
    public void insertData(String average, String max, String min, String intervalTime) {

        long intervalTimeLong = Long.parseLong(intervalTime);
        int beginTime = (int) (System.currentTimeMillis() / 1000 - intervalTimeLong);
        Temperature temperature = new Temperature();
        temperature.setAverage(Float.parseFloat(average));
        temperature.setMax(Float.parseFloat(max));
        temperature.setMin(Float.parseFloat(min));
        temperature.setBeginTime(beginTime);
        temperature.setEndTime((int) (System.currentTimeMillis() / 1000));
        temperatures.add(temperature);

        showContent(temperature);
    }

    /**
     * 加入数据并显示上传内容
     *
     * @param temperature
     */
    public void showContent(Temperature temperature) {

        stringBuilder.insert(0, "    平均浓度:" + temperature.getAverage() + "    最大浓度:" + temperature.getMax() + "    最低浓度:" + temperature.getMin() + "    收集时间间隔:" + (temperature.getEndTime() - temperature.getBeginTime()) + "\n");
        tvShow.setText(stringBuilder.toString());
    }
}
