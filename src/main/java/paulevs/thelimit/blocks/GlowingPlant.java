package paulevs.thelimit.blocks;

import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.thelimit.blocks.basic.PlantBlock;

public class GlowingPlant extends PlantBlock {
	public GlowingPlant(Identifier identifier) {
		super(identifier);
		setSounds(GRASS_SOUNDS);
		setHardness(0.25F);
		setLightEmittance(1F);
		setBoundingBox(0.0625F, 0, 0.0625F, 0.9375F, 0.5F, 0.9375F);
	}
}
