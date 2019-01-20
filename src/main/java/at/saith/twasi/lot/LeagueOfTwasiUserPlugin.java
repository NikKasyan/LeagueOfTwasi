package at.saith.twasi.lot;

import at.saith.twasi.lot.variable.RankVariable;
import net.twasi.core.models.Message.TwasiMessage;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.TwasiVariable;
import net.twasi.core.plugin.api.events.TwasiMessageEvent;

import java.util.ArrayList;
import java.util.List;

public class LeagueOfTwasiUserPlugin extends TwasiUserPlugin {

    @Override
    public List<TwasiVariable> getVariables() {
        ArrayList<TwasiVariable> list = new ArrayList<>();
        list.add(new RankVariable(this));
        return list;
    }

    @Override
    public void onMessage(TwasiMessageEvent e) {
        TwasiMessage message = e.getMessage();
        if (message.getMessage().equalsIgnoreCase("rank")) {
            message.reply("$rank(Saith1998)");
        }
    }
}
