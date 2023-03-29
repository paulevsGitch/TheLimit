package paulevs.thelimit.mixins.client;

import net.minecraft.block.BlockBase;
import net.minecraft.client.render.block.BlockRenderer;
import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.thelimit.rendering.VoidFluidRenderer;

@Mixin(value = ArsenicBlockRenderer.class, remap = false)
public class ArsenicBlockRendererMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void thelimit_onInit(BlockRenderer blockRenderer, CallbackInfo info) {
		VoidFluidRenderer.side = -1;
	}
	
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
	
	@Inject(method = "renderFluid", at = @At(value = "FIELD", target = "Lnet/minecraft/block/BlockBase;maxY:D"))
	private void thelimit_stopRenderFluid(BlockBase block, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		VoidFluidRenderer.side = -1;
	}
}
