package com.pvpshield.customitemreward.utils;

import com.hypixel.hytale.protocol.MovementSettings;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;

public class MovementBuilder {
   private final MovementManager movementManager;
   private final PlayerRef playerRef;
   private MovementSettings movementSettings;

   public MovementBuilder(MovementManager movementManager, PlayerRef playerRef) {
      this.movementManager = movementManager;
      this.playerRef = playerRef;
      if (movementManager != null && movementManager.getSettings() != null) {
         this.movementSettings = movementManager.getSettings();
      }
   }

   public void apply() {
      this.movementManager.update(this.playerRef.getPacketHandler());
   }

   public MovementBuilder mass(float mass) {
      this.movementSettings.mass = mass;
      return this;
   }

   public MovementBuilder dragCoefficient(float dragCoefficient) {
      this.movementSettings.dragCoefficient = dragCoefficient;
      return this;
   }

   public MovementBuilder invertedGravity(boolean invertedGravity) {
      this.movementSettings.invertedGravity = invertedGravity;
      return this;
   }

   public MovementBuilder velocityResistance(float velocityResistance) {
      this.movementSettings.velocityResistance = velocityResistance;
      return this;
   }

   public MovementBuilder jumpForce(float jumpForce) {
      this.movementSettings.jumpForce = jumpForce;
      return this;
   }

   public MovementBuilder swimJumpForce(float swimJumpForce) {
      this.movementSettings.swimJumpForce = swimJumpForce;
      return this;
   }

   public MovementBuilder jumpBufferDuration(float jumpBufferDuration) {
      this.movementSettings.jumpBufferDuration = jumpBufferDuration;
      return this;
   }

   public MovementBuilder jumpBufferMaxYVelocity(float jumpBufferMaxYVelocity) {
      this.movementSettings.jumpBufferMaxYVelocity = jumpBufferMaxYVelocity;
      return this;
   }

   public MovementBuilder acceleration(float acceleration) {
      this.movementSettings.acceleration = acceleration;
      return this;
   }

   public MovementBuilder airDragMin(float airDragMin) {
      this.movementSettings.airDragMin = airDragMin;
      return this;
   }

   public MovementBuilder airDragMax(float airDragMax) {
      this.movementSettings.airDragMax = airDragMax;
      return this;
   }

   public MovementBuilder airDragMinSpeed(float airDragMinSpeed) {
      this.movementSettings.airDragMinSpeed = airDragMinSpeed;
      return this;
   }

   public MovementBuilder airDragMaxSpeed(float airDragMaxSpeed) {
      this.movementSettings.airDragMaxSpeed = airDragMaxSpeed;
      return this;
   }

   public MovementBuilder airFrictionMin(float airFrictionMin) {
      this.movementSettings.airFrictionMin = airFrictionMin;
      return this;
   }

   public MovementBuilder airFrictionMax(float airFrictionMax) {
      this.movementSettings.airFrictionMax = airFrictionMax;
      return this;
   }

   public MovementBuilder airFrictionMinSpeed(float airFrictionMinSpeed) {
      this.movementSettings.airFrictionMinSpeed = airFrictionMinSpeed;
      return this;
   }

   public MovementBuilder airFrictionMaxSpeed(float airFrictionMaxSpeed) {
      this.movementSettings.airFrictionMaxSpeed = airFrictionMaxSpeed;
      return this;
   }

   public MovementBuilder airSpeedMultiplier(float airSpeedMultiplier) {
      this.movementSettings.airSpeedMultiplier = airSpeedMultiplier;
      return this;
   }

   public MovementBuilder airControlMinSpeed(float airControlMinSpeed) {
      this.movementSettings.airControlMinSpeed = airControlMinSpeed;
      return this;
   }

   public MovementBuilder airControlMaxSpeed(float airControlMaxSpeed) {
      this.movementSettings.airControlMaxSpeed = airControlMaxSpeed;
      return this;
   }

   public MovementBuilder airControlMinMultiplier(float airControlMinMultiplier) {
      this.movementSettings.airControlMinMultiplier = airControlMinMultiplier;
      return this;
   }

   public MovementBuilder airControlMaxMultiplier(float airControlMaxMultiplier) {
      this.movementSettings.airControlMaxMultiplier = airControlMaxMultiplier;
      return this;
   }

