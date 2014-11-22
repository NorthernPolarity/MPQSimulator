

import static org.junit.Assert.*;


import org.junit.Test;

import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;


public class TileTest {

	@Test
	public void testRandomImpl() {
		class StubRandomImpl implements Tile.RandomCaller {
			@Override
			public double random() {
				return 0;
			}
			
		}
		TileColor tc = Tile.getRandomColor(new StubRandomImpl());
		assertEquals(TileColor.BLACK, tc);
	}


	@Test
	public void testRandomImplDurable() {
		class StubRandomImpl implements Tile.RandomCaller {
			@Override
			public double random() {
				return 0;
			}
			
		}
		
		Tile.defaultRandomCaller = new StubRandomImpl();
		assertEquals(TileColor.BLACK, Tile.getRandomColor());
		assertEquals(TileColor.BLACK, Tile.getRandomColor());
		assertEquals(TileColor.BLACK, Tile.getRandomColor());
		assertEquals(TileColor.BLACK, Tile.getRandomColor());
	}

	@Test
	public void testRandomImplSetSequence() {
		class SeqRandomImpl implements Tile.RandomCaller {
			private int counter = 0;
			@Override
			public double random() {
				if(counter > Tile.NUM_NORMAL_TILES) {
					counter = 0;
				}
				double result = (double)counter /  Tile.NUM_NORMAL_TILES;
				counter += 1;
				return result;
			}
			
		}
		
		Tile.defaultRandomCaller = new SeqRandomImpl();
		assertEquals(TileColor.BLACK, Tile.getRandomColor());
		assertEquals(TileColor.BLUE, Tile.getRandomColor());
		assertEquals(TileColor.RED, Tile.getRandomColor());
		assertEquals(TileColor.GREEN, Tile.getRandomColor());
		assertEquals(TileColor.YELLOW, Tile.getRandomColor());
		assertEquals(TileColor.PURPLE, Tile.getRandomColor());
		assertEquals(TileColor.TEAMUP, Tile.getRandomColor());
		assertEquals(TileColor.BLACK, Tile.getRandomColor());
	}

	@Test
	public void testRandomImplSetSequence2() {
		class SeqRandomImpl implements Tile.RandomCaller {
			private int counter = 0;
			private TileColor[] sequence;
			
			public SeqRandomImpl(TileColor ... s) {
				sequence = s;
			}
			
			@Override
			public double random() {
				if(counter >= sequence.length) {
					counter = 0;
				}
				for(int i = 0; i < TileColor.values().length; i++) {
					if( sequence[counter] == TileColor.values()[i]) {
						double result = (double)i /  TileColor.values().length;
						counter += 1;
						return result;
					}
				}
				assert(false);
				return 0;
			}
			
		}
		
		Tile.defaultRandomCaller = new SeqRandomImpl(TileColor.BLACK, TileColor.BLUE);
		assertEquals(TileColor.BLACK, Tile.getRandomColor());
		assertEquals(TileColor.BLUE, Tile.getRandomColor());
		assertEquals(TileColor.BLACK, Tile.getRandomColor());
		assertEquals(TileColor.BLUE, Tile.getRandomColor());
	}
}
