package dk.nydt.discordmerge.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@DatabaseTable(tableName = "boost_codes")
public class BoostCode extends BaseDaoEnabled<BoostCode, Integer> {
    @Getter @Setter
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @Getter @Setter
    @DatabaseField(columnName = "code")
    private String code;

    public BoostCode() {
    }

    public BoostCode(String code) {
        this.code = code;
    }

}
