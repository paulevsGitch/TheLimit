package paulevs.thelimit.structures;

import net.minecraft.block.BlockBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.util.math.BlockPos;

import java.util.Random;

public class SimpleScatter extends ScatterStructure {
	private final BlockBase block;
	
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
}
