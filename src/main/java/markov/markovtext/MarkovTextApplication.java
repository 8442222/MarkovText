package markov.markovtext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarkovTextApplication implements CommandLineRunner {

    @Autowired
    AppProperties appProperties;

    @Autowired
    MarkovModel markovModel;

    public static void main( String... args ) {
        SpringApplication.run( MarkovTextApplication.class, args );
    }

    @Override
    public void run( String... args ) throws Exception {
        System.out.println( "inputFileName=" + appProperties.getInputFileName() );
        System.out.println( "outputFileName=" + appProperties.getOutputFileName() );
        System.out.println( "N=" + appProperties.getTotalStates() );
        System.out.println( "L=" + appProperties.getTotalOutputWords() );
        System.out.println( "fileReaderBufferSize=" + appProperties.getFileReaderBufferSize() );
        markovModel.readAndProcess( appProperties.getInputFileName(),
                appProperties.getTotalStates(),
                appProperties.getFileReaderBufferSize() );
        markovModel.printModel();
        markovModel.generateWords( appProperties.getTotalOutputWords(),
                appProperties.getOutputFileName() );
    }
}
