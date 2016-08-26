package com.kuaimei56.cjkzy_helper.utils;

import com.kuaimei56.cjkzy_helper.entity.Strategy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CLEVO on 2016/7/22.
 */
public class RegexMatch {
    List<Strategy> strategyList;
    String str;
    List<Boolean> isMatchList;
    boolean isAllMatch = false;

    public RegexMatch(List<Strategy> strategyList, String str) {
        this.strategyList = strategyList;
        this.str = str;
    }

    public List<Strategy> getStrategyList() {
        return strategyList;
    }

    public void setStrategyList(List<Strategy> strategyList) {
        this.strategyList = strategyList;
    }

    public List<Boolean> getIsMatchList() {
        return isMatchList;
    }

    public boolean isAllMatch() {
        return isAllMatch;
    }

    public boolean checkAll() {
        isAllMatch = false;
        if ((null != str) && (null != strategyList)) {
            for (Strategy strategy : strategyList) {
                if (strategy.getValid() > 0) {
                    // 按指定模式在字符串查找
                    String regex = strategy.getRegex();
                    // 创建 Pattern 对象
                    Pattern r = Pattern.compile(regex);

                    // 现在创建 matcher 对象
                    Matcher m = r.matcher(str);
                    // 策略组：找到一条对应就认为成功。
                    if ((strategy.getMode() == 1) && (strategy.getResultMode() == 0)) {
                        if (m.find()) {
                            isAllMatch = true;
                        }
                    }
                }

            }
        }
        return isAllMatch;
    }
}
