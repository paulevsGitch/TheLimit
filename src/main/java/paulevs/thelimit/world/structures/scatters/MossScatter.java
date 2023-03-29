package paulevs.thelimit.world.structures.scatters;

import net.minecraft.block.BlockBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.util.math.BlockPos;

import java.util.Random;

public class MossScatter extends SimpleScatter {
	public MossScatter(int radius, int count, BlockBase block) {
		super(radius, count, block);
	}
	
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		if (!level.getBlockState(x, y, z).isAir()) return false;
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockPos center = new BlockPos(x, y, z);
		
		/*for (int i = 0; i < count; i++) {
			pos.setX(x + random.nextInt(radius2) - radius);
			pos.setZ(z + random.nextInt(radius2) - radius);
			pos.setY(y + random.nextInt(radius2) - radius);
			if (!level.getBlockState(pos).isAir()) continue;
			place(level, random, pos, center);
		}*/
		
		for (int dx = -radius; dx <= radius; dx++) {
			pos.setX(x + dx);
			for (int dz = -radius; dz <= radius; dz++) {
				pos.setZ(z + dz);
				for (int dy = -radius; dy <= radius; dy++) {
					pos.setY(y + dy);
					if (count > 1 && random.nextInt(count) > 0) continue;
					if (!level.getBlockState(pos).isAir()) continue;
					place(level, random, pos, center);
				}
			}
		}
		
		return true;
	}
	
	@Override
	protected void place(Level level, Random random, BlockPos pos, BlockPos center) {
		if (block.canPlaceAt(level, pos.getX(), pos.getY(), pos.getZ())) {
			block.onAdjacentBlockUpdate(level, pos.getX(), pos.getY(), pos.getZ(), 0);
		}
	}
}
