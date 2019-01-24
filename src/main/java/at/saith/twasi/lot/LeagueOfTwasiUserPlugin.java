package at.saith.twasi.lot;

import at.saith.twasi.lot.variable.RankVariable;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.TwasiVariable;

import java.util.ArrayList;
import java.util.List;

public class LeagueOfTwasiUserPlugin extends TwasiUserPlugin {

    @Override
    public List<TwasiVariable> getVariables() {
        ArrayList<TwasiVariable> list = new ArrayList<>();
        list.add(new RankVariable(this));
        return list;
    }
}
