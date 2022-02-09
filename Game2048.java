package tp.p3;

import java.util.Scanner;
import java.util.Random;
import tp.p3.logic.multigames.Game;
import tp.p3.control.Controller;
import tp.p3.logic.multigames.Rules2048;

public class Game2048 {	
	
	public static void main(String [] args){
		try {
		long seed = new Random().nextInt(Integer.parseInt(args[2]));		
		Scanner sc = new Scanner (System.in);	
		Game game = new Game(new Rules2048(), seed , Integer.parseInt(args[0]),Integer.parseInt(args[1])); // Crea el juego con el tamaño, celdas iniciales y el método random.
		//La primera vez que se juega, crea un tablero 2048 original respecto a lo estipulado en el enunciado de la práctica
		Controller controller = new Controller(game, sc);
		game.setDefCells(Integer.parseInt(args[1]));
		game.setDefSeed(seed);
		game.setDefSize(Integer.parseInt(args[0]));
		
		/* * * * * * * * * * * * * * * * * * * * *
		 * Ejecuta la partida con el controlador.*
		 * * * * * * * * * * * * * * * * * * * * */
		 
		controller.run();
		sc.close();
		}catch(NumberFormatException e) {
        	System.out.println("The command-line arguments must be numbers" + e);
        }catch(NullPointerException e) {
        	System.out.println("One or more parameters were not introduced " + e);
        }catch(NegativeArraySizeException e) {
        	System.out.println("Parameters cant be negative." + e);
        }
	}
	
}