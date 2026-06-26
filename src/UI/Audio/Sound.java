package UI.Audio;
import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    private static float soundVolume = -25f;

    public static void playSound(String path) {
        try {
            AudioInputStream ais =
                    AudioSystem.getAudioInputStream(new File(path));

            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volume =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(soundVolume);
            }
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setVolume(float volume) {
        soundVolume = volume;
    }
}