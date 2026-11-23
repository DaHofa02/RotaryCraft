/*******************************************************************************
 * Private NeoForge 1.21.1 port of RotaryCraft; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.client.render;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

import reika.rotarycraft.client.OriginalModels;

/**
 * For machines that are plain cubes in the port but had a model in the original: draws the original model when the private
 * original-models pack provides it (the pack then also hides the cube), else nothing; an existing renderer (the containment dome)
 * still draws on top.
 */
public class OriginalModelRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {

	private final String id;
	@Nullable
	private final BlockEntityRenderer<T> parent;

	public OriginalModelRenderer(String id, @Nullable BlockEntityRenderer<T> parent) {
		this.id = id;
		this.parent = parent;
	}

	@Override
	public void render(T te, float partial, PoseStack pose, MultiBufferSource buf, int light, int overlay) {
		if (OriginalModels.has(id)) {
			pose.pushPose();
			pose.translate(0, 2.0F, 1.0F);
			pose.scale(1.0F, -1.0F, -1.0F);
			pose.translate(0.5F, 0.5F, 0.5F);
			OriginalModels.renderFacing(id, 0, te.hasLevel(), pose, buf, light, overlay);
			pose.popPose();
		}
		if (parent != null)
			parent.render(te, partial, pose, buf, light, overlay);
	}

	@Override
	public AABB getRenderBoundingBox(T te) {
		AABB box = new AABB(te.getBlockPos()).inflate(1);
		return parent == null ? box : parent.getRenderBoundingBox(te).minmax(box);
	}

	@Override
	public boolean shouldRenderOffScreen(T te) {
		return parent != null && parent.shouldRenderOffScreen(te);
	}

	@Override
	public int getViewDistance() {
		return parent == null ? 64 : parent.getViewDistance();
	}
}
