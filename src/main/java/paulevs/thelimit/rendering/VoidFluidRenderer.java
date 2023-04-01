package paulevs.thelimit.rendering;

import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.world.BlockStateView;
import paulevs.thelimit.blocks.VoidFluidBlock;

public class VoidFluidRenderer {
	public static final BlockPos.Mutable POS = new BlockPos.Mutable();
	public static Sprite voidFluid;
	public static Sprite voidFluidEmission;
	public static int side = -1;
	public static int layer = -1;
	public static int renderPass = -1;
	
	public static float getFluidHeight(BlockStateView view, int x, int y, int z) {
		BlockState state = view.getBlockState(POS.getX(), POS.getY() + 1, POS.getZ());
		
		if (VoidFluidBlock.isFluid(state)) return 1.0F;
		
		float value = 5F / 16F;
		for (byte dx = -1; dx < 1; dx++) {
			for (byte dz = -1; dz < 1; dz++) {
				if (x + dx == POS.getX() && z + dz == POS.getZ()) continue;
				state = view.getBlockState(x + dx, y + 1, z + dz);
				if (VoidFluidBlock.isFluid(state)) return 1.0F;
				state = view.getBlockState(x + dx, y, z + dz);
				if (VoidFluidBlock.isFluid(state) || state.getBlock().isFullCube()) {
					value += 5F / 16F;
				}
			}
		}
		if (value > (15F / 16F)) value = (15F / 16F);
		return value;
	}
}
