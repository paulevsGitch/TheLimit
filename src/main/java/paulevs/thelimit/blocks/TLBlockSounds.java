package paulevs.thelimit.blocks;

import net.minecraft.block.BlockSounds;

public class TLBlockSounds {
	public static final BlockSounds METAL = new BlockSounds("thelimit:metal", 1.0f, 1.0f);
	public static final BlockSounds HYPHUM = new HyphumSounds();
	
	private static class HyphumSounds extends BlockSounds {
		public HyphumSounds() {
			super("thelimit:hyphum", 1.0f, 1.0f);
		}
		
		/*@Override
		public String getBreakSound() {
			return "step.grass";
		}
		
		@Override
		public String getWalkSound() {
			return "step.grass";
		}*/
	}
}
