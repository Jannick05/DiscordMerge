package dk.nydt.discordmerge;

import co.aikar.commands.PaperCommandManager;
import dk.nydt.discordmerge.commands.configs.Config;
import dk.nydt.discordmerge.commands.discord.AccountCommand;
import dk.nydt.discordmerge.commands.discord.CodeCommand;
import dk.nydt.discordmerge.commands.minecraft.ClaimCommand;
import dk.nydt.discordmerge.commands.minecraft.LinkCommand;
import dk.nydt.discordmerge.commands.minecraft.UnlinkCommand;
import dk.nydt.discordmerge.handlers.CodeHandler;
import dk.nydt.discordmerge.handlers.ConfigHandler;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class DiscordMerge extends JavaPlugin {
    @Getter
    public static DiscordMerge instance;
    @Getter
    public static JDA jda;

    @Getter
    public static PaperCommandManager commandManager;

    @Getter
    public static CodeHandler codeHandler;
    @Getter
    public static ObjectHandler objectHandler;
    @Getter
    public static SQLiteHandler sqliteHandler;
    @Getter
    public static ConfigHandler configHandler;

    @Getter
    public static Config configuration;

    @Override
    public void onEnable() {
        instance = this;

        codeHandler = new CodeHandler();
        objectHandler = new ObjectHandler();
        sqliteHandler = new SQLiteHandler();

        configuration = ConfigManager.create(Config.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new LinkCommand());
        commandManager.registerCommand(new UnlinkCommand());
        commandManager.registerCommand(new ClaimCommand());

        jda = start();
        registerDiscordCommands();

    }

    @Override
    public void onDisable() {
        jda.shutdown();
        try {
            SQLiteHandler.getConnectionSource().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static JDA start() {
        JDA jda = JDABuilder.createLight(configuration.botToken)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ONLINE)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .enableCache(CacheFlag.ACTIVITY)
                .setAutoReconnect(true)
                .addEventListeners(
                        new CodeCommand())
                .addEventListeners(
                        new AccountCommand())
                .build();
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jda;
    }

    private static void registerDiscordCommands() {
        Guild guild = jda.getGuildById(configuration.guildId);
        if(guild == null) return;
        guild.updateCommands().addCommands(
                Commands.slash("code", "Generates a code for linking your account"),
                Commands.slash("account", "Shows linked accounts")
                        .addOption(OptionType.USER, "player", "The player to show linked accounts of", false)
                ).queue();
    }
}
