/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ververica.cdc.connectors.mysql.source.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/** Utilities for operation on {@link Object}. */
public class ObjectUtils {

    /**
     * Returns a number {@code Object} whose value is {@code (number + augend)}, Note: This method
     * does not consider number overflow because we don't want to change the object type.
     */
    public static Object plus(Object number, int augend) {
        if (number instanceof Integer) {
            return (int) number + augend;
        } else if (number instanceof Long) {
            return (long) number + augend;
        } else if (number instanceof BigInteger) {
            return ((BigInteger) number).add(BigInteger.valueOf(augend));
        } else if (number instanceof BigDecimal) {
            return ((BigDecimal) number).add(BigDecimal.valueOf(augend));
        } else {
            throw new UnsupportedOperationException(
                    String.format(
                            "Unsupported type %s for numeric plus.",
                            number.getClass().getSimpleName()));
        }
    }

    /** Returns the difference {@code BigDecimal} whose value is {@code (minuend - subtrahend)}. */
    public static BigDecimal minus(Object minuend, Object subtrahend) {
        if (!minuend.getClass().equals(subtrahend.getClass())) {
            throw new IllegalStateException(
                    String.format(
                            "Unsupported operand type, the minuend type %s is different with subtrahend type %s.",
                            minuend.getClass().getSimpleName(),
                            subtrahend.getClass().getSimpleName()));
        }
        if (minuend instanceof Integer) {
            return BigDecimal.valueOf((int) minuend).subtract(BigDecimal.valueOf((int) subtrahend));
        } else if (minuend instanceof Long) {
            return BigDecimal.valueOf((long) minuend)
                    .subtract(BigDecimal.valueOf((long) subtrahend));
        } else if (minuend instanceof BigInteger) {
            return new BigDecimal(
                    ((BigInteger) minuend).subtract((BigInteger) subtrahend).toString());
        } else if (minuend instanceof BigDecimal) {
            return ((BigDecimal) minuend).subtract((BigDecimal) subtrahend);
        } else {
            throw new UnsupportedOperationException(
                    String.format(
                            "Unsupported type %s for numeric minus.",
                            minuend.getClass().getSimpleName()));
        }
    }

    /**
     * Compares two comparable objects.
     *
     * @return The value {@code 0} if {@code num1} is equal to the {@code num2}; a value less than
     *     {@code 0} if the {@code num1} is numerically less than the {@code num2}; and a value
     *     greater than {@code 0} if the {@code num1} is numerically greater than the {@code num2}.
     * @throws ClassCastException if the compared objects are not instance of {@link Comparable} or
     *     not <i>mutually comparable</i> (for example, strings and integers).
     */
    @SuppressWarnings("unchecked")
    public static int compare(Object obj1, Object obj2) {
        Comparable<Object> c1 = (Comparable<Object>) obj1;
        Comparable<Object> c2 = (Comparable<Object>) obj2;
        return c1.compareTo(c2);
    }
}
