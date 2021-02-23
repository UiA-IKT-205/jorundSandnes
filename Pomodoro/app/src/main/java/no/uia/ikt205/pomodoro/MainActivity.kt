package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import no.uia.ikt205.pomodoro.util.minutesToMilliSeconds

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var pauseTimer: CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var startSelectSlider:SeekBar
    lateinit var pauseSelectSlider:SeekBar
    lateinit var repititionAmount:EditText
    lateinit var repetitionInput:TextView
    lateinit var pauseText:TextView

    private var hasWorkStarted: Boolean = false
    private var hasPauseStarted: Boolean = false

    // Default time 15 min
    private var workTimeMs = minutesToMilliSeconds(15)
    private var pauseTimeMs = minutesToMilliSeconds(15)

    private val timeTicks = 1000L

    // repititions set to 0 at beginning
    private var timeRepetitionAmount:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



       pauseText = findViewById<TextView>(R.id.pauseText)
       pauseText.visibility = View.INVISIBLE

        startSelectSlider = findViewById<SeekBar>(R.id.startSelectSlider)
        startSelectSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (hasWorkStarted){
                    timer.cancel()
                    hasWorkStarted = false
                }
                workTimeMs = progress * 36000L

                updateCountDownDisplay(workTimeMs)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Not impl")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Not impl")
            }
        })

        pauseSelectSlider = findViewById<SeekBar>(R.id.pauseSelectSlider)
        pauseSelectSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pauseTimeMs = progress * 36000L
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }
        })

        repititionAmount = findViewById<EditText>(R.id.repetitionAmount)


        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            startCountDown(it)
        }

        coutdownDisplay = findViewById<TextView>(R.id.countDownView)

        // Start value on activity mount is enforced to show 15 min
        updateCountDownDisplay(workTimeMs)

    }

    private fun startCountDown(v: View){
        if (hasWorkStarted){
            timer.cancel()
            hasWorkStarted = false
            Toast.makeText(this@MainActivity, "Stopping all timers", Toast.LENGTH_SHORT).show()
            return
        }

        timer = object : CountDownTimer(workTimeMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                hasWorkStarted = false

                timeRepetitionAmount = repetitionInput.text.toString().toInt()

                if (timeRepetitionAmount > 0){
                    Toast.makeText(this@MainActivity, "Tid for en pause", Toast.LENGTH_SHORT).show()
                    startPauseTimer(v)
                    timeRepetitionAmount--
                    repetitionInput.setText(timeRepetitionAmount.toString())
                }
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
        hasWorkStarted = true
    }

    private fun startPauseTimer(v: View){
        if (hasPauseStarted) {
            pauseTimer.cancel()
            hasPauseStarted = false
        }

        pauseTimer = object : CountDownTimer(pauseTimeMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Pausen er over", Toast.LENGTH_SHORT).show()
                hasPauseStarted = false

                timeRepetitionAmount = repetitionInput.text.toString().toInt()
                if (timeRepetitionAmount > 0){
                    timer.start()
                } else {
                    timer.cancel()
                }

                pauseText.visibility = View.INVISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                pauseText.visibility = View.INVISIBLE
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        pauseTimer.start()
        hasPauseStarted = true
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}