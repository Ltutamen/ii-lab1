package ua.axiom.labs.lab1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

@Component
public class RastruginFunction implements VariantFunction {
    @Value("${function.a-param}")
    private double paramA ;
    private final VariantConfiguration variantConfiguration;

    @Autowired
    public RastruginFunction(VariantConfiguration variantConfiguration) {
        this.variantConfiguration = variantConfiguration;
    }

    @Override
    public Double apply(Double[] input) {
        final Double[] solution = variantConfiguration.getSolution();

        long power = input.length;
        double result = paramA * power;

        for (int i = 0; i < power; ++i) {
            double gene = input[i];

            //  shift solution
            gene -= solution[i];

            result += gene * gene - paramA * cos(2. * PI * gene);
        }

        return result;
    }

}
