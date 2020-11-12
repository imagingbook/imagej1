package plugins.wilbur;

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
