import java.util.Scanner;
import java.io.FileNotFoundException;

public class Tester {
	
	public static void main(String[] args) throws FileNotFoundException{
	    System.out.println("Escriba la expresion algebreaica en postfijo:");
	    Scanner s = new Scanner(System.in);
	    String line = s.nextLine();
	    while(line != "") {
	    	if(line==null) break;
	    	else {
	    		infijo(line);
	    		line = s.nextLine();
	    	}
	    	}
	    System.out.println("Programa Finalizado ");
	    s.close();
	}

	private static void infijo(String formula){
		if (formula.equals("exit")) System.exit(-1);
		Expresion arbol = new Expresion(formula);
		System.out.print( arbol.toSimpleString() );
		System.out.print(" = ");
		System.out.println( arbol.toString() );
	}
}
