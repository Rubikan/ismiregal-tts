package at.rubikan.ismiregal.util;


import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;
import marytts.util.data.audio.MaryAudioUtils;

import javax.sound.sampled.AudioInputStream;
import java.io.*;
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

    public File getWav(String text) throws MaryConfigurationException, SynthesisException, IOException {
        if (vc.get(text) != null) {
            return vc.get(text);
        } else {
            vc.add(text, generateWav(text + IST_MIR_EGAL));
            return vc.get(text);
        }
    }

    private File generateWav(String text) throws MaryConfigurationException, SynthesisException, IOException {
        marytts = new LocalMaryInterface();
        marytts.setVoice("cmu-slt-hsmm");
        String path = System.getProperty("java.io.tmpdir") + File.separator + text.replace(" ", "") + ".wav";
        AudioInputStream audio = marytts.generateAudio(text);
        double[] samples = MaryAudioUtils.getSamplesAsDoubleArray(audio);
        MaryAudioUtils.writeWavFile(samples, path, audio.getFormat());

        return new File(path);
    }

    public List<String> getVoices() throws MaryConfigurationException {
        List<String> list = new ArrayList<>();
        Set<String> voices = marytts.getAvailableVoices();
        list.addAll(voices);

        return list;
    }
}
