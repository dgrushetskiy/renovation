package ru.fx.develop.renovation.generator.writer;

import com.google.common.collect.Lists;
import org.apache.poi.util.Units;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableCol;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import ru.fx.develop.renovation.generator.GeneratorUtils;
import ru.fx.develop.renovation.service.GenerateService;
import ru.fx.develop.renovation.util.StringToBigDecimal;
import ru.fx.develop.renovation.util.UtilPrimaryRights;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeForDemolitionWriter extends RowWriter {
    @Autowired
    private GenerateService generateService;

    private TotalData totalData = new TotalData();


    public HomeForDemolitionWriter(XMLSlideShow pptx, XSLFSlide slide, XSLFTable table) {
        super(pptx, slide, table);
        this.prevTableRect = slide.getShapes().get(1).getAnchor().getBounds();
//        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
//        table.getCTTable().getTrList().stream()
//                .forEach(tr -> tr.setH(GeneratorUtils.calculateRowHeight(tr, cellsWidth)));
//        if (needTableBreak()) {
//            sliceTable();
//        }
    }

    @Override
    public void writeRow(Map model) {

        String houseString = (String) model.get("house");
        Long house = UtilPrimaryRights.getStringToLong(houseString);
        if (totalData.getHouse() != null && !totalData.getHouse().equals(house)) {//
            writeTotal();
            totalData.reset();
        }

        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(1));

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));


        BigDecimal square;
        String squareOFormString = (String) model.get("sqrOForm");
        String sqrOFormHouseString = (String) model.get("sqrOFormHouse");


        BigDecimal squareOForm = StringToBigDecimal.toBigDecimal(squareOFormString);
        //System.out.println("squareOForm = " + squareOForm);
        BigDecimal sqrOFormHouse = squareOForm;//

        String address = (String) model.get("address");
        BigDecimal squareSobRF = (BigDecimal) model.get("squareSobRF");
        BigDecimal squareMoscow = (BigDecimal) model.get("squareMoscow");
        BigDecimal squareFizLandUrL = (BigDecimal) model.get("squareFizLandUrL");
        BigDecimal squareAll = sqrOFormHouse;
        ;

        String vidPrava = (String) model.get("vidPrava");
        String vidPravaDol = UtilPrimaryRights.getStringToString(vidPrava);
        String vidPravaSobstvennost = UtilPrimaryRights.getStringToStringSobstven(vidPrava);
        String vidPravaSovmestSobstven = UtilPrimaryRights.getStringToStringSovmSobstven(vidPrava);
        String vidPravaMoscow = UtilPrimaryRights.getStringToStringSobstvenMoscow(vidPrava);
        String nameSubject = (String) model.get("nameSubject");


        if (vidPrava.equals("Собственность Российской федерации") || vidPrava.equals("ФГУП Почта РФ") || vidPrava.equals("ПАО СБЕРБАНК России")) {
            newRow.set(table.getCTTable().getTrArray(2));
            //GeneratorUtils.addCellText(newRow, 0, model.get("count"));//model.get("unom") + "-" +
            GeneratorUtils.addCellText(newRow, 1, address);
            GeneratorUtils.addCellText(newRow, 2, vidPrava);
            GeneratorUtils.addCellText(newRow, 3, squareOForm);
            squareSobRF = squareOForm;

        }
        if (vidPrava.equals("Собственность г.Москвы")) {
            newRow.set(table.getCTTable().getTrArray(1));
            String vidPravaSR = (String) model.get("vidPrava");
            String vidPravaRed = (String) model.get("vidPravaRed");
            String nameSubjectSc = (String) model.get("nameSubject");
            //  GeneratorUtils.addCellText(newRow, 0,  model.get("count"));//model.get("unom") + "-" +
            GeneratorUtils.addCellText(newRow, 1, address);
            GeneratorUtils.addCellText(newRow, 2, vidPrava + " : " + nameSubject);
            GeneratorUtils.addCellText(newRow, 3, squareOForm);
            squareMoscow = squareOForm;
        } else {
            //  GeneratorUtils.addCellText(newRow, 0, model.get("count"));//model.get("unom") + "-" +
            GeneratorUtils.addCellText(newRow, 1, address);
            if (vidPravaDol.equals("") || vidPravaSobstvennost.equals("") || vidPravaSovmestSobstven.equals("")) {
                GeneratorUtils.addCellText(newRow, 2, nameSubject);
                squareFizLandUrL = squareOForm;
            } else {
                GeneratorUtils.addCellText(newRow, 2, vidPrava);
            }
        }
        GeneratorUtils.addCellText(newRow, 3, squareOForm);


        if (needTableBreak()) {
            table.getCTTable().removeTr(table.getCTTable().sizeOfTrArray() - 1);
            sliceTable();
            writeRow(model);
        } else {
            if (totalData.getHouse() == null) totalData.setHouse(house);
            // totalData.addSquare(square);
            totalData.addTotalSumHouse(sqrOFormHouse);
            totalData.addTotalAll(squareAll);
            totalData.addSquareRF(squareSobRF);
            totalData.addSquareMoscow(squareMoscow);
            totalData.addSquareFizAndYourFace(squareFizLandUrL);
        }
    }

    private void mergeCells() {
        int rowLength = table.getCTTable().getTrArray().length;
//        rowLength++;
        System.out.println("rowLength : " + rowLength);
        if (rowLength > 10) {
            int i = rowLength - 1;
            System.out.println(" первый шаг i = " + i);
            String lastAddress = GeneratorUtils.getCellText(table.getCTTable().getTrArray(i), 1);
            String lastAddressReturn = lastAddress.substring(0);//, lastSqOForm.lastIndexOf("-")
            String curAddress = lastAddressReturn;

            System.out.println(lastAddressReturn.equals(curAddress) && i >= 10);
            while (lastAddressReturn.equals(curAddress) && i >= 10) {
                System.out.println("выбор строки i-1= " + (i - 1));
                String curAddressNext = GeneratorUtils.getCellText(table.getCTTable().getTrArray(i - 1), 1);
                System.out.println("i-1= " + (i - 1));
                curAddress = !curAddressNext.trim().isEmpty() ?
                        curAddressNext.substring(0) : //, curSqOFormNext.lastIndexOf("-")
                        curAddressNext;
                System.out.println("i = " + i);
                i--;
                System.out.println("i-- = " + i);
            }

            final int firstRowInd = i + 1;
            System.out.println("firstRowInd i + y = " + firstRowInd);

            System.out.println("соединяем строки: ");
            System.out.println("rowLength = " + rowLength + " - " + "firstRowInd = " + firstRowInd);
            table.getCTTable().getTrArray()[firstRowInd].getTcArray()[0].setRowSpan(rowLength - firstRowInd);
            table.getCTTable().getTrArray()[firstRowInd].getTcArray()[1].setRowSpan(rowLength - firstRowInd);

            table.getCTTable().getTrList().stream()
                    .filter(tr -> table.getCTTable().getTrList().indexOf(tr) > firstRowInd)
                    .forEach(tr -> GeneratorUtils.addCellText(tr, 0, ""));
            List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
            table.getCTTable().getTrList().stream()
                    .filter(tr -> table.getCTTable().getTrList().indexOf(tr) >= firstRowInd)
                    .forEach(tr -> tr.setH(GeneratorUtils.calculateRowHeight(tr, cellsWidth)));
            //i++;

        }

    }

    private void mergeCellsTotal() {
        String curAddress = "";
        int i = 0, j = 0;
        List<CTTableRow> list = table.getCTTable().getTrList();
        ctTableRowArray(curAddress, i, j, list, table.getCTTable());
    }

    @Override
    public void writeTotal() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(4));
        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumHouse());
    }

    public void writeTotalRF() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(5));
        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumSobRF());
    }

    public void writeTotalMoscow() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(6));

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumMoscow());
    }

    public void writeTotalFizYurFace() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(7));

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumFizLandUrL());
    }

    public void writeTotalAll() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(8));

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSumAll());
    }


    @Override
    public void writeNoData() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(3));//выбирается строка для заполнения
        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
        GeneratorUtils.addCellText(newRow, 0, "В сносимых домах искомое нежилье отсутствует");
    }

    @Override
    public void sliceTable() {
        endSlideTable();

        XSLFSlide newSlide = pptx.createSlide();
        newSlide.importContent(pptx.getSlides().get(0));

        XSLFAutoShape captionShape = (XSLFAutoShape) newSlide.getShapes().get(0);
        prevTableRect = captionShape.getAnchor().getBounds();
        captionShape.getXmlObject().set(slide.getShapes().get(0).getXmlObject().copy());

//        Integer lastPageNumber = Integer.parseInt(((XSLFTextBox) slide.getShapes().get(6)).getText());
//        XSLFTextBox pageNumberBox = (XSLFTextBox) newSlide.getShapes().get(6);
//        pageNumberBox.setText("" + (lastPageNumber + 1));

        slide = newSlide;
        table = (XSLFTable) slide.getShapes().get(1);

        if (shapesToDelete.get(slide) != null) {
            shapesToDelete.get(slide).add(slide.getShapes().get(0));
        } else {
            shapesToDelete.put(slide, Lists.newArrayList(slide.getShapes().get(0)));
        }
    }

    @Override
    public int getCaptionRowsNumber() {
        return 1;
    }

    @Override
    public void endSlideTable() {
        for (int i = 9; i > 0; i--) table.getCTTable().removeTr(i);
        mergeCellsTotal();

        table.setAnchor(new Rectangle(Double.valueOf(prevTableRect.getX()).intValue(),
                Double.valueOf(prevTableRect.getY()).intValue(),
                Double.valueOf(table.getAnchor().getWidth()).intValue(),
                Double.valueOf(table.getCTTable().getTrList().stream().mapToDouble(CTTableRow::getH).sum() / Units.EMU_PER_POINT).intValue()
        ));

        if (table.getCTTable().getTrList().size() == getCaptionRowsNumber()) {
            if (shapesToDelete.get(slide) != null) {
                shapesToDelete.get(slide).add(slide.getShapes().get(1));
            } else {
                shapesToDelete.put(slide, Lists.newArrayList(
                        slide.getShapes().get(1)
                ));
            }
        }
    }

    private void endSlideTotal(int i) {
        table.getCTTable().removeTr(i - 1);

        table.setAnchor(new Rectangle(Double.valueOf(prevTableRect.getX()).intValue(),
                Double.valueOf(prevTableRect.getY()).intValue(),
                Double.valueOf(table.getAnchor().getWidth()).intValue(),
                Double.valueOf(table.getCTTable().getTrList().stream().mapToDouble(CTTableRow::getH).sum() / Units.EMU_PER_POINT).intValue()
        ));

        if (table.getCTTable().getTrList().size() == getCaptionRowsNumber()) {
            if (shapesToDelete.get(slide) != null) {
                shapesToDelete.get(slide).add(slide.getShapes().get(1));
            } else {
                shapesToDelete.put(slide, Lists.newArrayList(
                        slide.getShapes().get(1)
                ));
            }
        }
    }

    public TotalData getTotalData() {
        return totalData;
    }

    public void setTotalData(TotalData totalData) {
        this.totalData = totalData;
    }


    private class TotalData {

        private Long house;

        private BigDecimal squareSumHouse = BigDecimal.ZERO;
        // private BigDecimal squareSum = BigDecimal.ZERO;
        private BigDecimal squareSumSobRF = BigDecimal.ZERO;
        private BigDecimal squareSumMoscow = BigDecimal.ZERO;
        private BigDecimal squareSumFizLandUrL = BigDecimal.ZERO;
        private BigDecimal squareSumAll = BigDecimal.ZERO;

        public Long getHouse() {
            return house;
        }

        public void setHouse(Long house) {
            this.house = house;
        }

        public BigDecimal getSquareSumHouse() {
            return squareSumHouse;
        }

        public void setSquareSumHouse(BigDecimal squareSumHouse) {
            this.squareSumHouse = squareSumHouse;
        }

        public BigDecimal getSquareSumSobRF() {
            return squareSumSobRF;
        }

        public void setSquareSumSobRF(BigDecimal squareSumSobRF) {
            this.squareSumSobRF = squareSumSobRF;
        }

        public BigDecimal getSquareSumMoscow() {
            return squareSumMoscow;
        }

        public void setSquareSumMoscow(BigDecimal squareSumMoscow) {
            this.squareSumMoscow = squareSumMoscow;
        }

        public BigDecimal getSquareSumFizLandUrL() {
            return squareSumFizLandUrL;
        }

        public void setSquareSumFizLandUrL(BigDecimal squareSumFizLandUrL) {
            this.squareSumFizLandUrL = squareSumFizLandUrL;
        }

        public BigDecimal getSquareSumAll() {
            return squareSumAll;
        }

        public void setSquareSumAll(BigDecimal squareSumAll) {
            this.squareSumAll = squareSumAll;
        }

        public void reset() {
            house = null;
            squareSumHouse = BigDecimal.ZERO;
        }

        public void resetSquareSumHouse() {
            squareSumHouse = BigDecimal.ZERO;
        }

        public void addTotalSumHouse(BigDecimal sqrOFormHouse) {
            if (sqrOFormHouse != null) squareSumHouse = squareSumHouse.add(sqrOFormHouse);
        }

        public void addTotalAll(BigDecimal squareAll) {
            if (squareAll != null) squareSumAll = squareSumAll.add(squareAll);
        }

        public void add(BigDecimal squareSobRF, BigDecimal squareFizLandUrL, BigDecimal squareAll) {
            //  if (square != null) squareSum = squareSum.add(square);
            if (squareSobRF != null) squareSumSobRF = squareSumSobRF.add(squareSobRF);
            if (squareFizLandUrL != null) squareSumFizLandUrL = squareSumFizLandUrL.add(squareFizLandUrL);
            if (squareAll != null) squareSumAll = squareSumAll.add(squareAll);
        }

        public void addSquareRF(BigDecimal squareSobRF) {
            if (squareSobRF != null) squareSumSobRF = squareSumSobRF.add(squareSobRF);
        }

        public void addSquareFizAndYourFace(BigDecimal squareFizLandUrL) {
            if (squareFizLandUrL != null) squareSumFizLandUrL = squareSumFizLandUrL.add(squareFizLandUrL);
        }

        public void addSquareMoscow(BigDecimal squareMoscow) {
            if (squareMoscow != null) squareSumMoscow = squareSumMoscow.add(squareMoscow);
        }
    }
}
