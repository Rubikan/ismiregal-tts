package at.rubikan.ismiregal.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Andreas Rubik
 */
public class VoiceCache {
    private static Logger log = Logger.getLogger(VoiceCache.class);
    private static Cache<String, File> cache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, File>() {
                public void onRemoval(RemovalNotification<String, File> removal) {
                    if (removal.getValue() != null)
                        if (removal.getValue().delete()) {
                            log.info("The file " + removal.getValue().getName() + " has been deleted from the cache!");
                        } else {
                            log.warn("Error removing the file " + removal.getValue().getName() + " from the cache!");
                        }

                }
            })
            .build();

    public void add(String key, File f) {
        cache.cleanUp();
        cache.put(key, f);
    }

    public File get(String text) {
        File f = cache.getIfPresent(text);
        cache.cleanUp();
        return f;
    }
}
