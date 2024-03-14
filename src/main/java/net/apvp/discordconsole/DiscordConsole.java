package net.apvp.discordconsole;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordConsole extends JavaPlugin {

    private DiscordMessageListener messageListener;
    private String channelId;

    @Override
    public void onEnable() {
        // Load configuration
        saveDefaultConfig();

        // Get Discord bot token and channel ID from config.yml
        String botToken = getConfig().getString("discord.botToken");
        channelId = getConfig().getString("discord.channelId");
        if (botToken == null || botToken.isEmpty() || channelId == null || channelId.isEmpty()) {
            getLogger().warning("Discord bot token or channel ID is not provided in the config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize JDA
        messageListener = new DiscordMessageListener(channelId);
        JDABuilder.createDefault(botToken)
                .addEventListeners(messageListener)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL) // optional
                .setActivity(Activity.playing("Bot is running")) // optional
                .build();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("unloaddcs")) {
            // Unload the plugin
            getServer().getPluginManager().disablePlugin(this);
            return true;
        }
        return false;
    }
}
