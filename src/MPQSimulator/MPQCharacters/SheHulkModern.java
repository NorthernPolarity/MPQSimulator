package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;

public class SheHulkModern extends MPQCharacter {

	@Override
	protected List<Ability> initAbility1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Ability> initAbility2() {
		// TODO Auto-generated method stub
		return null;
	}

	//	Power Of Attorney - Red 9 :red: 
	//		She-Hulk strikes the ground, causing the earth to shift under her enemies' feet. Destroys 10 tiles from the bottom of the board and deals 83 Damage to the enemy team. Does not generate AP.
	//		Level 2: Deals 120 Damage
	//		Level 3: Causes a bigger shock-wave, destroying 13 Tiles
	//		Level 4: Deals 172 Damage
	//		Level 5: Destroys 16 Tiles and deals 224 Damage
	//		Max Level: Destroys 16 Tiles and deals 1401 Damage
	@Override
	protected List<Ability> initAbility3() {
		// TODO Auto-generated method stub
		int[] tilesDestroyedByLevel = {10, 10, 13, 13, 16};
		List<Ability> abilityList = new ArrayList<>();

		// TODO: figure out how to use DestroySpecificTilesAbilityComponent here instead to blow up 
		// MNMags' specific shapes
		for (int i = 0; i < 5; i++) {
			// TODO: have to figure out what this pattern should be
			// 1. has to include the bottom-most row
			// 2. I think there is some randomness in which columns are destroyed
			//    but for now just make a crude approximation
			boolean[][] pattern = new boolean[8][8];

			int startingRow = pattern.length - 1; // bottom-most row
			int remaining = tilesDestroyedByLevel[i];
			while( remaining > 0 ) {
				if( remaining > pattern[startingRow].length ) {
					Arrays.fill(pattern[startingRow], true);
					remaining -= pattern[startingRow].length;
				} else {
					for( int j = 0; j < remaining; j++ ) {
						pattern[startingRow][j] = true;
						remaining -= 1;
					}
					assert(remaining == 0);
					break;
				}
				startingRow -= 1;
			}
			
			
			AbilityComponent destroyTiles = new DestroySpecificTilesAbilityComponent(pattern);
			Ability ability = new Ability();
			ability.addComponent(destroyTiles);
			abilityList.add(ability);
		}
		return abilityList;
	}

}
