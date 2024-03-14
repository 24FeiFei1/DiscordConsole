package net.apvp.discordconsole;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class DiscordMessageListener extends ListenerAdapter {
    private String channelId; // Discord kanalının ID'sini saklamak için bir değişken

    // Constructor, Discord kanalının ID'sini alır
    public DiscordMessageListener(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Sadece belirtilen kanaldan gelen mesajları işle
        if (event.getChannel().getId().equals(channelId)) {
            // Filter out messages from bot itself
            if (event.getAuthor().isBot()) {
                return;
            }

            // Get the message content and send it to Minecraft console
            String messageContent = event.getMessage().getContentDisplay();
            sendToMinecraftConsole(messageContent);
        }
    }

    private void sendToMinecraftConsole(String message) {
        // Send message to Minecraft console
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), message);
    }
}
