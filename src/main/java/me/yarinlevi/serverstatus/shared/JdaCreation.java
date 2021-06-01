package me.yarinlevi.serverstatus.shared;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;

import javax.security.auth.login.LoginException;
import java.util.Timer;
import java.util.TimerTask;

public class JdaCreation {
    @Getter private JDA jda;

    public JdaCreation(String token) {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setAutoReconnect(true);

        try {
            jda = jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public void update(int playerCount) {
        jda.getPresence().setActivity(Activity.watching(playerCount + " players"));
    }
}
