package service;

import exception.InvalidWordException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import threads.ConnectionPool;
import threads.WordsCounterThread;

import java.io.File;
import java.io.IOException;

public class MenuService {


    private ConnectionPool connectionPool;

    public MenuService(){
        String[] filePath = {"output.txt",
                             "output.txt",
                             "output.txt",
                             "output.txt",
                             "output.txt"};
        connectionPool = ConnectionPool.getInstance(filePath, 5);
    }
    public void writeFile (String input){

        File file = connectionPool.getConnection();

        if (file != null){
            try{
                FileUtils.writeStringToFile(file, input + System.lineSeparator(), "UTF-8", true);
                System.out.println("File written");
                connectionPool.getConnectionBack(file);
            } catch (IOException e){
                System.out.println("Error during file writing");
                e.printStackTrace();
            }
        } else {
            System.out.println("There are no connections available");
        }
    }


    public void readFile(String path){

        try{
            File file = new File(path);
            String fileContent = FileUtils.readFileToString(file,"UTF-8");
            System.out.println(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countLettersInFile(File file){

        Runnable task = () -> {
            try {
                String fileContent = FileUtils.readFileToString(file,"UTF-8");
                String cleanText = StringUtils.remove(fileContent, ' ');
                cleanText = StringUtils.remove(cleanText, '\n');
                int letters = cleanText.length();
                System.out.println("The file contains " + letters + " letters");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }



    public void uniqueWords(File file) {
        WordsCounterThread counter = new WordsCounterThread(file);
        counter.start();
    }

    public void searchWordInFile(File file, String word) {
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
                System.out.println("The word " + word +" appears " + wordCounter +" times in the file");
            } else {
                throw new InvalidWordException("Invalid word. it should have at least 2 letters");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidWordException e) {
            System.out.println(e.getMessage());
        }

    }
}
