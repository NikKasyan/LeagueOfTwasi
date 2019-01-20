package at.saith.twasi.lot;

import at.saith.twasi.lot.lol.SummonerUtil;
import at.saith.twasi.lot.lol.data.database.mongodb.MongoDBFetcher;
import at.saith.twasi.lot.lol.data.exception.InvalidAPIKeyException;
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
        readAPIKey();
    }


    public void readAPIKey() {
        File f = new File("apikey.txt");
        try {

            String apiKey = "";
            if (!f.exists()) {
                Scanner scanner = new Scanner(System.in);
                TwasiLogger.log.info(prefix + "enter a valid API-Key");
                System.out.print(">");
                FileWriter writer = new FileWriter(f);
                apiKey = scanner.nextLine().replaceAll("\\s", "");
                writer.write(apiKey);
                writer.flush();
                writer.close();
            } else {
                apiKey = new String(Files.readAllBytes(f.toPath()));
            }
            SummonerUtil.setup(new MongoDBFetcher(apiKey));
        } catch (InvalidAPIKeyException e) {
            f.delete();
            System.out.println(e.getMessage() + " Try again.");

            readAPIKey();
        } catch (Exception ex) {
            ex.printStackTrace();
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
