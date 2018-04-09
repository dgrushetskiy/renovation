package ru.fx.develop.renovation.generator.writer;

import com.google.common.collect.Lists;
import org.apache.poi.util.Units;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableCol;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableRow;
import ru.fx.develop.renovation.generator.GeneratorUtils;
import ru.fx.develop.renovation.util.StringToBigDecimal;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VeteranOrgWriter extends RowWriter {

    private TotalDataCount totalData = new TotalDataCount();

    public VeteranOrgWriter(XMLSlideShow pptx, XSLFSlide slide, XSLFTable table) {
        super(pptx, slide, table);
        this.prevTableRect = slide.getShapes().get(5).getAnchor().getBounds();
    }

    public void writeRow(Map model) {
        if (totalData == null) {
            writeTotal();
            totalData.reset();
        }

        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(1));

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

        BigDecimal square = (BigDecimal) model.get("square");
        String squareOFormString = (String) model.get("sqrOForm");
        BigDecimal squareOForm = StringToBigDecimal.toBigDecimal(squareOFormString);

        square = squareOForm;

        GeneratorUtils.addCellText(newRow, 0, model.get("count"));
        GeneratorUtils.addCellText(newRow, 1, model.get("address"));
        GeneratorUtils.addCellText(newRow, 2, model.get("vidPrava"));
        GeneratorUtils.addCellText(newRow, 3, squareOForm);
        //mergeCells();

        if (needTableBreak()) {
            table.getCTTable().removeTr(table.getCTTable().sizeOfTrArray() - 1);
            sliceTable();
            writeRow(model);
        } else {
            totalData.add(square);
        }
    }


    @Override
    public void writeTotal() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(3));

        GeneratorUtils.addCellText(newRow, 3, totalData.getSquareSum());

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));
    }

    @Override
    public void writeNoData() {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(2));//выбирается строка для заполнения
        GeneratorUtils.addCellText(newRow, 0, "В сносимых домах ветеранские организации отсутствуют");
    }

    public void endSlideTable() {
        table.getCTTable().removeTr(3);
        table.getCTTable().removeTr(2);
        table.getCTTable().removeTr(1);

        // table.getCTTable().addNewTr().getTcArray(6);


        table.setAnchor(new Rectangle(Double.valueOf(prevTableRect.getX()).intValue(),
                Double.valueOf(prevTableRect.getY()).intValue(),
                Double.valueOf(table.getAnchor().getWidth()).intValue(),
                Double.valueOf(table.getCTTable().getTrList().stream().mapToDouble(CTTableRow::getH).sum() / Units.EMU_PER_POINT).intValue()
        ));
    }

    @Override
    public void sliceTable() {
        endSlideTable();

        XSLFSlide newSlide = pptx.createSlide();
        newSlide.importContent(pptx.getSlides().get(0));

        XSLFAutoShape captionShape = (XSLFAutoShape) newSlide.getShapes().get(0);
        prevTableRect = captionShape.getAnchor().getBounds();
        captionShape.getXmlObject().set(slide.getShapes().get(0).getXmlObject().copy());

//        Integer lastPageNumber = Integer.parseInt(((XSLFTextBox) slide.getShapes().get(5)).getText());
//        XSLFTextBox pageNUmberBox = (XSLFTextBox) newSlide.getShapes().get(1);
//        pageNUmberBox.setText("" + (lastPageNumber + 1));

        slide = newSlide;
        table = (XSLFTable) slide.getShapes().get(5);

        if (shapesToDelete.get(slide) != null) {
            shapesToDelete.get(slide).add(slide.getShapes().get(4));
        } else {
            shapesToDelete.put(slide, Lists.newArrayList(slide.getShapes().get(4)));
        }
    }

    @Override
    public int getCaptionRowsNumber() {
        return 1;
    }

    public TotalDataCount getTotalData() {
        return totalData;
    }

    private class TotalDataCount {
        private BigDecimal squareSum = BigDecimal.ZERO;

        public BigDecimal getSquareSum() {
            return squareSum;
        }

        public void setSquareSum(BigDecimal squareSum) {
            this.squareSum = squareSum;
        }

        public void reset() {

            squareSum = BigDecimal.ZERO;
        }

        public void add(BigDecimal square) {
            if (square != null) squareSum = squareSum.add(square);
        }
    }
}
