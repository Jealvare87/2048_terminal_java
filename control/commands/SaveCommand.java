package tp.p3.control.commands;


import tp.p3.exceptions.*;
import tp.p3.logic.multigames.Game;
import java.io.File;
import java.util.Scanner;
import tp.p3.util.MyStringUtils;

public class SaveCommand extends NoParamsCommand{
	private boolean filename_confirmed;
	public static final String filenameInUseMsg = "The file already exists ; do you want to overwrite it ? (Y/N)";
	private String nameFile;

	public SaveCommand() {
		super("save", "saves the actual game in a text file.");
	}
	
	public SaveCommand(String x) {
		super("save", "saves the actual game in a text file.");
		this.nameFile = x;
	}
	
	public boolean execute(Game game) throws ExecuteException{
		
			if(game.storeGame(this.nameFile) == true) {
				System.out.println("Game successfully saved to file; use load command to reload it. \n");
			}
			else {
				throw new ExecuteException("Something went wrong. \n");
			}
		return false;
	}

	
	public Command parse(String[] commandWords, Scanner in) throws ParseException{
		Command m = null;
		if(commandWords.length == 1) {
			throw new ParseException("Save must be followed by a filename. \\n");
		}
		else if((commandWords.length == 2)) {
			if(commandWords[0].equals("save")) {
				if(commandWords[1].length() > 4) {
					if(this.cumple(commandWords[1]) == true) {
						m = new SaveCommand(this.confirmFileNameStringForWrite(commandWords[1], in));
					}
					else {
						throw new ParseException("Invalid filename: the filename contains invalid characters. \\n");
					}
				}
				else {
					throw new ParseException("Invalid filename: the filename must be a '.txt'. \\n");
				}
			}
		}
		else {
			throw new ParseException("Invalid filename: the filename contains spaces. \\n");
		}
		return m;
	}

	
	//Sirve para comprobar si la segunda palabra es un archivo de texto válido
	private boolean cumple(String palabra) {
		boolean cumple = false;
		if((palabra.charAt(palabra.length() - 1) == 't') && (palabra.charAt(palabra.length() - 2) == 'x') && (palabra.charAt(palabra.length() - 3) == 't') && (palabra.charAt(palabra.length() - 4) == '.')) {
			cumple = true;
		}
		return cumple;
	}	
	
	/* This code supposes the following attribute declarations :
	*  	private boolean filename_confirmed;
	*	public static final filenameInUseMsg
	* 		= "The file already exists ; do you want to overwrite it ? (Y/N)";
	* 	You may also need to add a throws clause to the declarations .
	*/
	private String confirmFileNameStringForWrite(String filenameString, Scanner in) {
		String loadName = filenameString;
		filename_confirmed = false;
		try {
			while (!filename_confirmed) {
				if(MyStringUtils.validFileName(loadName)){////////////////////////
					File file = new File(loadName);
					if(!file.exists()) {
						filename_confirmed = true;
					}
					else{
						loadName = getLoadName(loadName, in);
						if(loadName == filenameString) {
							filename_confirmed = true;
						}
					}
				} 
				else {
					boolean espacio = false;
					int cont = 0;
					do {
						if(loadName.charAt(cont) == ' ') {
							espacio = true;
							System.out.println("Invalid filename: the filename contains spaces.");
						}
						else {
							cont++;
						}
					}while(espacio == false && cont < loadName.length());
					if(espacio == false) {
						System.out.println("Invalid filename: the filename contains invalid characters.");
					}
					loadName = null;
				}
			}
		}catch(NullPointerException e) {
			System.out.println("Invalid filename: the filename contains invalid characters" + e);
		}
	return loadName;
	}
	
	
	public String getLoadName(String filenameString, Scanner in) {
		String newFilename = null;
		boolean yesOrNo = false;
			while (!yesOrNo){
				System.out.print(filenameInUseMsg + ": ");
				String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
				if (responseYorN.length == 1) {
					switch (responseYorN[0]) {
					case "y":
						yesOrNo = true;
						newFilename = filenameString;
						break;
					case "n":
						yesOrNo = true;
						do {
							System.out.print("Please enter another filename: ");
							newFilename = in.nextLine();
						}while(this.cumple(newFilename) == false);
						//this.confirmFileNameStringForWrite(newFilename, in);
						break;
					default:
						System.out.println("Please answer ’Y’ or ’N’.");
						this.getLoadName(filenameString, in);
					}
				} 
				else {
					System.out.println("Please answer ’Y’ or ’N’.");
				}
			}
		return newFilename;
	}
}
