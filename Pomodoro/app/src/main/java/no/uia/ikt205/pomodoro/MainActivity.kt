package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import no.uia.ikt205.pomodoro.util.minutesToMilliSeconds

abstract class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var startSelectSlider:SeekBar
    lateinit var pauseSelectSlider:SeekBar
    lateinit var repititionAmount:EditText
    lateinit var pauseText:TextView

    private var hasWorkStarted: Boolean = false
    private var hasPauseStarted: Boolean = false

    // Default time 15 min
    private var workTimeMs = minutesToMilliSeconds(15)
    private var pauseTimeMs = minutesToMilliSeconds(15)

    private val timeTicks = 1000L

    // repititions set to 0 at beginning
    private var timeRepititionAmount:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           startCountDown(it)
       }


       coutdownDisplay = findViewById<TextView>(R.id.countDownView)
       pauseText = findViewById<TextView>(R.id.pauseText)
       pauseText.visibility = View.INVISIBLE

        startSelectSlider = findViewById<SeekBar>(R.id.startSelectSlider)
        startSelectSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (hasWorkStarted){
                    timer.cancel()
                    hasWorkStarted = false
                }
                workTimeMs = progress * 60000L

                updateCountDownDisplay(workTimeMs)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Not impl")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Not impl")
            }
        })


    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(workTimeMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}