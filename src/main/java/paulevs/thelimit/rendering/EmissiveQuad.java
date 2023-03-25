package paulevs.thelimit.rendering;

import net.modificationstation.stationapi.api.client.render.model.BakedQuad;

public interface EmissiveQuad {
	void setEmissive(boolean emissive);
	boolean isEmissive();
	
	static EmissiveQuad cast(BakedQuad quad) {
		return (EmissiveQuad) quad;
	}
}
