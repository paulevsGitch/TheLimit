package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.Direction;

public class BranchBlock extends TemplateBlockBase {
	public BranchBlock(Identifier identifier) {
		super(identifier, Material.WOOD);
		setSounds(WOOD_SOUNDS);
		setHardness(1F);
		
		BlockState state = getDefaultState();
		for (byte i = 0; i < 6; i++) {
			state = state.with(TLBlockProperties.FACES[i], false);
		}
		setDefaultState(state);
	}
	
	@Override
	public void appendProperties(Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(TLBlockProperties.FACES);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		Level level = context.getWorld();
		BlockPos pos = context.getBlockPos();
		return getFacingState(level, pos.getX(), pos.getY(), pos.getZ());
	}
	
	@Override
	public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int l) {
		BlockState state = level.getBlockState(x, y, z);
		BlockState facing = getFacingState(level, x, y, z);
		if (facing != state) {
			level.setBlockState(x, y, z, facing);
		}
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	private BlockState getFacingState(Level level, int x, int y, int z) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockState self = getDefaultState();
		for (byte i = 0; i < 6; i++) {
			Direction dir = Direction.byId(i);
			BlockState state = level.getBlockState(pos.set(x, y, z).move(dir));
			if (dir == Direction.UP && state.isOf(TLBlocks.STELLATA_FLOWER)) {
				self = self.with(TLBlockProperties.FACES[i], true);
			}
			else self = self.with(TLBlockProperties.FACES[i], canConnect(state, dir));
		}
		return self;
	}
	
	private boolean canConnect(BlockState state, Direction dir) {
		BlockBase block = state.getBlock();
		if (block instanceof StemBlock) return state.get(TLBlockProperties.AXIS) == dir.getAxis();
		return block instanceof BranchBlock || (block.isFullOpaque() && block.isFullCube());
	}
}
