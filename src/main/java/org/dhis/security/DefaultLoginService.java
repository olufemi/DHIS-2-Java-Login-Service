package org.dhis.security;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.dhis.user.User;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.concurrent.TimeUnit;

public class DefaultLoginService
        implements LoginService {

    /**
     * Cache for login attempts where usernames are keys and login attempts are
     * values.
     */
    private final int MAX_ATTEMPT = 5;
    private LoadingCache<String, Integer> USERNAME_LOGIN_ATTEMPTS_CACHE = null;

    // TODO Instantiate and configure this cache (https://github.com/ben-manes/caffeine)
    public DefaultLoginService() {
        super();
        USERNAME_LOGIN_ATTEMPTS_CACHE = Caffeine.newBuilder().
                expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String event) {
                return 0;
            }
        });
    }

    @Override
    public void registerAuthenticationFailure(AuthenticationEvent event) {
        // TODO Implement this method     
        
        int attempts = 0;
        attempts = USERNAME_LOGIN_ATTEMPTS_CACHE.get(event.toString());
        attempts++;
        USERNAME_LOGIN_ATTEMPTS_CACHE.put(event.toString(), attempts);

    }

    @Override
    public void registerAuthenticationSuccess(AuthenticationEvent event) {
        // TODO Implement this method   
        USERNAME_LOGIN_ATTEMPTS_CACHE.invalidate(event);
    }

    @Override
    public boolean isBlocked(User user) {

        if (USERNAME_LOGIN_ATTEMPTS_CACHE.get(user.toString()) > MAX_ATTEMPT) {
            return false;
        }
        else
        {
        return true;
        }
    }
}
