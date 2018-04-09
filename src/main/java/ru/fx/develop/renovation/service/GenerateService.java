package ru.fx.develop.renovation.service;

import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fx.develop.renovation.model.*;
import ru.fx.develop.renovation.util.UtilDisabledPerson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GenerateService {

    Logger log = Logger.getLogger(this.getClass().getName());
    List<Map> resultOutPrRight = new ArrayList<>();
    List<Map> resultOutSecondRight = new ArrayList<>();
    List<Map> resultOutVeterOrg = new ArrayList<>();
    List<Map> resultOutInvalidPerson = new ArrayList<>();

    private BigDecimal sqrOFormHouse;
    @Autowired
    private HouseService houseService;
    @Autowired
    private PrimaryRightsService primaryRightsService;
    @Autowired
    private SecondaryRightService secondaryRightService;
    @Autowired
    private ShareHolderService shareHolderService;
    @Autowired
    private VeteranOrgService veteranOrgService;
    @Autowired
    private DisabledPeopleService disabledPeopleService;

    public static HashMap<String, String> build(String... data) {
        HashMap<String, String> result = new HashMap<>();

        if (data.length % 2 != 0)
            throw new IllegalArgumentException("Odd number of arguments");

        String key = null;
        Integer step = -1;

        for (String value : data) {
            step++;
            switch (step % 2) {
                case 0:
                    if (value == null)
                        throw new IllegalArgumentException("Null key value");
                    key = value;
                    continue;
                case 1:
                    result.put(key, value);
                    break;
            }
        }
        return result;
    }

    public BigDecimal sqrOFormHouse() {
        return getSqrOFormHouse();
    }

    public BigDecimal getSqrOFormHouse() {
        return sqrOFormHouse;
    }

    public void setSqrOFormHouse(BigDecimal sqrOFormHouse) {
        this.sqrOFormHouse = sqrOFormHouse;
    }

    public List<Map> getResultOutPrRight() {
        return resultOutPrRight;
    }

    public void setResultOutPrRight(List<Map> resultOutPrRight) {
        this.resultOutPrRight = resultOutPrRight;
    }

    public List<Map> getResultOutSecondRight() {
        return resultOutSecondRight;
    }

    public void setResultOutSecondRight(List<Map> resultOutSecondRight) {
        this.resultOutSecondRight = resultOutSecondRight;
    }

    public List<Map> getResultOutVeterOrg() {
        return resultOutVeterOrg;
    }

    public void setResultOutVeterOrg(List<Map> resultOutVeterOrg) {
        this.resultOutVeterOrg = resultOutVeterOrg;
    }

    public List<Map> getResultOutInvalidPerson() {
        return resultOutInvalidPerson;
    }

    public void setResultOutInvalidPerson(List<Map> resultOutInvalidPerson) {
        this.resultOutInvalidPerson = resultOutInvalidPerson;
    }

    public BigDecimal resultSqrOFormHouse() {
        return getSqrOFormHouse();
    }


    //Основной метод
    @Transactional
    public List<Map> dataPrimaryRight(ObservableList<House> houses) {
        List<PrimaryRight> primaryRightsHouse = new ArrayList<>();
        //  showData(houses);
        List<House> primaryRightHouseUnom = houses.stream().collect(Collectors.toList());
        primaryRightHouseUnom.forEach(house -> {
            List<PrimaryRight> primaryRightList = primaryRightsService.getByHouse(house);
            System.out.println("house" + house);
//            BigDecimal sqrOForm = primaryRightsService.getSumSqrOForm(house);
//            List<BigDecimal> sqrOFormList = new ArrayList<>();
//            sqrOFormList.add(sqrOForm);
            //  sqrOFormList.forEach(this::setSqrOFormHouse);

            // setSqrOFormHouse(sqrOForm);
            // sqrOFormHouse();
            //  System.out.println("Площадь = " + sqrOForm);
            primaryRightList.stream().map(PrimaryRight::getHouse).collect(Collectors.toList());
            primaryRightsHouse.addAll(primaryRightList);
            System.out.println("unom : " + primaryRightList.size());
            System.out.println("primaryRight : " + primaryRightList);
            setResultOutPrRight(getResultPrimaryRight(primaryRightsHouse));
        });
        return getResultOutPrRight();
    }


    @Transactional
    public List<Map> dataSecondRight(ObservableList<House> houses) {
        List<SecondaryRight> secondaryRightHouse = new ArrayList<>();

        List<House> secondRightHouseUnom = houses.stream().collect(Collectors.toList());
        secondRightHouseUnom.forEach(house -> {
            List<SecondaryRight> secondaryRightList = secondaryRightService.getByHouse(house);
            secondaryRightList.stream().map(SecondaryRight::getHouse).collect(Collectors.toList());
            secondaryRightHouse.addAll(secondaryRightList);
            System.out.println("unom : " + secondaryRightList.size());
            System.out.println("secondaryRight : " + secondaryRightList);
            setResultOutSecondRight(getResultSecondaryRight(secondaryRightHouse));
        });
        return getResultOutSecondRight();
    }

    @Transactional
    public List<Map> dataVeteransOrg(ObservableList<House> houses) {
        List<VeteranOrg> veteranOrgListHouse = new ArrayList<>();
        //  showDataVeterns(houses);
        List<House> veteransOrgHouseUnom = houses.stream().collect(Collectors.toList());
        veteransOrgHouseUnom.forEach(house -> {
            List<VeteranOrg> veteranOrgList = veteranOrgService.getByHouse(house);
            veteranOrgList.stream().map(VeteranOrg::getHouse).collect(Collectors.toList());
            veteranOrgListHouse.addAll(veteranOrgList);
            System.out.println("house veteran : " + veteranOrgList.size());
            System.out.println("veteran org : " + veteranOrgList);
            setResultOutVeterOrg(getResultVeteransOrg(veteranOrgListHouse));
        });
        return getResultOutVeterOrg();
    }

    @Transactional
    public List<Map> dataDisabledPerson(ObservableList<House> houses) {
        List<DisabledPeople> disabledPeopleListHouse = new ArrayList<>();
        //showDataInvalid(houses);
        List<House> invalidPeopleHouseUnom = houses.stream().collect(Collectors.toList());
        invalidPeopleHouseUnom.forEach(house -> {
            List<DisabledPeople> disabledPeopleList = disabledPeopleService.getByHouse(house);
            disabledPeopleList.stream().map(DisabledPeople::getHouse).collect(Collectors.toList());
            disabledPeopleListHouse.addAll(disabledPeopleList);
            System.out.println("house invalid person : " + disabledPeopleList.size());
            System.out.println("invalid persons : " + disabledPeopleList);
            setResultOutInvalidPerson(getResultInvalidPerson(disabledPeopleListHouse));
        });
        return getResultOutInvalidPerson();
    }

    //@Transactional
    public void showData(ObservableList<House> houses) {
        List<House> primaryRightHouseUnom = houses.stream().collect(Collectors.toList());
        primaryRightHouseUnom.forEach(house -> {
            List<PrimaryRight> primaryRightList = primaryRightsService.getByHouse(house);
            primaryRightList.forEach(primaryRight -> {
                primaryRightList.stream().map(PrimaryRight::getHouse).collect(Collectors.toList());
                primaryRightList.add(primaryRight);
            });
            System.out.println("unom : " + primaryRightList.size());
            System.out.println("primaryRight : " + primaryRightList);
            resultOutPrRight = getResultPrimaryRight(primaryRightList);
        });

    }

    private List<Map> getResultPrimaryRight(List<PrimaryRight> primaryRightList) {
        Map map = new HashMap(primaryRightList.size());

        System.out.println(" map : " + map);
        List<Map> result = new ArrayList<>();

        int count = 1;
        for (PrimaryRight primaryRight : primaryRightList) {
            String house = String.valueOf(primaryRight.getHouse().getUnom());
            String address = primaryRight.getHouse().getAddress();
            String vidPrava = primaryRight.getVidPrava();
            String nameSubject = primaryRight.getNameSubject();
            BigDecimal sqrOForm = primaryRight.getSqrOForm();
            String countNum = String.valueOf(count++);
            String unomNum = String.valueOf(primaryRight.getHouse().getUnom());
            //  BigDecimal sqrOFormHouse = getSqrOFormHouse();
            //   System.out.println("sqrOFormHouse =" + sqrOFormHouse);
            map = GenerateService.build("address", address,
                    "vidPrava", vidPrava,
                    "sqrOForm", sqrOForm.toPlainString(),
                    "nameSubject", nameSubject,
                    "count", countNum,
                    "unom", unomNum,//"sqrOFormHouse",sqrOFormHouse.toPlainString(),
                    "house", house);
            result.add(map);
        }
        return result;
    }

    private List<Map> getResultSecondaryRight(List<SecondaryRight> secondaryRightList) {
        Map map = new HashMap(secondaryRightList.size());

        System.out.println(" map : " + map);
        List<Map> result = new ArrayList<>();

        for (SecondaryRight secondaryRight : secondaryRightList) {
            String vidPravaSc = secondaryRight.getVidPrava();
            String vidPravaRed = secondaryRight.getVidPravaRed();
            String nameSubjectSc = secondaryRight.getNameSubject();
            map = GenerateService.build("vidPrava", vidPravaSc,
                    "vidPravaRed", vidPravaRed,
                    "nameSubject", nameSubjectSc);
            result.add(map);
        }
        return result;
    }

    private void showDataVeterns(ObservableList<House> houses) {
        List<House> veteransOrgHouseUnom = houses.stream().collect(Collectors.toList());
        veteransOrgHouseUnom.forEach(house -> {
            List<VeteranOrg> veteranOrgList = veteranOrgService.getByHouse(house);
            veteranOrgList.stream().map(VeteranOrg::getHouse).collect(Collectors.toList());
            System.out.println("house veteran : " + veteranOrgList.size());
            System.out.println("veteran org : " + veteranOrgList);
            resultOutVeterOrg = getResultVeteransOrg(veteranOrgList);
        });
    }

    private List<Map> getResultVeteransOrg(List<VeteranOrg> veteranOrgList) {
        List<Map> result = new ArrayList<>();
        Map map = new HashMap(veteranOrgList.size());
        int count = 1;
        for (VeteranOrg veteranOrg : veteranOrgList) {
            String address = veteranOrg.getHouse().getAddress();
            String vidPrava = veteranOrg.getVidPrava();
            BigDecimal sqrOForm = veteranOrg.getSqrOForm();
            String countNum = String.valueOf(count++);
            map = GenerateService.build("address", address,
                    "vidPrava", vidPrava,
                    "sqrOForm", sqrOForm.toPlainString(),
                    "count", countNum);
            result.add(map);
        }
        return result;
    }

    private void showDataInvalid(ObservableList<House> houses) {
        List<House> invalidPeopleHouseUnom = houses.stream().collect(Collectors.toList());
        invalidPeopleHouseUnom.forEach(house -> {
            List<DisabledPeople> disabledPeopleList = disabledPeopleService.getByHouse(house);
            disabledPeopleList.stream().map(DisabledPeople::getHouse).collect(Collectors.toList());
            System.out.println("house invalid person : " + disabledPeopleList.size());
            System.out.println("invalid persons : " + disabledPeopleList);
            resultOutInvalidPerson = getResultInvalidPerson(disabledPeopleList);
        });
    }

    private List<Map> getResultInvalidPerson(List<DisabledPeople> disabledPeopleList) {
        List<Map> result = new ArrayList<>();
        Map map = new HashMap(disabledPeopleList.size());
        int count = 1;
        for (DisabledPeople disabledPeople : disabledPeopleList) {
            String address = disabledPeople.getAddress();
            Integer groupInvalid = disabledPeople.getGroupInvalid();
            Boolean armchairStr = disabledPeople.isArmchair();
            Boolean singleStr = disabledPeople.isSingle();
            String demandsStr = disabledPeople.getDemands();
            String adaptationStr = disabledPeople.getDemands();
            Integer totalsStr = disabledPeople.getTotals();
            String countNum = String.valueOf(count++);
            map = GenerateService.build("address", address,
                    "groupInvalid", UtilDisabledPerson.getIntegerInvalidGroup(groupInvalid),
                    "armchair", String.valueOf(armchairStr),
                    "single", String.valueOf(singleStr),
                    "demands", UtilDisabledPerson.getStringToString(demandsStr),
                    "adaptation", UtilDisabledPerson.getStringToStringAdaptation(adaptationStr),
                    "totals", String.valueOf(totalsStr),
                    "count", countNum);
            result.add(map);
        }
        return result;
    }
}
