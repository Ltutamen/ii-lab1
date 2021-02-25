package ua.axiom.labs.lab1.controller.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.axiom.labs.lab1.model.Chromosome;
import ua.axiom.labs.lab1.model.Population;
import ua.axiom.labs.lab1.model.PopulationModel;

import javax.vecmath.Vector2d;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Calculates statistical characteristics of group of BEST_GROUP_SIZE populations
 */
//  @Component
//  @Profile("shifting-test")
public class ShiftingStateObserverObserverService implements ObserverTask {
    /*    private final Double[] shift = new Double[]{0.001, 0.001};*/
    private final PopulationModel populationModel;
    private final static int BEST_GROUP_SIZE = 5;

    @Autowired
    public ShiftingStateObserverObserverService(PopulationModel populationModel) {
        this.populationModel = populationModel;
    }

    /**
     * Observes state of group of N best Populatiosn
     */
    @Override
    public void run() {
        Set<Population> bestGroup = getMostFitGroupOf();

        Set<Double[]> bestGenes = bestGroup.stream()
                .map(Population::getChromosome)
                .map(Chromosome::getGenes)
                .collect(Collectors.toSet());

        Vector2d[] minMaxGeneValues = getMinMaxGeneValues(bestGenes);

        double bestGroupRadius = getGroupRadius(minMaxGeneValues);
        double[] bestGroupCentre = getGroupMedianCentre(bestGenes);

        System.out.println("shift state observer: r:" + bestGroupRadius + ", c:" + Arrays.toString(bestGroupCentre));


    }

    private Set<Population> getMostFitGroupOf() {
        return populationModel.getNBest(BEST_GROUP_SIZE);
    }

    private Vector2d[] getMinMaxGeneValues(Set<Double[]> genes) {
        int genesLength = genes.iterator().next().length;

        Vector2d[] wights = new Vector2d[genesLength];

        for (int dimension = 0; dimension < genesLength ; ++dimension) {
            final int currentDimension = dimension;

            DoubleSummaryStatistics statistics = genes.stream()
                    .mapToDouble(gene -> gene[currentDimension])
                    .summaryStatistics();

            double lowestGene = statistics.getMin();
            double biggestGene = statistics.getMax();

            wights[dimension] = new Vector2d(lowestGene, biggestGene);
        }

        return wights;
    }

    private double getGroupRadius(Vector2d[] minMaxVales) {
        double squareDegreeSum = 0.;

        for (Vector2d minMax : minMaxVales) {
            double distance = minMax.y - minMax.x;
            if(distance < 0.) {
                throw new RuntimeException();
            }

            squareDegreeSum += distance * distance;
        }

        return Math.sqrt(squareDegreeSum);
    }

    private double[] getGroupMedianCentre(Set<Double[]> genes) {
        int dimensions = genes.iterator().next().length;

        double[] result = new double[dimensions];


        for (Double[] gene : genes) {
            for (int i = 0; i < dimensions ; ++i) {
                result[i] += gene[i];
            }
        }

        for (int i = 0; i < dimensions ; ++i) {
            result[i] = Math.pow(result[i], 1. / dimensions);
        }

        return result;
    }


}
