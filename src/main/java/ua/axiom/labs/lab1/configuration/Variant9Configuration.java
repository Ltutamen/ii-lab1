package ua.axiom.labs.lab1.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.vecmath.Vector2d;
import java.util.Arrays;

@Component
public class Variant9Configuration implements VariantConfiguration {
    private static final int DIMENSIONS_COUNT = 2;
    private final Double[] SOLUTION;
    private final Vector2d[] BOUNDS = new Vector2d[] {new Vector2d(-15., 15.), new Vector2d(-15., 15)};

    public Variant9Configuration(@Value("#{'${function.solution}'.split(',')}") Double[] solution) {
        this.SOLUTION = solution;
    }

    @Override
    public int getDimensionsCount() {
        return DIMENSIONS_COUNT;
    }

    @Override
    public Vector2d[] getBounds() {
        return Arrays.copyOf(BOUNDS, BOUNDS.length);
    }

    @Override
    public Double[] getSolution() {
        return Arrays.copyOf(SOLUTION, SOLUTION.length);
    }

    @Override
    public void shiftSolution(Double[] delta) {
        for (int i = 0; i < SOLUTION.length ; ++i) {
            SOLUTION[i] += delta[i];
        }
    }
}
