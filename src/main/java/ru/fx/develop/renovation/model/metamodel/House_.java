package ru.fx.develop.renovation.model.metamodel;

import ru.fx.develop.renovation.model.*;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(House.class)
public abstract class House_ {

	public static volatile SingularAttribute<House, Long> unom;
	public static volatile SetAttribute<House, PrimaryRight> primaryRightSet;
	public static volatile SingularAttribute<House, String> kadNom;
	public static volatile SingularAttribute<House, String> address;
	public static volatile SingularAttribute<House, String> mr;
	public static volatile SetAttribute<House, VeteranOrg> veteranOrgSet;
	public static volatile SetAttribute<House, SecondaryRight> secondaryRightSet;
	public static volatile SetAttribute<House, DisabledPeople> disabledPeopleSet;
	public static volatile SingularAttribute<House, String> ao;
	public static volatile SetAttribute<House, ShareHolder> shareHolderSet;

}