   public MovementBuilder comboAirSpeedMultiplier(float comboAirSpeedMultiplier) {
      this.movementSettings.comboAirSpeedMultiplier = comboAirSpeedMultiplier;
      return this;
   }

   public MovementBuilder baseSpeed(float baseSpeed) {
      this.movementSettings.baseSpeed = baseSpeed;
      return this;
   }

   public MovementBuilder climbSpeed(float climbSpeed) {
      this.movementSettings.climbSpeed = climbSpeed;
      return this;
   }

   public MovementBuilder climbSpeedLateral(float climbSpeedLateral) {
      this.movementSettings.climbSpeedLateral = climbSpeedLateral;
      return this;
   }

   public MovementBuilder climbUpSprintSpeed(float climbUpSprintSpeed) {
      this.movementSettings.climbUpSprintSpeed = climbUpSprintSpeed;
      return this;
   }

   public MovementBuilder climbDownSprintSpeed(float climbDownSprintSpeed) {
      this.movementSettings.climbDownSprintSpeed = climbDownSprintSpeed;
      return this;
   }

   public MovementBuilder horizontalFlySpeed(float horizontalFlySpeed) {
      this.movementSettings.horizontalFlySpeed = horizontalFlySpeed;
      return this;
   }

   public MovementBuilder verticalFlySpeed(float verticalFlySpeed) {
      this.movementSettings.verticalFlySpeed = verticalFlySpeed;
      return this;
   }

   public MovementBuilder maxSpeedMultiplier(float maxSpeedMultiplier) {
      this.movementSettings.maxSpeedMultiplier = maxSpeedMultiplier;
      return this;
   }

   public MovementBuilder minSpeedMultiplier(float minSpeedMultiplier) {
      this.movementSettings.minSpeedMultiplier = minSpeedMultiplier;
      return this;
   }

   public MovementBuilder wishDirectionGravityX(float wishDirectionGravityX) {
      this.movementSettings.wishDirectionGravityX = wishDirectionGravityX;
      return this;
   }

   public MovementBuilder wishDirectionGravityY(float wishDirectionGravityY) {
      this.movementSettings.wishDirectionGravityY = wishDirectionGravityY;
      return this;
   }

   public MovementBuilder wishDirectionWeightX(float wishDirectionWeightX) {
      this.movementSettings.wishDirectionWeightX = wishDirectionWeightX;
      return this;
   }

   public MovementBuilder wishDirectionWeightY(float wishDirectionWeightY) {
      this.movementSettings.wishDirectionWeightY = wishDirectionWeightY;
      return this;
   }

   public MovementBuilder canFly(boolean canFly) {
      this.movementSettings.canFly = canFly;
      return this;
   }

   public MovementBuilder collisionExpulsionForce(float collisionExpulsionForce) {
      this.movementSettings.collisionExpulsionForce = collisionExpulsionForce;
      return this;
   }

   public MovementBuilder forwardWalkSpeedMultiplier(float forwardWalkSpeedMultiplier) {
      this.movementSettings.forwardWalkSpeedMultiplier = forwardWalkSpeedMultiplier;
      return this;
   }

   public MovementBuilder backwardWalkSpeedMultiplier(float backwardWalkSpeedMultiplier) {
      this.movementSettings.backwardWalkSpeedMultiplier = backwardWalkSpeedMultiplier;
      return this;
   }

   public MovementBuilder strafeWalkSpeedMultiplier(float strafeWalkSpeedMultiplier) {
      this.movementSettings.strafeWalkSpeedMultiplier = strafeWalkSpeedMultiplier;
      return this;
   }

   public MovementBuilder forwardRunSpeedMultiplier(float forwardRunSpeedMultiplier) {
      this.movementSettings.forwardRunSpeedMultiplier = forwardRunSpeedMultiplier;
      return this;
   }

   public MovementBuilder backwardRunSpeedMultiplier(float backwardRunSpeedMultiplier) {
      this.movementSettings.backwardRunSpeedMultiplier = backwardRunSpeedMultiplier;
      return this;
   }

   public MovementBuilder strafeRunSpeedMultiplier(float strafeRunSpeedMultiplier) {
      this.movementSettings.strafeRunSpeedMultiplier = strafeRunSpeedMultiplier;
      return this;
   }

   public MovementBuilder forwardCrouchSpeedMultiplier(float forwardCrouchSpeedMultiplier) {
      this.movementSettings.forwardCrouchSpeedMultiplier = forwardCrouchSpeedMultiplier;
      return this;
   }

