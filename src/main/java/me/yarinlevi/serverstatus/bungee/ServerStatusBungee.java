package me.yarinlevi.serverstatus.bungee;

import lombok.Getter;
import lombok.Setter;
import me.yarinlevi.serverstatus.shared.JdaCreation;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class ServerStatusBungee extends Plugin {
    private JdaCreation jdaCreation;
    @Getter @Setter private Configuration pluginConfig;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            pluginConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        jdaCreation = new JdaCreation(pluginConfig.getString("token"));

        int seconds = pluginConfig.getInt("update-every");

        getProxy().getScheduler().schedule(this, () -> jdaCreation.update(getProxy().getPlayers().size()), seconds, seconds, TimeUnit.SECONDS);
    }
}
