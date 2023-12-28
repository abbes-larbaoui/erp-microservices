package dz.kyrios.adminservice.config.filter.specification;


import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.enums.Connecteur;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ListSpecification<T> implements Specification<T> {
    private List<List<Clause>>  clauses;

   private Connecteur clauseConnecteur;

   private Connecteur predicateConnecteur;


    public ListSpecification(List<List<Clause>>  clauses , Connecteur predicateConnecteur ,Connecteur clauseConnecteur ) {
        this.clauses = clauses;
        this.predicateConnecteur = predicateConnecteur;
        this.clauseConnecteur = clauseConnecteur;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        List<Predicate>  predicates = clauses.stream().map(clauses1 -> new GenericSpecification<T>(clauses1 ,predicateConnecteur).toPredicate(root,query, criteriaBuilder)).collect(Collectors.toList());
//        if(clauseConnecteur == Connecteur.Or){
//            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
//        }
//        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        return null;
    }
}
