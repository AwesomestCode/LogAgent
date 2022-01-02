package io.github.awesomestcode.logagent;

import org.bukkit.plugin.java.JavaPlugin;

public class LogAgent extends JavaPlugin {
    @Override
    public void onEnable() {
        if(System.getProperty("os.name").toLowerCase().contains("win")) throw new RuntimeException("This plugin expects to be run on a Unix or Unix-like system. Failed to load.");
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new LogEventListeners(), this);
        LogUtils.initialiseInstance(this.getConfig().getString("lokiServer"), this.getConfig().getString("serverName"), this.getConfig().getString("hostName"), this.getLogger());
    }
}
