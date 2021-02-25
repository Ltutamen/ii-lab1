package ua.axiom.labs.lab1.controller.mutation;

import ua.axiom.labs.lab1.model.Chromosome;

import java.util.Set;

public interface MutatorService<T extends Number> {

    Set<Chromosome> mutate(Chromosome chromosome, int kidsCount);
}
