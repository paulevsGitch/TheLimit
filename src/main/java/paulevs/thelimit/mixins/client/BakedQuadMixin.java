package paulevs.thelimit.mixins.client;

import net.modificationstation.stationapi.api.client.render.model.BakedQuad;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import paulevs.thelimit.rendering.EmissiveQuad;

import java.util.function.Function;

@Mixin(value = BakedQuad.class, remap = false)
public class BakedQuadMixin implements EmissiveQuad {
	@Unique private boolean isEmissive;
	@Unique Function<BlockPos, Float> lightFunction;
	
	@Override
	public void setEmissive(boolean emissive) {
		isEmissive = emissive;
	}
	
	@Override
	public boolean isEmissive() {
		return isEmissive;
	}
	
	@Override
	public void setLightFunction(Function<BlockPos, Float> lightFunction) {
		this.lightFunction = lightFunction;
	}
	
	@Override
	public Function<BlockPos, Float> getLightFunction() {
		return lightFunction;
	}
}
