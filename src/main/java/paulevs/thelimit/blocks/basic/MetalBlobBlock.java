package paulevs.thelimit.blocks.basic;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.thelimit.blocks.TLBlockSounds;

public class MetalBlobBlock extends BlobBlock {
	public MetalBlobBlock(Identifier identifier) {
		super(identifier, Material.METAL);
		setSounds(TLBlockSounds.METAL);
		setBlastResistance(2F);
		setHardness(1F);
	}
}
