package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var firstButton:Button
    lateinit var secondButton:Button
    lateinit var thirdButton: Button
    lateinit var fourthButton: Button

    var timeToCountDownInMs = 6000L
    val timeTicks = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           startCountDown(it)
       }

       firstButton = findViewById<Button>(R.id.firstButton)
       firstButton.setOnClickListener(){
           timeToCountDownInMs = 1800000L
       }

       secondButton = findViewById<Button>(R.id.secondButton)
       secondButton.setOnClickListener(){
           timeToCountDownInMs = 3600000L
        }

        thirdButton = findViewById<Button>(R.id.thirdButton)
        thirdButton.setOnClickListener(){
            timeToCountDownInMs = 5400000L
        }

        fourthButton = findViewById<Button>(R.id.fourthButton)
        fourthButton.setOnClickListener(){
            timeToCountDownInMs = 7200000L
        }

       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }



    fun startCountDown(v: View){
        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks)
        {
            override fun onFinish()
            {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                startButton.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long)
            {
               updateCountDownDisplay(millisUntilFinished)
                if (millisUntilFinished > 0){
                    startButton.isEnabled = false
                }

            }
        }
        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}