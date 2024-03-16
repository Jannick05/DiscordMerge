package dk.nydt.discordmerge.objects;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@DatabaseTable(tableName = "linked_users")
public class LinkedUser {
    @Setter @Getter
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @Setter @Getter
    @DatabaseField(columnName = "discordId")
    private String discordId;
    @Setter @Getter
    @ForeignCollectionField(columnName = "minecraftAccounts", eager = true)
    private ForeignCollection<MinecraftAccount> minecraftAccounts;

    public LinkedUser() {
    }

    public LinkedUser(String discordId) {
        this.discordId = discordId;
    }

    public void addMinecraftAccount(MinecraftAccount minecraftAccount) {
        this.minecraftAccounts.add(minecraftAccount);
    }

    public void removeMinecraftAccount(MinecraftAccount minecraftAccount) {
        this.minecraftAccounts.remove(minecraftAccount);
    }
}
