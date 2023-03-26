package paulevs.thelimit.structures;

import net.minecraft.block.BlockBase;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import paulevs.thelimit.blocks.TheLimitBlocks;

import java.util.Random;

public class GrassScatter extends ScatterStructure {
	private final BlockBase[] grasses;
	
	public GrassScatter(int radius, int count, BlockBase shortGrass, BlockBase normalGrass, BlockBase tallGrass) {
		super(radius, count);
		grasses = new BlockBase[] {tallGrass, normalGrass, shortGrass};
	}
	
	@Override
	protected void place(Level level, Random random, BlockPos pos, BlockPos center) {
		if (!level.getBlockState(pos.getX(), pos.getY() - 1, pos.getZ()).isOf(TheLimitBlocks.HYPHUM)) return;
		float distance = (int) (MathHelper.sqrt(pos.getSquaredDistance(center)) / radius);
		int type = (int) (distance * 4 + random.nextFloat() * 0.25F);
		type = net.modificationstation.stationapi.api.util.math.MathHelper.clamp(type, 0, 2);
		BlockBase block = grasses[type];
		if (!block.canPlaceAt(level, pos.getX(), pos.getY(), pos.getZ())) return;
		level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), block.getDefaultState());
		block.onBlockPlaced(level, pos.getX(), pos.getY(), pos.getZ());
	}
}
