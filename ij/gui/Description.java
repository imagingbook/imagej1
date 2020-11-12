package ij.gui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Field annotation with a (required) string parameter to supply a descriptive
 * text to the associated enum constant.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
	public String value(); // no default, value must be supplied!
}