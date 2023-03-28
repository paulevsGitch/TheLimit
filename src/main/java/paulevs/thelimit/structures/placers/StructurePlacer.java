package paulevs.thelimit.structures.placers;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.util.math.BlockPos;

import java.util.Random;
import java.util.function.Function;

public abstract class StructurePlacer {
	private static final Function<BlockPos, Boolean> DEFAULT_DENSITY = pos -> true;
	private static final BlockPos.Mutable POS = new BlockPos.Mutable();
	private final Structure structure;
	private final int count;
	private Function<BlockPos, Boolean> densityFunction;
	
	public StructurePlacer(Structure structure, int count) {
		this.densityFunction = DEFAULT_DENSITY;
		this.structure = structure;
		this.count = count;
	}
	
	public void place(Level level, Chunk chunk, Random random, int wx, int wz) {
		for (byte i = 0; i < count; i++) {
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int y = getHeight(level, chunk, random, x, z);
			if (!canPlace(level, chunk, random, x, y, z)) continue;
			if (!densityFunction.apply(POS.set(x | wx, y, z | wz))) continue;
			structure.generate(level, random, POS.getX(), POS.getY(), POS.getZ());
		}
	}
	
	protected abstract int getHeight(Level level, Chunk chunk, Random random, int x, int z);
	
	protected abstract boolean canPlace(Level level, Chunk chunk, Random random, int x, int y, int z);
	
	public StructurePlacer setDensityFunction(Function<BlockPos, Boolean> densityFunction) {
		this.densityFunction = densityFunction;
		return this;
	}
}
