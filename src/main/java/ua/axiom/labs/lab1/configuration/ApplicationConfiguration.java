package ua.axiom.labs.lab1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ua.axiom.labs.lab1.model.Chromosome;
import ua.axiom.labs.lab1.model.ChromosomeFactory;
import ua.axiom.labs.lab1.model.Population;

import java.io.OutputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {
    private final Random RANDOM;
    private static final int SOLUTION_DIMENSIONS = 2;

    private final ChromosomeFactory chromosomeFactory;
    @Value("${initial.population-count}")
    private int initialPopulationCount;

    @Autowired
    public ApplicationConfiguration(
            @Value("${random.seed}") long seed,
            ChromosomeFactory chromosomeFactory
    ) {
        this.RANDOM = new Random(seed);
        this.chromosomeFactory = chromosomeFactory;
    }

    @Bean("initial-population-set")
    protected Set<Population> getInitialPopulations() {
        Set<Population> populations = new HashSet<>();

        for (int i = 0; i < initialPopulationCount ; ++i) {
            Double[] genes = getRandomDoubleArray(SOLUTION_DIMENSIONS);
            Chromosome chromosome = chromosomeFactory.buildChromosome(genes);
            Population population = new Population(chromosome);

            populations.add(population);
        }

        return populations;
    }

    protected Double[] getRandomDoubleArray(int size) {
        Double[] arr = new Double[size];

        for (int i = 0; i < arr.length ; ++i) {
            arr[i] = RANDOM.nextDouble();
        }

        return arr;
    }

    @Bean
    protected OutputStream getOutputStream() {
        return System.out;
    }
}
