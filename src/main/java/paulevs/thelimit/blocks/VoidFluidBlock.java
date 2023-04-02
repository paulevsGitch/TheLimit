package paulevs.thelimit.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateFluid;
import paulevs.thelimit.rendering.VoidFluidRenderer;

public class VoidFluidBlock extends TemplateFluid {
	public VoidFluidBlock(Identifier id) {
		super(id, Material.WATER);
		setLightOpacity(255);
		texture = 0;
	}
	
	@Override
	@Environment(value=EnvType.CLIENT)
	public int getRenderPass() {
		return 1;
	}
	
	@Override
	public int getTextureForSide(int side) {
		VoidFluidRenderer.side = side;
		return 0;
	}
	
	@Override
	@Environment(value=EnvType.CLIENT)
	public float getBrightness(BlockView view, int x, int y, int z) {
		return VoidFluidRenderer.layer == 1 ? 1F : 0.5F;
	}
	
	@Override
	@Environment(value=EnvType.CLIENT)
	public boolean isSideRendered(BlockView level, int x, int y, int z, int side) {
		if (side == 1) return true;
		if (isFluid(getState(x, y, z))) return false;
		return super.isSideRendered(level, x, y, z, side);
	}
	
	public static boolean isFluid(BlockState state) {
		if (state.isOf(TheLimitBlocks.VOID_FLUID)) return true;
		return state.getProperties().contains(TLBlockProperties.VOIDLOGGED) && state.get(TLBlockProperties.VOIDLOGGED);
	}
	
	@Environment(value=EnvType.CLIENT)
	private BlockState getState(int x, int y, int z) {
		return ((Minecraft) FabricLoader.getInstance().getGameInstance()).level.getBlockState(x, y, z);
	}
}
