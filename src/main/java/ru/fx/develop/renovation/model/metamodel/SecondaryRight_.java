package ru.fx.develop.renovation.model.metamodel;

import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.SecondaryRight;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SecondaryRight.class)
public abstract class SecondaryRight_ {

	public static volatile SingularAttribute<SecondaryRight, String> note;
	public static volatile SingularAttribute<SecondaryRight, String> kadNom;
	public static volatile SingularAttribute<SecondaryRight, LocalDate> dateEndDogovor;
	public static volatile SingularAttribute<SecondaryRight, String> vidPrava;
	public static volatile SingularAttribute<SecondaryRight, String> vidPravaRed;
	public static volatile SingularAttribute<SecondaryRight, String> numDogovor;
	public static volatile SingularAttribute<SecondaryRight, LocalDate> dateDogovor;
	public static volatile SingularAttribute<SecondaryRight, House> house;
	public static volatile SingularAttribute<SecondaryRight, String> numEGRP;
	public static volatile SingularAttribute<SecondaryRight, String> nameSubject;
	public static volatile SingularAttribute<SecondaryRight, BigDecimal> sqrOForm;
	public static volatile SingularAttribute<SecondaryRight, Long> id;
	public static volatile SingularAttribute<SecondaryRight, LocalDate> dateEGRP;
	public static volatile SingularAttribute<SecondaryRight, String> kadNomPrava;

}

