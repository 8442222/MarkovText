package markov.markovtext;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class MarkovModel {
    private Integer totalStates;
    private final List<MarkovState> states = new LinkedList<>();
    private final Dictionary dictionary = new Dictionary();

    private MarkovState findState( LinkedList<String> buffer ) {
        MarkovState markovState = null;
        for( MarkovState state : states ) {
            if( state.compareWords( buffer ) ) {
                markovState = state;
            }
        }
        return markovState;
    }

    public void printModel() {
        System.out.println( "Model size=" + states.size() );
        for( MarkovState state : states ) {
            state.printState();
            System.out.println();
        }
        dictionary.calculateProbability();
        dictionary.print();
    }

    public void readAndProcess( String fileName, Integer totalStates, Integer bufferSize ) throws IOException {
        this.totalStates = totalStates;
        WordsBuffer wordsBuffer = new WordsBuffer( fileName, totalStates, bufferSize, dictionary );
        if( wordsBuffer.fillBuffer() ) {
            do {
                MarkovState markovState = findState( wordsBuffer.getWords() );
                if( markovState != null ) {
                    markovState.addNextWord( wordsBuffer.getNextWord() );
                } else {
                    markovState = new MarkovState( wordsBuffer.getWords(), wordsBuffer.getNextWord() );
                    states.add( markovState );
                }
            } while( wordsBuffer.shiftWord() );
        }
    }

    public void generateWords( Integer totalOutputWords, String outputFileName ) throws IOException {
        StackToFile stackToFile = new StackToFile( outputFileName );
        LinkedList<String> generatedWordsBuffer;

        if( totalOutputWords <= totalStates ) {
            generatedWordsBuffer = dictionary.generateWords( totalOutputWords );
            stackToFile.saveGeneratedWords( generatedWordsBuffer );
        } else {
            generatedWordsBuffer = dictionary.generateWords( totalStates );
            stackToFile.saveGeneratedWords( generatedWordsBuffer );
            for( int i = totalStates; i < totalOutputWords; i++ ) {
                String nextWord = generateNextWord( generatedWordsBuffer );
                generatedWordsBuffer.removeFirst();
                generatedWordsBuffer.addLast( nextWord );
                stackToFile.saveWord( nextWord );
            }
        }
        stackToFile.closeWriter();
    }

    public String generateNextWord( LinkedList<String> previousNWords ) {
        String result;
        MarkovState markovState = findState( previousNWords );
        if( markovState == null ) {
            result = dictionary.generateWord();
        } else {
            result = markovState.generateWord();
        }
        return result;
    }
}
