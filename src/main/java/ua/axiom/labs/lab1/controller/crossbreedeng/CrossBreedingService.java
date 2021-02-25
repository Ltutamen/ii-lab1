package ua.axiom.labs.lab1.controller.crossbreedeng;

import org.springframework.stereotype.Service;
import ua.axiom.labs.lab1.model.Population;

import java.util.Set;

@Service
public interface CrossBreedingService<T extends Number> {
    void crossBreed(Set<Population> populations);
}
