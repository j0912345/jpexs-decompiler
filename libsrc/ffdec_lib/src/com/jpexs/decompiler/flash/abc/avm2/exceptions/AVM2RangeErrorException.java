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
package com.jpexs.decompiler.flash.abc.avm2.exceptions;

/**
 * Range error exception.
 *
 * @author JPEXS
 */
public class AVM2RangeErrorException extends AVM2ExecutionException {

    /**
     * Constructs new AVM2RangeErrorException with the specified error code.
     *
     * @param code Error code
     * @param debug If true, the error message will contain a description of the
     * error
     */
    public AVM2RangeErrorException(int code, boolean debug) {
        super(codeToMessage(code, debug, null));
    }

    /**
     * Constructs new AVM2RangeErrorException with the specified error code and
     * parameters.
     *
     * @param code Error code
     * @param debug If true, the error message will contain a description of the
     * error
     * @param params Parameters for the error message
     */
    public AVM2RangeErrorException(int code, boolean debug, Object[] params) {
        super(codeToMessage(code, debug, params));
    }

    /**
     * Converts error code to error message.
     *
     * @param code Error code
     * @param debug If true, the error message will contain a description of the
     * error
     * @param params Parameters for the error message
     * @return Error message
     */
    private static String codeToMessage(int code, boolean debug, Object[] params) {
        String msg = null;
        /*switch (code) {
        }*/

        String result = "RangeError: Error #" + code;
        if (debug && msg != null) {
            result += ": " + msg;
        }

        return result;
    }
}
