package ru.fx.develop.renovation.generator.writer;

import com.google.common.collect.Lists;
import org.apache.poi.util.Units;
import org.apache.poi.xslf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableCol;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableRow;
import ru.fx.develop.renovation.generator.GeneratorUtils;
import ru.fx.develop.renovation.service.GenerateService;
import ru.fx.develop.renovation.util.StringToBigDecimal;
import ru.fx.develop.renovation.util.UtilPrimaryRights;

import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeForDemolitionWriter extends RowWriter{

    private TotalData totalData = new TotalData();


    public HomeForDemolitionWriter(XMLSlideShow pptx, XSLFSlide slide, XSLFTable table) {
        super(pptx, slide, table);
        this.prevTableRect = slide.getShapes().get(1).getAnchor().getBounds();
        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        table.getCTTable().getTrList().stream()
                .forEach(tr -> tr.setH(GeneratorUtils.calculateRowHeight(tr, cellsWidth)));
        if(needTableBreak()){
            sliceTable();
        }
    }
    @Override
    public void writeRow(Map model){
//        if (totalData != null){
//            writeTotal();
//            writeTotalRF();
//            writeTotalFizYurFace();
//            writeTotalAll();
//            totalData.reset();
//        }
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(1));



        BigDecimal square = (BigDecimal)model.get("square");
        String squareOFormString = (String) model.get("sqrOForm");
        BigDecimal squareOForm = StringToBigDecimal.toBigDecimal(squareOFormString);

        BigDecimal squareSobRF = (BigDecimal) model.get("squareSobRF");
        BigDecimal squareMoscow = (BigDecimal) model.get("squareMoscow");
        BigDecimal squareFizLandUrL = (BigDecimal) model.get("squareFizLandUrL");
        BigDecimal squareAll = (BigDecimal) model.get("squareAll");

        String vidPrava = (String)  model.get("vidPrava");
        String vidPravaDol = UtilPrimaryRights.getStringToString(vidPrava);
        String vidPravaSobstvennost = UtilPrimaryRights.getStringToStringSobstven(vidPrava);
        String vidPravaSovmestSobstven = UtilPrimaryRights.getStringToStringSovmSobstven(vidPrava);
        String vidPravaMoscow = UtilPrimaryRights.getStringToStringSobstvenMoscow(vidPrava);
        String nameSubject = (String)  model.get("nameSubject");

       // if (squareOForm != null) squareOForm = squareOForm.add(squareOForm);
        square = squareOForm;
        //if (squareSobRF == null) squareSobRF = squareSobRF.add(squareOForm);
       // if (squareFizLandUrL == null) squareFizLandUrL = squareFizLandUrL.add(squareOForm);
        //if (squareAll == null) squareAll = squareAll.add(square);
        squareAll = square;

        if (vidPrava.equals("Собственность Российской федерации")|| vidPrava.equals("ФГУП Почта РФ") || vidPrava.equals("ПАО СБЕРБАНК России")){
            newRow.set(table.getCTTable().getTrArray(2));
            GeneratorUtils.addCellText(newRow, 0, model.get("count"));
            GeneratorUtils.addCellText(newRow, 1, model.get("address"));
            GeneratorUtils.addCellText(newRow, 2,vidPrava);
            GeneratorUtils.addCellText(newRow, 3, squareOForm);
            squareSobRF = squareOForm;

        } if (vidPrava.equals("Собственность г.Москвы")){
            newRow.set(table.getCTTable().getTrArray(1));
            String vidPravaSR = (String) model.get("vidPrava");
            String vidPravaRed = (String) model.get("vidPravaRed");
            String nameSubjectSc = (String) model.get("nameSubject");
            GeneratorUtils.addCellText(newRow, 0, model.get("count"));
            GeneratorUtils.addCellText(newRow, 1, model.get("address"));
            GeneratorUtils.addCellText(newRow, 2,vidPrava + " : " + nameSubject);
            GeneratorUtils.addCellText(newRow, 3, squareOForm);
            squareMoscow = squareOForm;
        }else {
            GeneratorUtils.addCellText(newRow, 0, model.get("count"));
            GeneratorUtils.addCellText(newRow, 1, model.get("address"));
            if (vidPravaDol.equals("") || vidPravaSobstvennost.equals("") || vidPravaSovmestSobstven.equals("")){
                GeneratorUtils.addCellText(newRow, 2, nameSubject);
                squareFizLandUrL = squareOForm;
            }
            else {
                GeneratorUtils.addCellText(newRow, 2,vidPrava);
            }
        }
        GeneratorUtils.addCellText(newRow, 3, squareOForm);

       mergeCells();

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));



        if (needTableBreak()) {
            table.getCTTable().removeTr(table.getCTTable().sizeOfTrArray() - 1);
            sliceTable();
            writeRow(model);
        }
        else {

            totalData.addSquare(square);
            totalData.addTotalAll(squareAll);
            totalData.addSquareRF(squareSobRF);
            totalData.addSquareMoscow(squareMoscow);
            totalData.addSquareFizAndYourFace(squareFizLandUrL);

        }

