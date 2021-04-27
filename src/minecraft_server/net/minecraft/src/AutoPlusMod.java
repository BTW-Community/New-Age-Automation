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
	
	private AutoPlusMod() {
		super("Automation+", "1.0.0", "AutoPlus");
	}
	
	public static AutoPlusMod getInstance() {
		if (instance == null) {
			instance = new AutoPlusMod();
		}
		
		return instance;
	}

	@Override
	public void Initialize() {
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		AutoPlusDecoIntegration.init();
		AutoPlusDefs.addDefinitions();
		AutoPlusRecipes.addRecipes();
		FCAddOnHandler.LogMessage(this.getName() + " Initialized");
	}

	public String GetLanguageFilePrefix()
	{
		return "AutoPlus";
	}
}