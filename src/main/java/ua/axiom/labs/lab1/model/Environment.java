package ua.axiom.labs.lab1.model;

import org.springframework.beans.factory.annotation.Value;

/**
 * Used to control populations amount, so they don't die out, and don't multiply into infinity
 */
public class Environment {
    @Value("${application.environment-capacity}")
    private int populationCap;

    private EnvironmentModel environmentModel;


    private final double preferenceMod = 1.;

    public int getKidsCount(Chromosome chromosome) {
        return Math.min((int)(1. / chromosome.getFitness()), 6);
    }

}
