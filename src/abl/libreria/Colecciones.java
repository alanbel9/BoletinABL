package abl.libreria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Colecciones {

	public static void main(String[] args) {
		Colecciones c = new Colecciones();
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);list.add(2);list.add(3);list.add(4);list.add(5);
		
		//ArrayList<Integer> ej = c.getMultiplicaLista(list,3);
		ArrayList<String> ej = c.getNombres();
		for(String i : ej) {
			System.out.println(i );
		}
		
		
	}
	
	
	
	public static ArrayList<String> getNombres(){
		// agregar nombres al array.
        ArrayList<String> nombres = new ArrayList<String>();
        String txt = "a";
	    try {
			while(txt.length() != 0) {
				System.out.println("Introduce un nombre: " );
				Scanner scan = new Scanner(System.in);
				txt = scan.nextLine();
				txt = txt.trim();
				if(txt.length() != 0)
				nombres.add(txt);
			}
		}catch(InputMismatchException e) {
			txt ="";
		}
	    return nombres;
	}
	
	public static ArrayList<Integer> getMultiplicaLista( ArrayList<Integer> lista , int mult  ) 
	{
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		for( Integer i : lista)  numeros.add(i*mult);
		return numeros;
	}
	public static ArrayList<Byte> getMultiplicaLista( ArrayList<Byte> lista , byte mult  ) 
	{
		ArrayList<Byte> numeros = new ArrayList<Byte>();
		for( Byte i : lista)  numeros.add((byte)(i*mult));
		return numeros;
	}
	public static ArrayList<Short> getMultiplicaLista( ArrayList<Short> lista , short mult  ) 
	{
		ArrayList<Short> numeros = new ArrayList<Short>();
		for( Short i : lista)  numeros.add((short)(i*mult));
		return numeros;
	}
	public static ArrayList<Float> getMultiplicaLista( ArrayList<Float> lista , float mult  ) 
	{
		ArrayList<Float> numeros = new ArrayList<Float>();
		for( Float i : lista)  numeros.add((float)(i*mult));
		return numeros;
	}
	public static ArrayList<Double> getMultiplicaLista( ArrayList<Double> lista , double mult  ) 
	{
		ArrayList<Double> numeros = new ArrayList<Double>();
		for( Double i : lista)  numeros.add((double)(i*mult));
		return numeros;
	}
	
	
	public static ArrayList<Integer> getDobleLista( ArrayList<Integer> lista  ) {
		return getMultiplicaLista(lista , 2);
	}
	
	public static HashSet<Integer> getNoRepetidos(){
		HashSet<Integer> numeros = new HashSet<Integer>();
		int numero=-1;
		while(numero !=0) {
			numeros.add(Matematicas.getEntero());
		}
		return numeros;
	}
	
	public static ArrayList<Integer> getNumerosNoRepetidos()
	{
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		int numero = -1;
		while(numero != 0) {
			numero=Matematicas.getEntero();
			if(numeros.contains(numero) == false ) {
				numeros.add(numero);
			}
		}
		return numeros;
	}

	
	public static ArrayList<Integer> getNumerosPorTeclado() {
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		
		int numero = -1;
		while(numero != 0) {
			numero=Matematicas.getEntero();
			numeros.add(numero);
		}
		return numeros;
	
	}
	
	
	
	
	
	
	
	
	
	
}
