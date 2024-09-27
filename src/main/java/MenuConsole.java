import service.MenuService;

import java.io.File;
import java.util.Scanner;

public class MenuConsole {

    MenuService service = new MenuService();

    public void displayMenu(){

        int option;
        Scanner scanner = new Scanner(System.in);
        do {
                System.out.println("--------- MENU ---------");
                System.out.println("1. Enter and write text");
                System.out.println("2. Read file");
                System.out.println("3. Search word in file");
                System.out.println("0. Exit");
                System.out.print("Select an option: ");
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case Constants.ENTER_TEXT:
                        System.out.print("Insert your text: ");
                        String input = scanner.nextLine();
                        service.writeFile(input);
                        break;
                    case Constants.READ_FILE:
                        System.out.println("Insert path of file to read: ");
                        String path = scanner.next();
                        service.readFile(path);
                        break;
                    case Constants.SEARCH_WORD:
                        System.out.println("Insert word to search in the file: ");
                        String wordToSearch = scanner.next();
                        service.searchWordInFile(new File("output1.txt"), wordToSearch);
                        break;
                    case Constants.EXIT:
                        break;
                }
        } while (option != Constants.EXIT);
        service.countLettersInFile(new File("output1.txt"));
        service.uniqueWords(new File("output1.txt"));
    }
}
