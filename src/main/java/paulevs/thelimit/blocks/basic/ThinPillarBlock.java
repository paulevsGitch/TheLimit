package paulevs.thelimit.blocks.basic;

import net.minecraft.block.BlockBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import paulevs.thelimit.blocks.TLBlockProperties;
import paulevs.thelimit.blocks.TLBlockProperties.PillarShape;

public class ThinPillarBlock extends PillarBlock {
	public ThinPillarBlock(Identifier identifier, BlockBase source) {
		super(identifier, source.material);
		setHardness(source.getHardness());
		EMITTANCE[id] = EMITTANCE[source.id];
		setSounds(source.sounds);
	}
	
	@Override
	public void appendProperties(Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(TLBlockProperties.PILLAR);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		BlockState state = super.getPlacementState(context);
		BlockPos pos = context.getBlockPos();
		Axis axis = state.get(TLBlockProperties.AXIS);
		return getState(context.getWorld(), pos.getX(), pos.getY(), pos.getZ(), axis);
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		BlockState state = level.getBlockState(x, y, z);
		Axis axis = state.get(TLBlockProperties.AXIS);
		BlockState side;
		switch (axis) {
			case X -> {
				side = level.getBlockState(x - 1, y, z);
				if (side.getBlock() instanceof ThinPillarBlock pillar && side.get(TLBlockProperties.AXIS) == Axis.X) {
					BlockState newState = pillar.getState(level, x - 1, y, z, axis);
					if (newState != side) level.setBlockState(x - 1, y, z, newState);
				}
				side = level.getBlockState(x + 1, y, z);
				if (side.getBlock() instanceof ThinPillarBlock pillar && side.get(TLBlockProperties.AXIS) == Axis.X) {
					BlockState newState = pillar.getState(level, x + 1, y, z, axis);
					if (newState != side) level.setBlockState(x + 1, y, z, newState);
				}
			}
			case Y -> {
				side = level.getBlockState(x, y - 1, z);
				if (side.getBlock() instanceof ThinPillarBlock pillar && side.get(TLBlockProperties.AXIS) == Axis.Y) {
					BlockState newState = pillar.getState(level, x, y - 1, z, axis);
					if (newState != side) level.setBlockState(x, y - 1, z, newState);
				}
				side = level.getBlockState(x, y + 1, z);
				if (side.getBlock() instanceof ThinPillarBlock pillar && side.get(TLBlockProperties.AXIS) == Axis.Y) {
					BlockState newState = pillar.getState(level, x, y + 1, z, axis);
					if (newState != side) level.setBlockState(x, y + 1, z, newState);
				}
			}
			case Z -> {
				side = level.getBlockState(x, y, z - 1);
				if (side.getBlock() instanceof ThinPillarBlock pillar && side.get(TLBlockProperties.AXIS) == Axis.Z) {
					BlockState newState = pillar.getState(level, x, y, z - 1, axis);
					if (newState != side) level.setBlockState(x, y, z - 1, newState);
				}
				side = level.getBlockState(x, y, z + 1);
				if (side.getBlock() instanceof ThinPillarBlock pillar && side.get(TLBlockProperties.AXIS) == Axis.Z) {
					BlockState newState = pillar.getState(level, x, y, z + 1, axis);
					if (newState != side) level.setBlockState(x, y, z + 1, newState);
				}
			}
		}
	}
	
	@Override
	public void onBlockRemoved(Level level, int x, int y, int z) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		for (byte i = 0; i < 6; i++) {
			pos.set(x, y, z).move(Direction.byId(i));
			BlockState worldState = level.getBlockState(pos);
			if (worldState.getBlock() instanceof ThinPillarBlock pillar) {
				Axis axis = worldState.get(TLBlockProperties.AXIS);
				BlockState newState = pillar.getState(level, pos.getX(), pos.getY(), pos.getZ(), axis);
				if (newState != worldState) {
					level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), newState);
				}
			}
		}
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	private BlockState getState(Level level, int x, int y, int z, Axis axis) {
		boolean bottom = false;
		boolean top = false;
		
		switch (axis) {
			case X -> {
				bottom = level.getBlockState(x - 1, y, z).getBlock() instanceof ThinPillarBlock;
				top = level.getBlockState(x + 1, y, z).getBlock() instanceof ThinPillarBlock;
			}
			case Y -> {
				bottom = level.getBlockState(x, y - 1, z).getBlock() instanceof ThinPillarBlock;
				top = level.getBlockState(x, y + 1, z).getBlock() instanceof ThinPillarBlock;
			}
			case Z -> {
				bottom = level.getBlockState(x, y, z - 1).getBlock() instanceof ThinPillarBlock;
				top = level.getBlockState(x, y, z + 1).getBlock() instanceof ThinPillarBlock;
			}
		}
		
		BlockState state = getDefaultState().with(TLBlockProperties.AXIS, axis);
		if (top && bottom) state = state.with(TLBlockProperties.PILLAR, PillarShape.MIDDLE);
		else if (top) state = state.with(TLBlockProperties.PILLAR, PillarShape.BOTTOM);
		else if (bottom) state = state.with(TLBlockProperties.PILLAR, PillarShape.TOP);
		else state = state.with(TLBlockProperties.PILLAR, PillarShape.SMALL);
		
		return state;
	}
}
