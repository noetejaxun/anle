package sample;
import static sample.Tokens.*;
%%
%class Lexer
%type Tokens
espacio=[ ,\r]+

Whitespace  = [ ,\r]+
Number      = [0-9]+
Variable    = [a-zA-Z]
Variable_F  = [a-z]
Trig        = ("sin"|"cos"|"tan"|"csc"|"sec"|"cot")


%{
    public String lexeme;
%}
%%

/* Salto de linea */

( "f'("{Variable_F}")" | "d/d"{Variable_F} )    { lexeme=yytext(); return Derivada; }

( "=" )                                     { lexeme=yytext(); return Asignacion; }

( "\n" )                                    {return Linea;}

( {Trig} )                                  { lexeme=yytext(); return F_Trigonometrica; }

("(-"{Number}+")")|{Number}+                { lexeme=yytext(); return Numero; }

( "e" )                                     { lexeme=yytext(); return N_Euler; }

({Variable})                                { lexeme=yytext(); return Variable; }

( "Lim" | "lim" | "LIM" )                   { lexeme=yytext(); return Limite; }

( "->" )                                    { lexeme=yytext(); return Tiende_A; }

( "log" )                                   { lexeme=yytext(); return Logaritmo; }

( "ln"  )                                   { lexeme=yytext(); return Logaritmo_Natural; }

( "sqrt"  )                                 { lexeme=yytext(); return Raiz; }

{ Whitespace }                              { /* ignore */ }

( "("   )                                   { lexeme=yytext(); return Parentesis_Apertura; }

( ")"   )                                   { lexeme=yytext(); return Parentesis_Cierre; }

("["    )                                   { lexeme=yytext(); return Corchete_Apertura; }

( "]"   )                                   { lexeme=yytext(); return Corchete_Apertura; }

( "+"   )                                   { lexeme=yytext(); return Suma; }

( "-"   )                                   { lexeme=yytext(); return Resta; }

( "*"   )                                   { lexeme=yytext(); return Multiplicacion; }

( "/"   )                                   { lexeme=yytext(); return División; }

( "^"   )                                   { lexeme=yytext(); return Exponenciacion; }

( ","   )                                   { lexeme=yytext(); return Coma; }

/* Error de analisis */
 . {return ERROR;}