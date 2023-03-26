package paulevs.thelimit.structures;

import paulevs.thelimit.blocks.TheLimitBlocks;

public class TheLimitStructures {
	public static final StellataTreeStructure STELLATA_TREE = new StellataTreeStructure();
	public static final StellataTreeSmallStructure STELLATA_TREE_SMALL = new StellataTreeSmallStructure();
	
	public static final GrassScatter BLOB_GRASS = new GrassScatter(
		4, 30,
		TheLimitBlocks.BLOB_GRASS_SHORT,
		TheLimitBlocks.BLOB_GRASS_NORMAL,
		TheLimitBlocks.BLOB_GRASS_TALL
	);
}
