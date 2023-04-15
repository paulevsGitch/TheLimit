package paulevs.thelimit.blocks.basic;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import paulevs.thelimit.blocks.TLBlockSounds;
import paulevs.thelimit.rendering.SimpleTexturedBlock;

public class MetalBlock extends TemplateBlockBase implements SimpleTexturedBlock {
	public MetalBlock(Identifier identifier) {
		super(identifier, Material.METAL);
		setSounds(TLBlockSounds.METAL);
		setBlastResistance(2F);
		setHardness(1F);
	}
}
