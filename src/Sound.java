import java.io.File;
import javax.sound.sampled.*;

public class Sound {

    public static void playSound(String path) {
        try {
            AudioInputStream ais =
                    AudioSystem.getAudioInputStream(new File(path));

            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}