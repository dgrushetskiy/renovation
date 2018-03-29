package ru.fx.develop.renovation.model.specifications;


import org.springframework.data.jpa.domain.Specification;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.metamodel.House_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public final class HouseSpecifications {

    private HouseSpecifications(){
    }

    public static Specification<House> mrOrAddressContainsIgnoreCase(String searchTerm){
        return (root, query, cb) -> {
            String containsLikePattern = getContainsLikePattern(searchTerm);
            return cb.or(
                    //cb.like(cb.lower(root.<Long>get(House_.unom)), unom);
                    cb.like(cb.lower(root.<String>get(House_.mr)),containsLikePattern),
                    cb.like(cb.lower(root.<String>get(House_.address)), containsLikePattern)
            );
        };
    }

//    static Specification<House> unomIn(Long unom){
//        return (root, query, cb) -> {
//            Long containsUnom = getContainsIn(unom);
//
//            return cb.and(
//                    cb.in(cb.toLong(root.<Long>in(House_.unom)))
//            )
//        };
//    }

    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }

    private static Long getContainsIn(Long unom){
        if (unom == 0){
            return Long.valueOf(0);
        } else {
            return unom;
        }
    }


}
