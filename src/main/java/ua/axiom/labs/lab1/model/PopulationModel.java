package ua.axiom.labs.lab1.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.axiom.labs.lab1.controller.task.SolutionAppraiserService;
import ua.axiom.labs.lab1.controller.task.SolutionChecker;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class PopulationModel {
    private final Set<Population> populationSet = new HashSet<>();

    private final SolutionAppraiserService appraiserService;
    private final SolutionChecker solutionChecker;

    @Autowired
    public PopulationModel(
            @Qualifier("initial-population-set") Set<Population> initialPopulation,
            SolutionAppraiserService appraiserService,
            SolutionChecker solutionChecker
    ) {
        this.populationSet.addAll(initialPopulation);
        this.appraiserService = appraiserService;
        this.solutionChecker = solutionChecker;
    }

    public void multiply(Function<Population, Collection<Population>> multiplyFunc) {
        HashSet<Population> newPopulation = new HashSet<>();

        for (Population population : populationSet) {
            newPopulation.addAll(multiplyFunc.apply(population));
        }

        populationSet.clear();

        populationSet.addAll(newPopulation);
    }

    public int getSize() {
        return populationSet.size();
    }

    public double getPivotForPercents(double percent) {
        if(percent > 1. || percent < 0.) {
            throw new IllegalArgumentException();
        }

        DoubleSummaryStatistics statistics = populationSet.stream()
                .map(Population::getChromosome)
                .mapToDouble(appraiserService::rateSolution)
                .summaryStatistics();

        double min = statistics.getMin();
        double max = statistics.getMax();

        return min + (max - min) * percent;
    }

    public boolean hasSolution() {
        return populationSet.stream().map(Population::getChromosome).anyMatch(solutionChecker::isSolution);
    }

    public Collection<Chromosome> getSolution() {
        return populationSet.stream()
                .map(Population::getChromosome)
                .filter(solutionChecker::isSolution)
                .collect(Collectors.toSet());
    }

    public void removeIf(Predicate<Population> predicate) {
        populationSet.removeIf(predicate);
    }

    public void limitToNBest(int n) {
        Set<Population> newPopulations = populationSet.stream()
                .sorted(Comparator.comparingDouble(pop -> pop.getChromosome().getFitness()))
                .limit(n)
                .collect(Collectors.toSet());

        populationSet.clear();

        populationSet.addAll(newPopulations);
    }

    public DoubleSummaryStatistics getStatistics() {
        return populationSet.stream().mapToDouble(population -> population.getChromosome().getFitness()).summaryStatistics();
    }

    public Set<Population> getNBest(int count) {
        return populationSet.stream()
                .sorted(Comparator.comparingDouble(pop -> pop.getChromosome().getFitness()))
                .limit(count)
                .collect(Collectors.toSet());
    }
}
