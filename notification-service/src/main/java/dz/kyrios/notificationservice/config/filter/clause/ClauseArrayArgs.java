package dz.kyrios.notificationservice.config.filter.clause;




import dz.kyrios.notificationservice.config.filter.creator.AttribueCreator;
import dz.kyrios.notificationservice.config.filter.creator.JoinCreator;
import dz.kyrios.notificationservice.config.filter.enums.Operation;
import dz.kyrios.notificationservice.config.filter.factory.ValueFactory;

import jakarta.persistence.criteria.*;
import java.text.ParseException;
import java.util.Arrays;

public class ClauseArrayArgs extends Clause {
    private String[] args;



    public ClauseArrayArgs(String filed, Operation operation, String[] args) {
        super(filed, operation);
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    static  public Predicate toPredicate(Root root , CriteriaBuilder criteriaBuilder , Clause clause){
        ClauseArrayArgs clauseArrayArgs = (ClauseArrayArgs) clause;
        String attribute = AttribueCreator.createAttribute(clause.getFiled());
        Expression<String> parentExpression =createParentExpression(root , clause);
        try {
            if (clauseArrayArgs.getOperation() == Operation.NotIn) {
                parentExpression.in(Arrays.stream(clauseArrayArgs.getArgs()).map(arg ->{
                    try {
                        return  (Comparable) ValueFactory.toValue(root, attribute , arg);
                    } catch (ClassNotFoundException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray());
                return criteriaBuilder.not(parentExpression.in(clauseArrayArgs.getArgs()));
            }
            return parentExpression.in(Arrays.stream(clauseArrayArgs.getArgs()).map(arg ->{
                try {
                    return  (Comparable) ValueFactory.toValue(root, attribute , arg);
                } catch (ClassNotFoundException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }).toArray());
//            return parentExpression.in(clauseArrayArgs.getArgs());
        }catch (IllegalArgumentException illegalArgumentException){
            throw  illegalArgumentException;
        }
    }

    private static Expression<String> createParentExpression(Root root  , Clause clause){
        Join joinMap  = JoinCreator.createJoin(root , clause.getFiled());
        String attribute = AttribueCreator.createAttribute(clause.getFiled());
        if(joinMap == null){
            return root.get(attribute);
        }else {
            return joinMap.get(attribute);
        }
    }
}
