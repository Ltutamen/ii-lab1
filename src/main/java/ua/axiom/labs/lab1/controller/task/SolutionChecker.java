package ua.axiom.labs.lab1.controller.task;

import ua.axiom.labs.lab1.model.Chromosome;

public interface SolutionChecker {
    boolean isSolution(Chromosome chromosome);
}
