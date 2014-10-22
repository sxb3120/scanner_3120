package scanner_3120;
import java.io.*;
import java.util.*;

public class scanner_3120 
{

	static int charclass;
	static String lexeme;
	static char nextchar;
	static int lexlen;
	static int token;
	static int nexttoken;
	
	static final int LETTER = 0;
	static final int DIGIT = 1;
	static final int UNKNOWN = 99;
	static final int EOF = -1;

	static int INT_LIT = 10;
	static int IDENT = 11;
	static int ASSIGN_OP = 20;
	static int ADD_OP = 21;
	static int SUB_OP = 22;
	static int MULT_OP = 23;
	static int DIV_OP = 24;
	static int LEFT_PAREN = 25;
	static int RIGHT_PAREN = 26;
	static FileReader inputStream = null;


	public static void main(String args[]) throws IOException
	{
		try
		{
			inputStream = new FileReader("file.txt");
            getChar();
			do
			{
				lex();
			} while (nexttoken != EOF);
			 
		}
		catch(FileNotFoundException e)
		{
			
			System.out.println("File not found");
			System.out.println("Directory: "+ System.getProperty("user.dir"));
			return;
		}
		finally
		{
			if (inputStream != null)
			{
				try
				{
					inputStream.close();
				}
				catch(IOException e)
				{
					System.out.println("File close error.");
				}
			}
		}
		
	}

	public static void addchar()
	{
		if(lexlen <= 98)
		{
			lexeme = lexeme + nextchar;
			
		}
		else
			System.out.println("Error : Leeme is too Long");

	}


	public static void getChar() throws IOException
	{
		if((nextchar = (char)inputStream.read()) != EOF)
		{
			if (Character.isLetter(nextchar))
				charclass = LETTER;
			else if (Character.isDigit(nextchar))
				charclass = DIGIT;
			else charclass = UNKNOWN;
		}
		else
			charclass = EOF;

	}

	public static void getnonblank() throws IOException
	{
	   while(Character.isSpaceChar(nextchar))
	      getChar();
	}

	public static int lookup(char ch)
	{
	   switch(ch)
	   {
	       case '(': addchar();
			 nexttoken=LEFT_PAREN;
			 break;

	       case ')': addchar();
			 nexttoken=RIGHT_PAREN;
			 break;

	       case '+': addchar();
			 nexttoken=ADD_OP;
			 break;

	       case '-': addchar();
			 nexttoken=SUB_OP;
			 break;

	       case '*': addchar();
			 nexttoken=MULT_OP;
			 break;

	       case '/': addchar();
			 nexttoken=DIV_OP;
			 break;

	       default:  addchar();
			 nexttoken=EOF;
			 lexeme = "EOF";
			 break;
	   }
	   return nexttoken;
	}

	public static int lex() throws IOException
	{
	   lexlen=0;
	   lexeme = "";
	   getnonblank();
	   switch(charclass)
	   {
	      case LETTER: addchar();
			   getChar();
			   while(charclass == LETTER || charclass == DIGIT)
			   {
			      addchar();
			      getChar();
			   }
			   nexttoken=IDENT;
			   break;
	      case DIGIT:  addchar();
			   getChar();
			   while(charclass == DIGIT)
			   {
			      addchar();
			      getChar();
			   }
			   nexttoken=INT_LIT;
			   break;
	      case UNKNOWN: lookup(nextchar);
			    getChar();
			    break;
	      case EOF :   nexttoken=EOF;
			    lexeme = "EOF";
			    break;
			    
	   }
	   System.out.println("next token is:" + nexttoken + ", next lexeme is:" + lexeme);
	   return nexttoken;

	}

	
}
