package at.saith.twasi.lot.variable;

import at.saith.twasi.lot.api.DataFetcherRepository;
import at.saith.twasi.lot.lol.SummonerService;
import at.saith.twasi.lot.lol.data.database.mongodb.MongoDBFetcher;
import at.saith.twasi.lot.lol.summoner.*;
import net.twasi.core.database.models.Language;
import net.twasi.core.database.models.User;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;

import java.util.Arrays;

public class VariableProcessor {
    private static final DataFetcherRepository repository = ServiceRegistry.get(DataService.class).get(DataFetcherRepository.class);
    private MongoDBFetcher summonerFetcher;
    public VariableProcessor(User user){
        summonerFetcher =  new MongoDBFetcher(repository.getApikey(user));
    }
    public String process(String name, String[] params, Language language) {
        String variable = "";

       if(!summonerFetcher.hasValidKey())return "INVALID_API_KEY";
        try {
            if (params == null)
                return variable;
            //$rank((summonerId|summonerName),[stat],[region])
            if (params.length >= 1 && params.length <= 4) {
                //SummonerIdentifier may be the summonerId or summonerName
                String summonerIdentifier = params[0];
                String varName = "rankedstats";
                String regionString = "euw1";
                String queueTypeString = QueueType.RANKED_SOLO_5X5.name();
                if (params.length >= 2) {
                    varName = params[1];
                }
                if (params.length >= 3) {
                    regionString = params[2];
                }
                if (params.length >= 4) {
                    queueTypeString = params[3];
                }
                Region region = Region.byName(regionString);
                QueueType type = QueueType.byName(queueTypeString);
                if (name.equalsIgnoreCase("rank")) {
                    variable = getRankVariable(varName, summonerIdentifier, region, type);
                } else {
                    variable = getPropertyVariable(varName, summonerIdentifier, region);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR loading Summoner(League of Twasi)Variable = " + Arrays.toString(params);
        }

        return variable;
    }

    private String getPropertyVariable(String varName, String summonerIdentifier, Region region) {
        SummonerProperties properties = summonerFetcher.getPropertyByIdentifier(summonerIdentifier,region);
        return getPropertyVariable(varName, properties);
    }

    private String getRankVariable(String varName, String summonerIdentifier, Region region, QueueType type) {
        Summoner summoner = summonerFetcher.getSummonerByIdentifier(summonerIdentifier,region);
        String variable;
        SummonerRankedStats stats = summoner.getRankedStats(type);

        if (varName.equalsIgnoreCase("rankedstats")) {
            variable = rankedStatsToString(stats);
        } else {
            variable = getRankVariable(varName, stats);
        }
        return variable;
    }

    private static String getRankVariable(String varName, SummonerRankedStats stats) {
        String variable = "";
        switch (varName.toLowerCase()) {
            case "rank":
                variable = stats.getRank();
                break;
            case "tier":
                variable = stats.getTier();
                break;
            case "lp":
                variable += stats.getLeaguePoints();
                break;
            case "losses":
                variable += stats.getLosses();
                break;
            case "wins":
                variable += stats.getWins();
                break;
            case "series":
                variable += stats.getMiniSeriesProgress();
                break;
        }
        return variable;
    }

    private static String rankedStatsToString(SummonerRankedStats stats) {
        String variable = "";
        if (stats == null) {
            variable = "UNRANKED";
        } else {
            variable = stats.getTier() + " " + stats.getRank() + " " + stats.getLeaguePoints() + "LP";
            if (stats.hasMiniSeries()) {
                variable += " " + stats.getMiniSeriesProgress();
            }
        }
        return variable;
    }

    private static String getPropertyVariable(String varName, SummonerProperties prop) {
        String variable = "";
        switch (varName.toLowerCase()) {
            //PROPERTIES
            case "name":
                variable = prop.getName();
                break;
            case "accountid":
                variable += prop.getAccountId();
                break;

            case "summonerid"://Fall Through
            case "id":
                variable += prop.getId();
                break;
            case "profileiconid":
                variable += prop.getProfileIconId();
                break;
            case "summonerlevel":
            case "level":
                variable += prop.getSummonerLevel();
                break;
        }
        return variable;
    }
}
