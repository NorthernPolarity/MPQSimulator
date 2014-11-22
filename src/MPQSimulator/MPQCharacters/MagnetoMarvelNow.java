package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class MagnetoMarvelNow extends MPQCharacter {


	//Iron Hammer - Blue 6 AP
	//Magneto forms a mass of sharpnel to hurl at his enemies. Converts a random Blue tile into a 1 turn Countdown tile that destroys the 9 surrounding tiles.
	//Level Upgrades
	//Level 2: Increases tiles destroyed to 13, +1 AP Cost.
	//Level 3: Increases tiles destroyed to 21, +1 to countdown timer, +2 AP Cost.
	//Level 4: Increases tiles destroyed to 29, +1 to countdown timer.
	//Level 5: Increase area of effect to entire board, +1 to Countdown timer, +4 AP Cost.

	@Override
	protected List<Ability> initAbility1() {
		int[] tilesDestroyedByLevel = {9, 13, 21, 29, DestroyTileAbilityComponent.DESTROY_ALL_TILES};
		List<Ability> abilityList = new ArrayList<>();

		// TODO: figure out how to use DestroySpecificTilesAbilityComponent here instead to blow up 
		// MNMags' specific shapes
		for (int i = 0; i < 5; i++) {
			AbilityComponent destroyTiles = new DestroyTileAbilityComponent
					(tilesDestroyedByLevel[i], AbilityComponent.ALL_COLORS_LIST);
			Ability ability = new Ability();
			ability.addComponent(destroyTiles);
			abilityList.add(ability);
		}
		return abilityList;
	}

	//	Polarity Shift - Purple 11 AP
	//	Magneto uses the sheer force of magnetism to reconfigure the battlefield. Changes 3 selected basic color tiles to Blue.
	//	Level Upgrades
	//	Level 2: Costs 1 AP less.
	//	Level 3: +1 tile affected (total 4).
	//	Level 4: Costs 1 AP less.
	//	Level 5: +1 tile affected (total 5).
	@Override
	protected List<Ability> initAbility2() {
		int[] tilesChangedByLevel = {3, 3, 4, 4, 5};
		// TODO Auto-generated method stub
		List<Ability> abilityList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {

			AbilityComponent changeTiles = new ChangeTileColorAbilityComponent(tilesChangedByLevel[i], TileColor.BLUE);
			Ability ability = new Ability();
			ability.addComponent(changeTiles);
			abilityList.add(ability);
		}
		return abilityList;
	}

	//	Magnetic Flux - Red 8 AP
	//	Magneto creates an unstable magnetic field. Turns a random basic Red tile into a 3 turn Countdown tile that deals 183 damage to the current target.
	//	Level Upgrades
	//	Level 2: Reduces countdown to 2, +1 AP cost.
	//	Level 3: Destroy a 3x3 block around the Countdown tile. +2 AP cost.
	//	Level 4: Reduces countdown to 1.
	//	Level 5: Destroy a 5x5 block, +3 AP cost.
	//	Max Level: 1936 damage
	@Override
	protected List<Ability> initAbility3() {
		DestroySpecificTilesAbilityComponent destroy1x1 = new DestroySpecificTilesAbilityComponent(1, 1);
		DestroySpecificTilesAbilityComponent destroy3x3 = new DestroySpecificTilesAbilityComponent(3, 3);
		DestroySpecificTilesAbilityComponent destroy5x5 = new DestroySpecificTilesAbilityComponent(5, 5);

		return buildAbilityList(
				buildAbility(destroy1x1),
				buildAbility(destroy1x1),
				buildAbility(destroy3x3),
				buildAbility(destroy3x3),
				buildAbility(destroy5x5));

	}

}
