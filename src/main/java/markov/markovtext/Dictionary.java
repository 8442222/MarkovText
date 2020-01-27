package markov.markovtext;

import java.util.*;

public class Dictionary {
    private final HashMap<String, WordData> words = new HashMap<>();
    private final Random random = new Random();
    private Integer totalCount = 0;

    public void register( String word ) {
        WordData wordData;
        if( words.containsKey( word ) ) {
            wordData = words.get( word );
            wordData.incrementCount();
            words.replace( word, wordData );
        } else {
            wordData = new WordData();
            words.put( word, wordData );
        }
        totalCount++;
    }

    public void calculateProbability() {
        for( Map.Entry<String, WordData> entry : words.entrySet() ) {
            WordData wordData = entry.getValue();
            wordData.setProbability( wordData.getCount() * 100.0 / totalCount );
            entry.setValue( wordData );
        }
    }

    public String generateWord() {
        String result = null;
        int index = random.nextInt( totalCount );
        int calcIndex = 0;
        for( Map.Entry<String, WordData> entry : words.entrySet() ) {
            int entryCount = entry.getValue().getCount();
            if( calcIndex <= index && index < calcIndex + entryCount ) {
                result = entry.getKey();
                break;
            } else {
                calcIndex += entryCount;
            }
        }
        return result;
    }

    public LinkedList<String> generateWords( Integer count ) {
        LinkedList<String> generatedWords = new LinkedList<>();
        for( int i = 0; i < count; i++ ) {
            generatedWords.add( generateWord() );
        }
        return generatedWords;
    }

    public void print() {
        System.out.println( "Dictionary unique words=" + words.size() + " total words=" + totalCount );
        words.entrySet().forEach( e -> System.out.print( e + " " ) );
    }
}
