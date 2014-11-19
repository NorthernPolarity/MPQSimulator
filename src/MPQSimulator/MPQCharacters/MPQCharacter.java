package MPQSimulator.MPQCharacters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public abstract class MPQCharacter {
  private final List<Ability> ability1;
  private final List<Ability> ability2;
  private final List<Ability> ability3;
  public enum AbilityLevel {ONE, TWO, THREE, FOUR, FIVE};
  private static final int NUM_LEVELS = 5;
  private static final Map<AbilityLevel, Integer> abilityLevelToListIndexMap = 
      ImmutableMap.<AbilityLevel, Integer>builder()
          .put(AbilityLevel.ONE, 0)
          .put(AbilityLevel.TWO, 1)
          .put(AbilityLevel.THREE, 2)
          .put(AbilityLevel.FOUR, 3)
          .put(AbilityLevel.FIVE, 4)
          .build();
  
  MPQCharacter() {
    this.ability1 = initAbility1();
    this.ability2 = initAbility2();
    this.ability3 = initAbility3();
    Preconditions.checkState(ability1 == null || ability1.size() == NUM_LEVELS);
    Preconditions.checkState(ability2 == null || ability2.size() == NUM_LEVELS);
    Preconditions.checkState(ability3 == null || ability3.size() == NUM_LEVELS);
  }
  
  protected abstract List<Ability> initAbility1();
  protected abstract List<Ability> initAbility2();
  protected abstract List<Ability> initAbility3();
  
  public Ability getAbility1(AbilityLevel level) {
    return getAbility(ability1, level);
  }
  
  public Ability getAbility2(AbilityLevel level) {
    return getAbility(ability2, level);
  }
  
  public Ability getAbility3(AbilityLevel level) {
    return getAbility(ability3, level);
  }
  
  private Ability getAbility(List<Ability> abilityList, AbilityLevel level) {
    return abilityList.get(abilityLevelToListIndexMap.get(level));
  }
  
  
	protected Ability buildAbility(AbilityComponent ... components) {
		Ability ability = new Ability();
		for( AbilityComponent c : components) {
			ability.addComponent(c);
		}
		return ability;
	}
	

	protected List<Ability> buildRepeatAbilityList(AbilityComponent c) {
		List<Ability> abilityList = new ArrayList<>();
	    Ability ability = new Ability();
	    ability.addComponent(c);
	    
	    for (int i = 0; i < 5; i++) {
	      abilityList.add(ability);
	    }
	    return abilityList;
	}

	protected List<Ability> buildAbilityList(Ability ... abilities) {
	    List<Ability> abilityList =  new ArrayList<>();
	    for( Ability a : abilities ) {
	    	abilityList.add(a);
	    }
	    return abilityList;
	}
	
}
