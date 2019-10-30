package com.xw.myjzplayer;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        map.put("普清", "http://39.105.117.74:8085/uploads/fcvideo/video/20190215/386b8b67478a02632c9242a0ea11b472.mp4");
        map.put("标清", "http://2019030416250000z.oss-cn-beijing.aliyuncs.com/2019/5/8/9ea69a57-20c9-6568-fa7f-b4806e570f01.mp4");
        map.put("高清", "http://2019030416250000z.oss-cn-beijing.aliyuncs.com/2019/8/27/13b84d3c-71bb-104b-ae06-7dd70462a6b5.mp4");

        JZDataSource jzDataSource = new JZDataSource(map, "饺子不信");
        jzDataSource.looping = true;
        jzDataSource.currentUrlIndex = 0;
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
