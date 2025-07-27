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
package com.jpexs.decompiler.flash.action.parser.script;
import com.jpexs.decompiler.flash.action.parser.ActionParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

%%

%public
%class ActionScriptLexer
%final
%unicode
%char
%type ParsedSymbol
%throws ActionParseException

%{

    StringBuilder string = new StringBuilder();

    private static String xmlTagName = "";

    private int repeatNum = 1;

    private int itemStart = -1;

    private boolean caseSensitiveIdentifiers = false;

    public static final int SWF_VERSION_CASE_SENSITIVE = 7;

    public void setCaseSensitiveIdentifiers(boolean val) {
        this.caseSensitiveIdentifiers = val;
    }

    public int yychar() {
        return yychar;
    }

    private final Stack<ParsedSymbol> pushedBack = new Stack<>();

    public int yyline() {
        return yyline + 1;
    }

    private final List<LexListener> listeners = new ArrayList<>();

    public void addListener(LexListener listener){
        listeners.add(listener);
    }

    public void removeListener(LexListener listener){
        listeners.remove(listener);
    }

    public void informListenersLex(ParsedSymbol s){
        for(LexListener l:listeners){
            l.onLex(s);
        }
    }

    public void informListenersPushBack(ParsedSymbol s){
        for(LexListener l:listeners){
            l.onPushBack(s);
        }
    }

    public void pushback(ParsedSymbol symb) {
        pushedBack.push(symb);
        last = null;
        informListenersPushBack(symb);
    }

    private int count(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }

    ParsedSymbol last;
    public ParsedSymbol lex() throws java.io.IOException, ActionParseException{
        ParsedSymbol ret = null;
        if (!pushedBack.isEmpty()){
            ret = last = pushedBack.pop();
        } else {
            ret = last = yylex();
        }
        informListenersLex(ret);
        return ret;
    }

%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]+

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {AsDocComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/" | "/*" [*]* ~"*/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
AsDocComment = "/**" ~"*/"


/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*

IdentifierOrParent = {Identifier} | ".."

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*

HexIntegerLiteral = 0 [xX] 0* {HexDigit}+
HexDigit          = [0-9a-fA-F]

OctIntegerLiteral = 0+ [1-3]? {OctDigit}+
OctDigit          = [0-7]

/* floating point literals */
DoubleLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1    = [0-9]+ \. [0-9]*
FLit2    = \. [0-9]+
FLit3    = [0-9]+
Exponent = [eE] [+-]? [0-9]+

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]
OIdentifierCharacter = [^\r\n\u00A7\\]

Preprocessor = \u00A7\u00A7 {Identifier}

%state STRING, CHARLITERAL, XMLSTARTTAG, XML, OIDENTIFIER

%%

