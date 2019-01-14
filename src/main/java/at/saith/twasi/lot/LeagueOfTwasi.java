package at.saith.twasi.lot;

import at.saith.twasi.lot.lol.SummonerUtil;
import at.saith.twasi.lot.lol.data.DefaultSummonerFetcher;
import net.twasi.core.logger.TwasiLogger;
import net.twasi.core.plugin.TwasiPlugin;
import net.twasi.core.plugin.api.TwasiUserPlugin;

public class LeagueOfTwasi extends TwasiPlugin {
    private static String prefix = "[League Of Twasi] ";

    @Override
    public void onDeactivate() {
        TwasiLogger.log.info(prefix + " disabled.");
    }

    @Override
    public void onActivate() {
        TwasiLogger.log.info(prefix + " enabled.");
        SummonerUtil.setup(new DefaultSummonerFetcher());
    }

    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return LeagueOfTwasiUserPlugin.class;
    }

    public static String getPrefix() {
        return prefix;
    }
}
