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
import cn.miao.ncncd.api.SportApi;
import cn.miao.ncncd.api.handle.ApiCallBack;
import cn.miao.ncncd.http.entity.Sport;
import cn.miao.ncncd.util.ToastUtil;

/**
 * 上传运动数据界面
 */
public class SportActivity extends AppCompatActivity {

    /*返回按钮*/
    private ImageButton ibClose;

    /*上传的内容*/
    private EditText etstep;
    private EditText etDistance;
    private EditText etIntervalTime;

    /*加入、上传、显示上传数据*/
    private Button btInsert;
    private Button btnUpload;
    private Button btnReset;
    private TextView tvShow;

    private List<Sport> sports;

    private ProgressDialog progressDialog;

    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        initBoot();
        initView();
        initData();
        initEvent();
    }

    public void initBoot() {
        sports = new ArrayList<Sport>();
        stringBuilder = new StringBuilder();
    }

    public void initView() {
        ibClose = (ImageButton) findViewById(R.id.ib_close);

        etstep = (EditText) findViewById(R.id.et_step);
        etDistance = (EditText) findViewById(R.id.et_distance);
        etIntervalTime = (EditText) findViewById(R.id.et_interval_time);

        btInsert = (Button) findViewById(R.id.btn_insert);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnReset = (Button) findViewById(R.id.btn_reset);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    public void initData() {
        String intervalTime = etIntervalTime.getText().toString().trim();
        /*添加初始默认的一条数据*/
        insertData("1", "1", intervalTime);

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

                String step = etstep.getText().toString().trim();
                String distance = etDistance.getText().toString().trim();
                String intervalTime = etIntervalTime.getText().toString().trim();

                if (step.isEmpty()) {
                    ToastUtil.show(SportActivity.this, "请填写步数");
                    return;
                }
                if (distance.isEmpty()) {
                    ToastUtil.show(SportActivity.this, "请填写距离");
                    return;
                }
                if (intervalTime.isEmpty()) {
                    ToastUtil.show(SportActivity.this, "请填写时间间隔");
                    return;
                }

                insertData(step, distance, intervalTime);

            }
        });

        /*上传*/
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sports.isEmpty()) {
                    ToastUtil.show(SportActivity.this, "请加入上传数据");
                    return;
                }

                SportApi.uploadSport(SportActivity.this, "18811427233", sports, new ApiCallBack() {
                    @Override
                    public void onStart() {
                        //显示ProgressDialog
                        progressDialog = ProgressDialog.show(SportActivity.this, "Loading...", "Please wait...", true, false);

                    }

                    @Override
                    public void onSuccess() {
                        ToastUtil.show(SportActivity.this, "数据上传成功");

                        /*改为初始化状态*/
                        sports.clear();
                        stringBuilder = new StringBuilder();
                        etstep.setText("1");
                        etDistance.setText("1");
                        etIntervalTime.setText("1");

                        String intervalTime = etIntervalTime.getText().toString().trim();
                         /*添加初始默认的一条数据*/
                        insertData("1", "1", intervalTime);
                    }

                    @Override
                    public void onFailure(int errNo, String errMsg) {
                        ToastUtil.show(SportActivity.this, errMsg);

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
                sports.clear();
                stringBuilder = new StringBuilder();
                etstep.setText("1");
                etDistance.setText("1");
                etIntervalTime.setText("1");

                String intervalTime = etIntervalTime.getText().toString().trim();
                         /*添加初始默认的一条数据*/
                insertData("1", "1", intervalTime);

            }
        });
    }

    /**
     * 加入数据并显示上传内容
     *
     * @param step
     * @param distance
     * @param intervalTime
     */
    public void insertData(String step, String distance, String intervalTime) {
        long intervalTimeLong = Long.parseLong(intervalTime);
        int beginTime = (int) (System.currentTimeMillis() / 1000 - intervalTimeLong);
        Sport sport = new Sport();
        sport.setStep(Integer.parseInt(step));
        sport.setDistance(Integer.parseInt(distance));
        sport.setBeginTime(beginTime);
        sport.setEndTime((int) (System.currentTimeMillis() / 1000));
        sports.add(sport);

        showContent(sport);
    }

    /**
     * 加入数据并显示上传内容
     *
     * @param sport
     */
    public void showContent(Sport sport) {
        stringBuilder.insert(0, "    步数:" + sport.getStep() + "    距离:" + sport.getDistance() + "    收集时间间隔:" + (sport.getEndTime() - sport.getBeginTime()) + "\n");
        tvShow.setText(stringBuilder.toString());
    }
}
