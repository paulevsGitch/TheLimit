package paulevs.thelimit.blocks.basic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;

public class LeavesBlock extends TemplateBlockBase {
	public LeavesBlock(Identifier id) {
		super(id, Material.LEAVES);
		setHardness(0.2F);
		setSounds(GRASS_SOUNDS);
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	@Override
	@Environment(value= EnvType.CLIENT)
	public boolean isSideRendered(BlockView view, int x, int y, int z, int facing) {
		if (!(view instanceof BlockStateView level)) {
			return super.isSideRendered(view, x, y, z, facing);
		}
		
		Direction dir = Direction.byId(facing);
		BlockState state = level.getBlockState(
			x + dir.getOffsetX(),
			y + dir.getOffsetY(),
			z + dir.getOffsetZ()
		);
		
		if (state.isOf(this)) {
			/*state = level.getBlockState(
				x + dir.getOffsetX() * 2,
				y + dir.getOffsetY() * 2,
				z + dir.getOffsetZ() * 2
			);
			return !state.isOf(this);*/
			return true;
		}
		
		return super.isSideRendered(view, x, y, z, facing);
	}
}