<YYINITIAL> {

  /* keywords */
  "break"                        { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.BREAK, yytext()); }
  "case"                         { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.CASE, yytext()); }
  "continue"                     { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.CONTINUE, yytext()); }
  "default"                      { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.DEFAULT, yytext()); }
  "do"                           { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.DO, yytext()); }
  "while"                        { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.WHILE, yytext()); }
  "else"                         { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.ELSE, yytext()); }
  "for"                          { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.FOR, yytext()); }
  "each"                         { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.EACH, yytext()); }
  "in"                           { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.IN, yytext()); }
  "if"                           { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.IF, yytext()); }
  "return"                       { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.RETURN, yytext()); }
  "super"                        { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.SUPER, yytext()); }
  "switch"                       { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.SWITCH, yytext()); }
  "throw"                        { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.THROW, yytext()); }
  "try"                          { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.TRY, yytext()); }
  "catch"                        { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.CATCH, yytext()); }
  "finally"                      { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.FINALLY, yytext()); }
  "while"                        { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.WHILE, yytext()); }
  "with"                         { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.WITH, yytext()); }
  "dynamic"                      { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.DYNAMIC, yytext()); }
  "private"                      { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.PRIVATE, yytext()); }
  "public"                       { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.PUBLIC, yytext()); }
  "static"                       { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.STATIC, yytext()); }
  "class"                        { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.CLASS, yytext()); }
  "extends"                      { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.EXTENDS, yytext()); }
  "function"                     { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.FUNCTION, yytext()); }
  "get"                          { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.GET, yytext()); }
  "implements"                   { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.IMPLEMENTS, yytext()); }
  "interface"                    { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.INTERFACE, yytext()); }
  "set"                          { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.SET, yytext()); }
  "var"                          { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.VAR, yytext()); }
  "import"                       { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.IMPORT, yytext()); }
  "false"                        { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.FALSE, yytext()); }
  "null"                         { return new ParsedSymbol(yychar(), SymbolGroup.GLOBALCONST, SymbolType.NULL, yytext()); }
  "this"                         { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.THIS, yytext()); }
  "true"                         { return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.TRUE, yytext()); }
  /* getURL */ 
  [gG][eE][tT][uU][rR][lL]                                                        { if (caseSensitiveIdentifiers && !"getURL".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.GETURL, yytext()); }
  /* trace */ 
  [tT][rR][aA][cC][eE]                                                            { if (caseSensitiveIdentifiers && !"trace".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.TRACE, yytext()); }
  /* gotoAndStop */ 
  [gG][oO][tT][oO][aA][nN][dD][sS][tT][oO][pP]                                    { if (caseSensitiveIdentifiers && !"gotoAndStop".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.GOTOANDSTOP, yytext()); }
  /* nextFrame */ 
  [nN][eE][xX][tT][fF][rR][aA][mM][eE]                                            { if (caseSensitiveIdentifiers && !"nextFrame".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.NEXTFRAME, yytext()); }
  /* play */ 
  [pP][lL][aA][yY]                                                                { if (caseSensitiveIdentifiers && !"play".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.PLAY, yytext()); }
  /* prevFrame */ 
  [pP][rR][eE][vV][fF][rR][aA][mM][eE]                                            { if (caseSensitiveIdentifiers && !"prevFrame".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.PREVFRAME, yytext()); }
  /* tellTarget */ 
  [tT][eE][lL][lL][tT][aA][rR][gG][eE][tT]                                        { if (caseSensitiveIdentifiers && !"tellTarget".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.TELLTARGET, yytext()); }
  /* stop */ 
  [sS][tT][oO][pP]                                                                { if (caseSensitiveIdentifiers && !"stop".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.STOP, yytext()); }
  /* stopAllSounds */ 
  [sS][tT][oO][pP][aA][lL][lL][sS][oO][uU][nN][dD][sS]                            { if (caseSensitiveIdentifiers && !"stopAllSounds".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.STOPALLSOUNDS, yytext()); }
  /* toggleHighQuality */ 
  [tT][oO][gG][gG][lL][eE][hH][iI][gG][hH][qQ][uU][aA][lL][iI][tT][yY]            { if (caseSensitiveIdentifiers && !"toggleHighQuality".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.TOGGLEHIGHQUALITY, yytext()); }
  /* ifFrameLoaded */ 
  [iI][fF][fF][rR][aA][mM][eE][lL][oO][aA][dD][eE][dD]                            { if (caseSensitiveIdentifiers && !"ifFrameLoaded".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.KEYWORD, SymbolType.IFFRAMELOADED, yytext()); }
  /* ord */ 
  [oO][rR][dD]                                                                    { if (caseSensitiveIdentifiers && !"ord".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.ORD, yytext()); }
  /* chr */ 
  [cC][hH][rR]                                                                    { if (caseSensitiveIdentifiers && !"chr".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.CHR, yytext()); }
  /* duplicateMovieClip */ 
  [dD][uU][pP][lL][iI][cC][aA][tT][eE][mM][oO][vV][iI][eE][cC][lL][iI][pP]        { if (caseSensitiveIdentifiers && !"duplicateMovieClip".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.DUPLICATEMOVIECLIP, yytext()); }
  /* stopDrag */ 
  [sS][tT][oO][pP][dD][rR][aA][gG]                                                { if (caseSensitiveIdentifiers && !"stopDrag".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.STOPDRAG, yytext()); }
  /* getTimer */ 
  [gG][eE][tT][tT][iI][mM][eE][rR]                                                { if (caseSensitiveIdentifiers && !"getTimer".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.GETTIMER, yytext()); }
  /* loadVariables */ 
  [lL][oO][aA][dD][vV][aA][rR][iI][aA][bB][lL][eE][sS]                            { if (caseSensitiveIdentifiers && !"loadVariables".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.LOADVARIABLES, yytext()); }
  /* loadMovie */ 
  [lL][oO][aA][dD][mM][oO][vV][iI][eE]                                            { if (caseSensitiveIdentifiers && !"loadMovie".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.LOADMOVIE, yytext()); }
  /* gotoAndPlay */ 
  [gG][oO][tT][oO][aA][nN][dD][pP][lL][aA][yY]                                    { if (caseSensitiveIdentifiers && !"gotoAndPlay".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.GOTOANDPLAY, yytext()); }
  /* mbord */ 
  [mM][bB][oO][rR][dD]                                                            { if (caseSensitiveIdentifiers && !"mbord".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.MBORD, yytext()); }
  /* mbchr */ 
  [mM][bB][cC][hH][rR]                                                            { if (caseSensitiveIdentifiers && !"mbchr".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.MBCHR, yytext()); }
  /* mblength */ 
  [mM][bB][lL][eE][nN][gG][tT][hH]                                                { if (caseSensitiveIdentifiers && !"mblength".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.MBLENGTH, yytext()); }
  /* mbsubstring */ 
  [mM][bB][sS][uU][bB][sS][tT][rR][iI][nN][gG]                                    { if (caseSensitiveIdentifiers && !"mbsubstring".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.MBSUBSTRING, yytext()); }
  /* random */ 
  [rR][aA][nN][dD][oO][mM]                                                        { if (caseSensitiveIdentifiers && !"random".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.RANDOM, yytext()); }
  /* removeMovieClip */ 
  [rR][eE][mM][oO][vV][eE][mM][oO][vV][iI][eE][cC][lL][iI][pP]                    { if (caseSensitiveIdentifiers && !"removeMovieClip".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.REMOVEMOVIECLIP, yytext()); }
  /* startDrag */ 
  [sS][tT][aA][rR][tT][dD][rR][aA][gG]                                            { if (caseSensitiveIdentifiers && !"startDrag".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.STARTDRAG, yytext()); }
  /* substr */ 
  [sS][uU][bB][sS][tT][rR]                                                        { if (caseSensitiveIdentifiers && !"substr".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.SUBSTR, yytext()); }
  /* length */ 
  [lL][eE][nN][gG][tT][hH]                                                        { if (caseSensitiveIdentifiers && !"length".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.LENGTH, yytext()); }
  /* int */ 
  [iI][nN][tT]                                                                    { if (caseSensitiveIdentifiers && !"int".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.INT, yytext()); }
  /* targetPath */ 
  [tT][aA][rR][gG][eE][tT][pP][aA][tT][hH]                                        { if (caseSensitiveIdentifiers && !"targetPath".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.TARGETPATH, yytext()); }
  /* Number */ 
  [nN][uU][mM][bB][eE][rR]                                                        { if (caseSensitiveIdentifiers && !"Number".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.NUMBER_OP, yytext()); }
  /* String */ 
  [sS][tT][rR][iI][nN][gG]                                                        { if (caseSensitiveIdentifiers && !"String".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.STRING_OP, yytext()); }
  /* eval */ 
  [eE][vV][aA][lL]                                                                { if (caseSensitiveIdentifiers && !"eval".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.EVAL, yytext()); }
  /* undefined */ 
  [uU][nN][dD][eE][fF][iI][nN][eE][dD]                                            { if (caseSensitiveIdentifiers && !"undefined".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALCONST, SymbolType.UNDEFINED, yytext()); }
  /* newline */ 
  [nN][eE][wW][lL][iI][nN][eE]                                                    { if (caseSensitiveIdentifiers && !"newline".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALCONST, SymbolType.NEWLINE, yytext()); }
  /* getVersion */ 
  [gG][eE][tT][vV][eE][rR][sS][iI][oO][nN]                                        { if (caseSensitiveIdentifiers && !"getVersion".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.GETVERSION, yytext()); }
  /* call */ 
  [cC][aA][lL][lL]                                                                { if (caseSensitiveIdentifiers && !"call".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.CALL, yytext()); }
  /* loadMovieNum */ 
  [lL][oO][aA][dD][mM][oO][vV][iI][eE][nN][uU][mM]                                { if (caseSensitiveIdentifiers && !"loadMovieNum".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.LOADMOVIENUM, yytext()); }
  /* loadVariablesNum */ 
  [lL][oO][aA][dD][vV][aA][rR][iI][aA][bB][lL][eE][sS][nN][uU][mM]                { if (caseSensitiveIdentifiers && !"loadVariablesNum".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.LOADVARIABLESNUM, yytext()); }
  /* printAsBitmapNum */ 
  [pP][rR][iI][nN][tT][aA][sS][bB][iI][tT][mM][aA][pP][nN][uU][mM]                { if (caseSensitiveIdentifiers && !"printAsBitmapNum".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.PRINTASBITMAPNUM, yytext()); }
  /* printNum */ 
  [pP][rR][iI][nN][tT][nN][uU][mM]                                                { if (caseSensitiveIdentifiers && !"printNum".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.PRINTNUM, yytext()); }
  /* printAsBitmap */ 
  [pP][rR][iI][nN][tT][aA][sS][bB][iI][tT][mM][aA][pP]                            { if (caseSensitiveIdentifiers && !"printAsBitmap".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.PRINTASBITMAP, yytext()); }
  /* print */ 
  [pP][rR][iI][nN][tT]                                                            { if (caseSensitiveIdentifiers && !"print".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.PRINT, yytext()); }
  /* unloadMovie */ 
  [uU][nN][lL][oO][aA][dD][mM][oO][vV][iI][eE]                                    { if (caseSensitiveIdentifiers && !"unloadMovie".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.UNLOADMOVIE, yytext()); }
  /* unloadMovieNum */ 
  [uU][nN][lL][oO][aA][dD][mM][oO][vV][iI][eE][nN][uU][mM]                        { if (caseSensitiveIdentifiers && !"unloadMovieNum".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.UNLOADMOVIENUM, yytext()); }
  /* fscommand2 */ 
  [fF][sS][cC][oO][mM][mM][aA][nN][dD] 2                                          { if (caseSensitiveIdentifiers && !"fscommand2".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.FSCOMMAND2, yytext()); }
  /* fscommand */ 
  [fF][sS][cC][oO][mM][mM][aA][nN][dD]                                            { if (caseSensitiveIdentifiers && !"fscommand".equals(yytext())) return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); 
                                                                                    return new ParsedSymbol(yychar(), SymbolGroup.GLOBALFUNC, SymbolType.FSCOMMAND, yytext()); }
  

