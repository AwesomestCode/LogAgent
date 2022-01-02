package io.github.awesomestcode.logagent;

import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.text.PaperComponents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.io.IOException;

public class LogEventListeners implements Listener {
    @EventHandler
    public void onAsyncChatEvent(AsyncChatEvent event) throws IOException {
        LogUtils.getInstance().log(event.getPlayer().getName() + ": " + PaperComponents.plainTextSerializer().serialize(event.message()));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) throws IOException {
        LogUtils.getInstance().log(event.getPlayer().getName() + " issued server command: " + event.getMessage());
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) throws IOException {
        LogUtils.getInstance().log(event.getPlayer().getName() + " joined.");
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) throws IOException {
        LogUtils.getInstance().log(event.getPlayer().getName() + " left.");
    }

    @EventHandler
    public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) throws IOException {
        LogUtils.getInstance().log(event.getPlayer().getName() + " changed to " + event.getNewGameMode() + " (caused by " + event.getCause() + ")");
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) throws IOException {
        LogUtils.getInstance().log(event.getPlayer().getName() + " died.");
    }
}
