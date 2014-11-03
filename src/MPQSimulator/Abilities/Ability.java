package MPQSimulator.Abilities;

import java.util.ArrayList;
import java.util.List;

public class Ability {

  private List<AbilityComponent> components;
  
  public Ability() {
    components = new ArrayList<AbilityComponent>();
  }
  
  public void addComponent(AbilityComponent component) {
    components.add(component);
  }
  
  public List<AbilityComponent> getComponents() {
    return components;
  }
}
