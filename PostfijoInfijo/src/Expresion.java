import java.util.Stack;

/** Clase creada para Tarea 3 CC3001-02 FCFM Universidad de Chile
 * @author Bastián Inostroza. RUT 18.767.577-4 eMail: bastian.inostroza@gmail.com */
public class Expresion {
	String info;
	Expresion izq, der;
	
	/** Constructor de la forma de arbol pseudo binario de la clase Expresion, para hacer el arbol a mano
	 * 
	 * @param String x
	 * @param Expresion hojaIzq
	 * @param Expresion  hojaDer
	 * @author Bastián Inostroza
	 */
	public Expresion(String x, Expresion hojaIzq, Expresion hojaDer){
		info = x;  izq = hojaIzq;   der = hojaDer;
	}
	
	private String[] opera={"+","-","*","/","_"};
	
	/** Constructor de la clase, toma una formula en post fijo (notacion polaca) y la transforma en un arbol que representa la formula,  
	 * la formula debe comenzar con al menos dos variables y terminar con un operador en todos los casos.
	 * @param formula
	 * @author Bastián Inostroza
	 */
	public Expresion(String formula){
		if( formula==null || formula.equals("") ) lanzaError("vacio");
		if( !esValido(formula) ) lanzaError("formato");
		String[] form = formula.split(" ");
		Stack <Expresion>numeros = new Stack<Expresion>();
		Stack<String> operador = new Stack<String>();
		Expresion arbolFormula;
		for(int i=0 ; i < form.length ; i++){  // mete el numero en el Stack correspondiente
			if( form[i].matches("[a-zA-Z]+") ){
				Expresion numAux = new Expresion(form[i],null,null);
				numeros.push(numAux);
			}
			if( form[i].equals("+") ){  // Crea el pseudo arbol para + ------------
				operador.push("+");
				Expresion a=null,b=null ;
				if( !numeros.empty() ) a = numeros.pop();
				//else lanzaError("Invalido");
				if( !numeros.empty() ) b = numeros.pop();
				//else lanzaError("Invalido");
				arbolFormula = new Expresion(operador.pop(), b, a);
				numeros.push(arbolFormula);
			}
			if( form[i].equals("-") ){       // Crea el pseudo arbol para -  ------------
				operador.push("-");
				Expresion a=null,b=null ;
				if( !numeros.empty() ) a = numeros.pop();
				else lanzaError("Invalido");
				if( !numeros.empty() ) b = numeros.pop();
				else lanzaError("Invalido");
				arbolFormula = new Expresion(operador.pop(), b, a);
				numeros.push(arbolFormula);
			}
			if( form[i].equals("_") ){   // Crea el pseudo arbol para _  , una sola hoja (izquierda) ------------
				operador.push("_");
				Expresion a=null;
				if( !numeros.empty() ) a = numeros.pop();
				else lanzaError("Invalido");
				arbolFormula = new Expresion(operador.pop(), a, null);
				numeros.push(arbolFormula);
			}
			if( form[i].equals("*") ){    // Crea el pseudo arbol para * ------------
				operador.push("*");
				Expresion a=null,b=null ;
				if( !numeros.empty() ) a = numeros.pop();
				else lanzaError("Invalido");
				if( !numeros.empty() ) b = numeros.pop();
				else lanzaError("Invalido");
				arbolFormula = new Expresion(operador.pop(), b, a);
				numeros.push(arbolFormula);
			}
			if( form[i].equals("/") ){      // Crea el pseudo arbol para / ------------
				operador.push("/");
				Expresion a=null,b=null ;
				if( !numeros.empty() ) a = numeros.pop();
				else lanzaError("Invalido");
				if( !numeros.empty() ) b = numeros.pop();
				else lanzaError("Invalido");
				arbolFormula = new Expresion(operador.pop(), b,a);
				numeros.push(arbolFormula);
			}	
		}
		Expresion result = numeros.pop(); //el primer elemento al final del ciclo es la cabeza del arbol correspondiente a la formula
		info = result.info;
		izq = result.izq;
		der = result.der;
	}

