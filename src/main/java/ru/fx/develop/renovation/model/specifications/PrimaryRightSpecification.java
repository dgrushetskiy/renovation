package ru.fx.develop.renovation.model.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;

public final class PrimaryRightSpecification {

    private PrimaryRightSpecification(){
    }

    static Specification<PrimaryRight> hasUnom(House house){
        return (root, query, cb) -> {
            House containsHouseIn = getContainsUnomIn(house);
            return cb.or(
              //cb.
            );
        };
    }

    private static House getContainsUnomIn(House house) {
        return null;
    }
}
