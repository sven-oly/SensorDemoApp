package com.ccornelius.example.sensordemoapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

// Custom view for orientation graph.
import com.ccornelius.example.sensordemoapp.R;

import java.util.List;



public class SensorActivity extends ActionBarActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    List<Sensor> mDeviceSensors;

    private Sensor mCurrentSensor;

    private CompassView compassView;

    int currentSensorSelection = Sensor.TYPE_LINEAR_ACCELERATION;
    int currentSensorStringR = 0;
    String mSensorLabel = "Linear";

    private View colorBox;
    private TextView labelView;
    private TextView outputX;
    private TextView outputY;
    private TextView outputZ;
    private final int OUTPUT_COUNT = 5;
    private TextView [] outputViews;

    private int numValues = 3;

    private double mThreshhold = 0.05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // Compass view...
        compassView = (CompassView)findViewById(R.id.graphArea);
        compassView.setVisibility(View.INVISIBLE);

        mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

        colorBox = (View)findViewById(R.id.colorBox);
        if (currentSensorStringR == 0) {
            currentSensorStringR = R.string.linear_mode;
        }
        labelView = (TextView)findViewById(R.id.mheading);
        mSensorLabel = getString(currentSensorStringR);
        labelView.setText(mSensorLabel);

        outputViews = new TextView[5];
        outputViews[0] = outputX = (TextView)findViewById(R.id.linearX);
        outputViews[1] = outputY = (TextView)findViewById(R.id.linearY);
        outputViews[2] = outputZ = (TextView)findViewById(R.id.linearZ);
        outputViews[3] = (TextView)findViewById(R.id.output3);
        outputViews[4] = (TextView)findViewById(R.id.output4);

        setTextVisibility(numValues);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.set_rotation) {
            currentSensorSelection = Sensor.TYPE_GYROSCOPE;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
            currentSensorStringR = R.string.rotation_mode;
            mSensorLabel = getString(currentSensorStringR);
            labelView.setText(mSensorLabel);

            compassView.setVisibility(View.INVISIBLE);
            numValues = 3;
            setTextVisibility(numValues);
            return true;
        }
        if (id == R.id.set_rotation_vector) {
            currentSensorSelection = Sensor.TYPE_ROTATION_VECTOR;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
            currentSensorStringR = R.string.rotation_vector_mode;
            mSensorLabel = getString(currentSensorStringR);
            labelView.setText(mSensorLabel);

            compassView.setVisibility(View.INVISIBLE);
            numValues = 5;
            setTextVisibility(numValues);
            return true;
        }
        if (id == R.id.set_linear) {
            currentSensorSelection = Sensor.TYPE_LINEAR_ACCELERATION;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
            currentSensorStringR = R.string.linear_mode;
            mSensorLabel = getString(currentSensorStringR);

            labelView.setText(mSensorLabel);

            compassView.setVisibility(View.INVISIBLE);
            numValues = 3;
            setTextVisibility(numValues);
            return true;
        }
        if (id == R.id.set_magnetic) {
            currentSensorSelection = Sensor.TYPE_MAGNETIC_FIELD;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
            currentSensorStringR = R.string.magnetic_mode;
            mSensorLabel = getString(currentSensorStringR);

            labelView.setText(mSensorLabel);

            compassView.setVisibility(View.INVISIBLE);
            numValues = 3;
            setTextVisibility(numValues);
            return true;
        }
        if (id == R.id.set_orientation) {
            currentSensorSelection = Sensor.TYPE_ORIENTATION;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
            currentSensorStringR = R.string.orientation_mode;
            mSensorLabel = getString(currentSensorStringR);

            labelView.setText(mSensorLabel);

            compassView.setVisibility(View.VISIBLE);
            numValues = 3;
            setTextVisibility(numValues);
            return true;
        }
        if (id == R.id.set_proximity) {
            currentSensorSelection = Sensor.TYPE_PROXIMITY;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
            currentSensorStringR = R.string.proximity_mode;
            mSensorLabel = getString(currentSensorStringR);

            labelView.setText(mSensorLabel);

            compassView.setVisibility(View.INVISIBLE);
            numValues = 1;
            setTextVisibility(numValues);
            return true;
        }
        if (id == R.id.set_pressure) {
            currentSensorSelection = Sensor.TYPE_PRESSURE;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
            currentSensorStringR = R.string.pressure_mode;
            mSensorLabel = getString(currentSensorStringR);

            labelView.setText(mSensorLabel);

            compassView.setVisibility(View.INVISIBLE);
            numValues = 1;
            setTextVisibility(numValues);
            return true;
        }
        if (id == R.id.set_light) {
            currentSensorSelection = Sensor.TYPE_LIGHT;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            if (mCurrentSensor != null) {
                mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
                currentSensorStringR = R.string.light_mode;
                mSensorLabel = getString(currentSensorStringR);

                labelView.setText(mSensorLabel);
                numValues = 1;
            } else {
                mSensorLabel = (String)"No light sensor";
                labelView.setText(mSensorLabel);
                numValues = 0;
            }
            compassView.setVisibility(View.INVISIBLE);
            setTextVisibility(numValues);

            return true;
        }
        if (id == R.id.set_humidity) {
            currentSensorSelection = Sensor.TYPE_RELATIVE_HUMIDITY;
            mSensorManager.unregisterListener(this);
            mCurrentSensor = mSensorManager.getDefaultSensor(currentSensorSelection);

            if (mCurrentSensor != null) {
                mSensorManager.registerListener(this, mCurrentSensor, SensorManager.SENSOR_DELAY_NORMAL);
                currentSensorStringR = R.string.humidity_mode;
                mSensorLabel = getString(currentSensorStringR);

                labelView.setText(mSensorLabel);
                numValues = 1;
            } else {
                mSensorLabel = (String)"No humidity\n sensor!";
                labelView.setText(mSensorLabel);
                numValues = 0;
            }
            compassView.setVisibility(View.INVISIBLE);
            setTextVisibility(numValues);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        for (int i = 0; i < numValues; i ++) {
            outputViews[i].setText(String.valueOf(event.values[i]));
        }
        //outputX.setText(String.valueOf(event.values[0]));
        //outputY.setText(String.valueOf(event.values[1]));
        //outputZ.setText(String.valueOf(event.values[2]));

        if (compassView.getVisibility() == View.VISIBLE) {
            compassView.updateData(event.values[0]);
        }

        DoSomething(event.values);
    }

    private void DoSomething(float [] values) {
        int maxDim = 0;
        float maxVal = Math.abs(values[0]);

        for (int i = 1; i < numValues; i++) {
            if (Math.abs(values[i]) > maxVal) {
                maxVal = Math.abs(values[i]);
                maxDim = i;
            }
        }

        if (maxDim == 0) {
            colorBox.setBackgroundColor(0x99aa0000);
        } else if (maxDim == 1) {
            colorBox.setBackgroundColor(0x9900AA00);

        } else if (maxDim == 2) {
            colorBox.setBackgroundColor(0x990000aa);
        } else { // Obviously something else.
            colorBox.setBackgroundColor(0x99ffaa00);
        }
        if (maxVal < mThreshhold) {
            colorBox.setBackgroundColor(0xFFffffff);
        }

    }

    private void setTextVisibility(int max) {
        for (int i = 0; i < max; i++) {
            outputViews[i].setVisibility(View.VISIBLE);
        }
        for (int i = max; i < OUTPUT_COUNT; i++) {
            outputViews[i].setVisibility(View.INVISIBLE);
        }
    }
}
