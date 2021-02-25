package ua.axiom.labs.lab1.controller.multiplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.axiom.labs.lab1.controller.mutation.MutatorService;
import ua.axiom.labs.lab1.controller.task.SolutionAppraiserService;
import ua.axiom.labs.lab1.model.Chromosome;
import ua.axiom.labs.lab1.model.Population;
import ua.axiom.labs.lab1.model.PopulationModel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Multiplies populations, depending on their quality and environment
 */
@Service
public class GenericMultiplierService implements MultiplierService {
    private final Random RANDOM;

    private final SolutionAppraiserService solutionAppraise;
    private final MutatorService<Double> mutatorService;

    @Value("${application.environment-capacity}")
    private int envCapacity;

    @Autowired
    public GenericMultiplierService(
            SolutionAppraiserService solutionAppraise,
            MutatorService<Double> mutatorService,
            @Value("${random.seed}") long seed
    ) {
        this.solutionAppraise = solutionAppraise;
        this.mutatorService = mutatorService;
        this.RANDOM = new Random(seed);
    }

    @Override
    public void multiply(PopulationModel population) {
        int totalPopulation = population.getSize();

        double populationEmptinessFactor = ((double) envCapacity) / totalPopulation;

        double pivot_66p = population.getPivotForPercents(0.666);

        population.multiply(pop -> this.multiplyCountingFactorAndPivot(pop, populationEmptinessFactor, pivot_66p));

/*        return populations.stream()
                .map(population -> multiplyCountingFactor(population, populationEmptinessFactor))
                .flatMap(Arrays::stream)
                .collect(Collectors.toSet());*/
    }

    protected Collection<Population> multiplyCountingFactorAndPivot(Population population, double emptiness, double pivot) {
        double populationRating = 1. / solutionAppraise.rateSolution(population.getChromosome());

        int kidsCount = (int)(populationRating + emptiness) + 1;
        Collection<Population> kids = new HashSet<>();

        Set<Chromosome> mutatedChromosomes = mutatorService.mutate(population.getChromosome(), kidsCount);

        for (int i = 0; i < kidsCount; ++i) {
            Population newKid = new Population(random(mutatedChromosomes));
            kids.add(newKid);
        }

        return kids;
    }

    protected Chromosome random(Set<Chromosome> chromosomes) {
        int size = chromosomes.size();
        int i = RANDOM.nextInt(size);

        return (Chromosome)chromosomes.toArray()[i];
    }

}
