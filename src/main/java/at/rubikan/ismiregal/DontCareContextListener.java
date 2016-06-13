package at.rubikan.ismiregal;

import at.rubikan.ismiregal.util.VoiceUtilities;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DontCareContextListener implements ServletContextListener {
    private static Logger log = Logger.getLogger(DontCareContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        VoiceUtilities.cleanVoiceCache();
        VoiceUtilities.initialize();
        log.info("DontCare Application initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("DontCare Application destroyed");
    }
}
