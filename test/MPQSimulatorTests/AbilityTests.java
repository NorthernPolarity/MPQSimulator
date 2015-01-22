package MPQSimulatorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Core.GameEngineImpl;
import MPQSimulator.Core.Tile.TileColor;

public class AbilityTests {

	@Test
	public void testComponentStacking() {
		AbilityComponent black = 
				new SwapTileAbilityComponent(0, 0, TileColor.BLACK);
		AbilityComponent red = 
				new SwapTileAbilityComponent(1, 0, TileColor.RED);
		AbilityComponent green = 
				new SwapTileAbilityComponent(2, 0, TileColor.GREEN);
		AbilityComponent yellow = 
				new SwapTileAbilityComponent(0, 0, TileColor.YELLOW);
		AbilityComponent purple = 
				new SwapTileAbilityComponent(1, 0, TileColor.PURPLE);
		AbilityComponent blue = 
				new SwapTileAbilityComponent(2, 0, TileColor.BLUE);

		AbilityImpl level1 = new AbilityImpl();
		level1.addComponent(black);
		
		assertEquals(level1.getComponents().get(0), black);
		
		AbilityImpl level2 = new AbilityImpl(level1);
		level2.addComponent(red);
		assertEquals(level2.getComponents().get(0), black);
		assertEquals(level2.getComponents().get(1), red);

		AbilityImpl level3 = new AbilityImpl(level2);
		level3.addComponent(green);

		assertEquals(level3.getComponents().get(0), black);
		assertEquals(level3.getComponents().get(1), red);
		assertEquals(level3.getComponents().get(2), green);

		AbilityImpl level4 = new AbilityImpl(level3);
		level4.addComponent(yellow);
		
		assertEquals(level4.getComponents().get(0), black);
		assertEquals(level4.getComponents().get(1), red);
		assertEquals(level4.getComponents().get(2), green);
		assertEquals(level4.getComponents().get(3), yellow);

		
		AbilityImpl level5 = new AbilityImpl(level4);
		level5.addComponent(purple);
		level5.addComponent(blue);

		assertEquals(level5.getComponents().get(0), black);
		assertEquals(level5.getComponents().get(1), red);
		assertEquals(level5.getComponents().get(2), green);
		assertEquals(level5.getComponents().get(3), yellow);
		assertEquals(level5.getComponents().get(4), purple);
		assertEquals(level5.getComponents().get(5), blue);

	}
}
