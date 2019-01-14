package at.saith.twasi.lot;

import at.saith.twasi.lot.lol.SummonerUtil;
import at.saith.twasi.lot.lol.data.database.mongodb.MongoDBFetcher;
import at.saith.twasi.lot.lol.data.exception.InvalidAPIKeyException;
import net.twasi.core.logger.TwasiLogger;
import net.twasi.core.plugin.TwasiPlugin;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DatabaseService;

public class LeagueOfTwasi extends TwasiPlugin {
    private static String prefix = "[League Of Twasi] ";

    @Override
    public void onDeactivate() {
        TwasiLogger.log.info(prefix + " disabled.");
    }

    @Override
    public void onActivate() {
        TwasiLogger.log.info(prefix + " enabled.");
        try {
            SummonerUtil.setup(new MongoDBFetcher(""));
        } catch (InvalidAPIKeyException e) {
            e.printStackTrace();
        }
    }

    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return LeagueOfTwasiUserPlugin.class;
    }

    public static DatabaseService getDataBase() {
        return ServiceRegistry.get(DatabaseService.class);
    }
    public static String getPrefix() {
        return prefix;
    }
}
