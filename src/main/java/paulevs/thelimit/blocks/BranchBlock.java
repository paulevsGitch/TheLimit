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
	
	/*@Override
	public void updateBoundingBox(BlockView view, int x, int y, int z) {
		if (view instanceof Level) {
			BlockState state = ((Level) view).getBlockState(x, y, z);
			if (state.isOf(this)) {
				setBoundingBox(state);
				return;
			}
		}
		this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void doesBoxCollide(Level level, int x, int y, int z, Box box, ArrayList list) {
		BlockState state = level.getBlockState(x, y, z);
		if (state.isOf(this)) {
			setBoundingBox(state);
		}
		super.doesBoxCollide(level, x, y, z, box, list);
		this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}
	
	private void setBoundingBox(BlockState state) {
		Axis axis = state.get(TLBlockProperties.AXIS);
		switch (axis) {
			case X -> this.setBoundingBox(0.0F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
			case Y -> this.setBoundingBox(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
			case Z -> this.setBoundingBox(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 1.0F);
		}
	}*/
	
	private BlockState getFacingState(Level level, int x, int y, int z) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockState self = getDefaultState();
		for (byte i = 0; i < 6; i++) {
			Direction dir = Direction.byId(i);
			BlockState state = level.getBlockState(pos.set(x, y, z).move(dir));
			if (dir == Direction.UP && state.isOf(TheLimitBlocks.STELLATA_FLOWER)) {
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
