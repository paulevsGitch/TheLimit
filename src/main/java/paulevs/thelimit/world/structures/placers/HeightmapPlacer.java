package paulevs.thelimit.world.structures.placers;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.structure.Structure;

import java.util.Random;

public class HeightmapPlacer extends StructurePlacer {
	public HeightmapPlacer(Structure structure, int count) {
		super(structure, count);
	}
	
	@Override
	protected int getHeight(Level level, Chunk chunk, Random random, int x, int z) {
		return chunk.getHeight(x, z);
	}
	
	@Override
	protected boolean canPlace(Level level, Chunk chunk, Random random, int x, int y, int z) {
		return y > 0;
	}
}
