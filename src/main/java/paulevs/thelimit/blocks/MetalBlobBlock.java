package paulevs.thelimit.blocks;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;

public class MetalBlobBlock extends BlobBlock {
	public MetalBlobBlock(Identifier identifier) {
		super(identifier, Material.METAL);
		setSounds(TLBlockSounds.METAL);
		setBlastResistance(2F);
		setHardness(1F);
	}
}
