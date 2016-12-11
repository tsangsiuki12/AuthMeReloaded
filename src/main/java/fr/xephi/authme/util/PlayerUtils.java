package fr.xephi.authme.util;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Player utilities.
 */
public class PlayerUtils {
    public static final Map<String, String> IP_CACHE = new ConcurrentHashMap<>();

    // Utility class
    private PlayerUtils() {
    }

    /**
     * Get player's UUID if can, name otherwise.
     *
     * @param player Player to retrieve
     *
     * @return player's UUID or Name in String.
     */
    public static String getUUIDorName(OfflinePlayer player) {
        // We may made this configurable in future
        // so we can have uuid support.
        try {
            return player.getUniqueId().toString();
        } catch (NoSuchMethodError ignore) {
            return player.getName();
        }
    }

    /**
     * Returns the IP of the given player.
     *
     * @param p The player to return the IP address for
     *
     * @return The player's IP address
     */
    public static String getPlayerIp(Player p) {
        String key = getUUIDorName(p).toLowerCase();
        if (IP_CACHE.containsKey(key)) {
            return IP_CACHE.get(key);
        }
        InetAddress inetAddress = p.getAddress().getAddress();
        if (inetAddress == null) {
            // address is null, could probably happen when it get called async
            // in that case, we try to resolve the address again in the current thread
            String host = p.getAddress().getHostString();
            try {
                inetAddress = InetAddress.getByName(p.getAddress().getHostString());
            } catch (UnknownHostException e) {
                // should never happen, better than returning null
                return host;
            }
        }
        IP_CACHE.put(key, inetAddress.getHostAddress());
        return inetAddress.getHostAddress();
    }
}
