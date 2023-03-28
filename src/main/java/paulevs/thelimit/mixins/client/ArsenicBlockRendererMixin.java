package paulevs.thelimit.mixins.client;

import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import paulevs.thelimit.rendering.VoidFluidRenderer;

@Mixin(value = ArsenicBlockRenderer.class, remap = false)
public class ArsenicBlockRendererMixin {
	@ModifyVariable(method = "renderFluid", at = @At("STORE"))
	private Sprite thelimit_customFluidSprite1(Sprite sprite) {
		if (VoidFluidRenderer.side == -1) return sprite;
		if (VoidFluidRenderer.side < 2) return VoidFluidRenderer.sprite_small;
		return VoidFluidRenderer.sprite_big;
	}
	
	@ModifyVariable(method = "renderBottomFace", at = @At("STORE"))
	private Sprite thelimit_customFluidSprite2(Sprite sprite) {
		if (VoidFluidRenderer.side == -1) return sprite;
		return VoidFluidRenderer.sprite_small;
	}
}
