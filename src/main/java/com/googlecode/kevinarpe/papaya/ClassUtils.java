package com.googlecode.kevinarpe.papaya;

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public final class ClassUtils {
	
	private static final String ANON_CLASS_SIMPLE_NAME = "(anon class)";

	/**
	 * Returns the full class name: package name + "." + class name 
	 * 
	 * @param clazz class object to build full class name
	 * @return full class name, e.g., java.lang.String
	 * @throws NullPointerException if {@code clazz} is {@code null}
	 */
	@NotFullyTested
	public static <T> String getFullName(Class<T> clazz) {
		ObjectArgs.checkNotNull(clazz, "clazz");
		Package pkg = clazz.getPackage();
		String simpleName = clazz.getSimpleName();
		if (null == simpleName || simpleName.isEmpty()) {
			simpleName = ANON_CLASS_SIMPLE_NAME;
		}
		String s = pkg.getName() + "." + simpleName;
		return s;
	}
}
