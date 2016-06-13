package at.rubikan.ismiregal.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Andreas Rubik
 */
public class VoiceCache {
    private static Cache<String, File> cache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, File>() {
                public void onRemoval(RemovalNotification<String, File> removal) {
                    if (removal.getValue() != null)
                        removal.getValue().delete();
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
