package sample;
import static sample.Tokens.*;
%%
%class Lexer
%type Tokens
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\r]+
%{
    public String lexeme;
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}
/* Comentarios */
( "'''"(.)* ) {/*Ignore*/}
( "#"(.)* ) {/*Ignore*/}
/* Salto de linea */
( "\n" ) {return Linea;}
/* Tabulación */
( "\t" ) {return Tabulacion;}
/* Comillas */
( "\"" ) {lexeme=yytext(); return Comillas_Dobles;}
( "'" ) {lexeme=yytext(); return Comillas_Simples;}

/* Tipos de datos */
( byte | int | char | long | float | double ) {lexeme=yytext(); return T_Dato;}
/* Tipo de dato String */
( String ) {lexeme=yytext(); return Cadena;}
/* Palabra reservada */
( "None" | "as" | "assert" | "async" | "await" | "break" | "class" | "continue" | "def" | "del" | "except" | "finally" | "from" | "global" | "import" | "in" | "is" | "lambda" | "nonlocal" | "pass" | "raise" | "return" | "try" | "with" | "yield" ) {lexeme=yytext(); return P_reservada;}

/* Palabra reservada If */
( if ) {lexeme=yytext(); return If;}
/* Palabra reservada Else */
( else ) {lexeme=yytext(); return Else;}
/* Palabra reservada Elif */
( elif ) {lexeme=yytext(); return Else_If;}
/* Palabra reservada Do */
( do ) {lexeme=yytext(); return Do;}
/* Palabra reservada While */
( while ) {lexeme=yytext(); return While;}
/* Palabra reservada For */
( for ) {lexeme=yytext(); return For;}
/* Operador Igual */
( "=" ) {lexeme=yytext(); return Igual;}
/* Operador Suma */
( "+" ) {lexeme=yytext(); return Suma;}
/* Operador Resta */
( "-" ) {lexeme=yytext(); return Resta;}
/* Operador Multiplicacion */
( "*" ) {lexeme=yytext(); return Multiplicacion;}
/* Operador Division */
( "/" ) {lexeme=yytext(); return Division;}
/* Operador Division Entera */
( "//" ) {lexeme=yytext(); return Division_Entera;}
/* Operador Módulo */
( "%" ) {lexeme=yytext(); return Modulo;}
/* Operador Exponente */
( "**" ) {lexeme=yytext(); return Exponente;}
/* Operadores logicos */
( "and" | "or" | "not" ) {lexeme=yytext(); return Op_logico;}
/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<=" ) {lexeme = yytext(); return Op_relacional;}
/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "**=" | "//=" | "%=" ) {lexeme = yytext(); return Op_asignacion;}
/*Operadores Booleanos*/
( true | false ) {lexeme = yytext(); return Op_booleano;}

/* Parentesis de apertura */
( "(" ) {lexeme=yytext(); return Parentesis_a;}
/* Parentesis de cierre */
( ")" ) {lexeme=yytext(); return Parentesis_c;}
/* Llave de apertura */
( "{" ) {lexeme=yytext(); return Llave_a;}
/* Llave de cierre */
( "}" ) {lexeme=yytext(); return Llave_c;}
/* Corchete de apertura */
( "[" ) {lexeme = yytext(); return Corchete_a;}
/* Corchete de cierre */
( "]" ) {lexeme = yytext(); return Corchete_c;}
/* Marcador de inicio de algoritmo
( "main" ) {lexeme=yytext(); return Main;} */
/* Punto y coma
( ";" ) {lexeme=yytext(); return P_coma;} */
/* Identificador */
{L}({L}|{D})* {lexeme=yytext(); return Identificador;}
/* Numero */
("(-"{D}+")")|{D}+ {lexeme=yytext(); return Numero;}
/* Error de analisis */
 . {return ERROR;}