package org.brentwardindustries.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    public int soundIndex = -1;
    FloatControl floatControl;
    int volumeScale = 3;
    float volume;

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/song_01.wav");
        soundURL[1] = getClass().getResource("/sounds/dash.wav");
        soundURL[2] = getClass().getResource("/sounds/quick_dash.wav");
        soundURL[3] = getClass().getResource("/sounds/jump.wav");
        soundURL[4] = getClass().getResource("/sounds/jump_2.wav");
        soundURL[5] = getClass().getResource("/sounds/coin.wav");
        soundURL[6] = getClass().getResource("/sounds/god_no.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        soundIndex = i;
    }

    public void play() {
        if (soundIndex >= 0) {
            clip.start();
        }
    }

    public void playLoop() {
        if (soundIndex >= 0) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (soundIndex >= 0) {
            clip.stop();
        }
    }

    public void end() {
        stop();
        soundIndex = -1;
    }

    public void checkVolue() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        floatControl.setValue(volume);
    }
}
