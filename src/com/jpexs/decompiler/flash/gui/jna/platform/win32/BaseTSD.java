/*
 *  Copyright (C) 2010-2025 JPEXS
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.decompiler.flash.gui.jna.platform.win32;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Based on basetsd.h (various types)
 *
 * @author dblock[at]dblock[dot]org
 */
@SuppressWarnings("serial")
public interface BaseTSD extends StdCallLibrary {

    /**
     * Signed long type for pointer precision. Use when casting a pointer to a
     * long to perform pointer arithmetic.
     */
    public static class LONG_PTR extends IntegerType {

        public LONG_PTR() {
            this(0);
        }

        public LONG_PTR(long value) {
            super(Native.POINTER_SIZE, value);
        }

        public Pointer toPointer() {
            return Pointer.createConstant(longValue());
        }
    }

    /**
     * Signed SIZE_T.
     */
    public static class SSIZE_T extends LONG_PTR {

        public SSIZE_T() {
            this(0);
        }

        public SSIZE_T(long value) {
            super(value);
        }
    }

    /**
     * Unsigned LONG_PTR.
     */
    public static class ULONG_PTR extends IntegerType {

        public ULONG_PTR() {
            this(0);
        }

        public ULONG_PTR(long value) {
            super(Native.POINTER_SIZE, value, true);
        }

        public Pointer toPointer() {
            return Pointer.createConstant(longValue());
        }
    }

    /**
     * PULONG_PTR
     */
    public static final class ULONG_PTRByReference extends ByReference {

        public ULONG_PTRByReference() {
            this(new ULONG_PTR(0));
        }

        public ULONG_PTRByReference(ULONG_PTR value) {
            super(Native.POINTER_SIZE);
            setValue(value);
        }

        public void setValue(ULONG_PTR value) {
            if (Native.POINTER_SIZE == 4) {
                getPointer().setInt(0, value.intValue());
            } else {
                getPointer().setLong(0, value.longValue());
            }
        }

        public ULONG_PTR getValue() {
            return new ULONG_PTR(Native.POINTER_SIZE == 4
                    ? getPointer().getInt(0)
                    : getPointer().getLong(0));
        }
    }

    /**
     * Unsigned DWORD_PTR.
     */
    public static class DWORD_PTR extends IntegerType {

        public DWORD_PTR() {
            this(0);
        }

        public DWORD_PTR(long value) {
            super(Native.POINTER_SIZE, value);
        }
    }

    /**
     * The maximum number of bytes to which a pointer can point. Use for a count
     * that must span the full range of a pointer.
     */
    public static class SIZE_T extends ULONG_PTR {

        public SIZE_T() {
            this(0);
        }

        public SIZE_T(long value) {
            super(value);
        }
    }
}
