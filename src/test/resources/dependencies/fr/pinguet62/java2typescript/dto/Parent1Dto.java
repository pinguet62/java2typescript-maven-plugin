package fr.pinguet62.java2typescript.dto;

import fr.pinguet62.java2typescript.dto.child1.Child1Dto;
import fr.pinguet62.java2typescript.dto.child2.Child2Dto;

public class Parent1Dto {

    // Brother: same-folder
    private Parent2Dto parent2;
    public Parent2Dto getParent2() {
        return parent2;
    }
    public void setParent2(Parent2Dto parent2) {
        this.parent2 = parent2;
    }

    // Child: sub-package
    private Child1Dto child1;
    public Child1Dto getChild1() {
        return child1;
    }
    public void setChild1(Child1Dto child1) {
        this.child1 = child1;
    }
    // Child: sub-package
    private Child2Dto child2;
    public Child2Dto getChild2() {
        return child2;
    }
    public void setChild2(Child2Dto child2) {
        this.child2 = child2;
    }

}