import exception.InvalidWordException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuConsole {



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
                        writeFile(input);
                        break;
                    case Constants.READ_FILE:
                        System.out.println("Insert path of file to read: ");
                        String path = scanner.next();
                        readFile(path);
                        break;
                    case Constants.SEARCH_WORD:
                        System.out.println("Insert word to search in the file: ");
                        String wordToSearch = scanner.next();
                        searchWordInFile(new File("output.txt"), wordToSearch);
                        break;
                    case Constants.EXIT:
                        break;
                }
        } while (option != Constants.EXIT);
        countLettersInFile(new File("output.txt"));
        uniqueWords(new File("output.txt"));


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

    private void countLettersInFile(File file){

        try {
            String fileContent = FileUtils.readFileToString(file,"UTF-8");
            String cleanText = StringUtils.remove(fileContent, ' ');
            cleanText = StringUtils.remove(cleanText, '\n');
            int letters = cleanText.length();
            System.out.println("The file contains " + letters + " letters");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uniqueWords(File file) {

        try {
            String fileContent = FileUtils.readFileToString(file, "UTF-8");
            String[] wordsArray = StringUtils.split(fileContent);
            for (int i = 0; i<wordsArray.length; i++){
                wordsArray[i] = StringUtils.lowerCase(wordsArray[i]);
            }

            Map<String, Integer> wordCountMap = new HashMap<>();
            for (String word : wordsArray) {
                if (wordCountMap.containsKey(word)) {
                    wordCountMap.put(word, wordCountMap.get(word) + 1);
                } else {
                    wordCountMap.put(word, 1);
                }
            }
            int counter = 0;
            for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
                if (entry.getValue() == 1) {
                    counter++;
                }
            }

            System.out.println("There are " + counter + " unique words in the file.");
        } catch (IOException e) {

        }

    }

    private void searchWordInFile(File file, String word) {
        try{
            if(word.length() > 1){
                String fileContent = FileUtils.readFileToString(file, "UTF-8");
                String[] wordsArray = StringUtils.split(fileContent);
                int wordCounter = 0;
                for (String w : wordsArray){
                    if(w.equals(word)){
                        wordCounter ++;
                    }
                }
                System.out.println("The word " + word +" appears " + wordCounter +" in the file");
            } else {
                throw new InvalidWordException("Invalid word. it should have at leaset 2 letters");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidWordException e) {
            System.out.println(e.getMessage());
        }

    }
}
