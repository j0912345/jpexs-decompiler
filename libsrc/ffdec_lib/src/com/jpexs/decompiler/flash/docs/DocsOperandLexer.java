/*
 *  Copyright (C) 2010-2024 JPEXS, All rights reserved.
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
package com.jpexs.decompiler.flash.docs;

import java.util.Stack;

/**
 * This class is a scanner generated by
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.0 from the specification file
 * <tt>C:/Dropbox/Programovani/JavaSE/FFDec/libsrc/ffdec_lib/lexers/docs_operands.flex</tt>
 */
public final class DocsOperandLexer {

    /**
     * This character denotes the end of file
     */
    public static final int YYEOF = -1;

    /**
     * initial size of the lookahead buffer
     */
    private static final int ZZ_BUFFERSIZE = 16384;

    /**
     * lexical states
     */
    public static final int YYINITIAL = 0;

    /**
     * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
     * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l at the
     * beginning of a line l is of the form l = 2*k, k a non negative integer
     */
    private static final int ZZ_LEXSTATE[] = {
        0, 0
    };

    /**
     * Translates characters to character classes
     */
    private static final String ZZ_CMAP_PACKED
            = "\11\5\1\3\1\2\1\0\1\3\1\1\16\5\4\0\1\3\3\0"
            + "\1\4\5\0\1\14\1\0\1\10\1\0\1\6\1\0\12\5\1\7"
            + "\6\0\32\4\1\11\1\0\1\12\1\0\1\4\1\0\32\4\1\0"
            + "\1\13\2\0\41\5\2\0\4\4\4\0\1\4\2\0\1\5\7\0"
            + "\1\4\4\0\1\4\5\0\27\4\1\0\37\4\1\0\u01ca\4\4\0"
            + "\14\4\16\0\5\4\7\0\1\4\1\0\1\4\21\0\160\5\5\4"
            + "\1\0\2\4\2\0\4\4\1\0\1\4\6\0\1\4\1\0\3\4"
            + "\1\0\1\4\1\0\24\4\1\0\123\4\1\0\213\4\1\0\5\5"
            + "\2\0\246\4\1\0\46\4\2\0\1\4\6\0\51\4\6\0\1\4"
            + "\1\0\55\5\1\0\1\5\1\0\2\5\1\0\2\5\1\0\1\5"
            + "\10\0\33\4\4\0\4\4\15\0\6\5\5\0\1\4\4\0\13\5"
            + "\1\0\1\5\3\0\53\4\37\5\4\0\2\4\1\5\143\4\1\0"
            + "\1\4\10\5\1\0\6\5\2\4\2\5\1\0\4\5\2\4\12\5"
            + "\3\4\2\0\1\4\17\0\1\5\1\4\1\5\36\4\33\5\2\0"
            + "\131\4\13\5\1\4\16\0\12\5\41\4\11\5\2\4\4\0\1\4"
            + "\2\0\1\5\30\4\4\5\1\4\11\5\1\4\3\5\1\4\5\5"
            + "\22\0\31\4\3\5\4\0\13\4\5\0\30\4\1\0\6\4\1\0"
            + "\2\5\6\0\10\5\52\4\72\5\66\4\3\5\1\4\22\5\1\4"
            + "\7\5\12\4\2\5\2\0\12\5\1\0\20\4\3\5\1\0\10\4"
            + "\2\0\2\4\2\0\26\4\1\0\7\4\1\0\1\4\3\0\4\4"
            + "\2\0\1\5\1\4\7\5\2\0\2\5\2\0\3\5\1\4\10\0"
            + "\1\5\4\0\2\4\1\0\3\4\2\5\2\0\12\5\4\4\7\0"
            + "\2\4\1\0\1\5\2\0\3\5\1\0\6\4\4\0\2\4\2\0"
            + "\26\4\1\0\7\4\1\0\2\4\1\0\2\4\1\0\2\4\2\0"
            + "\1\5\1\0\5\5\4\0\2\5\2\0\3\5\3\0\1\5\7\0"
            + "\4\4\1\0\1\4\7\0\14\5\3\4\1\5\13\0\3\5\1\0"
            + "\11\4\1\0\3\4\1\0\26\4\1\0\7\4\1\0\2\4\1\0"
            + "\5\4\2\0\1\5\1\4\10\5\1\0\3\5\1\0\3\5\2\0"
            + "\1\4\17\0\2\4\2\5\2\0\12\5\1\0\1\4\7\0\1\4"
            + "\6\5\1\0\3\5\1\0\10\4\2\0\2\4\2\0\26\4\1\0"
            + "\7\4\1\0\2\4\1\0\5\4\2\0\1\5\1\4\7\5\2\0"
            + "\2\5\2\0\3\5\7\0\3\5\4\0\2\4\1\0\3\4\2\5"
            + "\2\0\12\5\1\0\1\4\20\0\1\5\1\4\1\0\6\4\3\0"
            + "\3\4\1\0\4\4\3\0\2\4\1\0\1\4\1\0\2\4\3\0"
            + "\2\4\3\0\3\4\3\0\14\4\4\0\5\5\3\0\3\5\1\0"
            + "\4\5\2\0\1\4\6\0\1\5\16\0\12\5\11\0\1\4\6\0"
            + "\5\5\10\4\1\0\3\4\1\0\27\4\1\0\20\4\2\0\1\5"
            + "\1\4\7\5\1\0\3\5\1\0\4\5\7\0\2\5\1\0\3\4"
            + "\2\0\1\4\2\0\2\4\2\5\2\0\12\5\20\0\1\4\3\5"
            + "\1\0\10\4\1\0\3\4\1\0\27\4\1\0\12\4\1\0\5\4"
            + "\2\0\1\5\1\4\7\5\1\0\3\5\1\0\4\5\7\0\2\5"
            + "\6\0\2\4\1\0\2\4\2\5\2\0\12\5\1\0\2\4\15\0"
            + "\4\5\11\4\1\0\3\4\1\0\51\4\2\5\1\4\7\5\1\0"
            + "\3\5\1\0\4\5\1\4\5\0\3\4\1\5\7\0\3\4\2\5"
            + "\2\0\12\5\12\0\6\4\1\0\3\5\1\0\22\4\3\0\30\4"
            + "\1\0\11\4\1\0\1\4\2\0\7\4\3\0\1\5\4\0\6\5"
            + "\1\0\1\5\1\0\10\5\6\0\12\5\2\0\2\5\15\0\60\4"
            + "\1\5\2\4\7\5\4\0\10\4\10\5\1\0\12\5\47\0\2\4"
            + "\1\0\1\4\1\0\5\4\1\0\30\4\1\0\1\4\1\0\12\4"
            + "\1\5\2\4\11\5\1\4\2\0\5\4\1\0\1\4\1\0\6\5"
            + "\2\0\12\5\2\0\4\4\40\0\1\4\27\0\2\5\6\0\12\5"
            + "\13\0\1\5\1\0\1\5\1\0\1\5\4\0\2\5\10\4\1\0"
            + "\44\4\4\0\24\5\1\0\2\5\5\4\13\5\1\0\44\5\11\0"
            + "\1\5\71\0\53\4\24\5\1\4\12\5\6\0\6\4\4\5\4\4"
            + "\3\5\1\4\3\5\2\4\7\5\3\4\4\5\15\4\14\5\1\4"
            + "\17\5\2\0\46\4\1\0\1\4\5\0\1\4\2\0\53\4\1\0"
            + "\u014d\4\1\0\4\4\2\0\7\4\1\0\1\4\1\0\4\4\2\0"
            + "\51\4\1\0\4\4\2\0\41\4\1\0\4\4\2\0\7\4\1\0"
            + "\1\4\1\0\4\4\2\0\17\4\1\0\71\4\1\0\4\4\2\0"
            + "\103\4\2\0\3\5\40\0\20\4\20\0\126\4\2\0\6\4\3\0"
            + "\u026c\4\2\0\21\4\1\0\32\4\5\0\113\4\3\0\13\4\7\0"
            + "\22\4\4\5\11\0\23\4\3\5\13\0\22\4\2\5\14\0\15\4"
            + "\1\0\3\4\1\0\2\5\14\0\64\4\40\5\3\0\1\4\3\0"
            + "\2\4\1\5\2\0\12\5\41\0\17\5\6\0\131\4\7\0\5\4"
            + "\2\5\42\4\1\5\1\4\5\0\106\4\12\0\37\4\1\0\14\5"
            + "\4\0\14\5\12\0\12\5\36\4\2\0\5\4\13\0\54\4\4\0"
            + "\32\4\6\0\12\5\46\0\27\4\5\5\4\0\65\4\12\5\1\0"
            + "\35\5\2\0\13\5\6\0\12\5\15\0\1\4\10\0\16\5\1\0"
            + "\20\5\61\0\5\5\57\4\21\5\10\4\3\0\12\5\21\0\11\5"
            + "\14\0\3\5\36\4\15\5\2\4\12\5\54\4\16\5\14\0\44\4"
            + "\24\5\10\0\12\5\3\0\3\4\12\5\44\4\2\0\11\4\7\0"
            + "\53\4\2\0\3\4\20\0\3\5\1\0\25\5\4\4\1\5\6\4"
            + "\1\5\2\4\3\5\1\4\5\0\300\4\100\5\u0116\4\2\0\6\4"
            + "\2\0\46\4\2\0\6\4\2\0\10\4\1\0\1\4\1\0\1\4"
            + "\1\0\1\4\1\0\37\4\2\0\65\4\1\0\7\4\1\0\1\4"
            + "\3\0\3\4\1\0\7\4\3\0\4\4\2\0\6\4\4\0\15\4"
            + "\5\0\3\4\1\0\7\4\16\0\5\5\32\0\5\5\20\0\2\4"
            + "\23\0\1\4\13\0\5\5\1\0\12\5\1\0\1\4\15\0\1\4"
            + "\20\0\15\4\3\0\41\4\17\0\15\5\4\0\1\5\3\0\14\5"
            + "\21\0\1\4\4\0\1\4\2\0\12\4\1\0\1\4\3\0\5\4"
            + "\6\0\1\4\1\0\1\4\1\0\1\4\1\0\4\4\1\0\13\4"
            + "\2\0\4\4\5\0\5\4\4\0\1\4\21\0\51\4\u0a77\0\345\4"
            + "\6\0\4\4\3\5\2\4\14\0\46\4\1\0\1\4\5\0\1\4"
            + "\2\0\70\4\7\0\1\4\17\0\1\5\27\4\11\0\7\4\1\0"
            + "\7\4\1\0\7\4\1\0\7\4\1\0\7\4\1\0\7\4\1\0"
            + "\7\4\1\0\7\4\1\0\40\5\57\0\1\4\u01d5\0\3\4\31\0"
            + "\11\4\6\5\1\0\5\4\2\0\5\4\4\0\126\4\2\0\2\5"
            + "\2\0\3\4\1\0\132\4\1\0\4\4\5\0\53\4\1\0\136\4"
            + "\21\0\40\4\60\0\20\4\u0200\0\u19c0\4\100\0\u568d\4\103\0\56\4"
            + "\2\0\u010d\4\3\0\20\4\12\5\2\4\24\0\57\4\1\5\4\0"
            + "\12\5\1\0\37\4\2\5\120\4\2\5\45\0\11\4\2\0\147\4"
            + "\2\0\100\4\5\0\2\4\1\0\1\4\1\0\5\4\30\0\20\4"
            + "\1\5\3\4\1\5\4\4\1\5\27\4\5\5\4\0\1\5\13\0"
            + "\1\4\7\0\64\4\14\0\2\5\62\4\22\5\12\0\12\5\6\0"
            + "\22\5\6\4\3\0\1\4\1\0\2\4\13\5\34\4\10\5\2\0"
            + "\27\4\15\5\14\0\35\4\3\0\4\5\57\4\16\5\16\0\1\4"
            + "\12\5\6\0\5\4\1\5\12\4\12\5\5\4\1\0\51\4\16\5"
            + "\11\0\3\4\1\5\10\4\2\5\2\0\12\5\6\0\27\4\3\0"
            + "\1\4\3\5\62\4\1\5\1\4\3\5\2\4\2\5\5\4\2\5"
            + "\1\4\1\5\1\4\30\0\3\4\2\0\13\4\5\5\2\0\3\4"
            + "\2\5\12\0\6\4\2\0\6\4\2\0\6\4\11\0\7\4\1\0"
            + "\7\4\1\0\53\4\1\0\16\4\6\0\163\4\10\5\1\0\2\5"
            + "\2\0\12\5\6\0\u2ba4\4\14\0\27\4\4\0\61\4\u2104\0\u016e\4"
            + "\2\0\152\4\46\0\7\4\14\0\5\4\5\0\1\4\1\5\12\4"
            + "\1\0\15\4\1\0\5\4\1\0\1\4\1\0\2\4\1\0\2\4"
            + "\1\0\154\4\41\0\u016b\4\22\0\100\4\2\0\66\4\50\0\15\4"
            + "\3\0\20\5\20\0\20\5\3\0\2\4\30\0\3\4\31\0\1\4"
            + "\6\0\5\4\1\0\207\4\2\0\1\5\4\0\1\4\13\0\12\5"
            + "\7\0\32\4\4\0\1\4\1\0\32\4\13\0\131\4\3\0\6\4"
            + "\2\0\6\4\2\0\6\4\2\0\3\4\3\0\2\4\3\0\2\4"
            + "\22\0\3\5\4\0\14\4\1\0\32\4\1\0\23\4\1\0\2\4"
            + "\1\0\17\4\2\0\16\4\42\0\173\4\105\0\65\4\210\0\1\5"
            + "\202\0\35\4\3\0\61\4\17\0\1\5\37\0\40\4\15\0\36\4"
            + "\5\0\46\4\5\5\5\0\36\4\2\0\44\4\4\0\10\4\1\0"
            + "\5\4\52\0\236\4\2\0\12\5\6\0\44\4\4\0\44\4\4\0"
            + "\50\4\10\0\64\4\14\0\13\4\1\0\17\4\1\0\7\4\1\0"
            + "\2\4\1\0\13\4\1\0\17\4\1\0\7\4\1\0\2\4\103\0"
            + "\u0137\4\11\0\26\4\12\0\10\4\30\0\6\4\1\0\52\4\1\0"
            + "\11\4\105\0\6\4\2\0\1\4\1\0\54\4\1\0\2\4\3\0"
            + "\1\4\2\0\27\4\12\0\27\4\11\0\37\4\101\0\23\4\1\0"
            + "\2\4\12\0\26\4\12\0\32\4\106\0\70\4\6\0\2\4\100\0"
            + "\1\4\3\5\1\0\2\5\5\0\4\5\4\4\1\0\3\4\1\0"
            + "\35\4\2\0\3\5\4\0\1\5\40\0\35\4\3\0\35\4\43\0"
            + "\10\4\1\0\34\4\2\5\31\0\66\4\12\0\26\4\12\0\23\4"
            + "\15\0\22\4\156\0\111\4\67\0\63\4\15\0\63\4\15\0\44\4"
            + "\4\5\10\0\12\5\u0146\0\52\4\1\0\2\5\3\0\2\4\116\0"
            + "\35\4\12\0\1\4\10\0\26\4\13\5\37\0\22\4\4\5\52\0"
            + "\25\4\33\0\27\4\11\0\3\5\65\4\17\5\37\0\13\5\2\4"
            + "\2\5\1\4\11\0\4\5\55\4\13\5\2\0\1\5\4\0\1\5"
            + "\12\0\1\5\2\0\31\4\7\0\12\5\6\0\3\5\44\4\16\5"
            + "\1\0\12\5\4\0\1\4\2\5\1\4\10\0\43\4\1\5\2\0"
            + "\1\4\11\0\3\5\60\4\16\5\4\4\4\0\4\5\1\0\14\5"
            + "\1\4\1\0\1\4\43\0\22\4\1\0\31\4\14\5\6\0\1\5"
            + "\101\0\7\4\1\0\1\4\1\0\4\4\1\0\17\4\1\0\12\4"
            + "\7\0\57\4\14\5\5\0\12\5\6\0\4\5\1\0\10\4\2\0"
            + "\2\4\2\0\26\4\1\0\7\4\1\0\2\4\1\0\5\4\1\0"
            + "\2\5\1\4\7\5\2\0\2\5\2\0\3\5\2\0\1\4\6\0"
            + "\1\5\5\0\5\4\2\5\2\0\7\5\3\0\5\5\213\0\65\4"
            + "\22\5\4\4\5\0\12\5\4\0\1\5\3\4\36\0\60\4\24\5"
            + "\2\4\1\0\1\4\10\0\12\5\246\0\57\4\7\5\2\0\11\5"
            + "\27\0\4\4\2\5\42\0\60\4\21\5\3\0\1\4\13\0\12\5"
            + "\46\0\53\4\15\5\1\4\7\0\12\5\66\0\33\4\2\0\17\5"
            + "\4\0\12\5\6\0\7\4\271\0\54\4\17\5\145\0\100\4\12\5"
            + "\25\0\10\4\2\0\1\4\2\0\10\4\1\0\2\4\1\0\30\4"
            + "\6\5\1\0\2\5\2\0\4\5\1\4\1\5\1\4\2\5\14\0"
            + "\12\5\106\0\10\4\2\0\47\4\7\5\2\0\7\5\1\4\1\0"
            + "\1\4\1\5\33\0\1\4\12\5\50\4\7\5\1\4\4\5\10\0"
            + "\1\5\10\0\1\4\13\5\56\4\20\5\3\0\1\4\22\0\111\4"
            + "\u0107\0\11\4\1\0\45\4\10\5\1\0\10\5\1\4\17\0\12\5"
            + "\30\0\36\4\2\0\26\5\1\0\16\5\111\0\7\4\1\0\2\4"
            + "\1\0\46\4\6\5\3\0\1\5\1\0\2\5\1\0\7\5\1\4"
            + "\1\5\10\0\12\5\6\0\6\4\1\0\2\4\1\0\40\4\5\5"
            + "\1\0\2\5\1\0\5\5\1\4\7\0\12\5\u0136\0\23\4\4\5"
            + "\271\0\1\4\54\0\4\4\37\0\u039a\4\146\0\157\4\21\0\304\4"
            + "\u0a4c\0\141\4\17\0\u042f\4\1\0\11\5\u0fc7\0\u0247\4\u21b9\0\u0239\4"
            + "\7\0\37\4\1\0\12\5\6\0\117\4\1\0\12\5\6\0\36\4"
            + "\2\0\5\5\13\0\60\4\7\5\11\0\4\4\14\0\12\5\11\0"
            + "\25\4\5\0\23\4\u02b0\0\100\4\200\0\113\4\4\0\1\5\1\4"
            + "\67\5\7\0\4\5\15\4\100\0\2\4\1\0\1\4\1\5\13\0"
            + "\2\5\16\0\u17f8\4\10\0\u04d6\4\52\0\11\4\u22e7\0\4\4\1\0"
            + "\7\4\1\0\2\4\1\0\u0123\4\55\0\3\4\21\0\4\4\10\0"
            + "\u018c\4\u0904\0\153\4\5\0\15\4\3\0\11\4\7\0\12\4\3\0"
            + "\2\5\1\0\4\5\u125c\0\56\5\2\0\27\5\u021e\0\5\5\3\0"
            + "\26\5\2\0\7\5\36\0\4\5\224\0\3\5\u01bb\0\125\4\1\0"
            + "\107\4\1\0\2\4\2\0\1\4\2\0\2\4\2\0\4\4\1\0"
            + "\14\4\1\0\1\4\1\0\7\4\1\0\101\4\1\0\4\4\2\0"
            + "\10\4\1\0\7\4\1\0\34\4\1\0\4\4\1\0\5\4\1\0"
            + "\1\4\3\0\7\4\1\0\u0154\4\2\0\31\4\1\0\31\4\1\0"
            + "\37\4\1\0\31\4\1\0\37\4\1\0\31\4\1\0\37\4\1\0"
            + "\31\4\1\0\37\4\1\0\31\4\1\0\10\4\2\0\62\5\u0200\0"
            + "\67\5\4\0\62\5\10\0\1\5\16\0\1\5\26\0\5\5\1\0"
            + "\17\5\u0450\0\37\4\341\0\7\5\1\0\21\5\2\0\7\5\1\0"
            + "\2\5\1\0\5\5\325\0\55\4\3\0\7\5\7\4\2\0\12\5"
            + "\4\0\1\4\u0141\0\36\4\1\5\21\0\54\4\16\5\5\0\1\4"
            + "\u04e0\0\7\4\1\0\4\4\1\0\2\4\1\0\17\4\1\0\305\4"
            + "\13\0\7\5\51\0\104\4\7\5\1\4\4\0\12\5\u0356\0\1\4"
            + "\u014f\0\4\4\1\0\33\4\1\0\2\4\1\0\1\4\2\0\1\4"
            + "\1\0\12\4\1\0\4\4\1\0\1\4\1\0\1\4\6\0\1\4"
            + "\4\0\1\4\1\0\1\4\1\0\1\4\1\0\3\4\1\0\2\4"
            + "\1\0\1\4\2\0\1\4\1\0\1\4\1\0\1\4\1\0\1\4"
            + "\1\0\1\4\1\0\2\4\1\0\1\4\2\0\4\4\1\0\7\4"
            + "\1\0\4\4\1\0\4\4\1\0\1\4\1\0\12\4\1\0\21\4"
            + "\5\0\3\4\1\0\5\4\1\0\21\4\u0d34\0\12\5\u0406\0\ua6e0\4"
            + "\40\0\u1039\4\7\0\336\4\2\0\u1682\4\16\0\u1d31\4\u0c1f\0\u021e\4"
            + "\u05e2\0\u134b\4\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uecc0\0"
            + "\1\5\36\0\140\5\200\0\360\5\uffff\0\uffff\0\ufe12\0";

