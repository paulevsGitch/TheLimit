package paulevs.thelimit.structures;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.blocks.TheLimitBlocks;

import java.util.Random;

public class StellataTreeSmallStructure extends Structure {
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		if (level.getBlockState(x, y - 1, z).isAir()) return false;
		int height = random.nextInt(3) + 1;
		
		BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z);
		if (!TheLimit.isReplaceable(level.getBlockState(pos))) return false;
		
		BlockState stem = TheLimitBlocks.STELLATA_STEM.getDefaultState();
		BlockState flower = TheLimitBlocks.STELLATA_FLOWER.getDefaultState();
		
		for (byte j = 0; j < height; j++) {
			if (!TheLimit.isReplaceable(level.getBlockState(pos))) return true;
			level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), stem);
			pos.setY(pos.getY() + 1);
		}
		
		if (!TheLimit.isReplaceable(level.getBlockState(pos))) return true;
		level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), flower);
		
		return true;
	}
}
