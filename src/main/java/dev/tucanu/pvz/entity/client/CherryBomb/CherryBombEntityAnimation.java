package dev.tucanu.pvz.entity.client.CherryBomb;// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Made with Blockbench 5.0.7
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 *
 * @author Author
 */
@OnlyIn(Dist.CLIENT)
public class CherryBombEntityAnimation {
		public static final AnimationDefinition CHERRY_BOMB_EXPLODE =
				AnimationDefinition.Builder.withLength(3.0F) // Matches 60 ticks
						.addAnimation("big_cherry", new AnimationChannel(AnimationChannel.Targets.SCALE,
								new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
								new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), AnimationChannel.Interpolations.LINEAR),
								new Keyframe(2.8F, KeyframeAnimations.scaleVec(1.4F, 1.4F, 1.4F),
										AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.8F, 1.8F, 1.8F), AnimationChannel.Interpolations.LINEAR)
						))
						.addAnimation("small_cherry", new AnimationChannel(AnimationChannel.Targets.SCALE,
								new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
								new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), AnimationChannel.Interpolations.LINEAR),
								new Keyframe(2.8F, KeyframeAnimations.scaleVec(1.4F, 1.4F, 1.4F), AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.8F, 1.8F, 1.8F), AnimationChannel.Interpolations.LINEAR)
						))
						.build();
}