package ua.axiom.labs.lab1.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.axiom.labs.lab1.controller.task.SolutionAppraiserService;

@Component
public class ChromosomeFactory {
    private final SolutionAppraiserService appraiserService;

    @Autowired
    public ChromosomeFactory(SolutionAppraiserService appraiserService) {
        this.appraiserService = appraiserService;
    }

    public Chromosome buildChromosome(Double[] genes) {
        return new Chromosome(genes, appraiserService.rateSolution(genes));
    }

    public Chromosome buildChromosome(Chromosome oldChromosome, double shift, int inDimension) {
        Double[] genes = oldChromosome.getGenes();

        genes[inDimension] += shift;

        double appraise = appraiserService.rateSolution(genes);

        return new Chromosome(genes, appraise);
    }
}
