package fr.pinguet62.java2typescript.dto.child2;

import fr.pinguet62.java2typescript.dto.Parent1Dto;
import fr.pinguet62.java2typescript.dto.Parent2Dto;
import fr.pinguet62.java2typescript.dto.child1.Child1Dto;

public class Child2Dto {

    // Parent: parent-folder
    private Parent1Dto parent1;
    public Parent1Dto getParent1() {
        return parent1;
    }
    public void setParent1(Parent1Dto parent1) {
        this.parent1 = parent1;
    }
    // Parent: parent-folder
    private Parent2Dto parent2;
    public Parent2Dto getParent2() {
        return parent2;
    }
    public void setParent2(Parent2Dto parent2) {
        this.parent2 = parent2;
    }

    // Cousin: sub-folder of parent-folder
    private Child1Dto child1;
    public Child1Dto getChild1() {
        return child1;
    }
    public void setChild1(Child1Dto child1) {
        this.child1 = child1;
    }

}