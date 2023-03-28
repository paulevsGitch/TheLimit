package paulevs.thelimit.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateFluid;
import paulevs.thelimit.rendering.VoidFluidRenderer;

public class VoidFluidBlock extends TemplateFluid {
	public VoidFluidBlock(Identifier id) {
		super(id, Material.WATER);
		setLightEmittance(1F);
		texture = 0;
	}
	
	@Override
	@Environment(value=EnvType.CLIENT)
	public int getRenderPass() {
		return 0;
	}
	
	@Override
	public int getTextureForSide(int side) {
		VoidFluidRenderer.side = side;
		return 0;
	}
}
