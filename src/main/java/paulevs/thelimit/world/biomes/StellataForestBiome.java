package paulevs.thelimit.world.biomes;

import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.world.structures.TheLimitStructures;

import java.util.Random;

public class StellataForestBiome extends TheLimitBiome {
	private static final BlockState GROUND = TLBlocks.HYPHUM.getDefaultState();
	
	public StellataForestBiome() {
		setName("Stellata Forest");
		addStructure(TheLimitStructures.STELLATA_TREE_PLACER);
		addStructure(TheLimitStructures.STELLATA_TREE_SMALL_PLACER);
		addStructure(TheLimitStructures.GUTTARBA_PLACER);
		addStructure(TheLimitStructures.GLOW_PLANT_PLACER);
		addStructure(TheLimitStructures.MOSS_PLACER);
	}
	
	@Override
	public BlockState getGround(Level level, Random random, int x, int y, int z) {
		return GROUND;
	}
}
