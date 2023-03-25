package paulevs.thelimit.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.gen.FixedBiomeSource;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.maths.Vec3f;
import net.modificationstation.stationapi.impl.level.StationDimension;
import paulevs.thelimit.TheLimit;

public class TheLimitDimension extends Dimension implements StationDimension {
	private final Vec3f skyColor = Vec3f.method_1293(90F / 255F, 40F / 255F, 121F / 255F);
	//private final float[] skyColor2 = new float[] { 0.5F, 0.2F, 0.0F, 1.0F };
	
	public TheLimitDimension(int id) {
		this.id = id;
		this.halvesMapping = true; // No Skylight
	}
	
	@Override
	public void initBiomeSource() {
		this.biomeSource = new FixedBiomeSource(Biome.SKY, 0.5, 0.0);
	}
	
	@Override
	public LevelSource createLevelSource() {
		return new TheLimitWorldgen(this.level);
	}
	
	@Override
	public boolean canSpawnOn(int x, int z) {
		int y = TheLimit.getHeight(level, x, z);
		return y > 0;
	}
	
	// That is fog color!
	@Environment(value= EnvType.CLIENT)
	public Vec3f getSkyColour(float f, float g) {
		return skyColor;
	}
	
	/*@Environment(value=EnvType.CLIENT)
	public float[] getSunsetDawnColour(float f, float g) {
		return skyColor2;
	}*/
	
	@Environment(value=EnvType.CLIENT)
	public boolean hasPaleSky() {
		return false;
	}
	
	@Override
	public float getSunPosition(long l, float f) {
		return 0.0f;
	}
	
	@Override
	public short getDefaultLevelHeight() {
		return 256;
	}
}
