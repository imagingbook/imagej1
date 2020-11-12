package ij.gui;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;


/**
 * More advanced version of adding enums to GenericDialog, using optional 
 * {@link Description} annotations
 * to attach desciptive texts to individual enum constants.
 * 
 * @author W. Burger
 * @version 2020/11/12
 */
public class GenericDialogWithEnums2 extends GenericDialog {
	private static final long serialVersionUID = 1L;
	
	public GenericDialogWithEnums2(String title) {
		super(title);
	}
	
	// -----------------------------------------------------------------------------------------------
	// Methods below to be added to ij.gui.GenericDialog
	// -----------------------------------------------------------------------------------------------
	
	private static <E extends Enum<E>> String[] getEnumNames(Class<E> enumclass) {
		E[] eConstants = enumclass.getEnumConstants();
		String[] eNames = new String[eConstants.length];
		for (int i = 0; i < eConstants.length; i++) {
			eNames[i] = eConstants[i].name();
		}
		return eNames;
	}
	
	private static String[] getEnumDescriptions(Class<? extends Enum<?>> enumClass) {
		Field[] declaredFields = enumClass.getDeclaredFields();
		List<String> descList = new LinkedList<String>();
		for (Field df : declaredFields) {
			if (df.isEnumConstant()) {
				Description desc = df.getDeclaredAnnotation(Description.class);
				String descString = (desc != null) ? desc.value() : df.getName();
				descList.add(descString);
			}
		}
		return descList.toArray(new String[0]);
	}
	
	
	/**
	 * Convenience method for {@link #addEnumChoice(String, Enum, boolean)},
	 * set up to show enum descriptions by default.
	 * 
	 * @param <E> the enum type containing the items to chose from
	 * @param label the label displayed for this choice group
	 * @param defaultItem the menu item initially selected
	 */
	public <E extends Enum<E>> void addEnumChoice(String label, Enum<E> defaultItem) {
		addEnumChoice(label, defaultItem, true);
	}
	
	/**
	 * Adds a sequence of choices to the dialog with menu items taken from the
	 * <code>enum</code> class of the specified default item (enum constant).
	 * The default item is automatically set.
	 * Optionally the descriptions of the enum constants are displayed
	 * (if defined); see {@link Enums.Description} and {@link Enums#getEnumDescriptions(Class)}.
	 * 
	 * This method calls the original string-based
	 * {@link GenericDialog#addChoice(String, String[], String)} method.
	 * 
	 * @param <E> the enum type containing the items to chose from
	 * @param label the label displayed for this choice group
	 * @param defaultItem the menu item initially selected
	 * @param showEnumDescriptions if true, the descriptions of the enum constants are shown 
	 */
	public <E extends Enum<E>> void addEnumChoice(String label, Enum<E> defaultItem, boolean showEnumDescriptions) {
		Class<E> enumClass = defaultItem.getDeclaringClass();
		String[] items = showEnumDescriptions ?
				getEnumDescriptions(enumClass) :
				getEnumNames(enumClass);
		String defaultDesc = items[defaultItem.ordinal()]; // defaultItem.name()
		this.addChoice(label, items, defaultDesc);
	}
	
	/**
	 * Returns the selected item in the next enum choice menu.
	 * Note that 'enumClass' must be supplied since there is no other way to infer 
	 * the proper enum type.
	 * 
	 * @param <E> the enum type
	 * @param enumClass the enum type
	 * @return the selected item
	 */
	public <E extends Enum<E>> E getNextEnumChoice(Class<E> enumClass) {
		int k = this.getNextChoiceIndex();
		return enumClass.getEnumConstants()[k];
	}
	
	// -----------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------
}

/* Usage EXAMPLE:
import ij.IJ;
import ij.gui.Description;
import ij.gui.GenericDialogWithEnums2;
import ij.plugin.PlugIn;

public class GenericDialogWithEnums_Example2 implements PlugIn {
	
	private enum MyEnum {
		@Description("My favorite method") Alpha,
		@Description("This I use sometimes") Beta,
		Gamma;
	}

	@Override
	public void run(String arg) {
		
		GenericDialogWithEnums2 gd = new GenericDialogWithEnums2("Testing enums with annotations");
		gd.addEnumChoice("Choose from here", MyEnum.Beta);
		gd.showDialog();
		if (gd.wasCanceled())
			return;
		MyEnum m = gd.getNextEnumChoice(MyEnum.class);
		IJ.log("Your choice was " + m);
	}

}
*/


