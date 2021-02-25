package ua.axiom.labs.lab1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ua.axiom.labs.lab1.controller.multiplier.MultiplierService;
import ua.axiom.labs.lab1.controller.observer.ObserverTask;
import ua.axiom.labs.lab1.controller.selection.SelectionService;
import ua.axiom.labs.lab1.model.PopulationModel;

import java.util.HashSet;
import java.util.Set;

@Component
public class SimpleLabProcess extends Application {
    private final Set<ObserverTask> observers;

    private final MultiplierService multiplier;
    private final SelectionService selector;

    private final PopulationModel population;

    @Autowired
    public SimpleLabProcess(
            MultiplierService multiplier,
            SelectionService selector,
            Set<ObserverTask> observers,
            PopulationModel initialPopulation,
            ApplicationContext appContext
    ) {

        this.multiplier = multiplier;
        this.selector = selector;
        this.observers = new HashSet<>(observers);

        this.population = initialPopulation;
    }

    @Override
    public void run() {
        while (!population.hasSolution()) {

            multiplier.multiply(population);
            selector.select(population);

            observers.forEach(ObserverTask::run);

        }

        population.getSolution().forEach(System.out::println);
    }
}
