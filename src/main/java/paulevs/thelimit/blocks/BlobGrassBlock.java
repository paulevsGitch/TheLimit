package paulevs.thelimit.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.registry.Identifier;

public class BlobGrassBlock extends PlantBlock {
	public BlobGrassBlock(Identifier identifier) {
		super(identifier);
	}
	
	@Environment(value= EnvType.CLIENT)
	public int getRenderPass() {
		return 1;
	}
}
