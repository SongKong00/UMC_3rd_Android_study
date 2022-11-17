package kr.co.hanbit.week7

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kr.co.hanbit.week7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var timeCount = 10 // 전체 시간 (초 기준)
    private var started = false
    private val soundPool = SoundPool.Builder().build()
    private var timerSound: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSounds()
        startTimer()
        stopTimer()
    }

    private fun initSounds() {
        timerSound = soundPool.load(this, R.raw.timer_sound, 1)
    }

    private fun stopTimer() {
        binding.stopBtn.setOnClickListener {
            started = false
            soundPool.autoPause()
        }
    }

    private fun startTimer() {
        val handler = Handler(Looper.getMainLooper())

        binding.startBtn.setOnClickListener {
            if (started == false) {
                started = true

                Thread() {
                    while(started) {
                        Thread.sleep(1000)

                        if (timeCount == 0) {
                            started = false
                            soundPool.autoPause()
                            timerSound?.let { soundId ->
                                soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
                            }
                        }

                        if (started) {
                            timeCount -= 1

                            handler.post {
                                val minute = String.format("%02d", timeCount / 60)
                                val second = String.format("%02d", timeCount % 60)
                                binding.Count.text = "$minute:$second"

                                timerSound?.let {
                                }
                            }
                        }
                    }
                }.start()
            }
        }
    }
}