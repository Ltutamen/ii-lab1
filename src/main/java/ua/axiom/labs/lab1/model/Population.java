package ua.axiom.labs.lab1.model;

public class Population {
    private final Chromosome chromosome;

    public Population(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }
}
