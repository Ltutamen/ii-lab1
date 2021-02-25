package ua.axiom.labs.lab1.configuration;

import javax.vecmath.Vector2d;

public interface VariantConfiguration {
    int getDimensionsCount();

    Vector2d[] getBounds();

    Double[] getSolution();

    void shiftSolution(Double[] delta);
}
