package at.rubikan.ismiregal.util;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.MaryAudioUtils;
import org.apache.log4j.Logger;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Andreas Rubik
 */
public class VoiceUtilities {
    private static final String IST_MIR_EGAL = " ist mir egal";
    private static final String DEFAULT_VOICE = "bits3-hsmm";
    private static final String VOICE_CACHE_DIR = System.getProperty("java.io.tmpdir") + File.separator + "voiceCache" + File.separator;
    private static MaryInterface marytts;
    private static VoiceCache vc = new VoiceCache();
    private static Logger log = Logger.getLogger(VoiceUtilities.class);

    public static void initialize() {
        if (marytts == null) {
            try {
                // Initialize MaryTTS Interface
                marytts = new LocalMaryInterface();
                marytts.setLocale(Locale.GERMAN);
                marytts.setVoice(DEFAULT_VOICE);
                // Create VoiceCache Folder
                File voiceCacheDir = new File(VOICE_CACHE_DIR);
                if (!voiceCacheDir.exists()) {
                    if (!voiceCacheDir.mkdir()) {
                        log.error("Error creating voiceCache directory!");
                    }
                }
            } catch (MaryConfigurationException e) {
                log.error("Error initializing LocalMaryInterface!");
            }
        } else {
            log.warn("Initialize method should only be called once!");
        }
    }

    public static void cleanVoiceCache() {
        File dir = new File(VOICE_CACHE_DIR);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.delete()) {
                    log.warn("Could not remove " + f.getName() + " from cache.");
                }
            }
        }
    }

    public File getWav(String text) throws MaryConfigurationException, SynthesisException, IOException {
        if (vc.get(text) != null) {
            return vc.get(text);
        } else {
            vc.add(text, generateWav(text + IST_MIR_EGAL));
            return vc.get(text);
        }
    }

    private File generateWav(String text) throws MaryConfigurationException, SynthesisException, IOException {
        String path = VOICE_CACHE_DIR + text.replace(" ", "") + ".wav";
        AudioInputStream audio = marytts.generateAudio(text);
        double[] samples = MaryAudioUtils.getSamplesAsDoubleArray(audio);
        MaryAudioUtils.writeWavFile(samples, path, audio.getFormat());

        return new File(path);
    }

    public List<String> getVoices() throws MaryConfigurationException {
        Set<String> voices = marytts.getAvailableVoices();
        List<String> list = new ArrayList<>();
        list.addAll(voices);

        return list;
    }
}
