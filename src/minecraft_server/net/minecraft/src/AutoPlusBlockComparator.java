package net.minecraft.src;

public class AutoPlusBlockComparator extends BlockComparator {
	public AutoPlusBlockComparator(int id, boolean powered) {
		super(id, powered);
		this.InitBlockBounds(0, 0, 0, 1, .125, 1);
	}
}