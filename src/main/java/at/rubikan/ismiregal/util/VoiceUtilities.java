package at.rubikan.ismiregal.util;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andreas Rubik
 */
public class VoiceUtilities {
    private static final String EGAL = " ist mir egal!";
    private static VoiceManager voiceManager = VoiceManager.getInstance();
    private static VoiceCache vc = new VoiceCache();

    public File getWav(String text) {
        if (vc.get(text) != null) {
            return vc.get(text);
        } else {
            vc.add(text, generateWav(text));
            return vc.get(text);
        }
    }

    private File generateWav(String text) {
        Voice voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        String path = System.getProperty("java.io.tmpdir") + File.separator + text.replace(" ", "");
        AudioPlayer ap = new SingleFileAudioPlayer(path, AudioFileFormat.Type.WAVE);
        voice.setAudioPlayer(ap);
        voice.speak(text + EGAL);
        voice.deallocate();
        ap.close();

        return new File(path + ".wav");
    }

    public List<String> getVoices() {
        List<String> list = new ArrayList<>();

        Voice[] voices = voiceManager.getVoices();
        for (Voice v : voices) {
            list.add(v.getName());
        }

        return list;
    }
}
