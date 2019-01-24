package at.saith.twasi.lot.lol.data.database.mongodb.summoner;

import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.NotSaved;

@Entity(value = "summoners", noClassnameStored = true)
public class MongoDBSummoner {

    @Id
    private String id;
    private String name;
    private Summoner summoner;
    private Region region;
    @NotSaved
    private boolean saveable = true;

    public MongoDBSummoner(Summoner summoner) {
        this.id = summoner.getProperties().getId();
        this.name = summoner.getProperties().getName();
        this.summoner = summoner;
        this.region = summoner.getRegion();
    }

    public MongoDBSummoner() {
        this(new Summoner());
        saveable = false;
    }

    public Summoner getSummoner() {
        return summoner;
    }
}