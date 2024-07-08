package dk.nydt.discordmerge.handlers;

import com.j256.ormlite.dao.ForeignCollection;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class ObjectHandler {
    private final CodeHandler codeHandler = DiscordMerge.getCodeHandler();
    public void createMerge(String discordId, String name, UUID uuid) {
        try {
            LinkedUser linkedUser = getOrCreateLinkedUser(discordId);
            MinecraftAccount minecraftAccount = getOrCreateMinecraftAccount(uuid, name, linkedUser);
            linkedUser.addMinecraftAccount(minecraftAccount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedUser getOrCreateLinkedUser(String discordId) throws SQLException {
        try {
            return SQLiteHandler.getLinkedUserDao().queryForEq("discordId", discordId).get(0);
        } catch (Exception e) {
            LinkedUser linkedUser = new LinkedUser(discordId);
            SQLiteHandler.getLinkedUserDao().create(linkedUser);
            return SQLiteHandler.getLinkedUserDao().queryForEq("discordId", discordId).get(0);
        }
    }

    public LinkedUser getLinkedUser(String discordId) throws SQLException {
        return SQLiteHandler.getLinkedUserDao().queryForEq("discordId", discordId).get(0);
    }

    public MinecraftAccount getOrCreateMinecraftAccount(UUID uuid, String name, LinkedUser linkedUser) throws SQLException {
        try {
            return SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", uuid).get(0);
        } catch (Exception e) {
            return new MinecraftAccount(uuid, name, linkedUser);
        }
    }

    public ForeignCollection<MinecraftAccount> getMinecraftAccounts(String discordId) throws SQLException {
        return getOrCreateLinkedUser(discordId).getMinecraftAccounts();
    }

    public Member getCachedUser(String id) {
        Guild guild = DiscordMerge.getJda().getGuildById(1168623927170437312L);
        UserSnowflake user = User.fromId(id);
        Long userId = user.getIdLong();
        CacheRestAction<Member> cache = Objects.requireNonNull(guild).retrieveMemberById(userId);
        return cache.complete();
    }

}
