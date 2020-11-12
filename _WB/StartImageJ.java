package _WB;

import ij.IJ;
import ij.ImageJ;
import ij.Prefs;


public class StartImageJ {

	public static void main(String[] argsignored) {
		System.out.println("Starting ImageJ ...");
		Prefs.weightedColor = true;
		ImageJ.main(new String[0]);
		if (IJ.getInstance() != null) {
			System.out.println("ImageJ " + IJ.getVersion() + " running!");
		}
		else {
			System.out.println("Could not start ImageJ!");
		}
	}
}

