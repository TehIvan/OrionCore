package com.devnics.orion;

import com.devnics.orion.component.Lobby;
import com.devnics.orion.listener.ServerReceiver;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;

public final class Orion extends Plugin {

    private final String host = "redis://default:@redis-10836.c8.us-east-1-4.ec2.cloud.redislabs.com:10836";

    private HashMap<String, Lobby> lobbies = new HashMap<>();

    @Setter
    private static Orion instance;

    @Override
    public void onEnable() {

        setInstance(this);
        Jedis jedis = new Jedis(this.host);

        new Thread(new Runnable() {
            @Override
            public void run() {
                jedis.subscribe(
                        new ServerReceiver(Orion.instance),
                        "lobby_server_register",
                        "minigame_server_register"
                );
            }
        }).start();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void addGameLobby(String id) {
        this.lobbies.put(
                id,
                new Lobby(id)
        );
        this.getProxy().getConsole().sendMessage(
                new TextComponent(this.translate("Minigame Lobby Registered: " + id))
        );
    }

    public void addGameServer(String id) {
        this.getProxy().getConsole().sendMessage(
                new TextComponent(this.translate("Minigame Server Registered: " + id))
        );
    }

}
