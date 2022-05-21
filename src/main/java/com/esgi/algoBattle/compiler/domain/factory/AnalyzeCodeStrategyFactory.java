package com.esgi.algoBattle.compiler.domain.factory;

import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.domain.strategies.AnalyzeCodeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class AnalyzeCodeStrategyFactory {
    private Map<Language, AnalyzeCodeStrategy> strategies;

    @Autowired
    public AnalyzeCodeStrategyFactory(Set<AnalyzeCodeStrategy> strategies) {
        instanciateStrategies(strategies);
    }

    private void instanciateStrategies(Set<AnalyzeCodeStrategy> strategiesToInstanciate) {
        strategies = new HashMap<>();
        strategiesToInstanciate.forEach(strategie ->
                strategies.put(strategie.getStrategyLanguage(), strategie));
    }

    public AnalyzeCodeStrategy getStrategy(Language strategyName) {
        return strategies.get(strategyName);
    }
}
