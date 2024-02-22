package dz.kyrios.notificationservice.config.filter.clause;

import dz.kyrios.notificationservice.config.filter.enums.Operation;

public class Clause {
    private String filed;
    private Operation operation;


    public Clause(String id, String s, int i) {
    }

    public Clause(String filed, Operation operation) {
        this.filed = filed;
        this.operation = operation;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

}
