package HealthRules;

import java.util.ArrayList;
import java.util.List;
import org.easyrules.api.RulesEngine;
import org.springframework.stereotype.Service;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

@Service
public class WeightRulesService {

	public static List<BasicWeightRule> weightRules;

	public WeightRulesService() {
		weightRules = new ArrayList<BasicWeightRule>();
	}

	public String activateWeightRulesEngine(int weight) {
		UnderWeightRule underWeightRule = new UnderWeightRule();
		OverWeightRule overWeightRule = new OverWeightRule();
		weightRules.add(underWeightRule);
		weightRules.add(overWeightRule);

		RulesEngine rulesEngine = aNewRulesEngine().build();

		rulesEngine.registerRule(underWeightRule);
		rulesEngine.registerRule(overWeightRule);

		// set the current weight on rules
		CurrentWeight currentWeight = new CurrentWeight(weight);
		for (BasicWeightRule rule : weightRules) {
			rule.setGameState(currentWeight);
		}

		rulesEngine.fireRules();
		System.out.println(currentWeight.getAlertMessage());
		return currentWeight.getAlertMessage();
	}

}