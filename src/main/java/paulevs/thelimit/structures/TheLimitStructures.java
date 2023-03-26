package paulevs.thelimit.structures;

import paulevs.thelimit.blocks.TheLimitBlocks;

public class TheLimitStructures {
	public static final StellataTreeStructure STELLATA_TREE = new StellataTreeStructure();
	public static final StellataTreeSmallStructure STELLATA_TREE_SMALL = new StellataTreeSmallStructure();
	
	public static final GrassScatter GUTTARBA = new GrassScatter(
		4, 30,
		TheLimitBlocks.GUTTARBA_SHORT,
		TheLimitBlocks.GUTTARBA_NORMAL,
		TheLimitBlocks.GUTTARBA_TALL
	);
}