   public MovementBuilder backwardCrouchSpeedMultiplier(float backwardCrouchSpeedMultiplier) {
      this.movementSettings.backwardCrouchSpeedMultiplier = backwardCrouchSpeedMultiplier;
      return this;
   }

   public MovementBuilder strafeCrouchSpeedMultiplier(float strafeCrouchSpeedMultiplier) {
      this.movementSettings.strafeCrouchSpeedMultiplier = strafeCrouchSpeedMultiplier;
      return this;
   }

   public MovementBuilder forwardSprintSpeedMultiplier(float forwardSprintSpeedMultiplier) {
      this.movementSettings.forwardSprintSpeedMultiplier = forwardSprintSpeedMultiplier;
      return this;
   }

   public MovementBuilder variableJumpFallForce(float variableJumpFallForce) {
      this.movementSettings.variableJumpFallForce = variableJumpFallForce;
      return this;
   }

   public MovementBuilder fallEffectDuration(float fallEffectDuration) {
      this.movementSettings.fallEffectDuration = fallEffectDuration;
      return this;
   }

   public MovementBuilder fallJumpForce(float fallJumpForce) {
      this.movementSettings.fallJumpForce = fallJumpForce;
      return this;
   }

   public MovementBuilder fallMomentumLoss(float fallMomentumLoss) {
      this.movementSettings.fallMomentumLoss = fallMomentumLoss;
      return this;
   }

   public MovementBuilder autoJumpObstacleSpeedLoss(float autoJumpObstacleSpeedLoss) {
      this.movementSettings.autoJumpObstacleSpeedLoss = autoJumpObstacleSpeedLoss;
      return this;
   }

   public MovementBuilder autoJumpObstacleSprintSpeedLoss(float autoJumpObstacleSprintSpeedLoss) {
      this.movementSettings.autoJumpObstacleSprintSpeedLoss = autoJumpObstacleSprintSpeedLoss;
      return this;
   }

   public MovementBuilder autoJumpObstacleEffectDuration(float autoJumpObstacleEffectDuration) {
      this.movementSettings.autoJumpObstacleEffectDuration = autoJumpObstacleEffectDuration;
      return this;
   }

   public MovementBuilder autoJumpObstacleSprintEffectDuration(float autoJumpObstacleSprintEffectDuration) {
      this.movementSettings.autoJumpObstacleSprintEffectDuration = autoJumpObstacleSprintEffectDuration;
      return this;
   }

   public MovementBuilder autoJumpObstacleMaxAngle(float autoJumpObstacleMaxAngle) {
      this.movementSettings.autoJumpObstacleMaxAngle = autoJumpObstacleMaxAngle;
      return this;
   }

   public MovementBuilder autoJumpDisableJumping(boolean autoJumpDisableJumping) {
      this.movementSettings.autoJumpDisableJumping = autoJumpDisableJumping;
      return this;
   }

   public MovementBuilder minSlideEntrySpeed(float minSlideEntrySpeed) {
      this.movementSettings.minSlideEntrySpeed = minSlideEntrySpeed;
      return this;
   }

   public MovementBuilder slideExitSpeed(float slideExitSpeed) {
      this.movementSettings.slideExitSpeed = slideExitSpeed;
      return this;
   }

   public MovementBuilder minFallSpeedToEngageRoll(float minFallSpeedToEngageRoll) {
      this.movementSettings.minFallSpeedToEngageRoll = minFallSpeedToEngageRoll;
      return this;
   }

   public MovementBuilder maxFallSpeedToEngageRoll(float maxFallSpeedToEngageRoll) {
      this.movementSettings.maxFallSpeedToEngageRoll = maxFallSpeedToEngageRoll;
      return this;
   }

   public MovementBuilder rollStartSpeedModifier(float rollStartSpeedModifier) {
      this.movementSettings.rollStartSpeedModifier = rollStartSpeedModifier;
      return this;
   }

   public MovementBuilder rollExitSpeedModifier(float rollExitSpeedModifier) {
      this.movementSettings.rollExitSpeedModifier = rollExitSpeedModifier;
      return this;
   }

   public MovementBuilder rollTimeToComplete(float rollTimeToComplete) {
      this.movementSettings.rollTimeToComplete = rollTimeToComplete;
      return this;
   }
}