package ua.axiom.labs.lab1.controller.selection;

import ua.axiom.labs.lab1.model.PopulationModel;

/**
 * Removes bad genes from population
 * @param <T>
 */
public interface SelectionService {
    void select(PopulationModel populations);

}
