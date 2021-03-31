package io.github.davidmc971.modularmsmf.api;

import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;

/**
 * An interface for all common player-related data.
 * 
 * @author David Alexander Pfeiffer (davidmc971)
 * @since 0.3.0
 */
public interface IPlayerData {

    public InetAddress IPAdress();
    /**
     * The current name of a player. Can change.
     * 
     * @return Player name.
     */
    public String Name();

    /**
     * The universally unique identifier of a player. Does not change.
     * 
     * @return Player UUID.
     */
    public UUID ID();

    /**
     * The online state of the player.
     * 
     * @return true if player is online.
     */
    public boolean IsOnline();

    /**
     * Java Date of the last time a player logged into the server.
     * 
     * @return Last login Date.
     */
    public Date LastLogin();

    /**
     * The Location the player is currently at.
     * 
     * @return Current player location.
     */
    public Location CurrentLocation();

    /**
     * The world the player is currently in.
     * 
     * @return Current player world.
     */
    public World CurrentWorld();

    /**
     * A Configuration which holds all custom properties and data for a player in
     * memory. Saving is handled inside it's implemetation.
     * 
     * @return The player's Configuration object.
     */
    public Configuration PlayerConfiguration();
}
