package at.saith.twasi.lot.api.models;

import at.saith.twasi.lot.api.DataFetcherRepository;
import at.saith.twasi.lot.lol.data.database.mongodb.MongoDBFetcher;
import net.twasi.core.database.models.User;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;


public class LeagueOfTwasiDTO {
    private DataFetcherRepository repository = ServiceRegistry.get(DataService.class).get(DataFetcherRepository.class);
    private User user;
    private MongoDBFetcher summonerFetcher;
    public LeagueOfTwasiDTO(User user) {
        this.user = user;
        String apikey = repository.getApikey(user);
        summonerFetcher = new MongoDBFetcher(apikey);
    }
    public boolean updateKey(String apiKey){
        boolean validKey = summonerFetcher.setAPIKey(apiKey);
        if(validKey) {
            repository.updateApiKey(user, apiKey);
        }
        return validKey;
    }

    public MongoDBFetcher getSummonerFetcher() {
        return summonerFetcher;
    }

    public MongoDBFetcher summonerFetcher(String apikey){
        return new MongoDBFetcher(apikey);
    }
}
