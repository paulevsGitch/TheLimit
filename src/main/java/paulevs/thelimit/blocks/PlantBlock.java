package paulevs.thelimit.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
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
	public boolean canPlaceAt(Level level, int x, int y, int z) {
		return canPlace(level, new BlockPos(x, y, z));
	}
	
	@Override
	public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int l) {
		if (!canPlace(level, new BlockPos(x, y, z))) {
			this.drop(level, x, y, z, 0);
			level.setBlockStateWithNotify(x, y, z, States.AIR.get());
		}
	}
	
	@Override
	public Box getCollisionShape(Level level, int x, int y, int z) {
		return null;
	}
	
	protected boolean canPlace(Level level, BlockPos pos) {
		BlockState below = level.getBlockState(pos.down());
		return below.isOf(TLBlocks.HYPHUM);
	}
}
