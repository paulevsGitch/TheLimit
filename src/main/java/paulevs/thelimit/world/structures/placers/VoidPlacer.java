package paulevs.thelimit.world.structures.placers;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.structure.Structure;

import java.util.Random;

public class VoidPlacer extends StructurePlacer {
	private final int difference;
	private final int minY;
	
	public VoidPlacer(Structure structure, int count, int minY, int difference) {
		super(structure, count);
		this.difference = difference;
		this.minY = minY;
	}
	
	@Override
	protected int getHeight(Level level, Chunk chunk, Random random, int x, int z) {
		return random.nextInt(difference) + minY;
	}
	
	@Override
	protected boolean canPlace(Level level, Chunk chunk, Random random, int x, int y, int z) {
		return chunk.getHeight(x, z) == 0;
	}
}
