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
import cn.miao.ncncd.api.BloodSugarApi;
import cn.miao.ncncd.api.handle.ApiCallBack;
import cn.miao.ncncd.http.entity.BloodSugar;
import cn.miao.ncncd.util.ToastUtil;

/**
 * 上传血糖数据界面
 */
public class BloodSugarActivity extends AppCompatActivity {

    /*返回按钮*/
    private ImageButton ibClose;

    /*上传的内容*/
    private EditText etType;
    private EditText etValue;

    /*加入、上传、重置、显示上传数据*/
    private Button btInsert;
    private Button btnUpload;
    private Button btnReset;
    private TextView tvShow;

    private List<BloodSugar> bloodSugars;

    private ProgressDialog progressDialog;

    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar);

        initBoot();
        initView();
        initData();
        initEvent();
    }

    public void initBoot() {
        bloodSugars = new ArrayList<BloodSugar>();
        stringBuilder = new StringBuilder();
    }

    public void initView() {
        ibClose = (ImageButton) findViewById(R.id.ib_close);

        etType = (EditText) findViewById(R.id.et_type);
        etValue = (EditText) findViewById(R.id.et_value);

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

                String type = etType.getText().toString().trim();
                String value = etValue.getText().toString().trim();

                if (type.isEmpty()) {
                    ToastUtil.show(BloodSugarActivity.this, "请填写血糖类型");
                    return;
                }
                if (!type.equals("1") && !type.equals("2")) {
                    ToastUtil.show(BloodSugarActivity.this, "请填写正确的血糖类型");
                    return;
                }

                if (value.isEmpty()) {
                    ToastUtil.show(BloodSugarActivity.this, "请填写测量数值");
                    return;
                }

                insertData(type, value);

            }
        });

        /*上传*/
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bloodSugars.isEmpty()) {
                    ToastUtil.show(BloodSugarActivity.this, "请加入上传数据");
                    return;
                }

                BloodSugarApi.uploadBloodSugar(BloodSugarActivity.this, "18811427233", bloodSugars, new ApiCallBack() {
                    @Override
                    public void onStart() {
                        //显示ProgressDialog
                        progressDialog = ProgressDialog.show(BloodSugarActivity.this, "Loading...", "Please wait...", true, false);

                    }

                    @Override
                    public void onSuccess() {
                        ToastUtil.show(BloodSugarActivity.this, "数据上传成功");

                        /*改为初始化状态*/
                        bloodSugars.clear();
                        stringBuilder = new StringBuilder();
                        etType.setText("1");
                        etValue.setText("1");

                         /*添加初始默认的一条数据*/
                        insertData("1", "1");
                    }

                    @Override
                    public void onFailure(int errNo, String errMsg) {

                        ToastUtil.show(BloodSugarActivity.this, errMsg);
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
                bloodSugars.clear();
                stringBuilder = new StringBuilder();
                etType.setText("1");
                etValue.setText("1");

                /*添加初始默认的一条数据*/
                insertData("1", "1");
            }
        });
    }

    /**
     * 加入数据
     *
     * @param type
     * @param value
     */
    public void insertData(String type, String value) {

        BloodSugar bloodSugar = new BloodSugar();
        bloodSugar.setType(Integer.parseInt(type));
        bloodSugar.setValue(Float.parseFloat(value));
        bloodSugar.setSampleTime((int) (System.currentTimeMillis() / 1000));
        bloodSugars.add(bloodSugar);

        showContent(bloodSugar);
    }

    /**
     * 显示上传内容
     *
     * @param bloodSugar
     */
    public void showContent(BloodSugar bloodSugar) {

        stringBuilder.insert(0, "    血糖类型:" + bloodSugar.getType() + "    测量数值:" + bloodSugar.getValue() + "\n");
        tvShow.setText(stringBuilder.toString());
    }
}