/* operators */

  "("                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.PARENT_OPEN, yytext());  }
  ")"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.PARENT_CLOSE, yytext());  }
  "{"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.CURLY_OPEN, yytext());  }
  "}"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.CURLY_CLOSE, yytext());  }
  "["                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.BRACKET_OPEN, yytext());  }
  "]"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.BRACKET_CLOSE, yytext());  }
  ";"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.SEMICOLON, yytext());  }
  ","                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.COMMA, yytext());  }
  "..."                          { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.REST, yytext());  }
  "."                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.DOT, yytext());  }
  "="                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN, yytext());  }
  ">"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.GREATER_THAN, yytext());  }
  "<"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.LOWER_THAN, yytext());  }
  "!"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.NOT, yytext());  }
  "~"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.NEGATE, yytext());  }
  "?"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.TERNAR, yytext());  }
  ":"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.COLON, yytext());  }
  "==="                          { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.STRICT_EQUALS, yytext());  }
  "=="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.EQUALS, yytext());  }
  "<="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.LOWER_EQUAL, yytext());  }
  ">="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.GREATER_EQUAL, yytext());  }
  "!=="                          { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.STRICT_NOT_EQUAL, yytext());  }
  "!=" | "<>"                    { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.NOT_EQUAL, yytext());  }
  "&&"                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.AND, yytext());  }
  "||"                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.OR, yytext());  }
  "++"                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.INCREMENT, yytext());  }
  "--"                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.DECREMENT, yytext());  }
  "+"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.PLUS, yytext());  }
  "-"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.MINUS, yytext());  }
  "*"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.MULTIPLY, yytext());  }
  "/"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.DIVIDE, yytext());  }
  "&"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.BITAND, yytext());  }
  "|"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.BITOR, yytext());  }
  "^"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.XOR, yytext());  }
  "%"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.MODULO, yytext());  }
  "<<"                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.SHIFT_LEFT, yytext());  }
  ">>"                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.SHIFT_RIGHT, yytext());  }
  ">>>"                          { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.USHIFT_RIGHT, yytext());  }
  "+="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_PLUS, yytext());  }
  "-="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_MINUS, yytext());  }
  "*="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_MULTIPLY, yytext());  }
  "/="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_DIVIDE, yytext());  }
  "&="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_BITAND, yytext());  }
  "|="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_BITOR, yytext());  }
  "^="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_XOR, yytext());  }
  "%="                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_MODULO, yytext());  }
  "<<="                          { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_SHIFT_LEFT, yytext());  }
  ">>="                          { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_SHIFT_RIGHT, yytext());  }
  ">>>="                         { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ASSIGN_USHIFT_RIGHT, yytext());  }
  "delete"                       { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.DELETE, yytext());  }
  "instanceof"                   { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.INSTANCEOF, yytext());  }
  "new"                          { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.NEW, yytext());  }
  "typeof"                       { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.TYPEOF, yytext());  }
  "void"                         { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.VOID, yytext());  }
  "@"                            { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.ATTRIBUTE, yytext());  }
  "and"                          { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.FULLAND, yytext());  }
  "or"                           { return new ParsedSymbol(yychar(), SymbolGroup.OPERATOR, SymbolType.FULLOR, yytext());  }

  "#" {Identifier}               { return new ParsedSymbol(yychar(), SymbolGroup.DIRECTIVE, SymbolType.DIRECTIVE, yytext().substring(1)); }

  /* string literal */
  \"                             {
                                    itemStart = yychar();
                                    string.setLength(0);
                                    yybegin(STRING);
                                 }
  {Preprocessor}                 {
                                    return new ParsedSymbol(yychar(), SymbolGroup.PREPROCESSOR, SymbolType.PREPROCESSOR, yytext().substring(2));
                                 }

  "\u00A7"                       {
                                    itemStart = yychar();
                                    string.setLength(0);
                                    yybegin(OIDENTIFIER);
                                 }


  /* character literal */
  \'                             {
                                    itemStart = yychar();
                                    string.setLength(0);
                                    yybegin(CHARLITERAL);
                                 }

  /* numeric literals */

  {DecIntegerLiteral}            { 
                                    try{
                                        return new ParsedSymbol(yychar(), SymbolGroup.INTEGER, SymbolType.INTEGER, Long.parseLong(yytext())); 
                                    } catch(NumberFormatException nfe){
                                        //its too long for a Long var
                                        return new ParsedSymbol(yychar(), SymbolGroup.DOUBLE, SymbolType.DOUBLE, Double.parseDouble(yytext())); 
                                    }
                                }

  {HexIntegerLiteral}            { return new ParsedSymbol(yychar(), SymbolGroup.INTEGER, SymbolType.INTEGER, Long.parseLong(yytext().substring(2), 16)); }

  {OctIntegerLiteral}            { return new ParsedSymbol(yychar(), SymbolGroup.INTEGER, SymbolType.INTEGER, Long.parseLong(yytext(), 8)); }

  {DoubleLiteral}                { return new ParsedSymbol(yychar(), SymbolGroup.DOUBLE, SymbolType.DOUBLE, Double.parseDouble(yytext())); }

  /* comments */
  {Comment}                      { yyline += count(yytext(),"\n"); }

  {LineTerminator}               { yyline++;}
  /* whitespace */
  {WhiteSpace}                   { /*ignore*/ }
  /* identifiers */
  {Identifier}                   { return new ParsedSymbol(yychar(), SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, yytext()); }    
}


