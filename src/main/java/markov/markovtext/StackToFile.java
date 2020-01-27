package markov.markovtext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class StackToFile {
    private static final String POINT = ".";
    private static final String COMMA = ",";
    private static final String SPACE = " ";

    private boolean wasPoint = false;
    private boolean wasComma = false;
    private boolean firstWord = true;

    private BufferedWriter writer;

    public StackToFile( String outputFileName ) throws IOException {
        writer = new BufferedWriter( new FileWriter( outputFileName ) );
    }

    public void saveGeneratedWords( LinkedList<String> generatedWords ) throws IOException {
        for( String word : generatedWords ) {
            saveWord( word );
        }
    }

    public void saveWord( String word ) throws IOException {
        writer.write( analyseWordToSave( word ) );
    }

    private String analyseWordToSave( String word ) {
        StringBuilder sb = new StringBuilder();

        if( word.equals( POINT ) ) {
            if( !wasPoint && !wasComma ) {
                sb.append( word );
                wasPoint = true;
                wasComma = false;
            }
        } else if( word.equals( COMMA ) ) {
            if( !wasPoint && !wasComma ) {
                sb.append( word );
                wasComma = true;
                wasPoint = false;
            }
        } else {
            if( !firstWord ) {
                sb.append( SPACE );
            } else {
                firstWord = false;
            }
            if( wasPoint ) {
                sb.append( word.toUpperCase().charAt( 0 ) );
                sb.append( word.substring( 1 ) );
            } else {
                sb.append( word );
            }
            wasPoint = false;
            wasComma = false;
        }

        return sb.toString();
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }
}
