package ua.axiom.labs.lab1.controller.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.axiom.labs.lab1.model.PopulationModel;

import java.io.OutputStream;
import java.util.DoubleSummaryStatistics;

@Component
public class PrintDataObserverTask implements ObserverTask {
    private final OutputStream output;
    private final PopulationModel population;

    @Autowired
    public PrintDataObserverTask(
            OutputStream output,
            PopulationModel populationModel
    ) {
        this.output = output;
        this.population = populationModel;
    }

    @Override
    public void run() {
        if(population.getSize() == 0) {
            throw new IllegalStateException("DEAD, everything is dead!");
        }

        DoubleSummaryStatistics statistics = population.getStatistics();

        String toPrint = "s:" + population.getSize() + " d:" + statistics.getMin() + " t:" + statistics.getMax() + " med:" + statistics.getAverage();


        if(population.getSize() == 1) {
            toPrint = toPrint + " -> " + population.getSolution().iterator().next();
        }

        System.out.println(toPrint);
    }
}
