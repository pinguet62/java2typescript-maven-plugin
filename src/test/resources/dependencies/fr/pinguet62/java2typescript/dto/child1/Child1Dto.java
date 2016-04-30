package fr.pinguet62.java2typescript.dto.child1;

import fr.pinguet62.java2typescript.dto.Parent1Dto;
import fr.pinguet62.java2typescript.dto.Parent2Dto;
import fr.pinguet62.java2typescript.dto.child2.Child2Dto;

public class Child1Dto {

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
    private Child2Dto child2;
    public Child2Dto getChild2() {
        return child2;
    }
    public void setChild2(Child2Dto child2) {
        this.child2 = child2;
    }

}