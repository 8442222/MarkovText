package markov.markovtext;

import java.io.IOException;
import java.util.LinkedList;

public class WordsBuffer {
    private FileToStack fileToStack;
    private Integer totalStates;
    private boolean wordAdded = true;
    private final LinkedList<String> wordsBuffer = new LinkedList<>();
    private Dictionary dictionary;

    public WordsBuffer( String fileName, Integer totalStates, Integer bufferSize, Dictionary dictionary ) throws IOException {
        this.fileToStack = new FileToStack( fileName, bufferSize );
        this.totalStates = totalStates;
        this.dictionary = dictionary;
    }

    public boolean fillBuffer() throws IOException {
        for( int i = 0; i < totalStates + 1 && wordAdded; i++ ) {
            wordsBuffer.add( getWord() );
        }
        return wordAdded;
    }

    public boolean shiftWord() throws IOException {
        wordsBuffer.removeFirst();
        wordsBuffer.addLast( getWord() );
        return wordAdded;
    }

    private String getWord() throws IOException {
        String resultWord = "";
        StringBuilder word = new StringBuilder();
        boolean doProcess = true;

        wordAdded = false;
        do {
            char nextChar = fileToStack.getNextChar();
            if( fileToStack.isEndOfFile() ) {
                doProcess = false;
            } else {
                switch( nextChar ) {
                    case '\n':
                    case '\r':
                    case '\t':
                    case ' ':
                        if( word.length() > 0 ) {
                            doProcess = false;
                        }
                        break;
                    case '.':
                    case ',':
                        doProcess = false;
                        if( word.length() > 0 ) {
                            fileToStack.putCharBack();
                        } else {
                            word.append( nextChar );
                        }
                        break;
                    default:
                        word.append( nextChar );
                        break;
                }
            }
        } while( doProcess );

        if( word.length() > 0 ) {
            resultWord = word.toString().toLowerCase();
            wordAdded = true;
            dictionary.register( resultWord );
        }
        return resultWord;
    }

    public LinkedList<String> getWords() {
        LinkedList<String> list = new LinkedList<>( wordsBuffer );
        list.removeLast();
        return list;
    }

    public String getNextWord() {
        return wordsBuffer.getLast();
    }
}
