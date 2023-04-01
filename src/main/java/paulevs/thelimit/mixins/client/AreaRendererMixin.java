package paulevs.thelimit.mixins.client;

import net.minecraft.block.BlockBase;
import net.minecraft.class_66;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.level.WorldPopulationRegion;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import paulevs.thelimit.rendering.VoidFluidRenderer;

import java.util.HashSet;

@Mixin(class_66.class)
public class AreaRendererMixin {
	@Inject(method = "method_296", at = @At(
		value = "JUMP",
		opcode = Opcodes.ISTORE,
		ordinal = 13
	), locals = LocalCapture.CAPTURE_FAILSOFT)
	private void thelimit_renderVoidlogged(
		CallbackInfo info, int var1, int var2, int var3, int var4, int var5, int var6, HashSet var7, int var8,
		WorldPopulationRegion var9, BlockRenderer var10, int index, int var12, int var13, int var14, int var15,
		int var16, int var17, int var18, BlockBase var19, int var20
	) {
		VoidFluidRenderer.renderPass = index;
	}
}
