import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    public static void playSound(String soundFileName) {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File("src/sound/" + soundFileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
    }
}
