package net.ryanland.empire.sys.util;

import javax.script.ScriptException;
import java.util.HashMap;

public class FormulaUtil {

    //TODO math parser
    private static final HashMap<String, Object> CACHE = new HashMap<>();

    private static String eval(String formula) throws ScriptException {
        EvalUtil Eval = new EvalUtil("nashorn");
        return String.valueOf(Eval.eval(formula));
    }

    public static int getInt(String formula) {
        return getInt(formula, -1);
    }

    public static int getInt(String formula, int stage) {
        if (stage <= 0) formula = formula.replaceAll("stage", String.valueOf(stage));
        Integer cachedResult = (Integer) CACHE.get("i" + formula);
        if (cachedResult != null) return cachedResult;
        try {
            int result = Integer.parseInt(eval(formula));
            CACHE.put("i" + formula, result);
            return result;
        } catch (ScriptException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static double getDouble(String formula) {
        return getDouble(formula, -1);
    }
    public static double getDouble(String formula, int stage) {
        if (stage <= 0) formula = formula.replaceAll("stage", String.valueOf(stage));
        Double cachedResult = (Double) CACHE.get("d" + formula);
        if (cachedResult != null) return cachedResult;
        try {
            double result = Double.parseDouble(eval(formula));
            CACHE.put("d" + formula, result);
            return result;
        } catch(ScriptException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
