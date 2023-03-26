package paulevs.thelimit.rendering;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.glsl.Shaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.maths.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.util.Random;

public class SkyRenderer {
	private static final boolean USE_SHADERS = FabricLoader.getInstance().isModLoaded("glsl");
	private static final int GRADIENT_1;
	private static int stars;
	private static int box;
	private static int cylinder;
	
	public static void render() {
		disable(GL11.GL_TEXTURE_2D);
		disable(GL11.GL_FOG);
		GL11.glDepthMask(false);
		
		GL11.glColor3f(1 * 0.1F, 0.5F * 0.1F, 1 * 0.1F);
		GL11.glCallList(box);
		
		enable(GL11.GL_TEXTURE_2D);
		enable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GRADIENT_1);
		GL11.glColor3f(1, 0.5F, 1);
		GL11.glCallList(cylinder);
		disable(GL11.GL_TEXTURE_2D);
		disable(GL11.GL_BLEND);
		
		GL11.glColor3f(1, 0.5F, 1);
		GL11.glCallList(stars);
		
		GL11.glDepthMask(true);
		enable(GL11.GL_TEXTURE_2D);
	}
	
	private static void enable(int target) {
		if (USE_SHADERS) Shaders.glEnableWrapper(target);
		else GL11.glEnable(target);
	}
	
	private static void disable(int target) {
		if (USE_SHADERS) Shaders.glDisableWrapper(target);
		else GL11.glDisable(target);
	}
	
	private static void makeStars() {
		Tessellator tessellator = Tessellator.INSTANCE;
		
		Random random = new Random(0);
		float pi2 = (float) (Math.PI * 2);
		
		stars = GL11.glGenLists(1);
		GL11.glNewList(stars, GL11.GL_COMPILE);
		
		tessellator.start();
		
		for (short i = 0; i < 1500; ++i) {
			float preX = random.nextFloat() - 0.5F;
			float preY = random.nextFloat() - 0.5F;
			float preZ = random.nextFloat() - 0.5F;
			
			float scale = 0.125F + random.nextFloat() * 0.125F;
			float dist = preX * preX + preY * preY + preZ * preZ;
			if (dist < 0.01F) continue;
			
			dist = 100F / MathHelper.sqrt(dist);
			preX *= dist;
			preY *= dist;
			preZ *= dist;
			
			float angle = (float) Math.atan2(preX, preZ);
			float sin1 = (float) Math.sin(angle);
			float cos1 = (float) Math.cos(angle);
			
			angle = (float) Math.atan2(Math.sqrt(preX * preX + preZ * preZ), preY);
			float sin2 = (float) Math.sin(angle);
			float cos2 = (float) Math.cos(angle);
			
			angle = random.nextFloat() * pi2;
			float sin3 = (float) Math.sin(angle);
			float cos3 = (float) Math.cos(angle);
			
			for (byte index = 0; index < 4; ++index) {
				float sx = ((index & 2) - 1) * scale;
				float sz = ((index + 1 & 2) - 1) * scale;
				
				float d23 = sx * cos3 - sz * sin3;
				float d24 = sz * cos3 + sx * sin3;
				float d26 = -d23 * cos2;
				
				float dx = d26 * sin1 - d24 * cos1;
				float dy = d23 * sin2;
				float dz = d24 * sin1 + d26 * cos1;
				
				tessellator.addVertex(preX + dx, preY + dy, preZ + dz);
			}
		}
		
		tessellator.draw();
		GL11.glEndList();
	}
	
	private static void makeBox() {
		box = GL11.glGenLists(1);
		GL11.glNewList(box, GL11.GL_COMPILE);
		
		Tessellator tessellator = Tessellator.INSTANCE;
		tessellator.start();
		
		tessellator.addVertex(-100,  100, -100);
		tessellator.addVertex( 100,  100, -100);
		tessellator.addVertex( 100,  100,  100);
		tessellator.addVertex(-100,  100,  100);
		
		tessellator.addVertex(-100, -100,  100);
		tessellator.addVertex( 100, -100,  100);
		tessellator.addVertex( 100, -100, -100);
		tessellator.addVertex(-100, -100, -100);
		
		tessellator.addVertex( 100, -100,  100);
		tessellator.addVertex( 100,  100,  100);
		tessellator.addVertex( 100,  100, -100);
		tessellator.addVertex( 100, -100, -100);
		
		tessellator.addVertex(-100, -100, -100);
		tessellator.addVertex(-100,  100, -100);
		tessellator.addVertex(-100,  100,  100);
		tessellator.addVertex(-100, -100,  100);
		
		tessellator.addVertex(-100,  100,  100);
		tessellator.addVertex( 100,  100,  100);
		tessellator.addVertex( 100, -100,  100);
		tessellator.addVertex(-100, -100,  100);
		
		tessellator.addVertex(-100, -100, -100);
		tessellator.addVertex( 100, -100, -100);
		tessellator.addVertex( 100,  100, -100);
		tessellator.addVertex(-100,  100, -100);
		
		tessellator.draw();
		GL11.glEndList();
	}
	
	private static void makeCylinder() {
		float pi2 = (float) (Math.PI * 2);
		
		cylinder = GL11.glGenLists(1);
		GL11.glNewList(cylinder, GL11.GL_COMPILE);
		Tessellator tessellator = Tessellator.INSTANCE;
		tessellator.start();
		
		final int count = 16;
		float sin1 = MathHelper.sin(0) * 100;
		float cos1 = MathHelper.cos(0) * 100;
		
		for (byte i = 1; i <= count; i++) {
			float angle = (float) i / count * pi2;
			float sin2 = MathHelper.sin(angle) * 100;
			float cos2 = MathHelper.cos(angle) * 100;
			
			tessellator.vertex(sin1, -50, cos1, 0, 0);
			tessellator.vertex(sin1,  50, cos1, 0, 1);
			tessellator.vertex(sin2,  50, cos2, 1, 1);
			tessellator.vertex(sin2, -50, cos2, 1, 0);
			
			sin1 = sin2;
			cos1 = cos2;
		}
		
		tessellator.draw();
		GL11.glEndList();
	}
	
	static {
		makeStars();
		makeBox();
		makeCylinder();
		
		TextureManager textureManager = ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager;
		GRADIENT_1 = textureManager.getTextureId("/assets/thelimit/stationapi/textures/environment/gradient_1.png");
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GRADIENT_1);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_FALSE);
	}
}
