package paulevs.thelimit.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.level.dimension.Dimension;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.DimensionRegistryEvent;
import net.modificationstation.stationapi.api.registry.DimensionContainer;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.BlockPos.Mutable;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.blocks.TheLimitBlocks;
import paulevs.thelimit.config.Configs;
import paulevs.thelimit.dimension.TheLimitDimension;
import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.noise.VoronoiNoise;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.util.Random;

public class InitEvents {
	@EventListener
	public void registerDimension(DimensionRegistryEvent event) {
		event.registry.register(TheLimit.id("the_limit"), new DimensionContainer<Dimension>(TheLimitDimension::new));
	}
	
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		TheLimitBlocks.init();
	}
	
	@EventListener
	public void saveConfigs(PostInitEvent event) {
		Configs.saveAll();
		//showIslands();
	}
	
	final PerlinNoise terrainNoise = new PerlinNoise(0);
	final VoronoiNoise islandNoise = new VoronoiNoise(0);
	final VoronoiNoise distortX = new VoronoiNoise(1);
	final VoronoiNoise distortY = new VoronoiNoise(2);
	final Random random = new Random(0);
	
	private void showIslands() {
		BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
		int cx = img.getWidth() >> 1;
		int cy = img.getHeight() >> 1;
		
		BlockPos.Mutable pos = new Mutable();
		for (int x = 0; x < 512; x++) {
			pos.setX(x - 256);
			for (int y = 0; y < 256; y++) {
				pos.setY(y);
				for (int z = 0; z < 512; z++) {
					pos.setZ(z - 256);
					if (getDensity(pos) < 0.5) continue;
					int px = ((pos.getZ() - pos.getX()) >> 1) + cx;
					int py = (-y >> 1) + ((pos.getX() + pos.getZ()) >> 2) + cy;
					if (px < 0 || py < 0 || px > img.getWidth() || py > img.getHeight()) continue;
					int rgb = y;// << 1;
					rgb = 255 << 24 | rgb << 16 | ((y & 1) * 64) << 8 | (255 - rgb);
					img.setRGB(px, py, rgb);
				}
			}
		}
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
	}
	
	private float getDensity(BlockPos.Mutable pos) {
		double px = pos.getX() * 0.01;
		double pz = pos.getZ() * 0.01;
		
		// Distortion
		float dx = distortX.get(px, pz);
		float dz = distortY.get(px, pz);
		px += dx;
		pz += dz;
		
		long seed = islandNoise.getID(px, pz);
		random.setSeed(seed);
		
		float scale = MathHelper.lerp(random.nextFloat(), 1F, 1.5F);
		float density = 0.9F - islandNoise.get(px, pz) * scale;
		
		float height = MathHelper.lerp(terrainNoise.get(px, pz), random.nextFloat(), 0.3F);
		height = MathHelper.lerp(height, -30, 30);//terrainNoise.get(pos.getX() * 0.01, pos.getZ() * 0.01);
		
		density += getGradient(pos.getY() + height, 125, -1 * scale, 0, -4);
		
		// Big details
		density -= terrainNoise.get(pos.getX() * 0.05, pos.getY() * 0.05, pos.getZ() * 0.05) * 0.1F;
		
		// Small details
		//density += terrainNoise.get(pos.getX() * 0.1, pos.getY() * 0.1, pos.getZ() * 0.1) * 0.01F;
		
		return density;
	}
	
	private float getGradient(float y, float level, float bottom, float middle, float top) {
		if (y < level) {
			return MathHelper.lerp(y / level, bottom, middle);
		}
		else {
			return MathHelper.lerp((y - level) / (255 - level), middle, top);
		}
	}
}
