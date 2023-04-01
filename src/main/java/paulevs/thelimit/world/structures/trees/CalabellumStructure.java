package paulevs.thelimit.world.structures.trees;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.thelimit.blocks.TLBlockProperties;
import paulevs.thelimit.blocks.TLBlockProperties.TriplePart;
import paulevs.thelimit.blocks.TheLimitBlocks;
import paulevs.thelimit.blocks.VoidFluidBlock;

import java.util.Random;

public class CalabellumStructure extends Structure {
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		if (!level.getBlockState(x, y - 1, z).getBlock().isFullCube()) return false;
		if (!level.getBlockState(x, y + 1, z).isAir()) return false;
		
		BlockState state = TheLimitBlocks.CALABELLUM.getDefaultState();
		BlockState bottom = state.with(TLBlockProperties.TRIPLE_PART, TriplePart.BOTTOM);
		BlockState middle = state.with(TLBlockProperties.TRIPLE_PART, TriplePart.MIDDLE);
		BlockState top = state.with(TLBlockProperties.TRIPLE_PART, TriplePart.TOP);
		
		int h = random.nextInt(5) + 5;
		
		level.setBlockState(x, y, z, getVoidlogged(bottom, level, x, y, z));
		for (int i = 1; i < h; i++) {
			int py = y + i;
			if (!level.getBlockState(x, py, z).isAir()) {
				level.setBlockState(x, py, z, top);
				return true;
			}
			level.setBlockState(x, py, z, getVoidlogged(middle, level, x, py, z));
		}
		level.setBlockState(x, y + h, z, top);
		
		return false;
	}
	
	private BlockState getVoidlogged(BlockState state, Level level, int x, int y, int z) {
		BlockState side = level.getBlockState(x, y, z);
		
		if (VoidFluidBlock.isFluid(side)) {
			return state.with(TLBlockProperties.VOIDLOGGED, true);
		}
		
		for (byte i = 0; i < 4; i++) {
			Direction dir = Direction.fromHorizontal(i);
			side = level.getBlockState(x + dir.getOffsetX(), y, z + dir.getOffsetZ());
			if (VoidFluidBlock.isFluid(side)) {
				return state.with(TLBlockProperties.VOIDLOGGED, true);
			}
		}
		
		return state;
	}
}
