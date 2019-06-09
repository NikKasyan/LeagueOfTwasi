package at.saith.twasi.lot;

import at.saith.twasi.lot.variable.RankVariable;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.TwasiVariable;
import net.twasi.core.plugin.api.events.TwasiEnableEvent;

import java.util.ArrayList;
import java.util.List;

public class LeagueOfTwasiUserPlugin extends TwasiUserPlugin {
    @Override
    public void onEnable(TwasiEnableEvent e) {
        registerVariable(RankVariable.class);
    }
}
