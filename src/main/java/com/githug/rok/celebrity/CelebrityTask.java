package com.githug.rok.celebrity;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class CelebrityTask implements Runnable {

    // TODO: Make config system !!!
    private static final int DISTANCE = 6;
    private static final double MIN_TPS = 18.0;
    private static final double MIN_TICKS = 2.0;
    private static final boolean USE_KNOCKBACK = true;
    private static final double KNOCKBACK_DISTANCE = 0.5; // TODO: Remove final
    public static final boolean USE_DEBUG = true;

    private final ArrayList<UUID> celebrities = new ArrayList<>();
    private final Map<UUID, UUID> invisible = new HashMap<>(); // player - celebrity
    List<UUID> toRemove = new ArrayList<>();
    // Variable
    private int ticks = 0;
    private static final int MAX_TICKS = (int) (MIN_TPS * MIN_TICKS);

    public CelebrityTask() {
        Main.logger().debug("CelebrityTask created");
    }

    @Override
    public void run() {
        int calcTicks = 1;
        if (getTPS() < MIN_TPS) {
            double v = MIN_TPS / getTPS();
            calcTicks = (v) > 1 ? (int) (v) : MAX_TICKS;
        }
        if (ticks > MIN_TICKS * calcTicks) {
            ticks = 0;

            celebrities.forEach(uuid -> {
                Player player = Bukkit.getPlayer(uuid);
                player.getLocation().getNearbyLivingEntities(DISTANCE - 2.2,
                        i -> i.getType().equals(EntityType.PLAYER)
                                && i != player
                                && !invisible.containsKey(i.getUniqueId())
                ).forEach(entity -> {
                    if (USE_KNOCKBACK) {
                        CelebrityManager.knockback(((Player) entity), player, KNOCKBACK_DISTANCE);
                        return;
                    }
                    invisible.put(entity.getUniqueId(), player.getUniqueId());
                    player.hidePlayer(Main.instance(), ((Player) entity));
                });
            });
            if (USE_KNOCKBACK) {
                ticks++;
                return;
            }

            for (Map.Entry<UUID, UUID> entry : invisible.entrySet()) {
                Player player = Bukkit.getPlayer(entry.getKey());
                Player entity = Bukkit.getPlayer(entry.getValue());
                if (entity.getLocation().distance(player.getLocation()) > DISTANCE) {
                    entity.showPlayer(Main.instance(), player);
                    toRemove.add(entry.getKey());
                }
            }
            toRemove.forEach(invisible::remove);
            toRemove.clear();
        }
        ticks++;
    }

    public void addCelebrity(UUID uuid) {
        Main.logger().debug(Bukkit.getPlayer(uuid) + " Celebrity added");
        celebrities.add(uuid);
    }

    public void removeCelebrity(UUID uuid) {
        Main.logger().debug(Bukkit.getPlayer(uuid) + " Celebrity removed");
        celebrities.remove(uuid);
    }

    public boolean isCelebrity(UUID uuid) {
        return celebrities.contains(uuid);
    }

    private double getTPS() {
        return Main.instance().getServer().getTPS()[0];
    }
}
