/* The following code was generated by JFlex 1.6.0 */

/*
 *  Copyright (C) 2010-2016 JPEXS, All rights reserved.
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
package com.jpexs.decompiler.flash.tags.text;



/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.0
 * from the specification file <tt>C:/Dropbox/Programovani/JavaSE/FFDec/libsrc/ffdec_lib/lexers/text.flex</tt>
 */
public final class TextLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int PARAMETER = 2;
  public static final int VALUE = 4;
  public static final int OIDENTIFIER = 6;
  public static final int STRING = 8;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4, 4
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\12\0\1\11\1\26\1\26\1\15\22\0\1\4\1\0\1\10\4\0"+
    "\1\24\10\0\1\13\11\2\7\0\6\5\24\0\1\16\1\7\1\3"+
    "\1\0\1\1\1\0\1\6\1\17\3\6\1\22\2\1\1\14\4\1"+
    "\1\21\3\1\1\23\1\1\1\20\1\27\2\1\1\25\2\1\1\30"+
    "\1\0\1\31\7\0\1\26\41\0\1\12\u1f80\0\1\26\1\26\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\udfe6\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\5\0\2\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\2\1\14\1\15\1\14"+
    "\1\2\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\27\1\30\1\31\1\17\1\32\1\33"+
    "\1\34\1\35\1\36\1\37\1\40\3\17\1\41\1\42"+
    "\1\43\3\41\7\0\1\44\1\45\1\0\1\46";

  private static int [] zzUnpackAction() {
    int [] result = new int[62];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\32\0\64\0\116\0\150\0\202\0\234\0\202"+
    "\0\202\0\266\0\202\0\320\0\352\0\u0104\0\202\0\202"+
    "\0\202\0\u011e\0\202\0\202\0\u0138\0\u0152\0\202\0\202"+
    "\0\202\0\202\0\202\0\202\0\202\0\202\0\202\0\202"+
    "\0\202\0\202\0\u016c\0\202\0\202\0\202\0\202\0\202"+
    "\0\202\0\202\0\u0186\0\u01a0\0\u01ba\0\202\0\202\0\202"+
    "\0\u0186\0\u01a0\0\u01d4\0\u01ee\0\u0208\0\u0222\0\u023c\0\u0256"+
    "\0\u0270\0\u028a\0\202\0\202\0\u0186\0\202";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[62];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
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
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\7\6\1\7\1\6\1\10\3\6\1\10\1\11\7\6"+
    "\1\10\3\6\1\10\1\12\1\10\1\13\1\14\1\10"+
    "\1\12\2\10\1\14\2\10\1\12\1\14\1\10\5\12"+
    "\1\10\1\12\1\10\1\12\2\10\1\15\1\16\1\15"+
    "\1\13\1\14\1\15\1\16\1\15\1\17\1\14\1\20"+
    "\1\15\1\16\1\14\1\15\5\16\1\15\1\16\1\15"+
    "\1\16\2\15\7\21\1\22\1\21\1\23\1\24\2\21"+
    "\1\25\23\21\1\26\1\27\1\10\3\21\1\10\14\21"+
    "\32\0\3\30\1\31\3\30\1\32\1\33\1\0\3\30"+
    "\1\0\1\34\1\35\1\36\1\37\1\40\1\41\1\42"+
    "\1\43\1\0\3\30\1\0\2\12\3\0\1\12\4\0"+
    "\2\12\2\0\5\12\1\0\1\12\1\0\1\12\6\0"+
    "\1\14\4\0\1\14\3\0\1\14\14\0\3\15\2\0"+
    "\3\15\3\0\2\15\1\0\15\15\2\16\2\0\1\15"+
    "\1\16\1\15\3\0\2\16\1\0\1\15\5\16\1\15"+
    "\1\16\1\15\1\16\2\15\7\30\1\44\1\30\1\0"+
    "\1\45\2\30\1\0\1\30\1\46\1\47\1\50\1\51"+
    "\1\52\1\30\1\53\1\0\1\54\1\55\1\30\11\0"+
    "\1\23\20\0\7\56\1\44\1\57\1\0\1\45\2\56"+
    "\1\0\1\56\1\46\1\47\1\50\1\51\1\52\1\60"+
    "\1\61\1\0\1\62\1\63\1\56\2\0\1\64\2\0"+
    "\2\64\4\0\1\64\3\0\1\64\2\0\1\64\11\0"+
    "\1\65\2\0\2\65\4\0\1\65\3\0\1\65\2\0"+
    "\1\65\11\0\1\66\2\0\2\66\4\0\1\66\3\0"+
    "\1\66\2\0\1\66\11\0\1\67\10\0\1\70\20\0"+
    "\1\71\10\0\1\72\20\0\1\73\2\0\2\73\4\0"+
    "\1\73\3\0\1\73\2\0\1\73\11\0\1\74\2\0"+
    "\2\74\4\0\1\74\3\0\1\74\2\0\1\74\11\0"+
    "\1\75\2\0\2\75\4\0\1\75\3\0\1\75\2\0"+
    "\1\75\11\0\1\67\10\0\1\67\1\72\12\0\1\72"+
    "\1\0\1\76\14\0\1\72\12\0\1\72\1\0\1\76"+
    "\2\0\1\71\10\0\1\71\15\0\1\76\31\0\1\76";

  private static int [] zzUnpackTrans() {
    int [] result = new int[676];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
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
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\5\0\1\11\1\1\2\11\1\1\1\11\3\1\3\11"+
    "\1\1\2\11\2\1\14\11\1\1\7\11\3\1\3\11"+
    "\3\1\7\0\2\11\1\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[62];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
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

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */

    private boolean finish = false;
    private boolean parameter = false;

    private StringBuilder string = null;

    private int repeatNum = 1;

    /**
     * Create an empty lexer, yyrset will be called later to reset and assign
     * the reader
     */
    public TextLexer() {

    }

    public int yychar() {
        return yychar;
    }

    public int yyline() {
        return yyline + 1;
    }



  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public TextLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 132) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
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
      if (totalRead == requested) { /* possibly more input available */
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
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
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
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public ParsedSymbol yylex() throws java.io.IOException, TextParseException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { if (string == null) string = new StringBuilder(); string.append(yytext());
          }
        case 39: break;
        case 2: 
          { if (!parameter) { if (string == null) string = new StringBuilder(); string.append(yytext()); }
          }
        case 40: break;
        case 3: 
          { parameter = true;
                                    yybegin(PARAMETER);
                                    if (string != null){
                                        String ret = string.toString();
                                        string = null;
                                        return new ParsedSymbol(SymbolType.TEXT, ret);
                                    }
          }
        case 41: break;
        case 4: 
          { yybegin(VALUE);
                                    return new ParsedSymbol(SymbolType.PARAMETER_IDENTIFIER, yytext());
          }
        case 42: break;
        case 5: 
          { yybegin(YYINITIAL);
                                    parameter = false;
          }
        case 43: break;
        case 6: 
          { 
          }
        case 44: break;
        case 7: 
          { return new ParsedSymbol(SymbolType.PARAMETER_VALUE, yytext());
          }
        case 45: break;
        case 8: 
          { return new ParsedSymbol(SymbolType.PARAMETER_IDENTIFIER, yytext());
          }
        case 46: break;
        case 9: 
          { string = new StringBuilder();
                                    yybegin(STRING);
          }
        case 47: break;
        case 10: 
          { string = new StringBuilder();
                                    yybegin(OIDENTIFIER);
          }
        case 48: break;
        case 11: 
          { for(int r=0;r<repeatNum;r++) string.append(yytext()); repeatNum = 1;
          }
        case 49: break;
        case 12: 
          { yybegin(VALUE);  yyline++;
          }
        case 50: break;
        case 13: 
          { yybegin(VALUE);
                                     repeatNum = 1;
                                     // length also includes the trailing quote
                                     String ret = string.toString();
                                     string = null;
                                     return new ParsedSymbol(SymbolType.PARAMETER_VALUE, ret);
          }
        case 51: break;
        case 14: 
          { yybegin(VALUE);
                                     repeatNum = 1;
                                     // length also includes the trailing quote
                                     String tos = string.toString();
                                     string = null;
                                     return new ParsedSymbol(SymbolType.PARAMETER_VALUE, tos);
          }
        case 52: break;
        case 15: 
          { throw new TextParseException("Illegal escape sequence \"" + yytext() + "\"", yyline + 1);
          }
        case 53: break;
        case 16: 
          { if (string == null) string = new StringBuilder(); string.append(']');
          }
        case 54: break;
        case 17: 
          { if (string == null) string = new StringBuilder(); string.append('\\');
          }
        case 55: break;
        case 18: 
          { if (string == null) string = new StringBuilder(); string.append('\"');
          }
        case 56: break;
        case 19: 
          { if (string == null) string = new StringBuilder(); string.append('[');
          }
        case 57: break;
        case 20: 
          { if (string == null) string = new StringBuilder(); string.append('\b');
          }
        case 58: break;
        case 21: 
          { if (string == null) string = new StringBuilder(); string.append('\t');
          }
        case 59: break;
        case 22: 
          { if (string == null) string = new StringBuilder(); string.append('\n');
          }
        case 60: break;
        case 23: 
          { if (string == null) string = new StringBuilder(); string.append('\f');
          }
        case 61: break;
        case 24: 
          { if (string == null) string = new StringBuilder(); string.append('\r');
          }
        case 62: break;
        case 25: 
          { if (string == null) string = new StringBuilder(); string.append('\'');
          }
        case 63: break;
        case 26: 
          { for(int r=0;r<repeatNum;r++) string.append('\\'); repeatNum = 1;
          }
        case 64: break;
        case 27: 
          { for(int r=0;r<repeatNum;r++) string.append('\u00A7'); repeatNum = 1;
          }
        case 65: break;
        case 28: 
          { for(int r=0;r<repeatNum;r++) string.append('\b'); repeatNum = 1;
          }
        case 66: break;
        case 29: 
          { for(int r=0;r<repeatNum;r++) string.append('\t'); repeatNum = 1;
          }
        case 67: break;
        case 30: 
          { for(int r=0;r<repeatNum;r++) string.append('\n'); repeatNum = 1;
          }
        case 68: break;
        case 31: 
          { for(int r=0;r<repeatNum;r++) string.append('\f'); repeatNum = 1;
          }
        case 69: break;
        case 32: 
          { for(int r=0;r<repeatNum;r++) string.append('\r'); repeatNum = 1;
          }
        case 70: break;
        case 33: 
          { repeatNum = 1; /* ignore illegal character escape */
          }
        case 71: break;
        case 34: 
          { for(int r=0;r<repeatNum;r++) string.append('\"'); repeatNum = 1;
          }
        case 72: break;
        case 35: 
          { for(int r=0;r<repeatNum;r++) string.append('\''); repeatNum = 1;
          }
        case 73: break;
        case 36: 
          { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   string.append(val);
          }
        case 74: break;
        case 37: 
          { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   for(int r=0;r<repeatNum;r++) string.append(val); repeatNum = 1;
          }
        case 75: break;
        case 38: 
          { repeatNum = Integer.parseInt(yytext().substring(2, yytext().length()-1));
          }
        case 76: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case YYINITIAL: {
              if (finish) {return null;} else {finish=true; return new ParsedSymbol(SymbolType.TEXT, string == null ? null : string.toString());}
            }
            case 63: break;
            default:
              {
                return null;
              }
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
