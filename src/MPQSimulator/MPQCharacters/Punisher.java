package MPQSimulator.MPQCharacters;

import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;

public class Punisher extends MPQCharacter {

	@Override
	protected List<Ability> initAbility1() {
		// TODO Auto-generated method stub
		return null;
	}

	//The Punisher unloads his arsenal. 
	// Destroys a 3x3 group of tiles, doing damage but not generating AP. 
	// Chaos creates an opening, converting a basic color tile to a strength 12 Strike tile.

	@Override
	protected List<Ability> initAbility2() {
		return MPQCharacter.buildRepeatAbilityList(new DestroySpecificTilesAbilityComponent(3, 3));
	}

	@Override
	protected List<Ability> initAbility3() {
		// TODO Auto-generated method stub
		return null;
	}

}
