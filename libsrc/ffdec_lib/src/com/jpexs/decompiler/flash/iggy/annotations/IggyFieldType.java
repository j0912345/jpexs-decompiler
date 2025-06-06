/*
 *  Copyright (C) 2010-2025 JPEXS, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.jpexs.decompiler.flash.iggy.annotations;

import com.jpexs.decompiler.flash.iggy.DataType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field type annotation.
 *
 * @author JPEXS
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IggyFieldType {

    /// Type of value
    DataType value() default DataType.unknown;

    /// Alternate type when condition is met
    DataType alternateValue() default DataType.unknown;

    /// Condition for alternate type
    String alternateCondition() default "";

    /// Count - used primarily for bit fields UB,SB,FB to specify number of bits
    int count() default -1;

    /// Field name on which Count depends
    String countField() default "";

    //Count to add to countField
    int countAdd() default 0;
}
