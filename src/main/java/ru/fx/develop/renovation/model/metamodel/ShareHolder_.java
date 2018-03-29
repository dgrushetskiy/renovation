package ru.fx.develop.renovation.model.metamodel;

import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.ShareHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ShareHolder.class)
public abstract class ShareHolder_ {

	public static volatile SingularAttribute<ShareHolder, String> kadNom;
	public static volatile SingularAttribute<ShareHolder, String> explikazia;
	public static volatile SingularAttribute<ShareHolder, String> numEGRP;
	public static volatile SingularAttribute<ShareHolder, String> vidPrava;
	public static volatile SingularAttribute<ShareHolder, String> nameSubject;
	public static volatile SingularAttribute<ShareHolder, BigDecimal> sqrOForm;
	public static volatile SingularAttribute<ShareHolder, Long> id;
	public static volatile SingularAttribute<ShareHolder, House> house;
	public static volatile SingularAttribute<ShareHolder, LocalDate> dateEGRP;
	public static volatile SingularAttribute<ShareHolder, String> kadNomPrava;

}

