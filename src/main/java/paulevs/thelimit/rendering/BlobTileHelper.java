package paulevs.thelimit.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockBase;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlobTileHelper {
	private static final short[] VALUES = new short[256];
	
	public static int getTexture(BlockStateView view, int x, int y, int z, BlockBase target, Direction face) {
		int index = 0;
		switch (face) {
			case UP, DOWN -> {
				if (view.getBlockState(x - 1, y, z - 1).getBlock() == target) index += 1;
				if (view.getBlockState(x, y, z - 1).getBlock() == target) index += 2;
				if (view.getBlockState(x + 1, y, z - 1).getBlock() == target) index += 4;
				if (view.getBlockState(x + 1, y, z).getBlock() == target) index += 8;
				if (view.getBlockState(x + 1, y, z + 1).getBlock() == target) index += 16;
				if (view.getBlockState(x, y, z + 1).getBlock() == target) index += 32;
				if (view.getBlockState(x - 1, y, z + 1).getBlock() == target) index += 64;
				if (view.getBlockState(x - 1, y, z).getBlock() == target) index += 128;
			}
			case NORTH, SOUTH -> {
				int dz = face == Direction.SOUTH ? 1 : -1;
				if (view.getBlockState(x, y + 1, z + dz).getBlock() == target) index += 1;
				if (view.getBlockState(x, y + 1, z).getBlock() == target) index += 2;
				if (view.getBlockState(x, y + 1, z - dz).getBlock() == target) index += 4;
				if (view.getBlockState(x, y, z - dz).getBlock() == target) index += 8;
				if (view.getBlockState(x, y - 1, z - dz).getBlock() == target) index += 16;
				if (view.getBlockState(x, y - 1, z).getBlock() == target) index += 32;
				if (view.getBlockState(x, y - 1, z + dz).getBlock() == target) index += 64;
				if (view.getBlockState(x, y, z + dz).getBlock() == target) index += 128;
			}
			case EAST, WEST -> {
				int dx = face == Direction.EAST ? 1 : -1;
				if (view.getBlockState(x + dx, y + 1, z).getBlock() == target) index += 1;
				if (view.getBlockState(x, y + 1, z).getBlock() == target) index += 2;
				if (view.getBlockState(x - dx, y + 1, z).getBlock() == target) index += 4;
				if (view.getBlockState(x - dx, y, z).getBlock() == target) index += 8;
				if (view.getBlockState(x - dx, y - 1, z).getBlock() == target) index += 16;
				if (view.getBlockState(x, y - 1, z).getBlock() == target) index += 32;
				if (view.getBlockState(x + dx, y - 1, z).getBlock() == target) index += 64;
				if (view.getBlockState(x + dx, y, z).getBlock() == target) index += 128;
			}
		}
		return VALUES[index];
	}
	
	static {
		short[] tilesetterRules = new short[] {
			56, 62, 14, 8, 248, 255, 143, 136,
			224, 227, 131, 128, 32, 34, 2, 0,
			40, 46, 58, 10, 42, 232, 239, 251,
			139, 235, 184, 191, 254, 142, 190, 160,
			163, 226, 130, 162, 168, 175, 250, 138,
			170, 187, 238, 186, 174, 234, 171
		};
		
		Map<Short, Short> conversionMap = new HashMap<>();
		
		for (short i = 0; i < tilesetterRules.length; i++) {
			conversionMap.put(getIndex(tilesetterRules[i]), i);
		}
		
		for (short i = 0; i < 256; i++) {
			short index = getIndex(i);
			VALUES[i] = conversionMap.get(index);
		}
	}
	
	private static short getIndex(int value) {
		boolean[] sides = new boolean[4];
		boolean[] corners = new boolean[4];
		
		sides[0] = ((value >> 1) & 1) == 0;
		sides[1] = ((value >> 3) & 1) == 0;
		sides[2] = ((value >> 5) & 1) == 0;
		sides[3] = ((value >> 7) & 1) == 0;
		
		corners[0] = (sides[0] && sides[1]) || ((((value >> 2) & 1) == 0) && !sides[0] && !sides[1]);
		corners[1] = (sides[1] && sides[2]) || ((((value >> 4) & 1) == 0) && !sides[1] && !sides[2]);
		corners[2] = (sides[2] && sides[3]) || ((((value >> 6) & 1) == 0) && !sides[2] && !sides[3]);
		corners[3] = (sides[3] && sides[0]) || (((value & 1) == 1) && !sides[3] && !sides[0]);
		
		int index = 0;
		for (int j = 0; j < 4; j++) {
			if (corners[j]) index += 1 << (j * 2);
			if (sides[j]) index += 1 << (j * 2 + 1);
		}
		return (short) index;
	}
}
