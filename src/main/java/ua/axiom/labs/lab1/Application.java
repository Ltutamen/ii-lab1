package ua.axiom.labs.lab1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public abstract class Application {

    public abstract void run();

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext("ua.axiom.labs.lab1");

        context.getBean(SimpleLabProcess.class).run();
    }
}
