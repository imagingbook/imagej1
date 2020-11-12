package ij.gui;

/**
 * Simple extension to {@link GenericDialog} to allow enum types
 * for choice groups.
 * 
 * @author W. Burger
 * @version 2020/11/12
 *
 */
public class GenericDialogWithEnums extends GenericDialog {
	private static final long serialVersionUID = 1L;
	
	public GenericDialogWithEnums(String title) {
		super(title);
	}
	
	// -----------------------------------------------------------------------------------------------
	// Two methods below to be added to ij.gui.GenericDialog
	// -----------------------------------------------------------------------------------------------
	
	/**
	 * Adds a group of choices to the dialog with menu items taken from the
	 * <code>enum</code> class of the specified default item (enum constant).
	 * The default item is automatically set. Calls the original (string-based)
	 * {@link GenericDialog#addChoice(String, String[], String)} method.
	 * 
	 * @param <E> the generic enum type containing the items to chose from
	 * @param label the label displayed for this choice group
	 * @param defaultItem the menu item initially selected
	 */
	public <E extends Enum<E>> void addEnumChoice(String label, Enum<E> defaultItem) {
		Class<E> enumClass = defaultItem.getDeclaringClass();	
		E[] enums = enumClass.getEnumConstants();
		String[] items = new String[enums.length];
		for (int i = 0; i < enums.length; i++) {
			items[i] = enums[i].name();
		}
		this.addChoice(label, items, defaultItem.name());
	}
	
	/**
	 * Returns the selected item in the next enum choice menu.
	 * Note that 'enumClass' is required to infer the proper enum type.
	 * Throws {@code IllegalArgumentException} if the selected item is not a defined
	 * constant in the specified enum class.
	 * 
	 * @param <E> the generic enum type
	 * @param enumClass the enum type
	 * @return the selected item
	 */
	public <E extends Enum<E>> E getNextEnumChoice(Class<E> enumClass) {
		String choiceString = this.getNextChoice();
		return Enum.valueOf(enumClass, choiceString);
	}
	
	// -----------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------
}

/* Usage EXAMPLE:
public class GenericDialogWithEnums_Example implements PlugIn {
	
	private enum MyEnum {
		Alpha, 
		Beta, 
		Gamma;
	}

	@Override
	public void run(String arg) {
		
		GenericDialogWithEnums2 gd = new GenericDialogWithEnums2("Testing enums");
		gd.addEnumChoice("Choose from here", MyEnum.Beta);
		gd.showDialog();
		if (gd.wasCanceled())
			return;
		MyEnum m = gd.getNextEnumChoice(MyEnum.class);
		IJ.log("Your choice was " + m);
	}

}
*/
