package dev.tucanu.pvz.entity.client.CherryBomb;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.PlantEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class CherryBombModel<T extends PlantEntity> extends HierarchicalModel<T>
{
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(PlantsvsZombies.MODID,
					"cherry_bomb"), "main");
	private final ModelPart root;
	private final ModelPart big_cherry;
	private final ModelPart small_cherry;
	private final ModelPart stem;

	public CherryBombModel(ModelPart root) {
		this.root = root;
		this.big_cherry = root.getChild("big_cherry");
		this.small_cherry = root.getChild("small_cherry");
		this.stem = root.getChild("stem");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition big_cherry = partdefinition.addOrReplaceChild("big_cherry", CubeListBuilder.create(), PartPose.offset(-4.25F, 19.75F, 0.0F));

		PartDefinition cube_r1 = big_cherry.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition small_cherry = partdefinition.addOrReplaceChild("small_cherry", CubeListBuilder.create(), PartPose.offset(5.6195F, 20.5086F, 0.0F));

		PartDefinition cube_r2 = small_cherry.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition stem = partdefinition.addOrReplaceChild("stem", CubeListBuilder.create().texOffs(24, 16).addBox(-5.0F, -4.5F, 0.0F, 10.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, 13.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(PlantEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
						  float netHeadYaw, float headPitch) {
		this.root.getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.explodingAnimationState, CherryBombEntityAnimation.CHERRY_BOMB_EXPLODE, ageInTicks);

		float animTime = ageInTicks / 20.0F;

		// Big Cherry
		this.big_cherry.xRot = (float) Math.toRadians(Math.sin(animTime * 30.0F) * 3.0F);
		this.big_cherry.zRot = (float) Math.toRadians(Math.cos(animTime * 20.0F) * 3.0F);

		// Small Cherry
		this.small_cherry.xRot = (float) Math.toRadians(Math.sin((animTime + 0.5F) * 30.0F) * 3.0F);
		this.small_cherry.zRot = (float) Math.toRadians(Math.cos((animTime + 0.5F) * 20.0F) * 3.0F);

		// Stem
		this.stem.zRot = (float) Math.toRadians(Math.sin((animTime) * 60.0F) * 5.0F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
							   int packedLight, int packedOverlay, int color) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root()
	{
		return this.root;
	}

}