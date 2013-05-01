package com.googlecode.kevinarpe.papaya.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates the item is considered fully tested, which is (initially) defined as
 * thoroughly tested with unit and system tests.  Where possible, all instructions and branches
 * should be tested and demonstrated via code coverage tools.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 * @see Untested
 */
@Retention(RetentionPolicy.CLASS)
@Target({
	ElementType.CONSTRUCTOR,
	ElementType.METHOD,
	ElementType.TYPE})
@Documented
public @interface Tested {
}
