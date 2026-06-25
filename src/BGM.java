import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;

public class BGM {

    private static Clip bgmClip;

    public static void playBGM(String path) {
        try {
            if (bgmClip != null && bgmClip.isRunning()) {
                return;
            }

            // Coba load dari working directory dulu
            File audioFile = new File(path);

            // Kalau tidak ketemu, coba dari root project (lokasi class)
            if (!audioFile.exists()) {
                URL url = BGM.class.getClassLoader().getResource(path);
                if (url != null) {
                    audioFile = new File(url.toURI());
                }
            }

            if (!audioFile.exists()) {
                System.err.println("[BGM] File tidak ditemukan: " + audioFile.getAbsolutePath());
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(audioFile);

            // Convert format ke PCM jika perlu (hindari UnsupportedAudioFileException)
            AudioFormat baseFormat = audio.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
            );

            if (!baseFormat.equals(targetFormat)) {
                audio = AudioSystem.getAudioInputStream(targetFormat, audio);
            }

            bgmClip = AudioSystem.getClip();
            bgmClip.open(audio);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();

            System.out.println("[BGM] Berhasil diputar: " + path);

        } catch (Exception e) {
            System.err.println("[BGM] Gagal memutar BGM: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void stopBGM() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }
}