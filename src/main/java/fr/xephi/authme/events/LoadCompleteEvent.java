package fr.xephi.authme.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * All verifications checks passed and player protecting are initialized on the main thread
 */
public class LoadCompleteEvent extends CustomEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final boolean authAvailable;
    private final boolean sessionLogin;
    private boolean cancelled;

    /**
     * Constructor.
     *
     * @param player The player
     * @param authAvailable
     * @param sessionLogin
     */
    public LoadCompleteEvent(Player player, boolean authAvailable, boolean sessionLogin) {
        this.player = player;
        this.authAvailable = authAvailable;
        this.sessionLogin = sessionLogin;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * @return if this account is registered
     */
    public boolean isAuthAvailable() {
        return authAvailable;
    }

    /**
     * Session logins are enabled and everything was checked to login the player after this event
     *
     * @return if the player will be logged in with a session auto login
     */
    public boolean isSessionLogin() {
        return sessionLogin;
    }

    /**
     * @return if further initializations are canceled like login message or timeout kicks
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * If this even is canceled no timeout task and login task will be started. This means the player won't see a
     * please register/login message and won't be kicked after a amount of time.
     * <p>
     * Moreover session auto logins will be prevented. So you might want check it with isSessionLogin()
     *
     * @param cancelled
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Return the list of handlers, equivalent to {@link #getHandlers()} and required by {@link Event}.
     *
     * @return The list of handlers
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
