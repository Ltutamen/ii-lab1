package ua.axiom.labs.lab1.controller.selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.axiom.labs.lab1.controller.task.SolutionAppraiserService;
import ua.axiom.labs.lab1.model.PopulationModel;

@Component
public class GenericSelectionService implements SelectionService {
    private final SolutionAppraiserService appraiserService;

    private final int environmentCapacity;

    @Autowired
    public GenericSelectionService(
            @Value("${application.environment-capacity}") int environmentCapacity,
            SolutionAppraiserService appraiserService
    ) {
        this.appraiserService = appraiserService;
        this.environmentCapacity = environmentCapacity;
    }

    @Override
    public void select(PopulationModel populations) {

        double populationFullnessFactor = Math.max(1., (double) populations.getSize() / environmentCapacity);

        double pivot = populations.getPivotForPercents(0.66);

        populations.removeIf(population -> appraiserService.rateSolution(population.getChromosome()) > pivot);

        populations.limitToNBest(environmentCapacity);


    }
}
