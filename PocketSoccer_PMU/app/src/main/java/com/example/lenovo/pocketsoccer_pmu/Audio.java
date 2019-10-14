package com.example.lenovo.pocketsoccer_pmu;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class Audio {

    private Context context;
    private SoundPool soundPool  = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
    private HashMap<Integer, Integer> soundPoolMap;

    public Audio(Context context) {
        this.context = context;
        initSoundPool();
    }



    private void initSoundPool() {
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(AppConstants.getGOAL(), soundPool.load(context, R.raw.goal, 1));
        soundPoolMap.put(AppConstants.getBALL(), soundPool.load(context, R.raw.ball, 1));
        soundPoolMap.put(AppConstants.getTIMEOUT(), soundPool.load(context, R.raw.timeout, 1));
    }

    public void playSound(int soundId) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float leftVolume = curVolume/maxVolume;
        float rightVolume = curVolume/maxVolume;
        soundPool.play(soundId, leftVolume, rightVolume, 1,0, 1f);
    }
}

