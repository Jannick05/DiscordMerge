package dk.nydt.discordmerge.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.HashMap;
import java.util.Map;

public class Config extends OkaeriConfig {
    @Comment("The bot token for the Discord bot, and the guild ID for the server the bot is in.")
    public String botToken = "TOKEN_HERE";
    public String guildId = "GUILD_ID_HERE";
    @Comment("Should the roles be added automatically when a player links their account?")
    public boolean autoRoles = true;

    @Comment("The permission a player should have to claim a Discord role.")
    public Map<Integer, Map<String, String>> rolePermissions = new HashMap<Integer, Map<String, String>>() {{
        put(1, new HashMap<String, String>() {{
            put("permission", "vagt.permission");
            put("roleId", "958380098061467648");
        }});
    }};
}