package markov.markovtext;

import lombok.Data;

@Data
public class WordData {
    private Integer count = 1;
    private Double probability = 0.0;

    public void incrementCount() {
        count++;
    }
}
