package net.minecraft.src;

public class AutoPlusRecipes {
	public static void addRecipes() {
		addStonecutterRecipesToVanillaBlocks();
	}
	
	private static void addStonecutterRecipesToVanillaBlocks() {
		Block.stone.setItemIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSmoothStoneSidingAndCorner.blockID);
		Block.stone.setItemCountDroppedOnStonecutter(2);
		
	}
}