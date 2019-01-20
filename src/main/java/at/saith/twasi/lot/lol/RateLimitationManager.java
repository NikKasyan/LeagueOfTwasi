package at.saith.twasi.lot.lol;

import java.net.URL;
import java.util.HashMap;

public class RateLimitationManager {
    /**
     * Check for HeaderFields in JsonUrlParser
     */
    private HashMap<URL, RateLimitation> rateLimits = new HashMap<>();

    public RateLimitationManager(URL... urls) {

    }

    public boolean contains(URL url) {
        return rateLimits.containsKey(url);
    }

    public boolean check(URL url) {
        return true;
        //return rateLimits.get(url).check();
    }
}
