package MPQSimulator.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ability {

  private List<AbilityComponent> components;
  
  public Ability() {
    components = new ArrayList<AbilityComponent>();
  }
  
  public Ability(Ability ability) {
    // Defensive copy
    components = new ArrayList<>(ability.components);
  }
  
  public Ability(AbilityComponent component) {
    // Defensive copy
    components = Arrays.asList(component);
  }
  
  public void addComponent(AbilityComponent component) {
    components.add(component);
  }
  
  public List<AbilityComponent> getComponents() {
    return components;
  }
}
