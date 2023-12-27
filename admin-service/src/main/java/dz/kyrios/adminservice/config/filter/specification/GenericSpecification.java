package dz.kyrios.adminservice.config.filter.specification;

import com.kyrios.authzservice.filter.clause.Clause;
import com.kyrios.authzservice.filter.clause.ClauseArrayArgs;
import com.kyrios.authzservice.filter.clause.ClauseOneArg;
import com.kyrios.authzservice.filter.clause.ClauseTwoArgs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


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