<OIDENTIFIER> {
    "\u00A7"                         {
                                     yybegin(YYINITIAL);
                                     repeatNum = 1;
                                     // length also includes the trailing quote
                                     return new ParsedSymbol(itemStart, SymbolGroup.IDENTIFIER, SymbolType.IDENTIFIER, string.toString());
                                 }

  {OIdentifierCharacter}         { for(int r=0;r<repeatNum;r++) string.append(yytext()); repeatNum = 1;}

  /* escape sequences */
  "\\b"                          { for(int r=0;r<repeatNum;r++) string.append('\b'); repeatNum = 1;}
  "\\t"                          { for(int r=0;r<repeatNum;r++) string.append('\t'); repeatNum = 1;}
  "\\n"                          { for(int r=0;r<repeatNum;r++) string.append('\n'); repeatNum = 1;}
  "\\f"                          { for(int r=0;r<repeatNum;r++) string.append('\f'); repeatNum = 1;}
  "\\\u00A7"                     { for(int r=0;r<repeatNum;r++) string.append('\u00A7'); repeatNum = 1;}
  "\\r"                          { for(int r=0;r<repeatNum;r++) string.append('\r'); repeatNum = 1;}
  "\\\\"                         { for(int r=0;r<repeatNum;r++) string.append('\\'); repeatNum = 1;}
  \\x{HexDigit}{2}        { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   for(int r=0;r<repeatNum;r++) string.append(val); repeatNum = 1;}
  \\u{HexDigit}{4}        { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   for(int r=0;r<repeatNum;r++) string.append(val); repeatNum = 1;}
  \\\{{DecIntegerLiteral}\}      { repeatNum = Integer.parseInt(yytext().substring(2, yytext().length()-1)); }

  /* escape sequences */

  \\.                            { throw new ActionParseException("Illegal escape sequence \"" + yytext() + "\"", yyline + 1, yychar());  }
  {LineTerminator}               { yybegin(YYINITIAL);  yyline++;}
}

