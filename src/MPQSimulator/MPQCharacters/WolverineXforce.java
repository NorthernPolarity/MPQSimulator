package MPQSimulator.MPQCharacters;

import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;

public class WolverineXforce extends MPQCharacter {

	//	X-Force - Green 8 AP 
	//	Wolverine unleashes his berserker rage, cutting with his adamantium claws. Deals 164 damage and destroys 5 tiles in a 3×3 X-shaped pattern. Does not generate AP.
	//	Level 2: Deals 465 damage.
	//	Level 3: Destroys 9 tiles in a 5×5 X-shaped pattern.
	//	Level 4: Deals 1030 damage.
	//	Level 5: Deals 1731, then destroys 15 tiles in three 3×3 X-shaped patterns.
	//	Max Level: 3441 damage
	//  from http://www.d3pforums.com/viewtopic.php?f=14&t=552
	
	@Override
	protected List<AbilityImpl> initAbility1() {
		
		boolean[][] pattern3x3 = { 
				{ true, false, true },
				{ false, true, false },
				{ true, false, true }
		};
		DestroySpecificTilesAbilityComponent destroy3x3 
		  = new DestroySpecificTilesAbilityComponent(pattern3x3, false, new Tile.RandomCallerImpl());

		boolean[][] pattern5x5 = { 
				{ true, false, false, false, true },
				{ false, true, false, true, false },
				{ false, false, true, false, false },
				{ false, true, false, true, false },
				{ true, false, false, false, true },
		};
		DestroySpecificTilesAbilityComponent destroy5x5 
		  = new DestroySpecificTilesAbilityComponent(pattern5x5, false, new Tile.RandomCallerImpl());

		return buildAbilityList(
				buildAbility(destroy3x3),
				buildAbility(destroy3x3),
				buildAbility(destroy5x5),
				buildAbility(destroy5x5),
				buildAbility(destroy3x3, destroy3x3, destroy3x3));
				
	}

	//	Surgical Strike - Black 11 AP
	//	Wolverine attacks with ruthless precision, crippling his enemy's ability to retaliate. Destroys every tile in the enemy team's strongest color, dealing 32 damage per tile. Does not generate AP.
	//	Level 2: Deals 87 damage per tile.
	//	Level 3: Deals 137 damage per tile.
	//	Level 4: Also reduces enemy AP in their strongest color by 10.
	//	Level 5: Deals 265 damage per tile. Destroyed tiles now generate AP.
	//	Max Level: 522 damage per tile
	//	from http://www.d3pforums.com/viewtopic.php?f=14&t=552
	@Override
	protected List<AbilityImpl> initAbility2() {
		// TODO: not sure what color to use, hard-coding GREEN for now
		return MPQCharacter.buildRepeatAbilityList(
		    new DestroyTileAbilityComponent(DestroyTileAbilityComponent.DESTROY_ALL_TILES, TileColor.GREEN));
	}

	@Override
	protected List<AbilityImpl> initAbility3() {
		// TODO Auto-generated method stub
		return null;
	}

}
