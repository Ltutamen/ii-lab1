package ua.axiom.labs.lab1.controller.observer;

import org.springframework.beans.factory.annotation.Autowired;
import ua.axiom.labs.lab1.configuration.VariantConfiguration;

/**
 * Shifts solution each turn, prints data about shift speed
 */
//  @Component
//  @Profile("shifting-test")
public class ShiftingStateShiftingService implements ObserverTask {

    private final VariantConfiguration variantConfiguration;
    //  private final ShiftingAuxService auxService;

    @Autowired
    public ShiftingStateShiftingService(VariantConfiguration variantConfiguration) {
        this.variantConfiguration = variantConfiguration;
    }

    @Override
    public void run() {
        Double[] delta= new Double[]{0.01, 0.01};
        variantConfiguration.shiftSolution(delta);
    }
}
