package threads;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordsCounterThread extends Thread{

    private File file;

    public WordsCounterThread(File file){
        this.file = file;
    }

    @Override
    public void run(){

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
            System.out.println(e.getMessage());
        }

    }
}


