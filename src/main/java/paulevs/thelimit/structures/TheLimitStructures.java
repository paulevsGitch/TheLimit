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
	
	public static final SimpleScatter GLOW_PLANT = new SimpleScatter(3, 20, TheLimitBlocks.GLOW_PLANT);
	
	public static final StructurePlacer STELLATA_TREE_PLACER = new StructurePlacer(STELLATA_TREE, 3);
	public static final StructurePlacer STELLATA_TREE_SMALL_PLACER = new StructurePlacer(STELLATA_TREE_SMALL, 1);
	public static final StructurePlacer GUTTARBA_PLACER = new StructurePlacer(GUTTARBA, 3);
	public static final StructurePlacer GLOW_PLANT_PLACER = new StructurePlacer(GLOW_PLANT, 1);
}
