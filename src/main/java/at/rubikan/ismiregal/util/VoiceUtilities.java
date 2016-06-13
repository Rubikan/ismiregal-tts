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
    private static VoiceCache vc = new VoiceCache();
    private static MaryInterface marytts;
    private static Logger log = Logger.getLogger(VoiceUtilities.class);

    public VoiceUtilities() {
        try {
            marytts = new LocalMaryInterface();
            marytts.setLocale(Locale.GERMAN);
            marytts.setVoice("bits3-hsmm");
        } catch (Exception e) {
            log.error("Error initializing LocalMaryInterface!");
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
        String path = System.getProperty("java.io.tmpdir") + File.separator + text.replace(" ", "") + ".wav";
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
