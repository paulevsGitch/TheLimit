package paulevs.thelimit.blocks;

import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.thelimit.blocks.basic.MetalBlock;

public class MetalLantern extends MetalBlock {
	public MetalLantern(Identifier identifier) {
		super(identifier);
		setLightEmittance(1F);
	}
}
