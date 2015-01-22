package MPQSimulator.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;

public class AbilityImpl implements Ability {

  private List<AbilityComponent> components;
  
  @Inject
  public AbilityImpl() {
    components = new ArrayList<AbilityComponent>();
  }
  
  public AbilityImpl(AbilityImpl ability) {
    // Defensive copy
    components = new ArrayList<>(ability.components);
  }
  
  public AbilityImpl(AbilityComponent component) {
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
