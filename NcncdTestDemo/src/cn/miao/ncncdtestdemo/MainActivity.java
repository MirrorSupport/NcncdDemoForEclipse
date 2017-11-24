package cn.miao.ncncdtestdemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.miao.ncncd.vm.NcncdRegisterActivity;
import cn.miao.ncncdtestdemo.adapter.TestAdapter;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private ListView lvTest;
    private TestAdapter testAdapter;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datas = new ArrayList<String>();
        datas.add("上传血糖数据");
        datas.add("上传健康数据");
        datas.add("上传血压数据");
        datas.add("上传运动数据");
        datas.add("上传睡眠数据");
        datas.add("上传血氧数据");
        datas.add("上传心率数据");
        datas.add("上传体温数据");
        datas.add("上传用户数据");
        lvTest = (ListView) findViewById(R.id.lv_test);
        testAdapter = new TestAdapter(this, datas);
        lvTest.setAdapter(testAdapter);

        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0://上传血糖数据
                        Intent iBloodSugar = new Intent(MainActivity.this, BloodSugarActivity.class);
                        startActivity(iBloodSugar);
                        break;
                    case 1://上传健康数据
                        Intent iHealth = new Intent(MainActivity.this, HealthActivity.class);
                        startActivity(iHealth);
                        break;
                    case 2://上传血压数据
                        Intent iBloodPressure = new Intent(MainActivity.this, BloodPressureActivity.class);
                        startActivity(iBloodPressure);
                        break;
                    case 3://上传运动数据
                        Intent iSport = new Intent(MainActivity.this, SportActivity.class);
                        startActivity(iSport);
                        break;
                    case 4://上传睡眠数据
                        Intent iSleep = new Intent(MainActivity.this, SleepActivity.class);
                        startActivity(iSleep);
                        break;
                    case 5://上传血氧数据
                        Intent iBloodOxygen = new Intent(MainActivity.this, BloodOxygenActivity.class);
                        startActivity(iBloodOxygen);
                        break;
                    case 6://上传心率数据
                        Intent iHeartRate = new Intent(MainActivity.this, HeartRateActivity.class);
                        startActivity(iHeartRate);
                        break;
                    case 7://上传体温数据
                        Intent iTemperature = new Intent(MainActivity.this, TemperatureActivity.class);
                        startActivity(iTemperature);
                        break;
                    case 8://上传用户数据
                        Intent iUser = new Intent(MainActivity.this, NcncdRegisterActivity.class);
                        startActivity(iUser);
                        break;

                }
            }
        });

    }

}
