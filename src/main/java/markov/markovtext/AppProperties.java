package markov.markovtext;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Component
@ConfigurationProperties( prefix = "main" )
@Validated
public class AppProperties {
    @NotBlank
    private String inputFileName;

    @NotBlank
    private String outputFileName;

    @Max( value = 19, message = "N < 20" )
    @Min( value = 1, message = "N > 0" )
    private Integer totalStates;

    @Min( value = 1, message = "L > 0" )
    private Integer totalOutputWords;

    @Max( value = 8192, message = "BUFFER_SIZE <= 8192")
    @Min( value = 1, message = "BUFFER_SIZE > 0")
    private Integer fileReaderBufferSize;
}
