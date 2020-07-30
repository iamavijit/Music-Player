package in.avijit.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer m;
    SeekBar music,volume;
    Button play,pause;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        music = findViewById(R.id.music);
        m = MediaPlayer.create(this,R.raw.testaudio);
        music.setMax(m.getDuration());
//        This is the seek bar for tracking music
        music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                m.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        Play button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.start();
                seekbarprogress();
            }
        });
//          Pause button
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.pause();
            }
        });

//        volume control
                volume = findViewById(R.id.volume);
                audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                volume.setMax(max);
                volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

    }
//It is the function for moving seek bar automatically
    public void seekbarprogress(){
        music.setProgress(m.getCurrentPosition());
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                if(m.isPlaying()){
                    try {
                        Thread.sleep(1750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    seekbarprogress();
                }
            }
        });

        thread.start();
    }
}
