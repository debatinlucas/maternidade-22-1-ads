package br.com.dlweb.maternidade_ads;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textViewValorX;
    private TextView textViewValorY;
    private TextView textViewValorZ;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        textViewValorX = findViewById(R.id.textViewValorX);
        textViewValorY = findViewById(R.id.textViewValorY);
        textViewValorZ = findViewById(R.id.textViewValorZ);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onResume () {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause () {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        textViewValorX.setText(String.valueOf(x));
        textViewValorY.setText(String.valueOf(y));
        textViewValorZ.setText(String.valueOf(z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}