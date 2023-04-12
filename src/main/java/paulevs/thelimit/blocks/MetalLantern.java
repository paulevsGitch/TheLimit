package paulevs.thelimit.blocks;

import net.modificationstation.stationapi.api.registry.Identifier;

public class MetalLantern extends MetalBlock {
	public MetalLantern(Identifier identifier) {
		super(identifier);
		setLightEmittance(1F);
	}
}
