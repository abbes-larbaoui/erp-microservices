package dz.kyrios.adminservice.config.filter.clause;


import dz.kyrios.adminservice.config.filter.creator.AttribueCreator;
import dz.kyrios.adminservice.config.filter.creator.JoinCreator;
import dz.kyrios.adminservice.config.filter.enums.Operation;
import dz.kyrios.adminservice.config.filter.factory.ValueFactory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ClauseTwoArgs extends Clause {
    private String arg1;
    private String arg2;


    public ClauseTwoArgs(String filed, Operation operation, String arg1, String arg2) {
        super(filed, operation);
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public static Predicate toPredicate(Root root , CriteriaBuilder criteriaBuilder , Clause clause){
        ClauseTwoArgs clauseTwoArgsArg = (ClauseTwoArgs) clause;
        Join joinMap  = JoinCreator.createJoin(root , clause.getFiled());
        String attribute = AttribueCreator.createAttribute(clause.getFiled());
        List<Predicate> predicates = new ArrayList<>();
        try {
            if(joinMap == null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(attribute), (Comparable) ValueFactory.toValue(root, attribute , clauseTwoArgsArg.getArg1())));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(attribute), (Comparable) ValueFactory.toValue(root, attribute , clauseTwoArgsArg.getArg2())));
            }else {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(joinMap.get(attribute), (Comparable) ValueFactory.toValue(joinMap, attribute , clauseTwoArgsArg.getArg1())));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(joinMap.get(attribute), (Comparable) ValueFactory.toValue(joinMap, attribute , clauseTwoArgsArg.getArg1())));
            }
            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }catch (IllegalArgumentException | ParseException | ClassNotFoundException e){
            throw  new RuntimeException(e);
        }
    }
}
