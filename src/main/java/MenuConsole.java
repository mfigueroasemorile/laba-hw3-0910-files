import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class MenuConsole {



    public void displayMenu(){

        int option;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("--------- MENU ---------");
            System.out.println("1. Enter and write text");
            System.out.println("2. Read file");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case Constants.ENTER_TEXT:
                    System.out.print("Insert your text: ");
                    String input = scanner.nextLine();
                    writeFile(input);
                    break;
                case Constants.READ_FILE:
                    System.out.println("Insert path of file to read: ");
                    String path = scanner.next();
                    readFile(path);
                    break;
                case Constants.EXIT:
                    break;
            }
        } while (option != Constants.EXIT);

    }

    private void writeFile (String input){
        try{
            File file = new File ("output.txt");
            FileUtils.writeStringToFile(file, input + System.lineSeparator(), "UTF-8", true);
            System.out.println("File written");
        } catch (IOException e){
            System.out.println("Error during file writing");
            e.printStackTrace();
        }

    }

    private void readFile(String path){

        try{
            File file = new File(path);
            String fileContent = FileUtils.readFileToString(file,"UTF-8");
            System.out.println(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
