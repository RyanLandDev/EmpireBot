package net.ryanland.empire.sys.util;

import net.ryanland.empire.bot.events.CommandEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class EvalUtil {
    private ScriptEngine engine;

    public EvalUtil(String scriptEngine) {
        this(scriptEngine, null);
    }
    public EvalUtil(String scriptEngine, CommandEvent event) {
        this.engine = new ScriptEngineManager().getEngineByName(scriptEngine);
        if (event != null) {
            this.engine.put("jda", event.getJDA());
            this.engine.put("event", event);
        }
    }

    public Object eval(String code) throws ScriptException {
        @SuppressWarnings("UnnecessaryLocalVariable")
        Object result = this.engine.eval(code);
        return result;
    }
}
