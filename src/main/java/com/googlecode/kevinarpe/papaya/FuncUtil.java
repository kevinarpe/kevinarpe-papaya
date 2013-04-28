package com.googlecode.kevinarpe.papaya;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class FuncUtil {

	public static interface Func0<TResult> {
		TResult run();
	}

	public static interface Func1<TResult, TArg1> {
		TResult run(TArg1 arg1);
	}

	public static interface Func2<TResult, TArg1, TArg2> {
		TResult run(TArg1 arg1, TArg2 arg2);
	}
	
	public static interface Func3<TResult, TArg1, TArg2, TArg3> {
		TResult run(TArg1 arg1, TArg2 arg2, TArg3 arg3);
	}
	
	/**
	 * Calls {@link StringUtil#parseBoolean(String)}.
	 */
	public static final Func1<Boolean, String> PARSE_BOOLEAN_FROM_STRING;
	
	/**
	 * Calls {@link Integer#parseInt(String, int)}.
	 */
	public static final Func2<Integer, String, Integer> PARSE_INTEGER_FROM_STRING;
	
	/**
	 * Calls {@link Integer#parseInt(String, int)}, where {@code radix = 8}.
	 */
	public static final Func1<Integer, String> PARSE_OCTAL_INTEGER_FROM_STRING;
	
	/**
	 * Calls {@link Integer#parseInt(String, int)}, where {@code radix = 10}.
	 */
	public static final Func1<Integer, String> PARSE_DECIMAL_INTEGER_FROM_STRING;
	
	/**
	 * Calls {@link Integer#parseInt(String, int)}, where {@code radix = 16}.
	 */
	public static final Func1<Integer, String> PARSE_HEXIDECIMAL_INTEGER_FROM_STRING;
	
	/**
	 * Calls {@link Float#parseFloat(String)}.
	 */
	public static final Func1<Float, String> PARSE_FLOAT_FROM_STRING;
	
	/**
	 * Calls {@link Double#parseDouble(String)}.
	 */
	public static final Func1<Double, String> PARSE_DOUBLE_FROM_STRING;
	
	static {
		PARSE_BOOLEAN_FROM_STRING = new Func1<Boolean, String>() {

			@Override
			public Boolean run(String str) {
				boolean b = StringUtil.parseBoolean(str);
				return b;
			}
		};
		
		PARSE_INTEGER_FROM_STRING = new Func2<Integer, String, Integer>() {

			@Override
			public Integer run(String str, Integer radix) {
				int i = Integer.parseInt(str, radix);
				return i;
			}
		};
		
		PARSE_OCTAL_INTEGER_FROM_STRING = new Func1<Integer, String>() {

			@Override
			public Integer run(String str) {
				final int radix = 8;
				int i = Integer.parseInt(str, radix);
				return i;
			}
		};
		
		PARSE_DECIMAL_INTEGER_FROM_STRING = new Func1<Integer, String>() {

			@Override
			public Integer run(String str) {
				final int radix = 10;
				int i = Integer.parseInt(str, radix);
				return i;
			}
		};
		
		PARSE_HEXIDECIMAL_INTEGER_FROM_STRING = new Func1<Integer, String>() {

			@Override
			public Integer run(String str) {
				final int radix = 16;
				int i = Integer.parseInt(str, radix);
				return i;
			}
		};
		
		PARSE_FLOAT_FROM_STRING = new Func1<Float, String>() {

			@Override
			public Float run(String str) {
				float x = Float.parseFloat(str);
				return x;
			}
		};
		
		PARSE_DOUBLE_FROM_STRING = new Func1<Double, String>() {

			@Override
			public Double run(String str) {
				double x = Double.parseDouble(str);
				return x;
			}
		};
	}
}
