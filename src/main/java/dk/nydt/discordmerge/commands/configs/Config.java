package dk.nydt.discordmerge.commands.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.HashMap;
import java.util.Map;

public class Config extends OkaeriConfig {
    @Comment("The bot token for the Discord bot, and the guild ID for the server the bot should be in.")
    public String botToken = "OTI1NTY1NTA5MzAxMzIxODE4.G_TQqd.g7Ho7P-HaeFDMrV37bTSxqgFoMTO3TylvrM8wk";
    public String guildId = "958069975443177542";

    @Comment("The permission a player should have to claim a Discord role.")
    public Map<Integer, Map<String, String>> rolePermissions = new HashMap<Integer, Map<String, String>>() {{
        put(1, new HashMap<String, String>() {{
            put("permission", "discordmerge.claim");
            put("roleId", "958380098061467648");
        }});
    }};
}
