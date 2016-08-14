package HealthRules;

import org.easyrules.core.BasicRule;

//TODO the "football game state" stuff should be a separate interface
public abstract class BasicWeightRule extends BasicRule {

  protected CurrentWeight currentWeight;

  public void setGameState(CurrentWeight gameState) {
      this.currentWeight = gameState;
  }
      
}
