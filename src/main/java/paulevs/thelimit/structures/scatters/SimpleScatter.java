package paulevs.thelimit.structures.scatters;

import net.minecraft.block.BlockBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.util.math.BlockPos;

import java.util.Random;

public class SimpleScatter extends ScatterStructure {
	protected final BlockBase block;
	
	public SimpleScatter(int radius, int count, BlockBase block) {
		super(radius, count);
		this.block = block;
	}
	
	@Override
	protected void place(Level level, Random random, BlockPos pos, BlockPos center) {
		if (block.canPlaceAt(level, pos.getX(), pos.getY(), pos.getZ())) {
			level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), block.getDefaultState());
		}
	}
	
	/*protected BlockState getBlockState(Level level, int x, int y, int z) {
		int cx = x >> 4;
		int cz = z >> 4;
		if (level.getChunkFromCache())
		Chunk chunk = level.getChunkFromCache(x >> 4, z >> 4);
		return chunk.getBlockState(x, y & 15, z);
	}*/
}
