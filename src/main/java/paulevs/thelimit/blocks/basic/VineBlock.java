package paulevs.thelimit.blocks.basic;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import paulevs.thelimit.blocks.TLBlockProperties;

public class VineBlock extends PlantBlock {
	public VineBlock(Identifier identifier) {
		super(identifier);
		setDefaultState(getDefaultState().with(TLBlockProperties.BOTTOM, true));
	}
	
	@Override
	public void appendProperties(Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(TLBlockProperties.BOTTOM);
	}
	
	@Override
	protected boolean canPlace(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos.up());
		if (state.isOf(this)) return true;
		return (state.getMaterial() == Material.LEAVES || state.isOpaque()) && state.getBlock().isFullCube();
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		BlockState state = level.getBlockState(x, y + 1, z);
		if (state.isOf(this)) {
			level.setBlockState(x, y + 1, z, state.with(TLBlockProperties.BOTTOM, false));
		}
	}
	
	@Override
	public void onBlockRemoved(Level level, int x, int y, int z) {
		BlockState state = level.getBlockState(x, y + 1, z);
		if (state.isOf(this)) {
			level.setBlockState(x, y + 1, z, state.with(TLBlockProperties.BOTTOM, true));
		}
	}
}
