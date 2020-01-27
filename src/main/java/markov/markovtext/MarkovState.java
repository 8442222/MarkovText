package markov.markovtext;

import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

@EqualsAndHashCode
public class MarkovState {
    private final LinkedList<String> words = new LinkedList<>();
    private final HashMap<String, Integer> nextWords = new HashMap<>();
    private final Random random = new Random();
    private Integer totalCount = 0;

    public MarkovState( LinkedList<String> words, String nextWord ) {
        this.words.addAll( words );
        Integer count = 1;
        nextWords.put( nextWord, count );
        totalCount++;
    }

    public void printState() {
        words.forEach( e -> System.out.print( e + " " ) );
        System.out.print( "--> " );
        nextWords.entrySet().forEach( e -> System.out.print( e + " " ) );
    }

    public void addNextWord( String word ) {
        Integer count = 1;
        if( nextWords.containsKey( word ) ) {
            count = nextWords.get( word );
            nextWords.replace( word, ++count );
        } else {
            nextWords.put( word, count );
        }
        totalCount++;
    }

    public boolean compareWords( LinkedList<String> buffer ) {
        boolean result = words.size() == buffer.size();
        if( result ) {
            for( int i = 0; i < words.size() && result; i++ ) {
                result = words.get( i ).equals( buffer.get( i ) );
            }
        }
        return result;
    }

    public String generateWord() {
        String result = null;
        int index = random.nextInt( totalCount );
        int calcIndex = 0;
        for( Map.Entry<String, Integer> entry : nextWords.entrySet() ) {
            int entryCount = entry.getValue();
            if( calcIndex <= index && index < calcIndex + entryCount ) {
                result = entry.getKey();
                break;
            } else {
                calcIndex += entryCount;
            }
        }
        return result;
    }
}
