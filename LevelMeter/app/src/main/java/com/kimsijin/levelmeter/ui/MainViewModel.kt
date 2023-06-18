package com.kimsijin.levelmeter.ui

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver, SensorEventListener {

    private val _x = mutableStateOf(0f)
    val x: State<Float> = _x

    private val _y = mutableStateOf(0f)
    val y: State<Float> = _y

    private val sensorManager by lazy {
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun registerSensor() {
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL,
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unregisterSensor() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            _x.value = event.values[0]
            _y.value = event.values[1]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

}