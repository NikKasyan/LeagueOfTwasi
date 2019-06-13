package at.saith.twasi.lot.api;

import net.twasi.core.database.models.BaseEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
@Entity(value = "datafetcher", noClassnameStored = true)
public class DataFetcherEntity extends BaseEntity {
    @Id
    private String name;
    private String apikey;

    public DataFetcherEntity(String name, String apikey) {
        this.name = name;
        this.apikey = apikey;
    }

    public String getApikey() {
        return apikey;
    }
}
