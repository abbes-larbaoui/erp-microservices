package dz.kyrios.notificationservice.config.filter.specification;


import dz.kyrios.notificationservice.config.filter.clause.Clause;
import dz.kyrios.notificationservice.config.filter.clause.ClauseArrayArgs;
import dz.kyrios.notificationservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.notificationservice.config.filter.clause.ClauseTwoArgs;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class GenericSpecification <T> implements Specification<T> {
    private List<Clause> filter = new ArrayList<>();

    public GenericSpecification(List<Clause> filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(filter !=null &&!filter.isEmpty()){

            //create the predicates
            filter.forEach(clause -> {
                if(clause instanceof ClauseOneArg){
                    try {
                        predicates.add(ClauseOneArg.toPredicate(root, criteriaBuilder, clause));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(clause instanceof ClauseTwoArgs){
                    predicates.add(ClauseTwoArgs.toPredicate(root, criteriaBuilder, clause));
                }
                if(clause instanceof ClauseArrayArgs){
                    predicates.add(ClauseArrayArgs.toPredicate(root, criteriaBuilder, clause));
                }
            });
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
