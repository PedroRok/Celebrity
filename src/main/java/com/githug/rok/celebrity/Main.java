package com.githug.rok.celebrity;

import com.githug.rok.celebrity.utils.PluginLogger;
import com.githug.rok.celebrity.utils.RokLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class Main extends JavaPlugin implements Listener {

    private static PluginLogger LOGGER;
    private static Main INSTANCE;
    private static CelebrityTask celebrityTask;

    @Override
    public void onEnable() {
        LOGGER = new RokLogger(getLogger());
        getServer().getPluginManager().registerEvents(this, this);
        INSTANCE = this;
        celebrityTask = new CelebrityTask();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, celebrityTask, 0, 1);
    }

    @Override
    public void onDisable() {
    }

    public static @NotNull PluginLogger logger() {
        return LOGGER;
    }

    public static @NotNull Main instance() {
        return INSTANCE;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            celebrityTask.addCelebrity(player.getUniqueId());
            player.sendMessage("You are op!");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            celebrityTask.removeCelebrity(player.getUniqueId());
        }
    }
}