//        writeTotal();
//        totalData.resetSquare();
       // mergeCells();
    }



    public void mergeCells(){
        int rowLength = table.getCTTable().getTrArray().length;

        if (rowLength > 9){

            int i = rowLength - (rowLength -9);
            String lastSqOForm = GeneratorUtils.getCellText(table.getCTTable().getTrArray(i), 3);
            String lastSqOFormReturn = lastSqOForm.substring(0);
            String curSqOForm = lastSqOFormReturn;

            while (lastSqOFormReturn.equals(curSqOForm) && i >= 9){
                String curSqOFormNext = GeneratorUtils.getCellText(table.getCTTable().getTrArray(i - 11 ), 3);
                curSqOForm = !curSqOFormNext.trim().isEmpty() ?
                        curSqOFormNext.substring(0) :
                        curSqOFormNext;
                i--;
                System.out.println("i = " + i);
            }
            final int firstRowInd = i + 1;
            System.out.println("firstRowInd =: " + firstRowInd);


            table.getCTTable().getTrArray()[firstRowInd].getTcArray()[0].setRowSpan(rowLength - firstRowInd);
            table.getCTTable().getTrArray()[firstRowInd].getTcArray()[1].setRowSpan(rowLength - firstRowInd);

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
        newRow.set(table.getCTTable().getTrArray(4));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSum());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    public void writeTotalRF() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(5));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumSobRF());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

    }

    public void writeTotalMoscow(){
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(6));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumMoscow());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    public void writeTotalFizYurFace() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(7));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumFizLandUrL());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    public void writeTotalAll() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(8));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumAll());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }


    @Override
    public void writeNoData() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(3));//выбирается строка для заполнения
        GeneratorUtils.addCellText(newRow, 0, "В сносимых домах искомое нежилье отсутствует");
    }

    @Override
    public void sliceTable() {
        endSlideTable();

        if (shapesToDelete.get(slide) != null) {
            shapesToDelete.get(slide).add(slide.getShapes().get(1));
        } else {
            shapesToDelete.put(slide, Lists.newArrayList(slide.getShapes().get(1)));
        }

        XSLFSlide newSlide = pptx.createSlide();
        newSlide.importContent(pptx.getSlides().get(0));

        XSLFAutoShape captionShape = (XSLFAutoShape) newSlide.getShapes().get(0);
        prevTableRect = captionShape.getAnchor().getBounds();
        captionShape.getXmlObject().set(slide.getShapes().get(0).getXmlObject().copy());

        Integer lastPageNumber = Integer.parseInt(((XSLFTextBox) slide.getShapes().get(1)).getText());
        XSLFTextBox pageNUmberBox = (XSLFTextBox) newSlide.getShapes().get(1);
        pageNUmberBox.setText("" + (lastPageNumber + 1));

        slide = newSlide;
        table = (XSLFTable) slide.getShapes().get(1);

        if (shapesToDelete.get(slide) != null) {
            shapesToDelete.get(slide).add(slide.getShapes().get(1));
        } else {
            shapesToDelete.put(slide, Lists.newArrayList(slide.getShapes().get(1)));
        }
    }

    @Override
    public int getCaptionRowsNumber() {
        return 1;
    }
    @Override
    public  void endSlideTable() {
        table.getCTTable().removeTr(8);
        table.getCTTable().removeTr(7);
        table.getCTTable().removeTr(6);
        table.getCTTable().removeTr(5);
        table.getCTTable().removeTr(4);
        table.getCTTable().removeTr(3);
        table.getCTTable().removeTr(2);
        table.getCTTable().removeTr(1);

        // table.getCTTable().addNewTr().getTcArray(6);


        table.setAnchor(new Rectangle(Double.valueOf(prevTableRect.getX()).intValue(),
                Double.valueOf(prevTableRect.getY()).intValue(),
                Double.valueOf(table.getAnchor().getWidth()).intValue(),
                Double.valueOf(table.getCTTable().getTrList().stream().mapToDouble(CTTableRow::getH).sum() / Units.EMU_PER_POINT).intValue()
        ));

        if(table.getCTTable().getTrList().size() == getCaptionRowsNumber()){
            if (shapesToDelete.get(slide) != null){
                shapesToDelete.get(slide).add(slide.getShapes().get(1));
            } else {
                shapesToDelete.put(slide, Lists.newArrayList(
                        slide.getShapes().get(1)
                ));
            }
        }
    }

    public void setTotalData(TotalData totalData) {
        this.totalData = totalData;
    }

    public TotalData getTotalData() {
        return totalData;
    }

    private class TotalData {

        private String name = null;
        private BigDecimal squareSum = BigDecimal.ZERO;
        private BigDecimal squareSumSobRF = BigDecimal.ZERO;
        private BigDecimal squareSumMoscow = BigDecimal.ZERO;
        private BigDecimal squareSumFizLandUrL = BigDecimal.ZERO;
        private BigDecimal squareSumAll = BigDecimal.ZERO;

        public void setName(String name) {
            this.name = name;
        }

        public void setSquareSum(BigDecimal squareSum) {
            this.squareSum = squareSum;
        }

        public void setSquareSumSobRF(BigDecimal squareSumSobRF) {
            this.squareSumSobRF = squareSumSobRF;
        }

        public void setSquareSumFizLandUrL(BigDecimal squareSumFizLandUrL) {
            this.squareSumFizLandUrL = squareSumFizLandUrL;
        }

        public void setSquareSumAll(BigDecimal squareSumAll) {
            this.squareSumAll = squareSumAll;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getSquareSum() {
            return squareSum;
        }

        public BigDecimal getSquareSumSobRF() {
            return squareSumSobRF;
        }

        public BigDecimal getSquareSumMoscow() {
            return squareSumMoscow;
        }

        public BigDecimal getSquareSumFizLandUrL() {
            return squareSumFizLandUrL;
        }

        public BigDecimal getSquareSumAll() {
            return squareSumAll;
        }

        public void reset() {
            name = null;
            squareSum = BigDecimal.ZERO;
            squareSumSobRF = BigDecimal.ZERO;
            squareSumMoscow = BigDecimal.ZERO;
            squareSumFizLandUrL = BigDecimal.ZERO;
            squareSumAll = BigDecimal.ZERO;
        }

        public void resetSquare(){
            squareSum = BigDecimal.ZERO;
        }

        public void addSquare(BigDecimal square) {
            if (square != null) squareSum = squareSum.add(square);
        }

        public void addTotalAll(BigDecimal squareAll) {
            if (squareAll != null) squareSumAll = squareSumAll.add(squareAll);
        }

        public void add(BigDecimal square, BigDecimal squareSobRF, BigDecimal squareFizLandUrL, BigDecimal squareAll) {
            if (square != null) squareSum = squareSum.add(square);
            if (squareSobRF != null) squareSumSobRF = squareSumSobRF.add(squareSobRF);
            if (squareFizLandUrL != null) squareSumFizLandUrL = squareSumFizLandUrL.add(squareFizLandUrL);
            if (squareAll != null) squareSumAll = squareSumAll.add(squareAll);
        }

        public void addSquareRF(BigDecimal squareSobRF) {
            if (squareSobRF !=null) squareSumSobRF = squareSumSobRF.add(squareSobRF);
        }

        public void addSquareFizAndYourFace(BigDecimal squareFizLandUrL) {
            if (squareFizLandUrL != null) squareSumFizLandUrL = squareSumFizLandUrL.add(squareFizLandUrL);
        }

        public void addSquareMoscow(BigDecimal squareMoscow) {
            if (squareMoscow != null) squareSumMoscow = squareSumMoscow.add(squareMoscow);
        }
    }
}
