package ua.axiom.labs.lab1.controller.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.axiom.labs.lab1.model.Chromosome;
import ua.axiom.labs.lab1.model.ChromosomeFactory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class GenericMutatorService implements MutatorService<Double> {

    private final double mutationMagnitude;
    /*private final SolutionAppraiserService appraiserService;*/
    private final ChromosomeFactory chromosomeFactory;

    private final Random random;

    @Autowired
    public GenericMutatorService(
            @Value("${random.seed}") long seed,
            @Value("${mutation-magnitude}") double magnitude,
            ChromosomeFactory chromosomeFactory
    ) {
        this.random = new Random(seed);
/*        this.appraiserService = appraiserService;*/
        this.mutationMagnitude = magnitude;
        this.chromosomeFactory =chromosomeFactory;
    }

    @Override
    public Set<Chromosome> mutate(Chromosome chromosome, int kidsCount) {
        Set<Chromosome> kids = new HashSet<>();
        int dimensions = chromosome.getPower();

        for (int i = 0; i < kidsCount ; ++i) {
            int inDimension = random.nextInt(dimensions);
            double shift = random.nextDouble() % mutationMagnitude * (random.nextBoolean() ? -1 : 1);

            kids.add(chromosomeFactory.buildChromosome(chromosome, shift, inDimension));
        }


        return kids;
    }

    protected final Chromosome mutateNewChromosomeByDimension(Chromosome chromosome, int dimentsion, double shift) {
        Double[] newGenes = new Double[chromosome.getPower()];
        for (int i = 0 ; i < newGenes.length ; ++i) {
            if(i == dimentsion) {
                newGenes[i] = chromosome.accessGene(i) + shift;
            } else {
                newGenes[i] = chromosome.accessGene(i);
            }
        }
        return chromosomeFactory.buildChromosome(newGenes);
    }
}
