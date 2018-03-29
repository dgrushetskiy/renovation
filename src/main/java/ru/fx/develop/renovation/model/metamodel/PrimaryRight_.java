package ru.fx.develop.renovation.model.metamodel;

import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PrimaryRight.class)
public abstract class PrimaryRight_ {

	public static volatile SingularAttribute<PrimaryRight, String> kadNom;
	public static volatile SingularAttribute<PrimaryRight, String> explikazia;
	public static volatile SingularAttribute<PrimaryRight, String> vidPrava;
	public static volatile SingularAttribute<PrimaryRight, String> nameSubject;
	public static volatile SingularAttribute<PrimaryRight, BigDecimal> sqrOForm;
	public static volatile SingularAttribute<PrimaryRight, Long> id;
	public static volatile SingularAttribute<PrimaryRight, House> house;
	public static volatile SingularAttribute<PrimaryRight, String> kadNomPrava;

}

