package at.saith.twasi.lot.api;

import at.saith.twasi.lot.api.models.LeagueOfTwasiDTO;
import net.twasi.core.database.models.User;
import net.twasi.core.graphql.TwasiCustomResolver;

public class LeagueOfTwasiResolver extends TwasiCustomResolver {
    public LeagueOfTwasiDTO getLeagueoftwasi(String token){
        User user = getUser(token);
        if(user == null){
            return null;
        }
        if(!user.getInstalledPlugins().contains("LeagueOfTwasi")){
            return null;
        }
        return new LeagueOfTwasiDTO(user);
    }
}
