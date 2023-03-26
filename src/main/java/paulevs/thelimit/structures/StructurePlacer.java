package paulevs.thelimit.structures;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.structure.Structure;

import java.util.Random;

public class StructurePlacer {
	private final Structure structure;
	private final int count;
	
	public StructurePlacer(Structure structure, int count) {
		this.structure = structure;
		this.count = count;
	}
	
	public void place(Level level, Chunk chunk, Random random, int wx, int wz) {
		for (byte i = 0; i < count; i++) {
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int y = chunk.getHeight(x, z);
			if (y < 1) continue;
			structure.generate(level, random, x | wx, y, z | wz);
		}
	}
}
