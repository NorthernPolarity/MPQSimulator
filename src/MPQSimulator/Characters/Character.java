package MPQSimulator.Characters;

import MPQSimulator.Abilities.Ability;

public abstract class Character {
  public final Ability ability1;
  public final Ability ability2;
  public final Ability ability3;
  
  Character() {
    this.ability1 = getAbility1();
    this.ability2 = getAbility2();
    this.ability3 = getAbility3();
  }
  
  protected abstract Ability getAbility1();
  protected abstract Ability getAbility2();
  protected abstract Ability getAbility3();
}
