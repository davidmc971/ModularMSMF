package io.github.davidmc971.modularmsmf.basics.util;

import org.bukkit.entity.Player;

/**
 * @author David Alexander Pfeiffer (davidmc971)
 */

public class PlayerKillConfig {
    private Player p;
    private KillType kt;

    public PlayerKillConfig(Player p, KillType kt) {
        this.p = p;
        this.kt = kt;
    }

    public Player getP() {
        return p;
    }

    public KillType getKt() {
        return kt;
    }
}
