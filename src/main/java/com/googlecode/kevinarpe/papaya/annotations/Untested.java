package com.googlecode.kevinarpe.papaya.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates the item is <b>not</b> considered fully tested.  Some tests may exist,
 * but they are not considered complete.  In the longer view, untested items should move towards
 * being fully tested.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 * @see Tested
 */
@Retention(RetentionPolicy.CLASS)
@Target({
	ElementType.CONSTRUCTOR,
	ElementType.METHOD,
	ElementType.TYPE})
@Documented
public @interface Untested {
}
