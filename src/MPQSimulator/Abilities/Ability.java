package MPQSimulator.Abilities;

import java.util.List;

public interface Ability {

  public void addComponent(AbilityComponent component);
  
  public List<AbilityComponent> getComponents();
  
}
