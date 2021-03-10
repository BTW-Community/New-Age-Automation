/**
 * 
 */
package net.minecraft.src;

/**
 * @author Abigail Moore
 *
 */
public class AutoPlusMod extends AddonExt {
	private static AutoPlusMod instance;
	
	public AutoPlusMod() {
		super("Automation Plus", "1.0.0", "AutoPlus");
	}
	
	public static AutoPlusMod getInstance() {
		if (instance == null) {
			instance = new AutoPlusMod();
		}
		
		return instance;
	}

	@Override
	public void Initialize() {
		AutoPlusDefs.addDefinitions();
		AutoPlusRecipes.addRecipes();
	}

	public String GetLanguageFilePrefix()
	{
		return "AutoPlus";
	}
}