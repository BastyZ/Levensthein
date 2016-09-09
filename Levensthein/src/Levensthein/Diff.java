package Levensthein;

import java.io.*;
import java.util.Scanner;

public class Diff {
	
	public static void main(String[]args){
		String ficheroA = "A.txt";
		String ficheroB = "B.txt";
		//String ficheroA = args[0];
		//String ficheroB = args[1];
		String lineaA = null;
		String lineaB = null;
		Scanner readA = null;
		Scanner readB = null;
		readA = new Scanner(ficheroA);
		readB = new Scanner(ficheroB);
		int largoA = largoFichero(ficheroA);
		int largoB = largoFichero(ficheroB);
		int result = 0;
		try{	// Quiero que A sea igual a B, leo cada linea, aplico levensthein
				// a cada linea y tomo una desicion
			
			if(largoA < largoB){ // Avanzo hasta el fin de A y voy tomando decisiones
				for(int i = 1; i <= largoA; i++){
					lineaA = readA.nextLine();
					lineaB = readB.nextLine();
					if ( lineaA.equals(lineaB) ) System.out.println("Lineas iguales");
					else {
						System.out.println("Sustituir"+lineaA+"por"+lineaB);
						result++;
					}
				}
				// Agregar los que faltan
				for( int i = largoA+1; i <= largoB; i++){
					System.out.println("Agregar:"+lineaB);
					result++;
				}
			} //------------------END---largoA < largoB--------------------------------
			if(largoA > largoB){ //Avanzo hasta el fin de B y luego elimino lo sobrante
				for(int i = 1; i <= largoB; i++){
					lineaA = readA.nextLine();
					lineaB = readB.nextLine();
					if ( lineaA.equals(lineaB) ) System.out.println("Lineas iguales, no realizar acciones");
					else {
						System.out.println("Sustituir"+lineaA+"por"+lineaB);
						result++;
					}
				}
				// Eliminar los que sobran
				for( int i = largoB+1; i <= largoA; i++){
					System.out.println("Eliminar:"+lineaA);
					result++;
				}
			}
		// TRY END
		} catch(Exception e){
			System.out.println("Error:"+e.getMessage());
		}
		readA.close();
		readB.close();
		System.out.print("Se deben hacer "+result+" modificaciones");
	}
	
	@SuppressWarnings({ "resource", "unused" })
	private static int largoFichero(String fichero){
		int largo=0;
		String linea;
		try{
			FileReader fr = new FileReader(fichero);
			BufferedReader br;
			br = new BufferedReader(fr);
	    	while((linea = br.readLine()) != null){
	    		largo+=1;
	    		}
			}
	    catch(Exception Ce){
	    	System.out.println("Error en el fichero"+fichero+":"+Ce);
	    }
		return largo;
	} 

}