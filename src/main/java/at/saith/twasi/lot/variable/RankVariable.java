package at.saith.twasi.lot.variable;

import net.twasi.core.database.models.Language;
import net.twasi.core.interfaces.api.TwasiInterface;
import net.twasi.core.models.Message.TwasiMessage;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.TwasiVariable;

import java.util.Arrays;
import java.util.List;

public class RankVariable extends TwasiVariable {
    public RankVariable(TwasiUserPlugin owner) {
        super(owner);
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList("rank", "summoner");
    }

    @Override
    public String process(String name, TwasiInterface inf, String[] params, TwasiMessage message) {
        Language language = inf.getStreamer().getUser().getConfig().getLanguage();
        return VariableProcessor.process(name, params, language);
    }
}
