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
import cn.miao.ncncd.api.HealthApi;
import cn.miao.ncncd.api.handle.ApiCallBack;
import cn.miao.ncncd.http.entity.Health;
import cn.miao.ncncd.util.ToastUtil;

/**
 * 上传健康数据界面
 */
public class HealthActivity extends AppCompatActivity {

    /*返回按钮*/
    private ImageButton ibClose;

    /*上传的内容*/
    private EditText etBmi;
    private EditText etBodyFat;
    private EditText etMuscleRate;
    private EditText etMoistureRate;
    private EditText etBmd;
    private EditText etBmr;

    /*加入、上传、显示上传数据*/
    private Button btInsert;
    private Button btnUpload;
    private Button btnReset;
    private TextView tvShow;

    private List<Health> healths;

    private ProgressDialog progressDialog;

    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        initBoot();
        initView();
        initData();
        initEvent();
    }

    public void initBoot() {
        healths = new ArrayList<Health>();
        stringBuilder = new StringBuilder();
    }

    public void initView() {

        ibClose = (ImageButton) findViewById(R.id.ib_close);

        etBmi = (EditText) findViewById(R.id.et_bmi);
        etBodyFat = (EditText) findViewById(R.id.et_bodyFat);
        etMuscleRate = (EditText) findViewById(R.id.et_muscleRate);
        etMoistureRate = (EditText) findViewById(R.id.et_moistureRate);
        etBmd = (EditText) findViewById(R.id.et_bmd);
        etBmr = (EditText) findViewById(R.id.et_bmr);

        btInsert = (Button) findViewById(R.id.btn_insert);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnReset = (Button) findViewById(R.id.btn_reset);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    public void initData() {

        /*添加初始默认的一条数据*/
        insertData("1", "1", "1", "1", "1", "1");

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

                String bmd = etBmd.getText().toString().trim();
                String bmi = etBmi.getText().toString().trim();
                String bmr = etBmr.getText().toString().trim();
                String bodyFat = etBodyFat.getText().toString().trim();
                String moistureRate = etMoistureRate.getText().toString().trim();
                String muscleRate = etMuscleRate.getText().toString().trim();

                if (bmi.isEmpty()) {
                    ToastUtil.show(HealthActivity.this, "请填写bmi指数");
                    return;
                }
                if (bodyFat.isEmpty()) {
                    ToastUtil.show(HealthActivity.this, "请填写体脂率");
                    return;
                }
                if (muscleRate.isEmpty()) {
                    ToastUtil.show(HealthActivity.this, "请填写肌肉率");
                    return;
                }
                if (moistureRate.isEmpty()) {
                    ToastUtil.show(HealthActivity.this, "请填写水分");
                    return;
                }
                if (bmd.isEmpty()) {
                    ToastUtil.show(HealthActivity.this, "请填写骨密度");
                    return;
                }
                if (bmr.isEmpty()) {
                    ToastUtil.show(HealthActivity.this, "请填写基本代谢率");
                    return;
                }

                insertData(bmd, bmi, bmr, bodyFat, moistureRate, muscleRate);
            }
        });

        /*上传*/
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (healths.isEmpty()) {
                    ToastUtil.show(HealthActivity.this, "请加入上传数据");
                    return;
                }

                HealthApi.uploadHealth(HealthActivity.this, "18811427233", healths, new ApiCallBack() {
                    @Override
                    public void onStart() {
                        //显示ProgressDialog
                        progressDialog = ProgressDialog.show(HealthActivity.this, "Loading...", "Please wait...", true, false);

                    }

                    @Override
                    public void onSuccess() {
                        ToastUtil.show(HealthActivity.this, "数据上传成功");

                        /*改为初始化状态*/
                        healths.clear();
                        stringBuilder = new StringBuilder();
                        etBmd.setText("1");
                        etBmi.setText("1");
                        etBmr.setText("1");
                        etBodyFat.setText("1");
                        etMoistureRate.setText("1");
                        etMuscleRate.setText("1");

                         /*添加初始默认的一条数据*/
                        insertData("1", "1", "1", "1", "1", "1");

                    }

                    @Override
                    public void onFailure(int errNo, String errMsg) {
                        ToastUtil.show(HealthActivity.this, errMsg);
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
                healths.clear();
                stringBuilder = new StringBuilder();
                etBmd.setText("1");
                etBmi.setText("1");
                etBmr.setText("1");
                etBodyFat.setText("1");
                etMoistureRate.setText("1");
                etMuscleRate.setText("1");

                         /*添加初始默认的一条数据*/
                insertData("1", "1", "1", "1", "1", "1");

            }
        });

    }

    /**
     * 加入数据
     *
     * @param bmd
     * @param bmi
     * @param bmr
     * @param bodyFat
     * @param moistureRate
     * @param MuscleRate
     */
    public void insertData(String bmd, String bmi, String bmr, String bodyFat, String moistureRate, String MuscleRate) {

        Health health = new Health();
        health.setBmd(Float.parseFloat(bmd));
        health.setBmi(Float.parseFloat(bmi));
        health.setBmr(Float.parseFloat(bmr));
        health.setBodyFat(Float.parseFloat(bodyFat));
        health.setMoistureRate(Float.parseFloat(moistureRate));
        health.setMuscleRate(Float.parseFloat(MuscleRate));
        health.setSampleTime((int) (System.currentTimeMillis() / 1000));
        healths.add(health);

        showContent(health);
    }

    /**
     * 显示上传内容
     *
     * @param health
     */
    public void showContent(Health health) {

        stringBuilder.insert(0, "    bmi指数:" + health.getBmi() + "    体脂率:" + health.getBodyFat() + "    肌肉率:" + health.getMuscleRate() + "    水分:" + health.getMoistureRate() + "    骨密度:" + health.getBmd() + "    基础代谢率:" + health.getBmr() + "\n");
        tvShow.setText(stringBuilder.toString());
    }

}
