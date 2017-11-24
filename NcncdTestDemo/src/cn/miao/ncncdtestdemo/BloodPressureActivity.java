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
import cn.miao.ncncd.api.BloodPressureApi;
import cn.miao.ncncd.api.handle.ApiCallBack;
import cn.miao.ncncd.http.entity.BloodPressure;
import cn.miao.ncncd.util.ToastUtil;

/**
 * 上传血压数据界面
 */
public class BloodPressureActivity extends AppCompatActivity {

    /*返回按钮*/
    private ImageButton ibClose;

    /*上传的内容*/
    private EditText etDiastolic;
    private EditText etSystolic;

    /*加入、上传、重置、显示上传数据*/
    private Button btInsert;
    private Button btnUpload;
    private Button btnReset;
    private TextView tvShow;

    private List<BloodPressure> pressures;

    private ProgressDialog progressDialog;

    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        initBoot();
        initView();
        initData();
        initEvent();
    }

    public void initBoot() {
        pressures = new ArrayList<BloodPressure>();
        stringBuilder = new StringBuilder();
    }

    public void initView() {

        ibClose = (ImageButton) findViewById(R.id.ib_close);

        etDiastolic = (EditText) findViewById(R.id.et_diastolic);
        etSystolic = (EditText) findViewById(R.id.et_systolic);

        btInsert = (Button) findViewById(R.id.btn_insert);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnReset = (Button) findViewById(R.id.btn_reset);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    public void initData() {

        /*添加初始默认的一条数据*/
        insertData("1", "1");

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

                String diastolic = etDiastolic.getText().toString().trim();
                String systolic = etSystolic.getText().toString().trim();

                if (diastolic.isEmpty()) {
                    ToastUtil.show(BloodPressureActivity.this, "请填写舒张压");
                    return;
                }

                if (systolic.isEmpty()) {
                    ToastUtil.show(BloodPressureActivity.this, "请填写舒张压");
                    return;
                }

                insertData(diastolic, systolic);
            }
        });

        /*上传*/
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pressures.isEmpty()) {
                    ToastUtil.show(BloodPressureActivity.this, "请加入上传数据");
                    return;
                }

                BloodPressureApi.uploadBloodPressure(BloodPressureActivity.this, "18811427233", pressures, new ApiCallBack() {
                    @Override
                    public void onStart() {
                        //显示ProgressDialog
                        progressDialog = ProgressDialog.show(BloodPressureActivity.this, "Loading...", "Please wait...", true, false);

                    }

                    @Override
                    public void onSuccess() {
                        ToastUtil.show(BloodPressureActivity.this, "数据上传成功");

                        /*改为初始化状态*/
                        pressures.clear();
                        stringBuilder = new StringBuilder();
                        etDiastolic.setText("1");
                        etSystolic.setText("1");

                         /*添加初始默认的一条数据*/
                        insertData("1", "1");


                    }

                    @Override
                    public void onFailure(int errNo, String errMsg) {
                        ToastUtil.show(BloodPressureActivity.this, errMsg);

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
                pressures.clear();
                stringBuilder = new StringBuilder();
                etDiastolic.setText("1");
                etSystolic.setText("1");

                /*添加初始默认的一条数据*/
                insertData("1", "1");
            }
        });
    }

    /**
     * 加入数据
     *
     * @param diastolic
     * @param systolic
     */
    public void insertData(String diastolic, String systolic) {

        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setDiastolic(Integer.parseInt(diastolic));
        bloodPressure.setSystolic(Integer.parseInt(systolic));
        bloodPressure.setSampleTime((int) (System.currentTimeMillis() / 1000));
        pressures.add(bloodPressure);

        showContent(bloodPressure);

    }

    /**
     * 显示上传内容
     *
     * @param bloodPressure
     */
    public void showContent(BloodPressure bloodPressure) {
        stringBuilder.insert(0, "    舒张压:" + bloodPressure.getDiastolic() + "    收缩压:" + bloodPressure.getSystolic() + "\n");
        tvShow.setText(stringBuilder.toString());
    }
}
