package dk.nydt.discordmerge.handlers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import lombok.Getter;

import java.io.File;

public class SQLiteHandler {
    @Getter
    private static JdbcConnectionSource connectionSource;
    @Getter
    private static Dao<LinkedUser, Integer> linkedUserDao;
    @Getter
    private static Dao<MinecraftAccount, Integer> minecraftAccountDao;

    public SQLiteHandler() {
        try {
            connectionSource = new JdbcConnectionSource(String.format("jdbc:sqlite:%s%s%s.db", DiscordMerge.getInstance().getDataFolder(), File.separator, DiscordMerge.getInstance().getDescription().getName()));
            setupDatabase(connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDatabase(ConnectionSource connectionSource) throws Exception {
        TableUtils.createTableIfNotExists(connectionSource, LinkedUser.class);
        linkedUserDao = DaoManager.createDao(connectionSource, LinkedUser.class);

        TableUtils.createTableIfNotExists(connectionSource, MinecraftAccount.class);
        minecraftAccountDao = DaoManager.createDao(connectionSource, MinecraftAccount.class);
    }
}
