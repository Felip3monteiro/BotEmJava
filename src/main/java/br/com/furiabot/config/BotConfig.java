package br.com.furiabot.config;

import io.github.cdimascio.dotenv.Dotenv;

public class BotConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getToken() {
        return dotenv.get("BOT_TOKEN");
    }
}
