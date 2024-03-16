package dk.nydt.discordmerge.handlers;

import com.j256.ormlite.dao.ForeignCollection;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;

import java.sql.SQLException;
import java.util.UUID;

public class ObjectHandler {
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
        LinkedUser linkedUser;
        try {
            return SQLiteHandler.getLinkedUserDao().queryForEq("discordId", discordId).get(0);
        } catch (Exception e) {
            linkedUser = new LinkedUser(discordId);
            SQLiteHandler.getLinkedUserDao().create(linkedUser);
            return SQLiteHandler.getLinkedUserDao().queryForEq("discordId", discordId).get(0);
        }
    }

    public MinecraftAccount getOrCreateMinecraftAccount(UUID uuid, String name, LinkedUser linkedUser) throws SQLException {
        MinecraftAccount minecraftAccount;
        try {
            return SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", uuid).get(0);
        } catch (Exception e) {
            minecraftAccount = new MinecraftAccount(uuid, name, linkedUser);
            SQLiteHandler.getMinecraftAccountDao().create(minecraftAccount);
            return SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", uuid).get(0);
        }
    }

    public ForeignCollection<MinecraftAccount> getMinecraftAccounts(String discordId) throws SQLException {
        return getOrCreateLinkedUser(discordId).getMinecraftAccounts();
    }
}
