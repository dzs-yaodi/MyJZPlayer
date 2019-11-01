package com.xw.myjzplayer;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.xw.jzplayerlibs.JZDataSource;
import com.xw.jzplayerlibs.Jzvd;
import com.xw.jzplayerlibs.JzvdStd;

import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    private JzvdStd jzvdStd;
    Jzvd.JZAutoFullscreenListener mSensorEventListener;
    SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jzvdStd = findViewById(R.id.jzvdStd);

        LinkedHashMap map = new LinkedHashMap();
        map.put("普清", "http://2019030416250000z.oss-cn-beijing.aliyuncs.com/transcode/2019/7/17/4354ba96-f85c-5d20-cbf0-1f704451ff33.mp4");
        map.put("标清", "http://2019030416250000z.oss-cn-beijing.aliyuncs.com/u-/transcode/2019/10/28/SD-MP4/4A05DBF092509E870722D702B6F2430D.mp4 ");
        map.put("高清", "http://2019030416250000z.oss-cn-beijing.aliyuncs.com/2019/8/27/13b84d3c-71bb-104b-ae06-7dd70462a6b5.mp4");

        JZDataSource jzDataSource = new JZDataSource(map, "饺子不信");
        jzDataSource.looping = true;
        jzDataSource.currentUrlIndex = 2;
        jzDataSource.headerMap.put("key", "value");//header
        jzvdStd.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new Jzvd.JZAutoFullscreenListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Jzvd.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        Jzvd.clearSavedProgress(this, null);
        //home back
        Jzvd.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

}
