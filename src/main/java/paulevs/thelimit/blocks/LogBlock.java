package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;

public class LogBlock extends TemplateBlockBase {
	public LogBlock(Identifier identifier) {
		super(identifier, Material.WOOD);
		setDefaultState(getDefaultState().with(TLBlockProperties.AXIS, Axis.Y));
		setSounds(WOOD_SOUNDS);
		setHardness(1F);
	}
	
	@Override
	public void appendProperties(Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(TLBlockProperties.AXIS);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		Axis axis = context.getSide().getAxis();
		return getDefaultState().with(TLBlockProperties.AXIS, axis);
	}
}
