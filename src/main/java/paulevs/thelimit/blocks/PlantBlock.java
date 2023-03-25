package paulevs.thelimit.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.util.math.BlockPos;

public class PlantBlock extends TemplateBlockBase {
	public PlantBlock(Identifier identifier) {
		super(identifier, Material.PLANT);
		setBoundingBox(0.125F, 0.0F, 0.125F, 0.875F, 0.875F, 0.875F);
		setSounds(GRASS_SOUNDS);
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		if (!canPlace(context.getWorld(), context.getBlockPos())) return null;
		return getDefaultState();
	}
	
	protected boolean canPlace(Level level, BlockPos pos) {
		BlockState below = level.getBlockState(pos.down());
		return below.isOf(TheLimitBlocks.HYPHUM);
	}
}
