package tetrisGameClone;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip musicClip;
    URL[] url = new URL[10];

    public Sound() {
        url[0] = getClass().getResource("/res/theme.wav");
        url[1] = getClass().getResource("/res/delete line.wav");
        url[2] = getClass().getResource("/res/gameover.wav");
        url[3] = getClass().getResource("/res/rotation.wav");
        url[4] = getClass().getResource("/res/touch floor.wav");
    }

    public void play(int i, boolean music) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
            }
            clip.open(ais);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            ais.close();
            clip.start();
        } catch (Exception ignored) {

        }
    }
        public void loop() {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        public void stop() {
            musicClip.stop();
            musicClip.close();
        }
    }



