package dk.nydt.discordmerge.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@DatabaseTable(tableName = "minecraft_accounts")
public class MinecraftAccount extends BaseDaoEnabled<MinecraftAccount, Integer> {
    @Setter @Getter
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @Setter @Getter
    @DatabaseField(columnName = "uuid")
    private UUID uuid;
    @Setter @Getter
    @DatabaseField(columnName = "name")
    private String name;
    @Setter @Getter
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "linkedUser")
    private LinkedUser linkedUser;

    public MinecraftAccount() {
    }

    public MinecraftAccount(UUID uuid, String name, LinkedUser linkedUser) {
        this.uuid = uuid;
        this.name = name;
        this.linkedUser = linkedUser;
    }
}
