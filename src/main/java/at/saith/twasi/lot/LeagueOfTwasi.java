package at.saith.twasi.lot;

import at.saith.twasi.lot.api.LeagueOfTwasiResolver;
import at.saith.twasi.lot.lol.SummonerService;
import at.saith.twasi.lot.lol.data.database.mongodb.MongoDBFetcher;
import at.saith.twasi.lot.lol.data.exception.InvalidAPIKeyException;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import net.twasi.core.logger.TwasiLogger;
import net.twasi.core.plugin.TwasiPlugin;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DatabaseService;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Scanner;

public class LeagueOfTwasi extends TwasiPlugin {
    private static String prefix = "[League Of Twasi] ";

    @Override
    public void onDeactivate() {
        TwasiLogger.log.info(prefix + "disabled.");
    }

    @Override
    public void onActivate() {

        TwasiLogger.log.info(prefix + "enabled.");
    }
    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return LeagueOfTwasiUserPlugin.class;
    }

    @Override
    public GraphQLQueryResolver getGraphQLResolver() {
        return new LeagueOfTwasiResolver();
    }

    public static String getPrefix() {
        return prefix;
    }
}
