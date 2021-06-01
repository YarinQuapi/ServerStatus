package me.yarinlevi.serverstatus.bukkit;

import lombok.Getter;
import me.yarinlevi.serverstatus.shared.JdaCreation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerStatusSpigot extends JavaPlugin {

    @Getter private JdaCreation jdaCreation;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        String token = this.getConfig().getString("token");
        int seconds = this.getConfig().getInt("update-every");

        jdaCreation = new JdaCreation(token);

        Bukkit.getScheduler().runTaskTimer(this, () -> jdaCreation.update(Bukkit.getOnlinePlayers().size()), seconds * 20L, seconds * 20L);
    }
}
