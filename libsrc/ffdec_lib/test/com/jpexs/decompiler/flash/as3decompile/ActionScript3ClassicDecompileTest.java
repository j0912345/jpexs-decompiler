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
package com.jpexs.decompiler.flash.as3decompile;

import com.jpexs.decompiler.flash.ActionScript3DecompileTestBase;
import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author JPEXS
 */
public class ActionScript3ClassicDecompileTest extends ActionScript3DecompileTestBase {

    @BeforeClass
    public void init() throws IOException, InterruptedException {
        addSwf("classic", "testdata/as3_new/bin/as3_new.flex.swf");
    }

    @Test
    public void testActivationArguments() {
        decompileMethod("classic", "testActivationArguments", "var func:Function = function(a:int, b:int):int\r\n"
                + "{\r\n"
                + "return a + b;\r\n"
                + "};\r\n"
                + "if(arguments.length > 0)\r\n"
                + "{\r\n"
                + "trace(arguments[0]);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testAndOrCoercion() {
        decompileMethod("classic", "testAndOrCoercion", "var x:TestInterface = this.ti || (this.ti = new TestClass()) && (this.ti = new TestClass());\r\n"
                + "var y:TestInterface = this.ti && (this.ti = new TestClass());\r\n"
                + "var z:TestClass = this.tc || (this.tc = new TestClass());\r\n"
                + "this.ti = this.ti && (this.ti = new TestClass());\r\n"
                + "var a:* = this.ti && (this.ti = new TestClass());\r\n"
                + "var b:int = 1 + (this.i || this.j);\r\n"
                + "this.test(this.ti && (this.ti = new TestClass()));\r\n"
                + "return this.ti && (this.ti = new TestClass());\r\n",
                 false);
    }

    @Test
    public void testArguments() {
        decompileMethod("classic", "testArguments", "return arguments[0];\r\n",
                 false);
    }

    @Test
    public void testBitwiseOperands() {
        decompileMethod("classic", "testBitwiseOperands", "var a:int = 100;\r\n"
                + "var b:int = a & 0x08FF;\r\n"
                + "var c:int = 0x08FF & a;\r\n"
                + "var d:int = a | 0x0480;\r\n"
                + "var e:int = 0x0480 | a;\r\n"
                + "var f:int = a ^ 0x0641;\r\n"
                + "var g:int = 0x0641 ^ a;\r\n"
                + "var h:int = ~0x0180;\r\n",
                 false);
    }

    @Test
    public void testCallCall() {
        decompileMethod("classic", "testCallCall", "var o:* = new getDefinitionByName(\"Object\")();\r\n"
                + "var o2:* = new (getDefinitionByName(\"Object\"))();\r\n",
                 false);
    }

    @Test
    public void testCallLocal() {
        decompileMethod("classic", "testCallLocal", "var f:Function = this.getF();\r\n"
                + "var b:int = f(1,3);\r\n",
                 false);
    }

    @Test
    public void testCatchFinally() {
        decompileMethod("classic", "testCatchFinally", "var a:* = 5;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "a = 9;\r\n"
                + "trace(\"intry\");\r\n"
                + "}\r\n"
                + "catch(e:*)\r\n"
                + "{\r\n"
                + "trace(\"incatch\");\r\n"
                + "}\r\n"
                + "finally\r\n"
                + "{\r\n"
                + "trace(\"infinally\");\r\n"
                + "}\r\n"
                + "trace(\"after\");\r\n",
                 false);
    }

    @Test
    public void testChain2() {
        decompileMethod("classic", "testChain2", "var g:Array = null;\r\n"
                + "var h:Boolean = false;\r\n"
                + "var extraLine:Boolean = false;\r\n"
                + "var r:int = 7;\r\n"
                + "var t:int = 0;\r\n"
                + "t = this.getInt();\r\n"
                + "if(t + 1 < g.length)\r\n"
                + "{\r\n"
                + "t++;\r\n"
                + "h = true;\r\n"
                + "}\r\n"
                + "if(t >= 0)\r\n"
                + "{\r\n"
                + "trace(\"ch\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testChainedAssignments() {
        decompileMethod("classic", "testChainedAssignments", "var a:int = 0;\r\n"
                + "var b:int = 0;\r\n"
                + "var c:int = 0;\r\n"
                + "var d:int = 0;\r\n"
                + "var f:int = 0;\r\n"
                + "d = c = b = a = 5;\r\n"
                + "var e:TestClass2 = TestClass2.createMe(\"test\");\r\n"
                + "e.attrib1 = e.attrib2 = e.attrib3 = this.getCounter();\r\n"
                + "this.traceIt(e.toString());\r\n"
                + "this.prop = f = a = 4;\r\n"
                + "if(f == 2)\r\n"
                + "{\r\n"
                + "trace(\"OK: \" + f);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testCollidingTry() {
        decompileMethod("classic", "testCollidingTry", "var e:int = 0;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "e = 0;\r\n"
                + "}\r\n"
                + "catch(e:*)\r\n"
                + "{\r\n"
                + "trace(e);\r\n"
                + "}\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"x\");\r\n"
                + "}\r\n"
                + "catch(e:*)\r\n"
                + "{\r\n"
                + "trace(e);\r\n"
                + "}\r\n"
                + "trace(\"y\");\r\n",
                 false);
    }

    @Test
    public void testComma() {
        decompileMethod("classic", "testComma", "var a:int = 5;\r\n"
                + "var b:int = 0;\r\n"
                + "trace(a > 4 ? (b = 5, a) : 35);\r\n",
                 false);
    }

    @Test
    public void testComplexExpressions() {
        decompileMethod("classic", "testComplexExpressions", "var i:int = 0;\r\n"
                + "var j:int = 0;\r\n"
                + "j = i = i + (i = i + i++);\r\n",
                 false);
    }

    @Test
    public void testCompoundAssignments() {
        decompileMethod("classic", "testCompoundAssignments", "var t:MyTest;\r\n"
                + "var b:* = [10,20,30];\r\n"
                + "var a:int = 0;\r\n"
                + "trace(\"a += 5\");\r\n"
                + "a += 5;\r\n"
                + "trace(\"arr[call()] = arr[call()] + 2;\");\r\n"
                + "b[this.calc()] = b[this.calc()] + 2;\r\n"
                + "t = new MyTest();\r\n"
                + "trace(\"t.attr *= 10\");\r\n"
                + "t.attr *= 10;\r\n"
                + "trace(\"attr -= 5\");\r\n"
                + "this.attr -= 5;\r\n"
                + "trace(\"arr[2] += 5\");\r\n"
                + "b[2] += 5;\r\n"
                + "trace(\"arr[call()] /= 5\");\r\n"
                + "b[this.calc()] = b[this.calc()] / 5;\r\n"
                + "trace(\"arr[call()][call()] &= 10;\");\r\n"
                + "b[this.calc()][this.calc()] = b[this.calc()][this.calc()] & 0x0A;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"in try\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(e.message);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testContinueLevels() {
        decompileMethod("classic", "testContinueLevels", "var b:* = undefined;\r\n"
                + "var c:* = undefined;\r\n"
                + "var d:* = undefined;\r\n"
                + "var e:* = undefined;\r\n"
                + "var a:* = 5;\r\n"
                + "switch(a)\r\n"
                + "{\r\n"
                + "case 57 * a:\r\n"
                + "trace(\"fiftyseven multiply a\");\r\n"
                + "b = 0;\r\n"
                + "while(b < 50)\r\n"
                + "{\r\n"
                + "if(b == 10)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "if(b == 15)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "b += 1;\r\n"
                + "}\r\n"
                + "break;\r\n"
                + "case 13:\r\n"
                + "trace(\"thirteen\");\r\n"
                + "case 14:\r\n"
                + "trace(\"fourteen\");\r\n"
                + "break;\r\n"
                + "case 89:\r\n"
                + "trace(\"eightynine\");\r\n"
                + "break;\r\n"
                + "default:\r\n"
                + "trace(\"default clause\");\r\n"
                + "}\r\n"
                + "loop1:\r\n"
                + "for(c = 0; c < 8; c += 1)\r\n"
                + "{\r\n"
                + "for(d = 0; d < 25; d++)\r\n"
                + "{\r\n"
                + "e = 0;\r\n"
                + "if(e < 50)\r\n"
                + "{\r\n"
                + "if(e == 9)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "if(e == 20)\r\n"
                + "{\r\n"
                + "continue loop1;\r\n"
                + "}\r\n"
                + "if(e != 8)\r\n"
                + "{\r\n"
                + "break loop1;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "}\r\n"
                + "trace(\"hello\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testConvert() {
        decompileMethod("classic", "testConvert", "var s:String = null;\r\n"
                + "var i:int = 0;\r\n"
                + "var a:* = undefined;\r\n"
                + "var dict:Dictionary = new Dictionary();\r\n"
                + "s = \"a\";\r\n"
                + "i = int(s);\r\n"
                + "var j:int = this.n;\r\n"
                + "s = String(j);\r\n"
                + "s = this.ns;\r\n"
                + "s = String(i == 4 ? \"\" : i);\r\n"
                + "s = i == 4 ? \"\" : String(i);\r\n"
                + "s = TestConvert.TEST;\r\n"
                + "i = this.TEST;\r\n"
                + "i = Number(\"4\") * 5;\r\n"
                + "i = a * 6;\r\n"
                + "i = a;\r\n"
                + "var o:Object = {\r\n"
                + "0:\"A\",\r\n"
                + "1:\"B\",\r\n"
                + "2:\"C\"\r\n"
                + "};\r\n"
                + "i = int(s.charAt(10));\r\n"
                + "var v:Vector.<String> = new Vector.<String>();\r\n"
                + "v.push(\"A\");\r\n"
                + "v.push(\"B\");\r\n"
                + "i = int(v[0]);\r\n"
                + "s = v[1];\r\n"
                + "s = v.join(\"x\");\r\n"
                + "i = int(v.join(\"x\"));\r\n"
                + "i = prot;\r\n"
                + "s = String(prot);\r\n"
                + "i = sprot;\r\n"
                + "s = String(sprot);\r\n"
                + "s = String(getTimer());\r\n"
                + "var x:XML = <list>\r\n"
                + "<item id=\"1\">1</item>\r\n"
                + "<item id=\"2\">2</item>\r\n"
                + "<item id=\"3\">3</item>\r\n"
                + "</list>;\r\n"
                + "s = x;\r\n"
                + "trace(\"a\");\r\n"
                + "var xlist:XMLList = x.item;\r\n"
                + "trace(\"b\");\r\n"
                + "i = int(xlist[i].@id);\r\n"
                + "trace(\"c\");\r\n"
                + "i = int(x.item[i].@id);\r\n"
                + "dict[String(x.item[i].@id)] = \"Hello\";\r\n"
                + "var lc:LocalClass = new LocalClass();\r\n"
                + "i = lc.attr;\r\n"
                + "s = String(lc.attr);\r\n"
                + "var f:Function = this.f;\r\n"
                + "if(Boolean(f))\r\n"
                + "{\r\n"
                + "trace(\"OK\");\r\n"
                + "}\r\n"
                + "if(Boolean(i))\r\n"
                + "{\r\n"
                + "trace(i);\r\n"
                + "}\r\n"
                + "if(Boolean(s))\r\n"
                + "{\r\n"
                + "trace(s);\r\n"
                + "}\r\n"
                + "if(Boolean(o))\r\n"
                + "{\r\n"
                + "trace(\"obj\");\r\n"
                + "}\r\n"
                + "s = xlist;\r\n"
                + "var d:Number = 0;\r\n"
                + "d = 1;\r\n"
                + "d = 1.5;\r\n"
                + "i = 1;\r\n"
                + "i = 1.5;\r\n"
                + "o[int(d * 5)] = 1;\r\n"
                + "this.n = 1.5;\r\n"
                + "super.prot = 1.5;\r\n"
                + "super.prot = int(s);\r\n"
                + "i = super.prot;\r\n"
                + "s = String(super.prot);\r\n",
                 false);
    }

    @Test
    public void testDecl2() {
        decompileMethod("classic", "testDecl2", "var k:int = 0;\r\n"
                + "var i:int = 5;\r\n"
                + "i += 7;\r\n"
                + "if(i == 5)\r\n"
                + "{\r\n"
                + "if(i < 8)\r\n"
                + "{\r\n"
                + "k = 6;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "k = 7;\r\n",
                 false);
    }

    @Test
    public void testDeclarationInterface() {
        decompileMethod("classic", "testDeclarationInterface", "var i:MyIFace = null;\r\n"
                + "var n:int = 2;\r\n"
                + "switch(n)\r\n"
                + "{\r\n"
                + "case 0:\r\n"
                + "i = new MyClass();\r\n"
                + "break;\r\n"
                + "case 1:\r\n"
                + "i = new MyClass2();\r\n"
                + "}\r\n"
                + "return i;\r\n",
                 false);
    }

    @Test
    public void testDeclarations() {
        decompileMethod("classic", "testDeclarations", "var vall:* = undefined;\r\n"
                + "var vstr:String = null;\r\n"
                + "var vint:int = 0;\r\n"
                + "var vuint:uint = 0;\r\n"
                + "var vclass:TestClass1 = null;\r\n"
                + "var vnumber:Number = NaN;\r\n"
                + "var vobject:Object = null;\r\n"
                + "vall = 6;\r\n"
                + "vstr = \"hello\";\r\n"
                + "vuint = 7;\r\n"
                + "vint = -4;\r\n"
                + "vclass = new TestClass1();\r\n"
                + "vnumber = 0.5;\r\n"
                + "vnumber = 6;\r\n"
                + "vobject = vclass;\r\n",
                 false);
    }

    @Test
    public void testDefaultNotLastGrouped() {
        decompileMethod("classic", "testDefaultNotLastGrouped", "var k:* = 10;\r\n"
                + "switch(k)\r\n"
                + "{\r\n"
                + "default:\r\n"
                + "case \"six\":\r\n"
                + "trace(\"def and 6\");\r\n"
                + "case \"five\":\r\n"
                + "trace(\"def and 6 and 5\");\r\n"
                + "break;\r\n"
                + "case \"four\":\r\n"
                + "trace(\"4\");\r\n"
                + "}\r\n"
                + "trace(\"after switch\");\r\n",
                 false);
    }

    @Test
    public void testDeobfuscation() {
        decompileMethod("classic", "testDeobfuscation", "var r:int = Math.random();\r\n"
                + "if(r > 5)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "if(r > 10)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "if(r > 15)\r\n"
                + "{\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "if(r > 20)\r\n"
                + "{\r\n"
                + "trace(\"D\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testDoWhile() {
        decompileMethod("classic", "testDoWhile", "var a:* = 8;\r\n"
                + "do\r\n"
                + "{\r\n"
                + "trace(\"a=\" + a);\r\n"
                + "a++;\r\n"
                + "}\r\n"
                + "while(a < 20);\r\n",
                 false);
    }

    @Test
    public void testDoWhile2() {
        decompileMethod("classic", "testDoWhile2", "var k:int = 5;\r\n"
                + "do\r\n"
                + "{\r\n"
                + "k++;\r\n"
                + "if(k == 7)\r\n"
                + "{\r\n"
                + "k = 5 * k;\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "k = 5 - k;\r\n"
                + "}\r\n"
                + "k--;\r\n"
                + "}\r\n"
                + "while(k < 9);\r\n"
                + "return 2;\r\n",
                 false);
    }

    @Test
    public void testDoWhile3() {
        decompileMethod("classic", "testDoWhile3", "do\r\n"
                + "{\r\n"
                + "this.nextChar();\r\n"
                + "}\r\n"
                + "while(this.ch != \"\\n\" && this.ch != \"\");\r\n",
                 false);
    }

    @Test
    public void testDoWhile4() {
        decompileMethod("classic", "testDoWhile4", "var k:int = 8;\r\n"
                + "do\r\n"
                + "{\r\n"
                + "if(k == 9)\r\n"
                + "{\r\n"
                + "trace(\"h\");\r\n"
                + "if(k == 9)\r\n"
                + "{\r\n"
                + "trace(\"f\");\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"b\");\r\n"
                + "}\r\n"
                + "trace(\"gg\");\r\n"
                + "}\r\n"
                + "while(k < 10);\r\n"
                + "trace(\"ss\");\r\n",
                 false);
    }

    @Test
    public void testDotParent() {
        decompileMethod("classic", "testDotParent", "var d:* = undefined;\r\n"
                + "var k:* = undefined;\r\n"
                + "var g:* = undefined;\r\n"
                + "d = new TestClass1();\r\n"
                + "k = null;\r\n"
                + "k.(++d.attrib, 0);\r\n"
                + "trace(\"between\");\r\n"
                + "g = k.(++d.attrib, 0);\r\n"
                + "trace(\"end\");\r\n",
                 false);
    }

    @Test
    public void testExecutionOrder() {
        decompileMethod("classic", "testExecutionOrder", "var m:MyClass = null;\r\n"
                + "m.x = (m = create() as MyClass).x + 5;\r\n"
                + "trace(m.x);\r\n",
                 false);
    }

    @Test
    public void testExpressions() {
        decompileMethod("classic", "testExpressions", "var arr:Array = null;\r\n"
                + "var i:int = 5;\r\n"
                + "var j:int = 5;\r\n"
                + "i = i = i / 2;\r\n"
                + "if(i == 1 || i == 2)\r\n"
                + "{\r\n"
                + "arguments.concat(i);\r\n"
                + "}\r\n"
                + "else if(i == 0)\r\n"
                + "{\r\n"
                + "i = j++;\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "arr[0]();\r\n"
                + "}\r\n"
                + "return i == 0;\r\n",
                 false);
    }

    @Test
    public void testFinallyZeroJump() {
        decompileMethod("classic", "testFinallyZeroJump", "var str:String = param1;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"error is :\" + e.message);\r\n"
                + "}\r\n"
                + "finally\r\n"
                + "{\r\n"
                + "trace(\"hi \");\r\n"
                + "if(5 == 4)\r\n"
                + "{\r\n"
                + "return str;\r\n"
                + "}\r\n"
                + "return \"hu\" + str;\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testFor() {
        decompileMethod("classic", "testFor", "for(var a:* = 0; a < 10; a++)\r\n"
                + "{\r\n"
                + "trace(\"a=\" + a);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForAnd() {
        decompileMethod("classic", "testForAnd", "var x:Boolean = false;\r\n"
                + "var len:int = 5;\r\n"
                + "var a:int = 4;\r\n"
                + "var b:int = 7;\r\n"
                + "var c:int = 9;\r\n"
                + "for(var i:uint = 0; i < len; x = a > 4 && b < 2 || c > 10)\r\n"
                + "{\r\n"
                + "c = 1;\r\n"
                + "if(c == 2)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "if(c == 7)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "trace(\"D\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForBreak() {
        decompileMethod("classic", "testForBreak", "for(var a:* = 0; a < 10; a++)\r\n"
                + "{\r\n"
                + "if(a == 5)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "trace(\"hello:\" + a);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForContinue() {
        decompileMethod("classic", "testForContinue", "for(var a:* = 0; a < 10; a += 1)\r\n"
                + "{\r\n"
                + "if(a == 9)\r\n"
                + "{\r\n"
                + "if(a == 5)\r\n"
                + "{\r\n"
                + "trace(\"part1\");\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"a=\" + a);\r\n"
                + "if(a == 7)\r\n"
                + "{\r\n"
                + "trace(\"part2\");\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"part3\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"part4\");\r\n"
                + "}\r\n"
                + "trace(\"part5\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForEach() {
        decompileMethod("classic", "testForEach", "var list:Array = null;\r\n"
                + "var item:* = undefined;\r\n"
                + "list = new Array();\r\n"
                + "list[0] = \"first\";\r\n"
                + "list[1] = \"second\";\r\n"
                + "list[2] = \"third\";\r\n"
                + "for each(item in list)\r\n"
                + "{\r\n"
                + "trace(\"item #\" + item);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForEachObjectArray() {
        decompileMethod("classic", "testForEachObjectArray", "var list:Array = null;\r\n"
                + "var test:Array = null;\r\n"
                + "list = new Array();\r\n"
                + "list[0] = \"first\";\r\n"
                + "list[1] = \"second\";\r\n"
                + "list[2] = \"third\";\r\n"
                + "test = new Array();\r\n"
                + "test[0] = 0;\r\n"
                + "for each(test[0] in list)\r\n"
                + "{\r\n"
                + "trace(\"item #\" + test[0]);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForEachObjectAttribute() {
        decompileMethod("classic", "testForEachObjectAttribute", "var list:Array = null;\r\n"
                + "list = new Array();\r\n"
                + "list[0] = \"first\";\r\n"
                + "list[1] = \"second\";\r\n"
                + "list[2] = \"third\";\r\n"
                + "for each(this.testPriv in list)\r\n"
                + "{\r\n"
                + "trace(\"item #\" + this.testPriv);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForEachReturn() {
        decompileMethod("classic", "testForEachReturn", "var list:Array = null;\r\n"
                + "var item:* = undefined;\r\n"
                + "list = new Array();\r\n"
                + "list[0] = \"first\";\r\n"
                + "list[1] = \"second\";\r\n"
                + "list[2] = \"third\";\r\n"
                + "var _loc3_:int = 0;\r\n"
                + "var _loc4_:* = list;\r\n"
                + "for each(item in _loc4_)\r\n"
                + "{\r\n"
                + "return item;\r\n"
                + "}\r\n"
                + "return null;\r\n",
                 false);
    }

    @Test
    public void testForEachReturn2() {
        decompileMethod("classic", "testForEachReturn2", "var item:* = undefined;\r\n"
                + "var obj:* = null;\r\n"
                + "var x:* = 5;\r\n"
                + "if(x != null)\r\n"
                + "{\r\n"
                + "obj = {};\r\n"
                + "for each(item in obj)\r\n"
                + "{\r\n"
                + "switch(item[\"key\"])\r\n"
                + "{\r\n"
                + "case 1:\r\n"
                + "case 2:\r\n"
                + "case 3:\r\n"
                + "case 4:\r\n"
                + "return item;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "}\r\n"
                + "return null;\r\n",
                 false);
    }

    @Test
    public void testForEachSwitch() {
        decompileMethod("classic", "testForEachSwitch", "var name:String = null;\r\n"
                + "var a:Boolean = true;\r\n"
                + "var b:Boolean = true;\r\n"
                + "var c:Boolean = true;\r\n"
                + "var s:int = 5;\r\n"
                + "var obj:Object = {};\r\n"
                + "for each(name in obj)\r\n"
                + "{\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "switch(s)\r\n"
                + "{\r\n"
                + "case 1:\r\n"
                + "trace(\"1\");\r\n"
                + "if(b)\r\n"
                + "{\r\n"
                + "trace(\"1b\");\r\n"
                + "}\r\n"
                + "case 2:\r\n"
                + "trace(\"2\");\r\n"
                + "break;\r\n"
                + "case 3:\r\n"
                + "trace(\"3\");\r\n"
                + "break;\r\n"
                + "case 4:\r\n"
                + "trace(\"4\");\r\n"
                + "}\r\n"
                + "if(c)\r\n"
                + "{\r\n"
                + "trace(\"2c\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "trace(\"before_continue\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForEachTry() {
        decompileMethod("classic", "testForEachTry", "var name:String = null;\r\n"
                + "var list:Object = {};\r\n"
                + "var b:Boolean = true;\r\n"
                + "for each(name in list)\r\n"
                + "{\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"xx\");\r\n"
                + "if(b)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "trace(\"D\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForGoto() {
        decompileMethod("classic", "testForGoto", "var c:int = 0;\r\n"
                + "var len:int = 5;\r\n"
                + "for(var i:uint = 0; i < len; i++)\r\n"
                + "{\r\n"
                + "c = 1;\r\n"
                + "if(c == 2)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "if(c != 3)\r\n"
                + "{\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "trace(\"exit\");\r\n",
                 false);
    }

    @Test
    public void testForIn() {
        decompileMethod("classic", "testForIn", "var dic:Dictionary = null;\r\n"
                + "var item:* = null;\r\n"
                + "for(item in dic)\r\n"
                + "{\r\n"
                + "trace(item);\r\n"
                + "}\r\n"
                + "for each(item in dic)\r\n"
                + "{\r\n"
                + "trace(item);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForInIf() {
        decompileMethod("classic", "testForInIf", "var a:String = null;\r\n"
                + "var arr:Array = [\"a\",\"b\",\"c\"];\r\n"
                + "var b:int = 5;\r\n"
                + "for(a in arr)\r\n"
                + "{\r\n"
                + "if(b == 5)\r\n"
                + "{\r\n"
                + "if(b <= 7)\r\n"
                + "{\r\n"
                + "return;\r\n"
                + "}\r\n"
                + "trace(\"b>7\");\r\n"
                + "}\r\n"
                + "trace(\"forend\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForInReturn() {
        decompileMethod("classic", "testForInReturn", "var dic:Dictionary = null;\r\n"
                + "var item:* = null;\r\n"
                + "var _loc3_:int = 0;\r\n"
                + "var _loc4_:* = dic;\r\n"
                + "for(item in _loc4_)\r\n"
                + "{\r\n"
                + "return item;\r\n"
                + "}\r\n"
                + "return null;\r\n",
                 false);
    }

    @Test
    public void testForInSwitch() {
        decompileMethod("classic", "testForInSwitch", "var a:String = null;\r\n"
                + "var arr:Array = [\"a\",\"b\",\"c\"];\r\n"
                + "for(a in arr)\r\n"
                + "{\r\n"
                + "switch(a)\r\n"
                + "{\r\n"
                + "case \"a\":\r\n"
                + "trace(\"val a\");\r\n"
                + "break;\r\n"
                + "case \"b\":\r\n"
                + "trace(\"val b\");\r\n"
                + "break;\r\n"
                + "case \"c\":\r\n"
                + "trace(\"val c\");\r\n"
                + "}\r\n"
                + "trace(\"final\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testForXml() {
        decompileMethod("classic", "testForXml", "var i:int;\r\n"
                + "var c:int = 0;\r\n"
                + "var name:String = \"ahoj\";\r\n"
                + "var myXML:XML = <order id=\"604\">\r\n"
                + "<book isbn=\"12345\">\r\n"
                + "<title>{name}</title>\r\n"
                + "</book>\r\n"
                + "</order>;\r\n"
                + "var k:* = null;\r\n"
                + "var len:int = 5;\r\n"
                + "var a:int = 5;\r\n"
                + "var b:int = 6;\r\n"
                + "for(i = 0; i < len; k = myXML.book.(@isbn == \"12345\"))\r\n"
                + "{\r\n"
                + "c = 1;\r\n"
                + "if(c == 2)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "if(c != 3)\r\n"
                + "{\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testGetProtected() {
        decompileMethod("classic", "testGetProtected", "var c:InnerClass = new InnerClass();\r\n"
                + "c.attr = 2;\r\n"
                + "var a:int = this.attr;\r\n"
                + "trace(a);\r\n",
                 false);
    }

    @Test
    public void testGotos() {
        decompileMethod("classic", "testGotos", "var a:Boolean = true;\r\n"
                + "var b:Boolean = false;\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "else if(b)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "try\r\n"
                + "{\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "return 7;\r\n"
                + "}\r\n"
                + "trace(\"x\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"z\");\r\n"
                + "}\r\n"
                + "trace(\"after\");\r\n"
                + "}\r\n"
                + "return 89;\r\n",
                 false);
    }

    @Test
    public void testGotos2() {
        decompileMethod("classic", "testGotos2", "var a:Boolean = true;\r\n"
                + "var b:Boolean = false;\r\n"
                + "var c:Boolean = true;\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "if(b)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "if(c)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"E\");\r\n"
                + "}\r\n"
                + "return 5;\r\n",
                 false);
    }

    @Test
    public void testGotos3() {
        decompileMethod("classic", "testGotos3", "var i:int = 0;\r\n"
                + "var a:int = 5;\r\n"
                + "if(a > 5)\r\n"
                + "{\r\n"
                + "for(i = 0; i < 5; i++)\r\n"
                + "{\r\n"
                + "if(i > 3)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "if(i == 4)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "trace(\"return\");\r\n",
                 false);
    }

    @Test
    public void testGotos4() {
        decompileMethod("classic", "testGotos4", "var a:int = 5;\r\n"
                + "if(a > 3)\r\n"
                + "{\r\n"
                + "if(a < 7)\r\n"
                + "{\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "catch(error:Error)\r\n"
                + "{\r\n"
                + "}\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "trace(\"return\");\r\n",
                 false);
    }

    @Test
    public void testGotos5() {
        decompileMethod("classic", "testGotos5", "var j:int = 0;\r\n"
                + "var s:String = \"A\";\r\n"
                + "for(var i:int = 0; i < 10; i++)\r\n"
                + "{\r\n"
                + "if(s == \"B\")\r\n"
                + "{\r\n"
                + "if(s == \"C\")\r\n"
                + "{\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "trace(\"D\");\r\n"
                + "j = 0;\r\n"
                + "while(j < 29)\r\n"
                + "{\r\n"
                + "trace(\"E\");\r\n"
                + "j++;\r\n"
                + "}\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testGotos6() {
        decompileMethod("classic", "testGotos6", "var a:Boolean = true;\r\n"
                + "var s:String = \"a\";\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "switch(s)\r\n"
                + "{\r\n"
                + "case \"a\":\r\n"
                + "trace(\"is A\");\r\n"
                + "break;\r\n"
                + "case \"b\":\r\n"
                + "trace(\"is B\");\r\n"
                + "case \"c\":\r\n"
                + "trace(\"is BC\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"D\");\r\n"
                + "}\r\n"
                + "trace(\"finish\");\r\n",
                 false);
    }

    @Test
    public void testGotos7() {
        decompileMethod("classic", "testGotos7", "for(var i:int = 0; i < 10; i++)\r\n"
                + "{\r\n"
                + "switch(i)\r\n"
                + "{\r\n"
                + "case 0:\r\n"
                + "trace(\"zero\");\r\n"
                + "continue;\r\n"
                + "case 5:\r\n"
                + "trace(\"five\");\r\n"
                + "break;\r\n"
                + "case 10:\r\n"
                + "trace(\"ten\");\r\n"
                + "break;\r\n"
                + "case 1:\r\n"
                + "if(i == 7)\r\n"
                + "{\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"one\");\r\n"
                + "default:\r\n"
                + "trace(\"def\");\r\n"
                + "}\r\n"
                + "trace(\"before loop end\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testHello() {
        decompileMethod("classic", "testHello", "trace(\"hello\");\r\n",
                 false);
    }

    @Test
    public void testIf() {
        decompileMethod("classic", "testIf", "var a:* = 5;\r\n"
                + "if(a == 7)\r\n"
                + "{\r\n"
                + "trace(\"onTrue\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testIfElse() {
        decompileMethod("classic", "testIfElse", "var a:* = 5;\r\n"
                + "if(a == 7)\r\n"
                + "{\r\n"
                + "trace(\"onTrue\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"onFalse\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testIfFinally() {
        decompileMethod("classic", "testIfFinally", "var a:int = Math.random();\r\n"
                + "if(a == 5)\r\n"
                + "{\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"in try body\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"in catch\");\r\n"
                + "}\r\n"
                + "finally\r\n"
                + "{\r\n"
                + "trace(\"in finally\");\r\n"
                + "}\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testIfInIf() {
        decompileMethod("classic", "testIfInIf", "var k:int = 5;\r\n"
                + "if(k > 5 && k < 20)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "if(k < 4)\r\n"
                + "{\r\n"
                + "return 1;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "else if(k > 4 && k < 10)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "if(k < 7)\r\n"
                + "{\r\n"
                + "return 2;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "trace(\"C\");\r\n"
                + "return 7;\r\n",
                 false);
    }

    @Test
    public void testIfTry() {
        decompileMethod("classic", "testIfTry", "var c:int = 0;\r\n"
                + "var i:int = 0;\r\n"
                + "var b:Boolean = true;\r\n"
                + "if(b)\r\n"
                + "{\r\n"
                + "c = 5;\r\n"
                + "for(i = 0; i < c; i++)\r\n"
                + "{\r\n"
                + "trace(\"xx\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"in try\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"in catch\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testIgnoreAndOr() {
        decompileMethod("classic", "testIgnoreAndOr", "var k:int = Math.random();\r\n"
                + "if(k > 5)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "if(k > 10)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "}\r\n"
                + "if(k > 15)\r\n"
                + "{\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "if(k > 20)\r\n"
                + "{\r\n"
                + "trace(\"D\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testImplicitCoerce() {
        decompileMethod("classic", "testImplicitCoerce", "var j:int = 2;\r\n"
                + "var i:int = 5;\r\n"
                + "var r:* = Math.random();\r\n"
                + "if(Boolean(j & Number(r == 1)) && Boolean(5))\r\n"
                + "{\r\n"
                + "trace(\"OK\");\r\n"
                + "}\r\n"
                + "var s:String = \"hello: \" + r;\r\n"
                + "if(Boolean(s))\r\n"
                + "{\r\n"
                + "trace(\"F\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testImportedConst() {
        decompileMethod("classic", "testImportedConst", "trace(myconst);\r\n",
                 false);
    }

    @Test
    public void testImportedVar() {
        decompileMethod("classic", "testImportedVar", "trace(myvar);\r\n"
                + "myvar = 5;\r\n",
                 false);
    }

    @Test
    public void testInc2() {
        decompileMethod("classic", "testInc2", "var a:* = [1];\r\n"
                + "var d:* = a[this.getInt()]++;\r\n"
                + "var e:* = ++a[this.getInt()];\r\n"
                + "++a[this.getInt()];\r\n"
                + "++a[this.getInt()];\r\n"
                + "var b:* = 1;\r\n"
                + "b++;\r\n"
                + "var c:* = 1;\r\n"
                + "b = c++;\r\n",
                 false);
    }

    @Test
    public void testIncDec() {
        decompileMethod("classic", "testIncDec", "var a:* = 5;\r\n"
                + "var b:* = 0;\r\n"
                + "trace(\"++var\");\r\n"
                + "b = ++a;\r\n"
                + "trace(\"var++\");\r\n"
                + "b = a++;\r\n"
                + "trace(\"--var\");\r\n"
                + "b = --a;\r\n"
                + "trace(\"var--\");\r\n"
                + "b = a--;\r\n"
                + "var c:* = [1,2,3,4,5];\r\n"
                + "trace(\"++arr\");\r\n"
                + "b = ++c[2];\r\n"
                + "trace(\"arr++\");\r\n"
                + "b = c[2]++;\r\n"
                + "trace(\"--arr\");\r\n"
                + "b = --c[2];\r\n"
                + "trace(\"arr--\");\r\n"
                + "b = c[2]--;\r\n"
                + "var d:* = new TestClass1();\r\n"
                + "trace(\"++property\");\r\n"
                + "trace(++d.attrib);\r\n"
                + "trace(\"property++\");\r\n"
                + "trace(d.attrib++);\r\n"
                + "trace(\"--property\");\r\n"
                + "trace(--d.attrib);\r\n"
                + "trace(\"property--\");\r\n"
                + "trace(d.attrib--);\r\n"
                + "trace(\"arr[e++]\");\r\n"
                + "var chars:Array = new Array(36);\r\n"
                + "var index:uint = 0;\r\n"
                + "var _loc7_:* = index++;\r\n"
                + "chars[_loc7_] = 5;\r\n"
                + "trace(\"arr[++e]\");\r\n"
                + "var _loc8_:* = ++index;\r\n"
                + "chars[_loc8_] = 5;\r\n"
                + "trace(\"attr++\");\r\n"
                + "trace(this.attrx++);\r\n"
                + "++this.attrx;\r\n"
                + "trace(\"attr--\");\r\n"
                + "trace(this.attrx--);\r\n"
                + "--this.attrx;\r\n"
                + "trace(\"++attr\");\r\n"
                + "trace(++this.attrx);\r\n"
                + "++this.attrx;\r\n"
                + "trace(\"--attr\");\r\n"
                + "trace(--this.attrx);\r\n"
                + "--this.attrx;\r\n",
                 false);
    }

    @Test
    public void testInlineFunctions() {
        decompileMethod("classic", "testInlineFunctions", "var first:String = null;\r\n"
                + "first = \"value1\";\r\n"
                + "var traceParameter:Function = function(aParam:String):String\r\n"
                + "{\r\n"
                + "var second:String = null;\r\n"
                + "second = \"value2\";\r\n"
                + "second += \"cc\";\r\n"
                + "var traceParam2:Function = function(bParam:String):String\r\n"
                + "{\r\n"
                + "trace(bParam + \",\" + aParam);\r\n"
                + "return first + second + aParam + bParam;\r\n"
                + "};\r\n"
                + "trace(second);\r\n"
                + "traceParam2(aParam);\r\n"
                + "return first;\r\n"
                + "};\r\n"
                + "traceParameter(\"hello\");\r\n",
                 false);
    }

    @Test
    public void testInlineFunctions2() {
        decompileMethod("classic", "testInlineFunctions2", "var f:Function = function(a:int):int\r\n"
                + "{\r\n"
                + "return a + 1;\r\n"
                + "};\r\n"
                + "var g:Function = function(a:int):int\r\n"
                + "{\r\n"
                + "return a + 1;\r\n"
                + "};\r\n"
                + "var h:Function = function h2(a:int):int\r\n"
                + "{\r\n"
                + "return a + 1;\r\n"
                + "};\r\n"
                + "(function(a:int):int\r\n"
                + "{\r\n"
                + "return a + 1;\r\n"
                + "})(1);\r\n",
                 false);
    }

    @Test
    public void testInnerFunctionScope() {
        decompileMethod("classic", "testInnerFunctionScope", "var innerFunc:Function = function(b:String):*\r\n"
                + "{\r\n"
                + "testProm = 4;\r\n"
                + "trace(testProm);\r\n"
                + "};\r\n"
                + "innerFunc(a);\r\n",
                 false);
    }

    @Test
    public void testInnerFunctions() {
        decompileMethod("classic", "testInnerFunctions", "var s:int = 0;\r\n"
                + "var innerFunc:Function = function(b:String):*\r\n"
                + "{\r\n"
                + "trace(b);\r\n"
                + "};\r\n"
                + "var k:int = 5;\r\n"
                + "if(k == 6)\r\n"
                + "{\r\n"
                + "s = 8;\r\n"
                + "}\r\n"
                + "innerFunc(a);\r\n",
                 false);
    }

    @Test
    public void testInnerIf() {
        decompileMethod("classic", "testInnerIf", "var a:* = 5;\r\n"
                + "var b:* = 4;\r\n"
                + "if(a == 5)\r\n"
                + "{\r\n"
                + "if(b == 6)\r\n"
                + "{\r\n"
                + "trace(\"b==6\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"b!=6\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "else if(b == 7)\r\n"
                + "{\r\n"
                + "trace(\"b==7\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"b!=7\");\r\n"
                + "}\r\n"
                + "trace(\"end\");\r\n",
                 false);
    }

    @Test
    public void testInnerTry() {
        decompileMethod("classic", "testInnerTry", "try\r\n"
                + "{\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"try body 1\");\r\n"
                + "}\r\n"
                + "catch(e:DefinitionError)\r\n"
                + "{\r\n"
                + "trace(\"catched DefinitionError\");\r\n"
                + "}\r\n"
                + "trace(\"after try 1\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"catched Error\");\r\n"
                + "}\r\n"
                + "finally\r\n"
                + "{\r\n"
                + "trace(\"finally block\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testLogicalComputing() {
        decompileMethod("classic", "testLogicalComputing", "var b:Boolean = false;\r\n"
                + "var i:* = 5;\r\n"
                + "var j:* = 7;\r\n"
                + "if(i > j)\r\n"
                + "{\r\n"
                + "j = 9;\r\n"
                + "b = true;\r\n"
                + "}\r\n"
                + "b = (i == 0 || i == 1) && j == 0;\r\n",
                 false);
    }

    @Test
    public void testLoopInLoop() {
        decompileMethod("classic", "testLoopInLoop", "var i:* = undefined;\r\n"
                + "var a:Boolean = true;\r\n"
                + "var b:Boolean = true;\r\n"
                + "for(var c:Boolean = true; true; )\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "for(i = 0; i < 10; i++)\r\n"
                + "{\r\n"
                + "if(!a)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "if(c)\r\n"
                + "{\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"D\");\r\n"
                + "if(b)\r\n"
                + "{\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"H\");\r\n"
                + "}\r\n"
                + "if(c)\r\n"
                + "{\r\n"
                + "trace(\"L\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "}\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testManualConvert() {
        decompileMethod("classic", "testManualConvert", "trace(\"String(this).length\");\r\n"
                + "trace(String(this).length);\r\n",
                 false);
    }

    @Test
    public void testMetadata() {
        decompileMethod("classic", "testMetadata", "trace(\"hello\");\r\n",
                 false);
    }

    @Test
    public void testMissingDefault() {
        decompileMethod("classic", "testMissingDefault", "var jj:int = 1;\r\n"
                + "switch(jj)\r\n"
                + "{\r\n"
                + "case 1:\r\n"
                + "jj = 1;\r\n"
                + "break;\r\n"
                + "case 2:\r\n"
                + "jj = 2;\r\n"
                + "break;\r\n"
                + "default:\r\n"
                + "jj = 3;\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testMultipleCondition() {
        decompileMethod("classic", "testMultipleCondition", "var a:* = 5;\r\n"
                + "var b:* = 8;\r\n"
                + "var c:* = 9;\r\n"
                + "if((a <= 4 || b <= 8) && c == 7)\r\n"
                + "{\r\n"
                + "trace(\"onTrue\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"onFalse\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testNamedAnonFunctions() {
        decompileMethod("classic", "testNamedAnonFunctions", "var test:* = function testFunc(param1:*, param2:int, param3:Array):Boolean\r\n"
                + "{\r\n"
                + "return (param1 as TestClass2).attrib1 == 5;\r\n"
                + "};\r\n",
                 false);
    }

    @Test
    public void testNames() {
        decompileMethod("classic", "testNames", "var ns:* = this.getNamespace();\r\n"
                + "var name:* = this.getName();\r\n"
                + "var a:* = ns::unnamespacedFunc();\r\n"
                + "var b:* = ns::[name];\r\n"
                + "trace(b.c);\r\n"
                + "var c:* = myInternal::neco;\r\n"
                + "var d:* = this.myInternal2::neco;\r\n",
                 false);
    }

    @Test
    public void testNames2() {
        decompileMethod("classic", "testNames2", "var j:int = 0;\r\n"
                + "var g:Function = null;\r\n"
                + "this.i = 0;\r\n"
                + "this.i = 1;\r\n"
                + "j = 2;\r\n"
                + "trace(this.i);\r\n"
                + "trace(this.i);\r\n"
                + "trace(j);\r\n"
                + "this.f();\r\n"
                + "this.f();\r\n"
                + "g();\r\n",
                 false);
    }

    @Test
    public void testNegate() {
        decompileMethod("classic", "testNegate", "var a:int = 5;\r\n"
                + "var b:int = ~a;\r\n",
                 false);
    }

    @Test
    public void testNumberCall() {
        decompileMethod("classic", "testNumberCall", "var a:String = (5).toString();\r\n"
                + "var b:String = 5.2.toString();\r\n",
                 false);
    }

    @Test
    public void testOperations() {
        decompileMethod("classic", "testOperations", "var cr:MyClass = null;\r\n"
                + "var br:Boolean = false;\r\n"
                + "var r:Number = NaN;\r\n"
                + "var v:* = undefined;\r\n"
                + "var xlr:XMLList = null;\r\n"
                + "var sr:String = null;\r\n"
                + "var c:MyClass = new MyClass();\r\n"
                + "var d:Dictionary = new Dictionary();\r\n"
                + "var n1:Number = 2;\r\n"
                + "var n2:Number = 3;\r\n"
                + "var b1:Boolean = true;\r\n"
                + "var b2:Boolean = false;\r\n"
                + "var x:XML = <a>\r\n"
                + "<b>one\r\n"
                + "<c>\r\n"
                + "<b>two</b>\r\n"
                + "</c>\r\n"
                + "</b>\r\n"
                + "<b>three</b>\r\n"
                + "</a>;\r\n"
                + "var o:Object = {\r\n"
                + "\"a\":1,\r\n"
                + "\"b\":2\r\n"
                + "};\r\n"
                + "var s1:String = \"hello\";\r\n"
                + "var s2:String = \"there\";\r\n"
                + "r = -n1;\r\n"
                + "r = ~n1;\r\n"
                + "br = !b1;\r\n"
                + "r = ++n1;\r\n"
                + "r = n1++;\r\n"
                + "cr = c as MyClass;\r\n"
                + "br = \"hello\" in d;\r\n"
                + "r = b1 ? n1 : n2;\r\n"
                + "r = n1 << n2;\r\n"
                + "r = n1 >> n2;\r\n"
                + "r = n1 >>> n2;\r\n"
                + "r = n1 & n2;\r\n"
                + "r = n1 | n2;\r\n"
                + "r = n1 / n2;\r\n"
                + "r = n1 % n2;\r\n"
                + "br = n1 == n2;\r\n"
                + "br = n1 === n2;\r\n"
                + "br = n1 != n2;\r\n"
                + "br = n1 !== n2;\r\n"
                + "br = n1 < n2;\r\n"
                + "br = n1 <= n2;\r\n"
                + "br = n1 > n2;\r\n"
                + "br = n1 >= n2;\r\n"
                + "br = b1 && b2;\r\n"
                + "br = b1 || b2;\r\n"
                + "r = n1 - n2;\r\n"
                + "r = n1 * n2;\r\n"
                + "r = n1 + n2;\r\n"
                + "r = n1 ^ n2;\r\n"
                + "br = c instanceof MyClass;\r\n"
                + "br = c is MyClass;\r\n"
                + "xlr = x..b;\r\n"
                + "sr = s1 + s2;\r\n"
                + "r &= n1;\r\n"
                + "r |= n1;\r\n"
                + "r /= n1;\r\n"
                + "r -= n1;\r\n"
                + "r %= n1;\r\n"
                + "r *= n1;\r\n"
                + "r += n1;\r\n"
                + "r <<= n1;\r\n"
                + "r >>= n1;\r\n"
                + "r >>>= n1;\r\n"
                + "r ^= n1;\r\n"
                + "br &&= b1;\r\n"
                + "br ||= b1;\r\n"
                + "sr += s1;\r\n"
                + "delete o.a;\r\n"
                + "\"test\" + this.f();\r\n"
                + "v = undefined;\r\n"
                + "sr = typeof c;\r\n",
                 false);
    }

    @Test
    public void testOptimization() {
        decompileMethod("classic", "testOptimization", "var f:int = 0;\r\n"
                + "var g:int = 0;\r\n"
                + "var h:int = 0;\r\n"
                + "var a:int = 1;\r\n"
                + "var b:int = 2;\r\n"
                + "var c:int = 3;\r\n"
                + "var d:int = 4;\r\n"
                + "var e:int = d + 5;\r\n"
                + "var i:int = h = g = f;\r\n",
                 false);
    }

    @Test
    public void testOptimizationAndOr() {
        decompileMethod("classic", "testOptimizationAndOr", "var plugin:Object = null;\r\n"
                + "var o:Object = {\r\n"
                + "\"a\":\"Object\",\r\n"
                + "\"b\":\"Object\",\r\n"
                + "\"c\":\"Object\"\r\n"
                + "};\r\n"
                + "var a:String = \"d\";\r\n"
                + "if(a in o && (plugin = new o[a]()).toString().length > 2)\r\n"
                + "{\r\n"
                + "trace(\"okay\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testOptimizationWhile() {
        decompileMethod("classic", "testOptimizationWhile", "var a:int = 1;\r\n"
                + "var b:int = 2;\r\n"
                + "var c:int = 3;\r\n"
                + "var d:int = 4;\r\n"
                + "while(true)\r\n"
                + "{\r\n"
                + "d = Math.round(Math.random() * 10);\r\n"
                + "if(d >= 10)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "trace(\"xxx\");\r\n"
                + "d++;\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testParamNames() {
        decompileMethod("classic", "testParamNames", "return firstp + secondp + thirdp;\r\n",
                 false);
    }

    @Test
    public void testParamsCount() {
        decompileMethod("classic", "testParamsCount", "return firstp;\r\n",
                 false);
    }

    @Test
    public void testPrecedence() {
        decompileMethod("classic", "testPrecedence", "var a:* = 0;\r\n"
                + "a = (5 + 6) * 7;\r\n"
                + "a = 5 * (2 + 3);\r\n"
                + "a = 5 + 6 * 7;\r\n"
                + "a = 5 * 2 + 2;\r\n"
                + "a = 5 * (25 % 3);\r\n"
                + "a = 5 % (24 * 307);\r\n"
                + "a = 1 / (2 / 3);\r\n"
                + "a = 1 / (2 * 3);\r\n"
                + "a = 1 * 2 * 3;\r\n"
                + "a = 1 * 2 / 3;\r\n"
                + "trace(\"a=\" + a);\r\n",
                 false);
    }

    @Test
    public void testPrecedenceX() {
        decompileMethod("classic", "testPrecedenceX", "var a:* = 5;\r\n"
                + "var b:* = 2;\r\n"
                + "var c:* = 3;\r\n"
                + "var d:* = a << (b >>> c);\r\n"
                + "var e:* = a << b >>> c;\r\n",
                 false);
    }

    @Test
    public void testProperty() {
        decompileMethod("classic", "testProperty", "var d:TestClass1 = new TestClass1();\r\n"
                + "var k:* = 7 + 8;\r\n"
                + "if(k == 15)\r\n"
                + "{\r\n"
                + "d.method(d.attrib * 5);\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testRegExp() {
        decompileMethod("classic", "testRegExp", "var r:Number = NaN;\r\n"
                + "var a1:* = /[a-z\\r\\n0-9\\\\]+/i;\r\n"
                + "var a2:* = /[a-z\\r\\n0-9\\\\]+/i;\r\n"
                + "var b1:* = /[0-9AB]+/;\r\n"
                + "var b2:* = /[0-9AB]+/;\r\n"
                + "var n1:Number = 5;\r\n"
                + "var n2:Number = 2;\r\n"
                + "var n3:Number = 1;\r\n"
                + "trace(\"not a regexp 1\");\r\n"
                + "r = n1 / n2 / n3;\r\n"
                + "trace(\"not a regexp 2\");\r\n"
                + "r /= n1 / n2;\r\n",
                 false);
    }

    @Test
    public void testRest() {
        decompileMethod("classic", "testRest", "trace(\"firstRest:\" + restval[0]);\r\n"
                + "return firstp;\r\n",
                 false);
    }

    @Test
    public void testSlots() {
        decompileMethod("classic", "testSlots", "var i:int = 1;\r\n"
                + "var f:Function = function():void\r\n"
                + "{\r\n"
                + "trace(\"hello\");\r\n"
                + "};\r\n"
                + "i = 0;\r\n"
                + "trace(i++);\r\n"
                + "trace(i--);\r\n"
                + "trace(++i);\r\n"
                + "trace(--i);\r\n",
                 false);
    }

    @Test
    public void testSlots2() {
        decompileMethod("classic", "testSlots2", "var f:Function = function():void\r\n"
                + "{\r\n"
                + "var n:int = 0;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"intry\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "n = 1;\r\n"
                + "}\r\n"
                + "};\r\n",
                 false);
    }

    @Test
    public void testStrictEquals() {
        decompileMethod("classic", "testStrictEquals", "var k:int = 6;\r\n"
                + "if(this.f() !== this.f())\r\n"
                + "{\r\n"
                + "trace(\"is eight\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testStringCoerce() {
        decompileMethod("classic", "testStringCoerce", "var text1:String = this.a[\"test\"];\r\n"
                + "var text2:String = String(this.a[\"test\"]);\r\n",
                 false);
    }

    @Test
    public void testStringConcat() {
        decompileMethod("classic", "testStringConcat", "var k:int = 8;\r\n"
                + "this.traceIt(\"hello\" + 5 * 6);\r\n"
                + "this.traceIt(\"hello\" + (k - 1));\r\n"
                + "this.traceIt(\"hello\" + 5 + 6);\r\n",
                 false);
    }

    @Test
    public void testStrings() {
        decompileMethod("classic", "testStrings", "trace(\"hello\");\r\n"
                + "trace(\"quotes:\\\"hello!\\\"\");\r\n"
                + "trace(\"backslash: \\\\ \");\r\n"
                + "trace(\"single quotes: \\'hello!\\'\");\r\n"
                + "trace(\"new line \\r\\n hello!\");\r\n",
                 false);
    }

    @Test
    public void testSwitch() {
        decompileMethod("classic", "testSwitch", "var a:* = 5;\r\n"
                + "switch(a)\r\n"
                + "{\r\n"
                + "case 57 * a:\r\n"
                + "trace(\"fiftyseven multiply a\");\r\n"
                + "break;\r\n"
                + "case 13:\r\n"
                + "trace(\"thirteen\");\r\n"
                + "case 14:\r\n"
                + "trace(\"fourteen\");\r\n"
                + "break;\r\n"
                + "case 89:\r\n"
                + "trace(\"eightynine\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testSwitchBig() {
        decompileMethod("classic", "testSwitchBig", "var k:* = 10;\r\n"
                + "switch(k)\r\n"
                + "{\r\n"
                + "case \"A\":\r\n"
                + "trace(\"A\");\r\n"
                + "break;\r\n"
                + "case \"B\":\r\n"
                + "case \"C\":\r\n"
                + "trace(\"BC\");\r\n"
                + "break;\r\n"
                + "case \"D\":\r\n"
                + "default:\r\n"
                + "case \"E\":\r\n"
                + "trace(\"D-default-E\");\r\n"
                + "break;\r\n"
                + "case \"F\":\r\n"
                + "trace(\"F no break\");\r\n"
                + "case \"G\":\r\n"
                + "trace(\"G\");\r\n"
                + "break;\r\n"
                + "case \"H\":\r\n"
                + "trace(\"H last\");\r\n"
                + "}\r\n"
                + "trace(\"after switch\");\r\n",
                 false);
    }

    @Test
    public void testSwitchComma() {
        decompileMethod("classic", "testSwitchComma", "var b:int = 5;\r\n"
                + "var a:String = \"A\";\r\n"
                + "switch(a)\r\n"
                + "{\r\n"
                + "case \"A\":\r\n"
                + "trace(\"is A\");\r\n"
                + "break;\r\n"
                + "case \"B\":\r\n"
                + "trace(\"is B\");\r\n"
                + "case TestSwitchComma.X, \"C\":\r\n"
                + "trace(\"is C\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testSwitchContinue() {
        decompileMethod("classic", "testSwitchContinue", "var i:int = 0;\r\n"
                + "var r:int = Math.random() % 10;\r\n"
                + "if(r > 5)\r\n"
                + "{\r\n"
                + "for(i = 0; i < 10; i++)\r\n"
                + "{\r\n"
                + "switch(i)\r\n"
                + "{\r\n"
                + "case 0:\r\n"
                + "trace(\"hello\");\r\n"
                + "break;\r\n"
                + "case 1:\r\n"
                + "trace(\"hi\");\r\n"
                + "break;\r\n"
                + "case 2:\r\n"
                + "trace(\"howdy\");\r\n"
                + "break;\r\n"
                + "default:\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"message shown\");\r\n"
                + "}\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testSwitchDefault() {
        decompileMethod("classic", "testSwitchDefault", "var a:* = 5;\r\n"
                + "switch(a)\r\n"
                + "{\r\n"
                + "case 57 * a:\r\n"
                + "trace(\"fiftyseven multiply a\");\r\n"
                + "break;\r\n"
                + "case 13:\r\n"
                + "trace(\"thirteen\");\r\n"
                + "case 14:\r\n"
                + "trace(\"fourteen\");\r\n"
                + "break;\r\n"
                + "case 89:\r\n"
                + "trace(\"eightynine\");\r\n"
                + "break;\r\n"
                + "default:\r\n"
                + "trace(\"default clause\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testSwitchDefaultEndMultiple() {
        decompileMethod("classic", "testSwitchDefaultEndMultiple", "var a:* = \"X\";\r\n"
                + "switch(a)\r\n"
                + "{\r\n"
                + "case \"A\":\r\n"
                + "trace(\"A\");\r\n"
                + "break;\r\n"
                + "case \"B\":\r\n"
                + "trace(\"B\");\r\n"
                + "break;\r\n"
                + "case \"C\":\r\n"
                + "case \"D\":\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testSwitchIf() {
        decompileMethod("classic", "testSwitchIf", "var code:String = \"4\";\r\n"
                + "var a:Boolean = true;\r\n"
                + "switch(int(code) - 2)\r\n"
                + "{\r\n"
                + "case 0:\r\n"
                + "case 1:\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "trace(\"B\");\r\n",
                 false);
    }

    @Test
    public void testTernarOperator() {
        decompileMethod("classic", "testTernarOperator", "var a:* = 5;\r\n"
                + "var b:* = 4;\r\n"
                + "var c:* = 4;\r\n"
                + "var d:* = 78;\r\n"
                + "var e:* = a == b ? (c == d ? 1 : 7) : 3;\r\n"
                + "trace(\"e=\" + e);\r\n",
                 false);
    }

    @Test
    public void testTernarOperator2() {
        decompileMethod("classic", "testTernarOperator2", "var b:Boolean = true;\r\n"
                + "var i:int = 1;\r\n"
                + "var j:int = b ? i : i + 1;\r\n"
                + "var k:int = Boolean(i) ? j : j + 1;\r\n",
                 false);
    }

    @Test
    public void testTry() {
        decompileMethod("classic", "testTry", "var i:int = 0;\r\n"
                + "i = 7;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "trace(\"try body\");\r\n"
                + "}\r\n"
                + "catch(e:DefinitionError)\r\n"
                + "{\r\n"
                + "trace(\"catched DefinitionError\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"Error message:\" + e.message);\r\n"
                + "trace(\"Stacktrace:\" + e.getStackTrace());\r\n"
                + "}\r\n"
                + "finally\r\n"
                + "{\r\n"
                + "trace(\"Finally part\");\r\n"
                + "}\r\n"
                + "trace(\"end\");\r\n",
                 false);
    }

    @Test
    public void testTryIf() {
        decompileMethod("classic", "testTryIf", "var a:int = Math.random();\r\n"
                + "try\r\n"
                + "{\r\n"
                + "if(a > 5 && a < 50)\r\n"
                + "{\r\n"
                + "trace(\"in limits\");\r\n"
                + "}\r\n"
                + "trace(\"next\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"in catch\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testTryReturn() {
        decompileMethod("classic", "testTryReturn", "var i:int = 0;\r\n"
                + "var b:Boolean = false;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "i = 0;\r\n"
                + "b = true;\r\n"
                + "if(i > 0)\r\n"
                + "{\r\n"
                + "while(this.testDoWhile2())\r\n"
                + "{\r\n"
                + "if(b)\r\n"
                + "{\r\n"
                + "return 5;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "}\r\n"
                + "i++;\r\n"
                + "return 2;\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "}\r\n"
                + "return 4;\r\n",
                 false);
    }

    @Test
    public void testTryReturn2() {
        decompileMethod("classic", "testTryReturn2", "var a:Boolean;\r\n"
                + "var b:Boolean;\r\n"
                + "var d:Boolean;\r\n"
                + "var e:Boolean;\r\n"
                + "var c:Boolean = false;\r\n"
                + "trace(\"before\");\r\n"
                + "a = true;\r\n"
                + "b = false;\r\n"
                + "c = true;\r\n"
                + "d = false;\r\n"
                + "e = true;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "return \"A\";\r\n"
                + "}\r\n"
                + "if(b)\r\n"
                + "{\r\n"
                + "return \"B\";\r\n"
                + "}\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "if(c)\r\n"
                + "{\r\n"
                + "return \"C\";\r\n"
                + "}\r\n"
                + "}\r\n"
                + "finally\r\n"
                + "{\r\n"
                + "if(d)\r\n"
                + "{\r\n"
                + "return \"D\";\r\n"
                + "}\r\n"
                + "if(e)\r\n"
                + "{\r\n"
                + "return \"E\";\r\n"
                + "}\r\n"
                + "}\r\n"
                + "trace(\"after\");\r\n"
                + "return \"X\";\r\n",
                 false);
    }

    @Test
    public void testUndefined() {
        decompileMethod("classic", "testUndefined", "var i:int = 0;\r\n"
                + "var j:int = 0;\r\n"
                + "var c:int = 0;\r\n"
                + "c = 5 + i;\r\n"
                + "var f:Function = function():void\r\n"
                + "{\r\n"
                + "trace(c);\r\n"
                + "trace(j);\r\n"
                + "};\r\n"
                + "while(i < 10)\r\n"
                + "{\r\n"
                + "trace(i);\r\n"
                + "i++;\r\n"
                + "}\r\n"
                + "f();\r\n",
                 false);
    }

    @Test
    public void testUsagesTry() {
        decompileMethod("classic", "testUsagesTry", "var a:Boolean;\r\n"
                + "var b:Boolean;\r\n"
                + "var k:int = 5;\r\n"
                + "switch(k)\r\n"
                + "{\r\n"
                + "case 0:\r\n"
                + "trace(\"1\");\r\n"
                + "break;\r\n"
                + "case 1:\r\n"
                + "trace(\"2\");\r\n"
                + "}\r\n"
                + "a = true;\r\n"
                + "b = true;\r\n"
                + "try\r\n"
                + "{\r\n"
                + "if(b)\r\n"
                + "{\r\n"
                + "return \"B\";\r\n"
                + "}\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "trace(\"E\");\r\n"
                + "}\r\n"
                + "finally\r\n"
                + "{\r\n"
                + "trace(\"finally\");\r\n"
                + "}\r\n"
                + "trace(\"after\");\r\n"
                + "return \"X\";\r\n",
                 false);
    }

    @Test
    public void testVarFqn() {
        decompileMethod("classic", "testVarFqn", "var b:int = TestClass + 5;\r\n"
                + "var f:Function = function(x:int, y:int):int\r\n"
                + "{\r\n"
                + "return x + y + TestClass;\r\n"
                + "};\r\n",
                 false);
    }

    @Test
    public void testVector() {
        decompileMethod("classic", "testVector", "var v:Vector.<String> = new Vector.<String>();\r\n"
                + "v.push(\"hello\");\r\n"
                + "v[0] = \"hi\";\r\n"
                + "var a:int = 5;\r\n"
                + "v[a * 8 - 39] = \"hi2\";\r\n"
                + "trace(v[0]);\r\n",
                 false);
    }

    @Test
    public void testVector2() {
        decompileMethod("classic", "testVector2", "var a:Vector.<Vector.<int>> = new Vector.<Vector.<int>>();\r\n"
                + "var b:Vector.<int> = new <int>[10,20,30];\r\n",
                 false);
    }

    @Test
    public void testWhileAnd() {
        decompileMethod("classic", "testWhileAnd", "var a:int = 5;\r\n"
                + "var b:int = 10;\r\n"
                + "while(a < 10 && b > 1)\r\n"
                + "{\r\n"
                + "a++;\r\n"
                + "b--;\r\n"
                + "}\r\n"
                + "a = 7;\r\n"
                + "b = 9;\r\n",
                 false);
    }

    @Test
    public void testWhileBreak() {
        decompileMethod("classic", "testWhileBreak", "var a:int = 0;\r\n"
                + "while(a < 10)\r\n"
                + "{\r\n"
                + "if(a > 1 && a > 2 && a > 3 && a > 4 && a > 5)\r\n"
                + "{\r\n"
                + "return \"A\";\r\n"
                + "}\r\n"
                + "trace(\"middle\");\r\n"
                + "if(a == 5)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "return \"B\";\r\n",
                 false);
    }

    @Test
    public void testWhileBreak2() {
        decompileMethod("classic", "testWhileBreak2", "var k:int = 8;\r\n"
                + "while(true)\r\n"
                + "{\r\n"
                + "trace(\"X\");\r\n"
                + "if(k == 1)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "return;\r\n"
                + "}\r\n"
                + "trace(\"Y\");\r\n"
                + "if(k < 10)\r\n"
                + "{\r\n"
                + "trace(\"k1\");\r\n"
                + "if(k == 2)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "if(k > 1)\r\n"
                + "{\r\n"
                + "trace(\"B1\");\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "trace(\"B2\");\r\n"
                + "}\r\n"
                + "trace(\"Z\");\r\n"
                + "if(k == 3)\r\n"
                + "{\r\n"
                + "trace(\"C\");\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "trace(\"Z2\");\r\n"
                + "if(k == 4)\r\n"
                + "{\r\n"
                + "trace(\"D\");\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "trace(\"k2\");\r\n"
                + "}\r\n"
                + "trace(\"E\");\r\n"
                + "if(k == 2)\r\n"
                + "{\r\n"
                + "trace(\"E1\");\r\n"
                + "return;\r\n"
                + "}\r\n"
                + "trace(\"gg\");\r\n"
                + "}\r\n"
                + "trace(\"ss\");\r\n",
                 false);
    }

    @Test
    public void testWhileBreak3() {
        decompileMethod("classic", "testWhileBreak3", "var i:int = Math.floor(Math.random() * 11);\r\n"
                + "while(true)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "if(i < 100)\r\n"
                + "{\r\n"
                + "if(i < 0)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "if(i < 4)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "if(i == 4)\r\n"
                + "{\r\n"
                + "trace(\"D\");\r\n"
                + "return i;\r\n"
                + "}\r\n"
                + "}\r\n"
                + "return i;\r\n",
                 false);
    }

    @Test
    public void testWhileContinue() {
        decompileMethod("classic", "testWhileContinue", "var a:* = 5;\r\n"
                + "while(true)\r\n"
                + "{\r\n"
                + "if(a == 9)\r\n"
                + "{\r\n"
                + "if(a == 8)\r\n"
                + "{\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "if(a == 9)\r\n"
                + "{\r\n"
                + "break;\r\n"
                + "}\r\n"
                + "trace(\"hello 1\");\r\n"
                + "}\r\n"
                + "trace(\"hello2\");\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testWhileDoWhile() {
        decompileMethod("classic", "testWhileDoWhile", "trace(\"A\");\r\n"
                + "var i:int = 0;\r\n"
                + "while(i < 10)\r\n"
                + "{\r\n"
                + "trace(\"B\");\r\n"
                + "do\r\n"
                + "{\r\n"
                + "i++;\r\n"
                + "trace(\"C\");\r\n"
                + "}\r\n"
                + "while(i < 5);\r\n"
                + "\r\n"
                + "}\r\n"
                + "trace(\"E\");\r\n",
                 false);
    }

    @Test
    public void testWhileSwitch() {
        decompileMethod("classic", "testWhileSwitch", "var a:Boolean = true;\r\n"
                + "var d:int = 5;\r\n"
                + "var e:Boolean = true;\r\n"
                + "var i:int = 0;\r\n"
                + "while(i < 100)\r\n"
                + "{\r\n"
                + "trace(\"start\");\r\n"
                + "if(a)\r\n"
                + "{\r\n"
                + "trace(\"A\");\r\n"
                + "}\r\n"
                + "else\r\n"
                + "{\r\n"
                + "switch(d)\r\n"
                + "{\r\n"
                + "case 1:\r\n"
                + "trace(\"D1\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "if(e)\r\n"
                + "{\r\n"
                + "trace(\"E\");\r\n"
                + "}\r\n"
                + "i++;\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testWhileTrue() {
        decompileMethod("classic", "testWhileTrue", "var a:int = Math.floor(Math.random() * 6);\r\n"
                + "if(a > 4)\r\n"
                + "{\r\n"
                + "while(true)\r\n"
                + "{\r\n"
                + "}\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testWhileTry() {
        decompileMethod("classic", "testWhileTry", "while(true)\r\n"
                + "{\r\n"
                + "try\r\n"
                + "{\r\n"
                + "while(true)\r\n"
                + "{\r\n"
                + "trace(\"a\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "catch(e:EOFError)\r\n"
                + "{\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "}\r\n"
                + "}\r\n",
                 false);
    }

    @Test
    public void testWhileTry2() {
        decompileMethod("classic", "testWhileTry2", "var i:*;\r\n"
                + "var j:* = undefined;\r\n"
                + "for(i = 0; i < 100; i++)\r\n"
                + "{\r\n"
                + "try\r\n"
                + "{\r\n"
                + "for(j = 0; j < 20; j++)\r\n"
                + "{\r\n"
                + "trace(\"a\");\r\n"
                + "}\r\n"
                + "}\r\n"
                + "catch(e:EOFError)\r\n"
                + "{\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "catch(e:Error)\r\n"
                + "{\r\n"
                + "continue;\r\n"
                + "}\r\n"
                + "trace(\"after_try\");\r\n"
                + "}\r\n"
                + "trace(\"end\");\r\n",
                 false);
    }

    @Test
    public void testXml() {
        decompileMethod("classic", "testXml", "var list:Vector.<int> = null;\r\n"
                + "var i:int = 0;\r\n"
                + "var g:XML = null;\r\n"
                + "var testCdata:XML = null;\r\n"
                + "var testComment:XML = null;\r\n"
                + "var xtaga:String = null;\r\n"
                + "var xtagb:String = null;\r\n"
                + "var xattrname:String = null;\r\n"
                + "var xattrval:String = null;\r\n"
                + "var xcontent:String = null;\r\n"
                + "var xxx:XML = null;\r\n"
                + "var m:XMLList = null;\r\n"
                + "var name:String = \"ahoj\";\r\n"
                + "var myXML:XML = <order id=\"604\">\r\n"
                + "<book isbn=\"12345\">\r\n"
                + "<title>{name}</title>\r\n"
                + "</book>\r\n"
                + "</order>;\r\n"
                + "var k:* = myXML.@id;\r\n"
                + "var all:String = myXML.@*.toXMLString();\r\n"
                + "k = myXML.book;\r\n"
                + "k = myXML.book.(@isbn == \"12345\");\r\n"
                + "list = new Vector.<int>();\r\n"
                + "i = int(Math.random());\r\n"
                + "list[i] = myXML.book.(@isbn == i + 1);\r\n"
                + "g = <script>\r\n"
                + "<![CDATA[\r\n"
                + "function() {\r\n"
                + "\r\n"
                + "FBAS = {\r\n"
                + "\r\n"
                + "setSWFObjectID: function( swfObjectID ) {\r\n"
                + "FBAS.swfObjectID = swfObjectID;\r\n"
                + "},\r\n"
                + "\r\n"
                + "init: function( opts ) {\r\n"
                + "FB.init( FB.JSON.parse( opts ) );\r\n"
                + "\r\n"
                + "FB.Event.subscribe( 'auth.sessionChange', function( response ) {\r\n"
                + "FBAS.updateSwfSession( response.session );\r\n"
                + "} );\r\n"
                + "},\r\n"
                + "\r\n"
                + "setCanvasAutoResize: function( autoSize, interval ) {\r\n"
                + "FB.Canvas.setAutoResize( autoSize, interval );\r\n"
                + "},\r\n"
                + "\r\n"
                + "setCanvasSize: function( width, height ) {\r\n"
                + "FB.Canvas.setSize( { width: width, height: height } );\r\n"
                + "},\r\n"
                + "\r\n"
                + "login: function( opts ) {\r\n"
                + "FB.login( FBAS.handleUserLogin, FB.JSON.parse( opts ) );\r\n"
                + "},\r\n"
                + "\r\n"
                + "addEventListener: function( event ) {\r\n"
                + "FB.Event.subscribe( event, function( response ) {\r\n"
                + "FBAS.getSwf().handleJsEvent( event, FB.JSON.stringify( response ) );\r\n"
                + "} );\r\n"
                + "},\r\n"
                + "\r\n"
                + "handleUserLogin: function( response ) {\r\n"
                + "if( response.session == null ) {\r\n"
                + "FBAS.updateSwfSession( null );\r\n"
                + "return;\r\n"
                + "}\r\n"
                + "\r\n"
                + "if( response.perms != null ) {\r\n"
                + "// user is logged in and granted some permissions.\r\n"
                + "// perms is a comma separated list of granted permissions\r\n"
                + "FBAS.updateSwfSession( response.session, response.perms );\r\n"
                + "} else {\r\n"
                + "FBAS.updateSwfSession( response.session );\r\n"
                + "}\r\n"
                + "},\r\n"
                + "\r\n"
                + "logout: function() {\r\n"
                + "FB.logout( FBAS.handleUserLogout );\r\n"
                + "},\r\n"
                + "\r\n"
                + "handleUserLogout: function( response ) {\r\n"
                + "swf = FBAS.getSwf();\r\n"
                + "swf.logout();\r\n"
                + "},\r\n"
                + "\r\n"
                + "ui: function( params ) {\r\n"
                + "obj = FB.JSON.parse( params );\r\n"
                + "method = obj.method;\r\n"
                + "cb = function( response ) { FBAS.getSwf().uiResponse( FB.JSON.stringify( response ), method ); }\r\n"
                + "FB.ui( obj, cb );\r\n"
                + "},\r\n"
                + "\r\n"
                + "getSession: function() {\r\n"
                + "session = FB.getSession();\r\n"
                + "return FB.JSON.stringify( session );\r\n"
                + "},\r\n"
                + "\r\n"
                + "getLoginStatus: function() {\r\n"
                + "FB.getLoginStatus( function( response ) {\r\n"
                + "if( response.session ) {\r\n"
                + "FBAS.updateSwfSession( response.session );\r\n"
                + "} else {\r\n"
                + "FBAS.updateSwfSession( null );\r\n"
                + "}\r\n"
                + "} );\r\n"
                + "},\r\n"
                + "\r\n"
                + "getSwf: function getSwf() {\r\n"
                + "return document.getElementById( FBAS.swfObjectID );\r\n"
                + "},\r\n"
                + "\r\n"
                + "updateSwfSession: function( session, extendedPermissions ) {\r\n"
                + "swf = FBAS.getSwf();\r\n"
                + "extendedPermissions = ( extendedPermissions == null ) ? '' : extendedPermissions;\r\n"
                + "\r\n"
                + "if( session == null ) {\r\n"
                + "swf.sessionChange( null );\r\n"
                + "} else {\r\n"
                + "swf.sessionChange( FB.JSON.stringify( session ), FB.JSON.stringify( extendedPermissions.split( ',' ) ) );\r\n"
                + "}\r\n"
                + "}\r\n"
                + "};\r\n"
                + "}\r\n"
                + "]]>\r\n"
                + "</script>;\r\n"
                + "testCdata =  <![CDATA[\r\n"
                + "hello from cdata;\r\n"
                + "function(){\r\n"
                + "here some code;\r\n"
                + "}\r\n"
                + "]]>;\r\n"
                + "testComment =  <!-- myXML comment-->;\r\n"
                + "xtaga = \"a\";\r\n"
                + "xtagb = \"b\";\r\n"
                + "xattrname = \"attr\";\r\n"
                + "xattrval = \"value\";\r\n"
                + "xcontent = \"content\";\r\n"
                + "xxx = <{xtaga}>\r\n"
                + "<{xtagb}>\r\n"
                + "<ul>\r\n"
                + "<li>Item 1</li>\r\n"
                + "<li {xattrname}=\"val\" attr2={xattrval}>Item 2: {xcontent}</li>\r\n"
                + "<?processinstr testvalue ?>\r\n"
                + "<!--\r\n"
                + "comment\r\n"
                + "-->\r\n"
                + "</ul>\r\n"
                + "</{xtagb}>\r\n"
                + "</{xtaga}>;\r\n"
                + "m = myXML.*;\r\n",
                 false);
    }
}
