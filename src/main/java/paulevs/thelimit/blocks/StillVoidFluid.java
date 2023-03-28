package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateFluid;

import java.util.Random;
import java.util.function.Supplier;

public class StillVoidFluid extends TemplateFluid {
	private Supplier<BlockState> flowingFluid;
	
	protected StillVoidFluid(Identifier id) {
		super(id, Material.WATER);
		texture = 3;
		this.setTicksRandomly(false);
	}
	
	public void setFlowingFluid(Supplier<BlockState> flowingFluid) {
		this.flowingFluid = flowingFluid;
	}
	
	@Override
	public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int side) {
		super.onAdjacentBlockUpdate(level, x, y, z, side);
		if (level.getBlockState(x, y, z).isOf(this)) {
			this.updateFluid(level, x, y, z);
		}
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		if (this.material != Material.LAVA) return;
		byte count = (byte) random.nextInt(3);
		for (byte i = 0; i < count; ++i) {
			BlockState state = level.getBlockState(x += random.nextInt(3) - 1, ++y, z += random.nextInt(3) - 1);
			if (state.isAir()) {
				if (!this.isBurnable(level, x - 1, y, z) && !this.isBurnable(level, x + 1, y, z) && !this.isBurnable(level, x, y, z - 1) && !this.isBurnable(level, x, y, z + 1) && !this.isBurnable(level, x, y - 1, z) && !this.isBurnable(level, x, y + 1, z)) continue;
				level.setTile(x, y, z, BlockBase.FIRE.id);
				return;
			}
			if (!state.getBlock().material.blocksMovement()) continue;
			return;
		}
	}
	
	private void updateFluid(Level level, int x, int y, int z) {
		level.stopPhysics = true;
		level.setBlockStateWithNotify(x, y, z, flowingFluid.get());
		level.method_202(x, y, z, x, y, z);
		level.method_216(x, y, z, this.id - 1, this.getTickrate());
		level.stopPhysics = false;
	}
	
	private boolean isBurnable(Level level, int x, int y, int z) {
		return level.getMaterial(x, y, z).isBurnable();
	}
}
