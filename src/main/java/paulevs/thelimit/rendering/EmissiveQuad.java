package paulevs.thelimit.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.client.render.model.BakedQuad;
import net.modificationstation.stationapi.api.util.math.BlockPos;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public interface EmissiveQuad {
	void setEmissive(boolean emissive);
	boolean isEmissive();
	void setLightFunction(Function<BlockPos, Float> lightFunction);
	Function<BlockPos, Float> getLightFunction();
	
	static EmissiveQuad cast(BakedQuad quad) {
		return (EmissiveQuad) quad;
	}
}
