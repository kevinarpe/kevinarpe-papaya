package com.googlecode.kevinarpe.papaya.argument;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ValueAsTypeIterator._IValueAsLongIterator;

public class LongIterableArgs {

    // Disable default constructor
    private LongIterableArgs() {
    }

    /**
     * Tests if all values in an {@link Iterable} are positive: greater than zero.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is not positive
     * 
     * //@see #checkPositive(Long[], String)
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkPositive(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkPositive(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an {@link Iterable} are not positive: less than or equal to zero.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is positive
     * 
     * //@see #checkPositive(Long[], String)
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkNotPositive(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkNotPositive(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an {@link Iterable} are negative: greater than zero.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is not negative
     * 
     * //@see #checkNegative(Long[], String)
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkNegative(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkNegative(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an {@link Iterable} are not negative: less than or equal to zero.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is negative
     * 
     * //@see #checkNegative(Long[], String)
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkNotNegative(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkNotNegative(iter, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} are within specified range.
     * <p>
     * Example: All values must be >= 3 and <= 7:
     * <br>{@code checkValueInsideRange(numbersList, 3, 7, "numbersList");}
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code ref} is outside allowed range</li>
     * </ul>
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkValueInsideRange(TIterable ref, long minValue, long maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkValueInsideRange(iter, minValue, maxValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} are <b>not</b> within specified range.
     * <p>
     * Example: All values must be <= 3 and >= 7:
     * <br>{@code checkValueInsideRange(numbersList, 4, 6, "numbersList");}
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code ref} is inside not-allowed range</li>
     * </ul>
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkValueOutsideRange(TIterable ref, long minValue, long maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkValueOutsideRange(iter, minValue, maxValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} have a minimum value.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive)
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is less than {@code minValue}
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkMinValue(TIterable ref, long minValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkMinValue(iter, minValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} have a maximum value.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param maxValue
     *        maximum value (inclusive)
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is greater than {@code maxValue}
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkMaxValue(TIterable ref, long maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkMaxValue(iter, maxValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} have an exact value.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is {@code exactValue}
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkExactValue(TIterable ref, long exactValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkExactValue(iter, exactValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} do not have an exact value.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is {@code exactValue}
     */
    @NotFullyTested
    public static <TIterable extends Iterable<Long>>
    TIterable checkNotExactValue(TIterable ref, long exactValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
