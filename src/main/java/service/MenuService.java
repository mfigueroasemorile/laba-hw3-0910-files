package service;

import exception.InvalidWordException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import threads.ConnectionPool;
import threads.WordsCounterThread;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class MenuService {


    private ConnectionPool connectionPool;

    public MenuService(){

    }
    public void writeFile (String input){
        String[] filePath = {"output1.txt",
                "output2.txt",
                "output3.txt",
                "output4.txt",
                "output5.txt"};

        connectionPool = ConnectionPool.getInstance(filePath, 5); //pool 5 conec
        ExecutorService executor = Executors.newFixedThreadPool(7);//7 threads

        for (int i = 0; i<=7; i++){
            int threadId = i;
            executor.submit(() -> {
                try {
                    System.out.println("Thread " + threadId + " attempting to get a connection...");
                    File connection = connectionPool.getConnection(); //get connec

                    sleep(15000); // see the execution

                    connectionPool.getConnectionBack(connection); //conec back to available list
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        /*try{

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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/

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
