/*
    Copyright 2000-2011 Francois de Bertrand de Beuvron

    This file is part of UtilsBeuvron.

    UtilsBeuvron is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UtilsBeuvron is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UtilsBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.insa.beuvron.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil implements java.io.Serializable { static final long serialVersionUID =30101L;

  public static String valueOf(int[] tabInt) {
    StringBuffer res = new StringBuffer("[");
    for (int i = 0 ; i < tabInt.length; i ++) {
      res.append(tabInt[i]);
      if (i < tabInt.length - 1) {
        res.append(" ");
      }
    }
    res.append("]");
    return res.toString();
  }

  /**
   *
   * @return true iff the string is a standard java identifier : ie begin whith
   * a {@link java.lang.Character#isJavaIdentifierStart(char ch)} and continue whith
   *  {@link java.lang.Character#isJavaIdentifierPart(char ch)}
   */
  public static boolean isSimpleSymbol(String s) {
    boolean res = s.length() > 0 && Character.isJavaIdentifierStart(s.charAt(0));
    int i = 1;
    while (res && i < s.length()) {
      res = Character.isJavaIdentifierPart(s.charAt(i));
      i ++;
    }
    return res;
  }

  /**
   * write a symbol without " if it is a valid Java identifier, with " aroud, and
   * with rewriting of the escape characters
   *
   * @param s the initial string
   * @return s written as a canonical symbol
   */
  public static String getCanonicalSymbol(String s) {
    if (isSimpleSymbol(s)) {
      return s;
    }
    else {
      StringBuffer res = new StringBuffer("\"");
      for (int i = 0 ; i < s.length(); i ++) {
        char cur = s.charAt(i);
        if (cur == '\n') {
          res.append("\\n");
        }
        else if (cur == '\r') {
          res.append("\\r");
        }
        else if (cur == '\t') {
          res.append("\\t");
        }
        else if (cur == '\b') {
          res.append("\\b");
        }
        else if (cur == '\f') {
          res.append("\\f");
        }
        else if (cur == '"') {
          res.append("\\\"");
        }
        else if (cur == '\'') {
          res.append("\\\'");
        }
        else if (cur == '\\') {
          res.append("\\\\");
        }
        else if (cur == ' ') {
          res.append(" ");
        }
        else if (Character.isJavaIdentifierPart(cur)) {
          res.append(cur);
        }
        else {
          res.append("\\u");
          String code = "0000" + Integer.toHexString((int) cur);
          code = code.substring(code.length()-4);
          res.append(code);
        }
      }
      res.append("\"");
      return res.toString();
    }
  }


  /**
   * parse a string as if by the java compiler.
   * @param s the initial string
   * @return s with all escape characters interpreted
   */
  public static String parse(String s) throws StringFormatException {
    if (s.length() < 2 || s.charAt(0) != '"' || s.charAt(s.length()-1) != '"') {
      throw new StringFormatException("should begin and end with \"");
    }
    StringBuffer res = new StringBuffer();
    for (int i = 1 ; i < s.length()-1; i ++) {
      char cur = s.charAt(i);
      if (cur != '\\') {
        res.append(cur);
      }
      else {
        i ++;
        if (i >= s.length()) {
          throw new StringFormatException("invalid escape sequence \\ alone");
        }
        cur = s.charAt(i);
        char pred = cur;
        if (cur == 'n') {
          res.append("\n");
        }
        else if (cur == 'r') {
          res.append("\r");
        }
        else if (cur == 't') {
          res.append("\t");
        }
        else if (cur == 'b') {
          res.append("\b");
        }
        else if (cur == 'f') {
          res.append("\f");
        }
        else if (cur == '"') {
          res.append("\"");
        }
        else if (cur == '\'') {
          res.append("\'");
        }
        else if (cur == '\\') {
          res.append("\\");
        }
        else if (cur == 'u') {
          while (i < s.length() && s.charAt(i) == 'u') {
            i ++;
          }
          if (i+3 >= s.length()) {
            throw new StringFormatException("invalid escape sequence \\u without 4 hex digits");
          }
          int hexVal = 0;
          try {
            hexVal = Integer.parseInt(s.substring(i,i+4),16);
          }
          catch (NumberFormatException e) {
            throw new StringFormatException("invalid escape sequence invalid hex digits after \\u");
          }
          i=i+3;
          res.append((char) hexVal);
        }
        else if (cur >= '0' && cur <= '3') {
          int octalVal = (int) cur;
          i ++;
          if (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '7') {
            octalVal = octalVal * 8 + ((int) s.charAt(i));
            i ++;
            if (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '7') {
              octalVal = octalVal * 8 + ((int) s.charAt(i));
            }
          }
          res.append((char) octalVal);
        }
        else if (cur >= '4' && cur <= '7') {
          int octalVal = (int) cur;
          i ++;
          if (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '7') {
            octalVal = octalVal * 8 + ((int) s.charAt(i));
            i ++;
          }
          res.append((char) octalVal);
        }
        else {
          throw new StringFormatException("invalid escape sequence \\"+pred);
        }
      }
    }
    return res.toString();
  }

  /**
   * the concatenation of s with itself nbg times.
   * @param s
   * @param nbr
   * @return the concatenation of s with itself nbg times.
   */
  public static String mult(String s,int nbr) {
    StringBuffer res = new StringBuffer();
    for (int i = 0 ; i < nbr ; i ++) {
      res.append(s);
    }
    return res.toString();
  }
  
  public static String padLeft(String s,int largeur) {
      return mult(" ",largeur-s.length())+s;
  }

  public static String padRight(String s,int largeur) {
      return s+mult(" ",largeur-s.length());
  }

  /**
   * put indenter at the begining of each line in s
   * @param s
   * @param indenter
   * @return indented s
   */
  public static String specialIndent(String s, String indenter) {
    StringBuffer res = new StringBuffer();
    boolean beginLine = true;
    for (int i = 0 ; i < s.length() ; i ++) {
      if (beginLine) {
        res.append(indenter);
        beginLine = false;
      }
      char cur = s.charAt(i);
      res.append(cur);
      if (cur == '\n') {
        beginLine = true;
      }
    }
    return res.toString();
  }

  /**
   * add spaces before each line in s
   * @param s the original string
   * @param nbrSpace the number of space to insert at the begining of each line in s
   * @return indented s
   */
  public static String indent(String s,int nbrSpace) {
    String spaces = mult(" ",nbrSpace);
    return specialIndent(s,spaces);
  }

  //==================================== some utilities
  /**
   * replace <return> ("\n") with " <BR>\n" in string (to produce HTML)
   */
  public static String replaceReturnBR(String in) {
    StringBuffer res = new StringBuffer();
    for (int i = 0 ; i < in.length() ; i ++) {
      if (in.charAt(i) == '\n') {
        res.append(" <BR>\n");
      }
      else {
        res.append(in.charAt(i));
      }
    }
    return res.toString();
  }

  /**
   * add " and + that allow to define a text as a string in a java source file.
   * Attention : ne fonctionne pas bien pour la dernière ligne du texte.
   */
  public static String fromTextToJavaSourceString(String text) {
     String res = text;
     res = res.replaceAll(Pattern.quote("\\"),Matcher.quoteReplacement("\\\\"));
     res = res.replaceAll(Pattern.quote("\""),Matcher.quoteReplacement("\\\""));
     StringBuilder res2 = new StringBuilder(res.length());
     res2.append('\"');
     for (int i = 0 ; i < res.length() ; i ++) {
         char c = res.charAt(i);
         if (c == '\n') {
             res2.append("\\n\"+\n\"");
         } else {
             res2.append(c);
         }
     }
     res2.append('\"');
     return res2.toString();
  }


}


