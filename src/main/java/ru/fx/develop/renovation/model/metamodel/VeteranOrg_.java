package ru.fx.develop.renovation.model.metamodel;

import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.VeteranOrg;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VeteranOrg.class)
public abstract class VeteranOrg_ {

	public static volatile SingularAttribute<VeteranOrg, String> address;
	public static volatile SingularAttribute<VeteranOrg, String> vidPrava;
	public static volatile SingularAttribute<VeteranOrg, BigDecimal> sqrOForm;
	public static volatile SingularAttribute<VeteranOrg, String> document;
	public static volatile SingularAttribute<VeteranOrg, Long> id;
	public static volatile SingularAttribute<VeteranOrg, House> house;

}

