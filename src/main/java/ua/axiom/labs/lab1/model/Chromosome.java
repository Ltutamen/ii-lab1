package ua.axiom.labs.lab1.model;

import java.util.Arrays;

public class Chromosome {
    private final Double[] genes;
    private final double fitness;

    Chromosome(Chromosome copyFrom, double fitness) {
        this.genes = copyFrom.genes;
        this.fitness = fitness;
    }

    Chromosome(Double[] genes, double fitness) {
        this.genes = Arrays.copyOf(genes, genes.length);
        this.fitness = fitness;
    }

/*    public Chromosome(Chromosome<T> chromosome, double fitness, T mutatedValue, int mutationPlace) {
        this.genes = Arrays.copyOf(chromosome.genes, chromosome.genes.length);
        this.fitness = fitness;
        genes[mutationPlace] = mutatedValue;
    }*/

    public double getFitness() {
        return fitness;
    }

    public double accessGene(int n) {
        return genes[n];
    }

    public int getPower() {
        return genes.length;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "genes=" + Arrays.toString(genes) +
                '}';
    }

    public Double[] getGenes() {
        return Arrays.copyOf(genes, genes.length);
    }
}
