package paulevs.thelimit.world.structures.scatters;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import paulevs.thelimit.TheLimit;

import java.util.Random;

public abstract class ScatterStructure extends Structure {
	protected final int radius2;
	protected final int radius;
	protected final int count;
	
	protected ScatterStructure(int radius, int count) {
		this.radius2 = radius * 2 + 1;
		this.radius = radius;
		this.count = count;
	}
	
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockPos center = new BlockPos(x, y, z);
		for (int i = 0; i < count; i++) {
			pos.setX(x + random.nextInt(radius2) - radius);
			pos.setZ(z + random.nextInt(radius2) - radius);
			pos.setY(y + 5);
			BlockState state = level.getBlockState(pos.getX(), pos.getY() - 1, pos.getZ());
			for (int j = 0; j < 11; j++) {
				BlockState below = level.getBlockState(pos.getX(), pos.getY() - 1, pos.getZ());
				if (state.isAir() && !TheLimit.isReplaceable(below)) {
					place(level, random, pos, center);
					break;
				}
				state = below;
				pos.setY(pos.getY() - 1);
			}
		}
		return true;
	}
	
	protected abstract void place(Level level, Random random, BlockPos pos, BlockPos center);
}
