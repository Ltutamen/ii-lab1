package ua.axiom.labs.lab1.controller.crossbreedeng;

import org.springframework.beans.factory.annotation.Autowired;
import ua.axiom.labs.lab1.controller.task.SolutionAppraiserService;
import ua.axiom.labs.lab1.model.Population;

import java.util.Set;
import java.util.stream.Collectors;

public class MixBestCrossBreedingService implements CrossBreedingService<Double> {

    private final SolutionAppraiserService appraiserService;


    @Autowired
    public MixBestCrossBreedingService(SolutionAppraiserService appraiserService) {
        this.appraiserService = appraiserService;
    }

    @Override
    public void crossBreed(Set<Population> populations) {
        double pivot = appraiserService.getPivotFor(0.333, populations);

        populations.stream()
                .filter(population -> appraiserService.rateSolution(population.getChromosome()) < pivot)
                .collect(Collectors.toSet());

    }
}
