package ua.axiom.labs.lab1.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.axiom.labs.lab1.configuration.VariantFunction;
import ua.axiom.labs.lab1.model.Chromosome;

import static java.lang.Math.abs;

@Component
public class SolutionFunctionChecker implements SolutionChecker {
    private final VariantFunction function;

    @Autowired
    public SolutionFunctionChecker(VariantFunction function) {
        this.function = function;
    }

    @Override
    public boolean isSolution(Chromosome chromosome) {
        return abs(function.apply(chromosome.getGenes())) < 10e-5;
    }
}