	public static boolean esValido(String formula){
		if(formula==null || formula.equals("")) return false;
		if( formula.matches("(.*)[a-zA-Z]+(.*)") ){
			if( formula.matches("(.*)[+](.*)")	|| formula.matches("(.*)[-](.*)") || formula.matches("(.*)[*](.*)") || formula.matches("(.*)[/](.*)") || formula.matches("(.*)[_](.*)") ){
				String[] array = formula.split(" ");
				if( array.length < 3 ) return false;
				if( array[0].matches("(.*)[a-z]+(.*)") && array[1].matches("(.*)[a-z]+(.*)")){
					if(array[array.length-1].matches("(.*)[+](.*)") || array[array.length-1].matches("(.*)[-](.*)") ||array[array.length-1].matches("(.*)[*](.*)") || array[array.length-1].matches("(.*)[/](.*)") || array[array.length-1].matches("(.*)[_](.*)") ){
						return true;
					}
					else return false;
				}
				else return false;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}

	/** Función que a partir de un objeto de la clase Expresion genera un string con la formula en in-fijo
	 * 
	 * @return String formula
	 * @author Bastian Inostroza
	 */
	public String toSimpleString(){
		String resultado = null;
		String vacio = null;
		if (this.info==null) return vacio;
		Expresion aux = this;
		if(aux.izq!=null && aux.der!=null){  //  ES UN OPERADOR
			resultado = "("+aux.izq.toSimpleString()+aux.info+aux.der.toSimpleString()+")";
		}
		else if( aux.info.equals("_") && aux.der==null && aux.izq.info!=null ){  // ES MENOS UNARIO
			resultado = "-("+aux.izq.toSimpleString()+")";
		}
		else if( aux.izq==null && aux.der==null ){ //ES UNA VARIABLE
			resultado = aux.info;
		}
		return resultado;
	}
	
	public String toString(){
		String resultado = null;
		String vacio = null;
		if (this.info==null) return vacio;
		Expresion aux = this;
		if(aux.izq!=null && aux.der!=null){  //  ES UN OPERADOR NO _
			if (aux.izq.esOperador() && aux.der.esOperador()){
				if(prioridad(aux.info)>prioridad(aux.izq.info) && prioridad(aux.info)>prioridad(aux.der.info)){
					resultado = "("+aux.izq.toString()+")"+aux.info+"("+aux.der.toString()+")";
				}
				else if(prioridad(aux.info)>prioridad(aux.der.info)){
					resultado = aux.izq.toString()+aux.info+"("+aux.der.toString()+")";
				}
				else if(prioridad(aux.info)>prioridad(aux.izq.info)){
					resultado = "("+aux.izq.toString()+")"+aux.info+aux.der.toString();
				}
				else{ // los dos operadores tienen prioridad igual o menor
					resultado = aux.izq.toString()+aux.info+aux.der.toString();
				}
			}
			else if ( aux.izq.esOperador() && !aux.der.esOperador() ){
				if( prioridad(aux.info)>prioridad(aux.izq.info) ){
					resultado = "("+aux.izq.toString()+")"+aux.info+aux.der.toString();
				}
				else resultado = aux.izq.toString()+aux.info+aux.der.toString();
			}
			else if ( aux.der.esOperador() && !aux.izq.esOperador() ){
				if( prioridad(aux.info)>prioridad(aux.der.info) ){
					resultado = aux.izq.toString()+aux.info+"("+aux.der.toString()+")";
				}
				else resultado = aux.izq.toString()+aux.info+aux.der.toString();
			}
			else resultado = aux.izq.toString()+aux.info+aux.der.toString();
		}
		else if( aux.info.equals("_") && aux.der==null && aux.izq.info!=null ){  // ES MENOS UNARIO
			resultado = "-("+aux.izq.toString()+")";
		}
		else if( aux.izq==null && aux.der==null ){ //ES UNA VARIABLE
			resultado = aux.info;
		}
		return resultado;
	}
	
	private boolean esOperador(){
		String aux = this.info;
		if( aux.matches("(.*)[a-zA-Z](.*)") ) return false;
		else return true;
	}
	
	/** Entrega la prioridad de los operadores + - * / _ , 2 es la mayor prioridad,
	 *  y 0 es la mayor prioridad, en caso de Error entrega -1. 
	 *  @author Bastián Inostroza */
	private int prioridad(String operador){
		if     (operador.equals(opera[0]) || operador.equals(opera[1])) return 0;
		else if(operador.equals(opera[2]) || operador.equals(opera[3]))	return 1;
		else if(operador.equals(opera[4])) return 2;
		else return -1;
	}
	
 	private void lanzaError(String error){
		if (error.equals("Invalido")){
			System.out.println("ERROR: La formula es invalida/mal escrita");
		}
		if (error.equals("formato")){
			System.out.println("ERROR: La formula es invalida, o posee caracteres no soportados, ");
			System.out.println("la formula solo puede contener letras como variables, y los ");
			System.out.println("operadores + - * / _ (menos unario).");
		}
		if (error.equals("vacio")){
			System.out.println("ERROR: String vacío.");
		}
		System.exit(-1);
	}
}