package com.devnics.orion.listener;

import com.devnics.orion.Orion;
import redis.clients.jedis.JedisPubSub;

public class ServerReceiver extends JedisPubSub {

    private Orion plugin;

    public ServerReceiver(Orion plugin) {
        this.plugin = plugin;
    }

    public void onMessage(String channel, String message) {

        if (channel.equalsIgnoreCase("lobby_server_register")) {
            this.plugin.addGameLobby(message);
        }

        if (channel.equalsIgnoreCase("minigame_server_register")) {
            this.plugin.addGameServer(message);
        }
    }

}
