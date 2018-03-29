package ru.fx.develop.renovation.generator.writer;

import com.google.common.collect.Lists;
import org.apache.poi.xslf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableCol;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableRow;
import ru.fx.develop.renovation.generator.GeneratorUtils;
import ru.fx.develop.renovation.util.UtilDisabledPerson;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DisabledPersonWriter extends RowWriter {

    private CountData countData = new CountData();

    public DisabledPersonWriter(XMLSlideShow pptx, XSLFSlide slide, XSLFTable table) {
        super(pptx, slide, table);
        this.prevTableRect = slide.getShapes().get(3).getAnchor().getBounds();
    }

    @Override
    public void writeRow(Map model) {
//        if (countData != null){
//            writeTotal();
//            writeCountFirstGroup();
//            writeCountSecondGroup();
//            writeCountThreeGroup();
//            writeCountFourGroup();
//            countData.reset();
//        }

        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(1));//строка запонения с данными на шаблоне под номер 1(счет сверху 0,1,2 зависит от количества строк в таблице)


        String groupInvalid = (String) model.get("groupInvalid");

        String armchairStr = (String) model.get("armchair");
        Boolean armchairBool = Boolean.parseBoolean(armchairStr);
        String armchair = UtilDisabledPerson.toStringYesNo(armchairBool);

        String singleStr = (String) model.get("single");
        Boolean singleBool = Boolean.parseBoolean(singleStr);
        String single = UtilDisabledPerson.toStringYesNo(singleBool);

        String demands = (String) model.get("demands");

        String adaptation = (String) model.get("adaptation");

        String totalsString = (String) model.get("totals");
        Integer totals = Integer.parseInt(totalsString);


        Integer groupInvalidFirstGroupCount = UtilDisabledPerson.getIntegerFirstGroup(groupInvalid);
        Integer groupInvalidSecondGroupCount = UtilDisabledPerson.getIntegerSecondGroup(groupInvalid);
        Integer groupInvalidThreeGroupCount = UtilDisabledPerson.getIntegerThreeGroup(groupInvalid);
        Integer groupInvalidFourGroupCount = UtilDisabledPerson.getIntegerFourGroup(groupInvalid);
        Integer countArmchair = UtilDisabledPerson.getBoolInteger(armchairBool);
        Integer lonelySingle = UtilDisabledPerson.getBoolInteger(singleBool);
        Integer demandsCount = UtilDisabledPerson.getIntegerStr(demands);
        Integer adaptationCount = UtilDisabledPerson.getIntegerStrAdaptation(adaptation);
        Integer all = groupInvalidFirstGroupCount + groupInvalidSecondGroupCount + groupInvalidThreeGroupCount + groupInvalidFourGroupCount;

        GeneratorUtils.addCellText(newRow, 0, model.get("count"));
        GeneratorUtils.addCellText(newRow, 1, model.get("address"));
        GeneratorUtils.addCellText(newRow,2,groupInvalid);

        GeneratorUtils.addCellText(newRow,3, armchair);
        GeneratorUtils.addCellText(newRow, 4,single);
        GeneratorUtils.addCellText(newRow, 5, demands);
        GeneratorUtils.addCellText(newRow,6, adaptation);
        GeneratorUtils.addCellText(newRow,7, totals);

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

        mergeCells();

        if (needTableBreak()) {
            table.getCTTable().removeTr(table.getCTTable().sizeOfTrArray() - 1);
            sliceTable();
            writeRow(model);
        }
        else {
            countData.add(all);
            countData.addFirstGroup(groupInvalidFirstGroupCount);
            countData.addSecondGroup(groupInvalidSecondGroupCount);
            countData.addThreeGroup(groupInvalidThreeGroupCount);
            countData.addFourGroup(groupInvalidFourGroupCount);
            countData.addArmchairStroller(countArmchair, lonelySingle, demandsCount,adaptationCount);
        }

    }

    public void mergeCells(){
        int rowLength = table.getCTTable().getTrArray().length;
        if (rowLength > 8){
            int i = rowLength -(rowLength - 8);
            String lastGrupInv = GeneratorUtils.getCellText(table.getCTTable().getTrArray(i), 2);
            String lastGrupInvReturn = lastGrupInv.substring(0);
            String curGrupInv = lastGrupInvReturn;

            while (lastGrupInvReturn.equals(curGrupInv) && i >= 8){
                String curGrupInvNext = GeneratorUtils.getCellText(table.getCTTable().getTrArray(i - 10), 2);
                System.out.println("5");
                curGrupInv = !curGrupInvNext.trim().isEmpty() ?
                        curGrupInvNext.substring(0) :
                        curGrupInvNext;
                i--;
                System.out.println(i);
            }
            final int firstRowInd = i + 1;

            System.out.println(firstRowInd);
            System.out.println("=====");
            //table.getCTTable().getTrArray()[firstRowInd].getTcArray()[0].setRowSpan(rowLength - firstRowInd);
            table.getCTTable().getTrArray()[firstRowInd].getTcArray()[0].setRowSpan(rowLength - firstRowInd);
            table.getCTTable().getTrArray()[firstRowInd].getTcArray()[1].setRowSpan(rowLength - firstRowInd);
            table.getCTTable().getTrArray()[firstRowInd].getTcArray()[7].setRowSpan(rowLength - firstRowInd);


            table.getCTTable().getTrList().stream()
                    .filter(tr -> table.getCTTable().getTrList().indexOf(tr) > firstRowInd)
                    .forEach(tr -> GeneratorUtils.addCellText(tr,1,""));
            List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
            table.getCTTable().getTrList().stream()
                    .filter(tr -> table.getCTTable().getTrList().indexOf(tr) >= firstRowInd)
                    .forEach(tr -> tr.setH(GeneratorUtils.calculateRowHeight(tr, cellsWidth)));
        }
    }

    @Override
    public void writeTotal() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(3));

        GeneratorUtils.addCellText(newRow, 3, countData.getCountArmchairStroller());
        GeneratorUtils.addCellText(newRow, 4, countData.getLonelyAccomodation());
        GeneratorUtils.addCellText(newRow, 5, countData.getImprovementOfConditions());
        GeneratorUtils.addCellText(newRow, 6, countData.getAdaptation());
        GeneratorUtils.addCellText(newRow, 7, countData.getCountAll());


        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    public void writeCountFirstGroup() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(4));

        GeneratorUtils.addCellText(newRow, 7, countData.getCount1Group());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    public void writeCountSecondGroup() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(5));

        GeneratorUtils.addCellText(newRow, 7, countData.getCount2Group());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    public void writeCountThreeGroup() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(6));

        GeneratorUtils.addCellText(newRow, 7, countData.getCount3Group());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    public void writeCountFourGroup() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(7));

        GeneratorUtils.addCellText(newRow, 7, countData.getCount4Group());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    @Override
    public void writeNoData() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(2));//выбирается строка для заполнения
        GeneratorUtils.addCellText(newRow, 0, "В выбранных объектах инвалиды не проживают");
    }

    @Override
    public void sliceTable() {
        endSlideTable();

        if (shapesToDelete.get(slide) != null) {
            shapesToDelete.get(slide).add(slide.getShapes().get(3));
        } else {
            shapesToDelete.put(slide, Lists.newArrayList(slide.getShapes().get(3)));
        }

        XSLFSlide newSlide = pptx.createSlide();
        newSlide.importContent(pptx.getSlides().get(0));

        XSLFAutoShape captionShape = (XSLFAutoShape) newSlide.getShapes().get(0);
        prevTableRect = captionShape.getAnchor().getBounds();
        captionShape.getXmlObject().set(slide.getShapes().get(0).getXmlObject().copy());

        Integer lastPageNumber = Integer.parseInt(((XSLFTextBox) slide.getShapes().get(3)).getText());
        XSLFTextBox pageNUmberBox = (XSLFTextBox) newSlide.getShapes().get(1);
        pageNUmberBox.setText("" + (lastPageNumber + 1));

        slide = newSlide;
        table = (XSLFTable) slide.getShapes().get(3);

        if (shapesToDelete.get(slide) != null) {
            shapesToDelete.get(slide).add(slide.getShapes().get(3));
        } else {
            shapesToDelete.put(slide, Lists.newArrayList(slide.getShapes().get(3)));
        }
    }

    @Override
    public int getCaptionRowsNumber() {
        return 1;
    }

    public CountData getCountData() {
        return countData;
    }

    private class CountData {
        private int countAll = 0;//всего человек
        private int countArmchairStroller = 0;//количество колясочников
        private int lonelyAccomodation = 0;//количество одиноких
        private int improvementOfConditions = 0;//количество на улучшения
        private int adaptation = 0;// количество на приспособление
        private int count1Group = 0;
        private int count2Group = 0;
        private int count3Group = 0;
        private int count4Group = 0;

        public int getCountAll() {
            return countAll;
        }

        public void setCountAll(int countAll) {
            this.countAll = countAll;
        }

        public int getCountArmchairStroller() {
            return countArmchairStroller;
        }

        public void setCountArmchairStroller(int countArmchairStroller) {
            this.countArmchairStroller = countArmchairStroller;
        }

        public int getLonelyAccomodation() {
            return lonelyAccomodation;
        }

        public void setLonelyAccomodation(int lonelyAccomodation) {
            this.lonelyAccomodation = lonelyAccomodation;
        }

        public int getImprovementOfConditions() {
            return improvementOfConditions;
        }

        public void setImprovementOfConditions(int improvementOfConditions) {
            this.improvementOfConditions = improvementOfConditions;
        }

        public int getAdaptation() {
            return adaptation;
        }

        public void setAdaptation(int adaptation) {
            this.adaptation = adaptation;
        }

        public int getCount1Group() {
            return count1Group;
        }

        public void setCount1Group(int count1Group) {
            this.count1Group = count1Group;
        }

        public int getCount2Group() {
            return count2Group;
        }

        public void setCount2Group(int count2Group) {
            this.count2Group = count2Group;
        }

        public int getCount3Group() {
            return count3Group;
        }

        public void setCount3Group(int count3Group) {
            this.count3Group = count3Group;
        }

        public int getCount4Group() {
            return count4Group;
        }

        public void setCount4Group(int count4Group) {
            this.count4Group = count4Group;
        }

        public void reset() {
            countAll = 0;//всего человек
            countArmchairStroller = 0;//количество колясочников
            lonelyAccomodation = 0;//количество одиноких
            improvementOfConditions = 0;//количество на улучшения
            adaptation = 0;// количество на приспособление
            count1Group = 0;
            count2Group = 0;
            count3Group = 0;
            count4Group = 0;
        }

        public void add(Integer all, Integer groupFirst, Integer groupSecond, Integer groupThree, Integer groupFour) {
            if (all != 0) countAll = countAll+all;
            if (groupFirst != 0) count1Group = count1Group + groupFirst;
            if (groupSecond != 0) count2Group = count2Group + groupSecond;
            if (groupThree != 0) count3Group = count3Group + groupThree;
            if (groupFour != 0) count4Group = count4Group + groupFour;
        }

        public void add(Integer all) {
            if (all != 0) countAll = countAll+all;
        }

        public void addFirstGroup(Integer groupInvalidFirstGroupCount){
            if (groupInvalidFirstGroupCount !=0) count1Group = count1Group + groupInvalidFirstGroupCount;
        }

        public void addSecondGroup(Integer groupInvalidSecondGroupCount){
            if (groupInvalidSecondGroupCount !=0) count2Group = count2Group + groupInvalidSecondGroupCount;
        }

        public void addThreeGroup(Integer groupInvalidThreeGroupCount){
            if (groupInvalidThreeGroupCount !=0) count3Group = count3Group + groupInvalidThreeGroupCount;
        }

        public void addFourGroup(Integer groupInvalidFourGroupCount){
            if (groupInvalidFourGroupCount !=0) count4Group = count4Group + groupInvalidFourGroupCount;
        }

        public void addArmchairStroller(Integer countArmchair,Integer lonelySingle, Integer demendsCount, Integer adaptationCount){
            if (countArmchair !=0) countArmchairStroller = countArmchairStroller + countArmchair;
            if (lonelySingle !=0) lonelyAccomodation = lonelyAccomodation + lonelySingle;
            if (demendsCount != 0) improvementOfConditions = improvementOfConditions + demendsCount;
            if (adaptationCount !=0) adaptation = adaptation + adaptationCount;
        }
    }
}