<STRING> {
  \"                             {
                                     yybegin(YYINITIAL);
                                     // length also includes the trailing quote
                                     return new ParsedSymbol(itemStart, SymbolGroup.STRING, SymbolType.STRING, string.toString());
                                 }

  {StringCharacter}+             { string.append(yytext()); }

  /* escape sequences */
  "\\b"                          { string.append('\b'); }
  "\\t"                          { string.append('\t'); }
  "\\n"                          { string.append('\n'); }
  "\\f"                          { string.append('\f'); }
  "\\r"                          { string.append('\r'); }
  "\\\""                         { string.append('\"'); }
  "\\'"                          { string.append('\''); }
  "\\\\"                         { string.append('\\'); }
  \\x{HexDigit}{2}        { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   string.append(val); }
  \\u{HexDigit}{4}        { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   string.append(val); }
  \\{OctDigit}{3}         { char val = (char) Integer.parseInt(yytext().substring(1), 8);
                        				   string.append(val); }

  /* escape sequences */

  \\.                            { string.append('\\'); /*illegal escape sequence*/  }
  {LineTerminator}               { yybegin(YYINITIAL);  yyline++;}
}

<CHARLITERAL> {
  \'                             {
                                     yybegin(YYINITIAL);
                                     // length also includes the trailing quote
                                     return new ParsedSymbol(itemStart, SymbolGroup.STRING, SymbolType.STRING, string.toString());
                                 }

  {SingleCharacter}+             { string.append(yytext()); }

  /* escape sequences */
/* escape sequences */
  "\\b"                          { string.append('\b'); }
  "\\t"                          { string.append('\t'); }
  "\\n"                          { string.append('\n'); }
  "\\f"                          { string.append('\f'); }
  "\\r"                          { string.append('\r'); }
  "\\\""                         { string.append('\"'); }
  "\\'"                          { string.append('\''); }
  "\\\\"                         { string.append('\\'); }
  \\x{HexDigit}{2}        { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   string.append(val); }
  \\u{HexDigit}{4}        { char val = (char) Integer.parseInt(yytext().substring(2), 16);
                        				   string.append(val); }

  /* escape sequences */

  \\.                            { string.append('\\'); /*illegal escape sequence*/  }
  {LineTerminator}               { yybegin(YYINITIAL);  yyline++;}
}

/* error fallback */
[^]                              {  }
<<EOF>>                          { return new ParsedSymbol(yychar(), SymbolGroup.EOF, SymbolType.EOF, null); }