    /**
     * Translates characters to character classes
     */
    private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

    /**
     * Translates DFA states to action switch labels.
     */
    private static final int[] ZZ_ACTION = zzUnpackAction();

    private static final String ZZ_ACTION_PACKED_0
            = "\1\0\1\1\3\2\1\3\1\1\1\4\1\5\1\6"
            + "\1\7\1\10\1\11\1\0\1\12";

    private static int[] zzUnpackAction() {
        int[] result = new int[15];
        int offset = 0;
        offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackAction(String packed, int offset, int[] result) {
        int i = 0;
        /* index in packed string  */
        int j = offset;
        /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }

    /**
     * Translates a state to a row index in the transition table
     */
    private static final int[] ZZ_ROWMAP = zzUnpackRowMap();

    private static final String ZZ_ROWMAP_PACKED_0
            = "\0\0\0\15\0\32\0\15\0\47\0\64\0\101\0\15"
            + "\0\15\0\15\0\15\0\15\0\15\0\116\0\15";

    private static int[] zzUnpackRowMap() {
        int[] result = new int[15];
        int offset = 0;
        offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackRowMap(String packed, int offset, int[] result) {
        int i = 0;
        /* index in packed string  */
        int j = offset;
        /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int high = packed.charAt(i++) << 16;
            result[j++] = high | packed.charAt(i++);
        }
        return j;
    }

    /**
     * The transition table of the DFA
     */
    private static final int[] ZZ_TRANS = zzUnpackTrans();

    private static final String ZZ_TRANS_PACKED_0
            = "\1\2\1\3\1\4\1\5\1\6\1\2\1\7\1\10"
            + "\1\11\1\12\1\13\1\14\1\15\17\0\1\4\15\0"
            + "\1\5\15\0\2\6\15\0\1\16\14\0\1\17\6\0";

    private static int[] zzUnpackTrans() {
        int[] result = new int[91];
        int offset = 0;
        offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackTrans(String packed, int offset, int[] result) {
        int i = 0;
        /* index in packed string  */
        int j = offset;
        /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            value--;
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }


    /* error codes */
    private static final int ZZ_UNKNOWN_ERROR = 0;
    private static final int ZZ_NO_MATCH = 1;
    private static final int ZZ_PUSHBACK_2BIG = 2;

    /* error messages for the codes above */
    private static final String ZZ_ERROR_MSG[] = {
        "Unkown internal scanner error",
        "Error: could not match input",
        "Error: pushback value was too large"
    };

    /**
     * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
     */
    private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();

    private static final String ZZ_ATTRIBUTE_PACKED_0
            = "\1\0\1\11\1\1\1\11\3\1\6\11\1\0\1\11";

    private static int[] zzUnpackAttribute() {
        int[] result = new int[15];
        int offset = 0;
        offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackAttribute(String packed, int offset, int[] result) {
        int i = 0;
        /* index in packed string  */
        int j = offset;
        /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }

    /**
     * the input device
     */
    private java.io.Reader zzReader;

    /**
     * the current state of the DFA
     */
    private int zzState;

    /**
     * the current lexical state
     */
    private int zzLexicalState = YYINITIAL;

    /**
     * this buffer contains the current text to be matched and is the source of
     * the yytext() string
     */
    private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

    /**
     * the textposition at the last accepting state
     */
    private int zzMarkedPos;

    /**
     * the current text position in the buffer
     */
    private int zzCurrentPos;

    /**
     * startRead marks the beginning of the yytext() string in the buffer
     */
    private int zzStartRead;

    /**
     * endRead marks the last character in the buffer, that has been read from
     * input
     */
    private int zzEndRead;

    /**
     * number of newlines encountered up to the start of the matched text
     */
    private int yyline;

    /**
     * the number of characters up to the start of the matched text
     */
    private int yychar;

    /**
     * the number of characters from the last newline up to the start of the
     * matched text
     */
    private int yycolumn;

    /**
     * zzAtBOL == true <=> the scanner is currently at the beginning of a line
     */
    private boolean zzAtBOL = true;

    /**
     * zzAtEOF == true <=> the scanner is at the EOF
     */
    private boolean zzAtEOF;

    /**
     * denotes if the user-EOF-code has already been executed
     */
    private boolean zzEOFDone;

    /**
     * The number of occupied positions in zzBuffer beyond zzEndRead. When a
     * lead/high surrogate has been read from the input stream into the final
     * zzBuffer position, this will have a value of 1; otherwise, it will have a
     * value of 0.
     */
    private int zzFinalHighSurrogate = 0;

    /* user code: */
    StringBuilder string = new StringBuilder();

    public int yychar() {
        return yychar;
    }

    private final Stack<ParsedSymbol> pushedBack = new Stack<>();

    public int yyline() {
        return yyline + 1;
    }

    public void pushback(ParsedSymbol symb) {
        pushedBack.push(symb);
        last = null;
    }

    private int count(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }

    ParsedSymbol last;

    public ParsedSymbol lex() throws java.io.IOException {
        ParsedSymbol ret = null;
        if (!pushedBack.isEmpty()) {
            ret = last = pushedBack.pop();
        } else {
            ret = last = yylex();
        }
        return ret;
    }

    /**
     * Creates a new scanner
     *
     * @param in the java.io.Reader to read input from.
     */
    public DocsOperandLexer(java.io.Reader in) {
        this.zzReader = in;
    }

    /**
     * Unpacks the compressed character translation table.
     *
     * @param packed the packed character translation table
     * @return the unpacked character translation table
     */
    private static char[] zzUnpackCMap(String packed) {
        char[] map = new char[0x110000];
        int i = 0;
        /* index in packed string  */
        int j = 0;
        /* index in unpacked array */
        while (i < 3722) {
            int count = packed.charAt(i++);
            char value = packed.charAt(i++);
            do {
                map[j++] = value;
            } while (--count > 0);
        }
        return map;
    }

    /**
     * Refills the input buffer.
     *
     * @return <code>false</code>, iff there was new input.
     * @throws java.io.IOException if any I/O-Error occurs
     */
    private boolean zzRefill() throws java.io.IOException {

        /* first: make room (if you can) */
        if (zzStartRead > 0) {
            zzEndRead += zzFinalHighSurrogate;
            zzFinalHighSurrogate = 0;
            System.arraycopy(zzBuffer, zzStartRead,
                    zzBuffer, 0,
                    zzEndRead - zzStartRead);

            /* translate stored positions */
            zzEndRead -= zzStartRead;
            zzCurrentPos -= zzStartRead;
            zzMarkedPos -= zzStartRead;
            zzStartRead = 0;
        }

        /* is the buffer big enough? */
        if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
            /* if not: blow it up */
            char newBuffer[] = new char[zzBuffer.length * 2];
            System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
            zzBuffer = newBuffer;
            zzEndRead += zzFinalHighSurrogate;
            zzFinalHighSurrogate = 0;
        }

        /* fill the buffer with new input */
        int requested = zzBuffer.length - zzEndRead;
        int totalRead = 0;
        while (totalRead < requested) {
            int numRead = zzReader.read(zzBuffer, zzEndRead + totalRead, requested - totalRead);
            if (numRead == -1) {
                break;
            }
            totalRead += numRead;
        }

        if (totalRead > 0) {
            zzEndRead += totalRead;
            if (totalRead == requested) {
                /* possibly more input available */
                if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
                    --zzEndRead;
                    zzFinalHighSurrogate = 1;
                }
            }
            return false;
        }

        // totalRead = 0: End of stream
        return true;
    }

    /**
     * Closes the input stream.
     */
    public final void yyclose() throws java.io.IOException {
        zzAtEOF = true;
        /* indicate end of file */
        zzEndRead = zzStartRead;
        /* invalidate buffer    */

        if (zzReader != null) {
            zzReader.close();
        }
    }

    /**
     * Resets the scanner to read from a new input stream. Does not close the
     * old reader.
     * <p>
     * All internal variables are reset, the old input stream
     * <b>cannot</b> be reused (internal buffer is discarded and lost). Lexical
     * state is set to <tt>ZZ_INITIAL</tt>.
     * <p>
     * Internal scan buffer is resized down to its initial length, if it has
     * grown.
     *
     * @param reader the new input stream
     */
    public final void yyreset(java.io.Reader reader) {
        zzReader = reader;
        zzAtBOL = true;
        zzAtEOF = false;
        zzEOFDone = false;
        zzEndRead = zzStartRead = 0;
        zzCurrentPos = zzMarkedPos = 0;
        zzFinalHighSurrogate = 0;
        yyline = yychar = yycolumn = 0;
        zzLexicalState = YYINITIAL;
        if (zzBuffer.length > ZZ_BUFFERSIZE) {
            zzBuffer = new char[ZZ_BUFFERSIZE];
        }
    }

    /**
     * Returns the current lexical state.
     */
    public final int yystate() {
        return zzLexicalState;
    }

    /**
     * Enters a new lexical state
     *
     * @param newState the new lexical state
     */
    public final void yybegin(int newState) {
        zzLexicalState = newState;
    }

    /**
     * Returns the text matched by the current regular expression.
     */
    public final String yytext() {
        return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
    }

    /**
     * Returns the character at position <tt>pos</tt> from the matched text.
     * <p>
     * It is equivalent to yytext().charAt(pos), but faster
     *
     * @param pos the position of the character to fetch. A value from 0 to
     * yylength()-1.
     * @return the character at position pos
     */
    public final char yycharat(int pos) {
        return zzBuffer[zzStartRead + pos];
    }

    /**
     * Returns the length of the matched text region.
     */
    public final int yylength() {
        return zzMarkedPos - zzStartRead;
    }

    /**
     * Reports an error that occured while scanning.
     * <p>
     * In a wellformed scanner (no or only correct usage of yypushback(int) and
     * a match-all fallback rule) this method will only be called with things
     * that "Can't Possibly Happen". If this method is called, something is
     * seriously wrong (e.g. a JFlex bug producing a faulty scanner etc.).
     * <p>
     * Usual syntax/scanner level error handling should be done in error
     * fallback rules.
     *
     * @param errorCode the code of the errormessage to display
     */
    private void zzScanError(int errorCode) {
        String message;
        try {
            message = ZZ_ERROR_MSG[errorCode];
        } catch (ArrayIndexOutOfBoundsException e) {
            message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
        }

        throw new Error(message);
    }

    /**
     * Pushes the specified amount of characters back into the input stream.
     * <p>
     * They will be read again by then next call of the scanning method
     *
     * @param number the number of characters to be read again. This number must
     * not be greater than yylength()!
     */
    public void yypushback(int number) {
        if (number > yylength()) {
            zzScanError(ZZ_PUSHBACK_2BIG);
        }

        zzMarkedPos -= number;
    }

    /**
     * Resumes scanning until the next regular expression is matched, the end of
     * input is encountered or an I/O-Error occurs.
     *
     * @return the next token
     * @throws java.io.IOException if any I/O-Error occurs
     */
    public ParsedSymbol yylex() throws java.io.IOException {
        int zzInput;
        int zzAction;

        // cached fields:
        int zzCurrentPosL;
        int zzMarkedPosL;
        int zzEndReadL = zzEndRead;
        char[] zzBufferL = zzBuffer;
        char[] zzCMapL = ZZ_CMAP;

        int[] zzTransL = ZZ_TRANS;
        int[] zzRowMapL = ZZ_ROWMAP;
        int[] zzAttrL = ZZ_ATTRIBUTE;

        while (true) {
            zzMarkedPosL = zzMarkedPos;

            yychar += zzMarkedPosL - zzStartRead;

            zzAction = -1;

            zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

            zzState = ZZ_LEXSTATE[zzLexicalState];

            // set up zzAction for empty match case:
            int zzAttributes = zzAttrL[zzState];
            if ((zzAttributes & 1) == 1) {
                zzAction = zzState;
            }

            zzForAction:
            {
                while (true) {

                    if (zzCurrentPosL < zzEndReadL) {
                        zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
                        zzCurrentPosL += Character.charCount(zzInput);
                    } else if (zzAtEOF) {
                        zzInput = YYEOF;
                        break zzForAction;
                    } else {
                        // store back cached positions
                        zzCurrentPos = zzCurrentPosL;
                        zzMarkedPos = zzMarkedPosL;
                        boolean eof = zzRefill();
                        // get translated positions and possibly new buffer
                        zzCurrentPosL = zzCurrentPos;
                        zzMarkedPosL = zzMarkedPos;
                        zzBufferL = zzBuffer;
                        zzEndReadL = zzEndRead;
                        if (eof) {
                            zzInput = YYEOF;
                            break zzForAction;
                        } else {
                            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
                            zzCurrentPosL += Character.charCount(zzInput);
                        }
                    }
                    int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
                    if (zzNext == -1) {
                        break zzForAction;
                    }
                    zzState = zzNext;

                    zzAttributes = zzAttrL[zzState];
                    if ((zzAttributes & 1) == 1) {
                        zzAction = zzState;
                        zzMarkedPosL = zzCurrentPosL;
                        if ((zzAttributes & 8) == 8) {
                            break zzForAction;
                        }
                    }

                }
            }

            // store back cached position
            zzMarkedPos = zzMarkedPosL;

            switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
                case 1: {
                }
                case 11:
                    break;
                case 2: {
                    /*ignore*/
                }
                case 12:
                    break;
                case 3: {
                    return new ParsedSymbol(ParsedSymbol.TYPE_IDENTIFIER, yytext());
                }
                case 13:
                    break;
                case 4: {
                    return new ParsedSymbol(ParsedSymbol.TYPE_COLON, yytext());
                }
                case 14:
                    break;
                case 5: {
                    return new ParsedSymbol(ParsedSymbol.TYPE_COMMA, yytext());
                }
                case 15:
                    break;
                case 6: {
                    return new ParsedSymbol(ParsedSymbol.TYPE_BRACKET_OPEN, yytext());
                }
                case 16:
                    break;
                case 7: {
                    return new ParsedSymbol(ParsedSymbol.TYPE_BRACKET_CLOSE, yytext());
                }
                case 17:
                    break;
                case 8: {
                    return new ParsedSymbol(ParsedSymbol.TYPE_PIPE, yytext());
                }
                case 18:
                    break;
                case 9: {
                    return new ParsedSymbol(ParsedSymbol.TYPE_STAR, yytext());
                }
                case 19:
                    break;
                case 10: {
                    return new ParsedSymbol(ParsedSymbol.TYPE_DOTS, yytext());
                }
                case 20:
                    break;
                default:
                    if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
                        zzAtEOF = true;
                        {
                            return new ParsedSymbol(ParsedSymbol.TYPE_EOF, null);
                        }
                    } else {
                        zzScanError(ZZ_NO_MATCH);
                    }
            }
        }
    }

}
