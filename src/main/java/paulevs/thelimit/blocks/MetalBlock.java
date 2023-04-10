package paulevs.thelimit.blocks;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class MetalBlock extends TemplateBlockBase {
	public MetalBlock(Identifier identifier) {
		super(identifier, Material.METAL);
		setSounds(TLBlockSounds.METAL);
		setBlastResistance(2F);
		setHardness(1F);
	}
}
