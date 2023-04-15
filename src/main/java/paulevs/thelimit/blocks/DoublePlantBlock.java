package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import paulevs.thelimit.blocks.TLBlockProperties.TopBottom;
import paulevs.thelimit.blocks.basic.PlantBlock;

import java.util.Collections;
import java.util.List;

public class DoublePlantBlock extends PlantBlock {
	public DoublePlantBlock(Identifier identifier) {
		super(identifier);
		setDefaultState(getDefaultState().with(TLBlockProperties.TOP_BOTTOM, TopBottom.BOTTOM));
	}
	
	@Override
	public void appendProperties(Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(TLBlockProperties.TOP_BOTTOM);
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		super.onBlockPlaced(level, x, y, z);
		BlockState state = level.getBlockState(x, y, z);
		if (state.isOf(this) && state.get(TLBlockProperties.TOP_BOTTOM) == TopBottom.TOP) return;
		state = getDefaultState().with(TLBlockProperties.TOP_BOTTOM, TopBottom.TOP);
		level.setBlockState(x, y + 1, z, state);
	}
	
	@Override
	public boolean canPlaceAt(Level level, int x, int y, int z) {
		return level.getBlockState(x, y + 1, z).isAir() && super.canPlaceAt(level, x, y, z);
	}
	
	@Override
	protected boolean canPlace(Level level, BlockPos pos) {
		BlockState self = level.getBlockState(pos);
		if (self.isOf(this) && self.get(TLBlockProperties.TOP_BOTTOM) == TopBottom.TOP) {
			return level.getBlockState(pos.down()).isOf(this);
		}
		return super.canPlace(level, pos);
	}
	
	@Override
	public List<ItemInstance> getDropList(Level level, int x, int y, int z, BlockState state, int meta) {
		if (state.isOf(this) && state.get(TLBlockProperties.TOP_BOTTOM) == TopBottom.TOP) {
			return Collections.emptyList();
		}
		return super.getDropList(level, x, y, z, state, meta);
	}
	
	@Override
	public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int l) {
		BlockState state = level.getBlockState(x, y, z);
		if (!state.isOf(this)) return;
		TopBottom part = state.get(TLBlockProperties.TOP_BOTTOM);
		if (part == TopBottom.TOP && level.getBlockState(x, y - 1, z).isOf(this)) return;
		else if (part == TopBottom.BOTTOM && level.getBlockState(x, y + 1, z).isOf(this)) return;
		this.drop(level, x, y, z, l);
	}
	
	@Override
	public void onBlockRemoved(Level level, int x, int y, int z) {
		super.onBlockRemoved(level, x, y, z);
		
		BlockState state = level.getBlockState(x, y - 1, z);
		if (state.isOf(this)) {
			state.getBlock().drop(level, x, y - 1, z, 0);
			level.setBlockState(x, y - 1, z, States.AIR.get());
			return;
		}
		
		state = level.getBlockState(x, y + 1, z);
		if (state.isOf(this)) {
			level.setBlockState(x, y + 1, z, States.AIR.get());
		}
	}
}
