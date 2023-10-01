package com.githug.rok.celebrity;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CelebrityManager {
    public static void knockback(Player player, Player celebrity, double distance) {
        Vector vector = player.getLocation().toVector().subtract(celebrity.getLocation().toVector()).normalize().multiply(distance);
        player.setVelocity(vector);
    }
}
