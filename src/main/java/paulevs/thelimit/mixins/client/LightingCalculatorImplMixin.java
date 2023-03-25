package paulevs.thelimit.mixins.client;

import net.modificationstation.stationapi.api.client.render.model.BakedQuad;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.aocalc.LightingCalculatorImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.thelimit.rendering.EmissiveQuad;

import java.util.function.Function;

@Mixin(value = LightingCalculatorImpl.class, remap = false)
public abstract class LightingCalculatorImplMixin {
	@Shadow @Final public float[] light;
	
	@Shadow private int x;
	@Shadow private int y;
	@Shadow private int z;
	
	@Shadow protected abstract void calculateForQuad(Direction face, double v00x, double v00y, double v00z, double v01x, double v01y, double v01z, double v11x, double v11y, double v11z, double v10x, double v10y, double v10z, boolean shade);
	
	@Unique private final BlockPos.Mutable blockPos = new BlockPos.Mutable();
	
	@Inject(
		method = "calculateForQuad(Lnet/modificationstation/stationapi/api/client/render/model/BakedQuad;)V",
		at = @At("HEAD"), cancellable = true
	)
	private void thelimit_processEmissive(BakedQuad quad, CallbackInfo info) {
		EmissiveQuad emissiveQuad = EmissiveQuad.cast(quad);
		if (!emissiveQuad.isEmissive()) return;
		info.cancel();
		
		Function<BlockPos, Float> function = emissiveQuad.getLightFunction();
		float light = function == null ? 1.0F : function.apply(blockPos.set(x, y, z));
		if (light < 0.99F) {
			Direction face = quad.getFace();
			this.calculateForQuad(
				face,
				x + Float.intBitsToFloat(quad.getVertexData()[0]),
				y + Float.intBitsToFloat(quad.getVertexData()[1]),
				z + Float.intBitsToFloat(quad.getVertexData()[2]),
				x + Float.intBitsToFloat(quad.getVertexData()[8]),
				y + Float.intBitsToFloat(quad.getVertexData()[9]),
				z + Float.intBitsToFloat(quad.getVertexData()[10]),
				x + Float.intBitsToFloat(quad.getVertexData()[16]),
				y + Float.intBitsToFloat(quad.getVertexData()[17]),
				z + Float.intBitsToFloat(quad.getVertexData()[18]),
				x + Float.intBitsToFloat(quad.getVertexData()[24]),
				y + Float.intBitsToFloat(quad.getVertexData()[25]),
				z + Float.intBitsToFloat(quad.getVertexData()[26]),
				quad.hasShade()
			);
			light = MathHelper.lerp(light, this.light[0], 1);
		}
		for (int i = 0; i < 4; i++) this.light[i] = light;
	}
}
