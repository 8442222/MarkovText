package markov.markovtext;

import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileToStack {
    private final char[] buffer;
    private int bufferTotalItems = 0;
    private int bufferCurrentIndex = 0;
    private FileInputStream fileInputStream;
    private Reader fileReader;

    @Getter
    private boolean endOfFile;

    public FileToStack( String fileName, Integer bufferSize ) throws FileNotFoundException {
        buffer = new char[bufferSize];
        fileInputStream = new FileInputStream( fileName );
        fileReader = new InputStreamReader( fileInputStream, StandardCharsets.UTF_8 );
        endOfFile = false;
    }

    public char getNextChar() throws IOException {
        char result = 0;
        if( !endOfFile && bufferCurrentIndex >= bufferTotalItems ) {
            bufferTotalItems = fileReader.read( buffer, 0, buffer.length );
            if( bufferTotalItems <= 0 ) {
                endOfFile = true;
                close();
            } else {
                bufferCurrentIndex = 0;
            }
        }
        if( !endOfFile ) {
            result = buffer[bufferCurrentIndex++];
        }
        return result;
    }

    public void putCharBack() throws ArrayIndexOutOfBoundsException {
        if( bufferCurrentIndex > 0 ) {
            bufferCurrentIndex--;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void close() {
        try {
            fileReader.close();
            fileInputStream.close();
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }
}
