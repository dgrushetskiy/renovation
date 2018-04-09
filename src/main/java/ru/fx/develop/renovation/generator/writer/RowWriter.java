package ru.fx.develop.renovation.generator.writer;

import org.apache.poi.util.Units;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTable;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableCol;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableRow;
import ru.fx.develop.renovation.generator.GeneratorUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class RowWriter {

    protected XMLSlideShow pptx;
    protected XSLFSlide slide;
    protected XSLFTable table;
    protected Rectangle prevTableRect;
    protected Map<XSLFSlide, List<XSLFShape>> shapesToDelete = new HashMap<>();
    protected double dataHeight;

    public RowWriter(XMLSlideShow pptx, XSLFSlide slide, XSLFTable table) {
        this.pptx = pptx;
        this.slide = slide;
        this.table = table;
    }

    public XMLSlideShow getPptx() {
        return pptx;
    }

    public XSLFSlide getSlide() {
        return slide;
    }

    public XSLFTable getTable() {
        return table;
    }

    public Rectangle getPrevTableRect() {
        return prevTableRect;
    }

    public Map<XSLFSlide, List<XSLFShape>> getShapesToDelete() {
        return shapesToDelete;
    }

    public double getDataHeight() {
        return dataHeight;
    }

    public boolean needTableBreak() {
        dataHeight = prevTableRect.getY() + prevTableRect.getHeight() + table.getCTTable().getTrList().stream().mapToDouble(CTTableRow::getH).sum() / Units.EMU_PER_POINT;
        return dataHeight > pptx.getPageSize().getHeight();
    }

    public void writeRow(Map model) {
        CTTableRow newRow = table.getCTTable().addNewTr();
        newRow.set(table.getCTTable().getTrArray(1));
        GeneratorUtils.addCellText(newRow, 0, model.get("count"));
        GeneratorUtils.addCellText(newRow, 1, model.get("address"));
        GeneratorUtils.addCellText(newRow, 2, model.get("vidPrava"));
        GeneratorUtils.addCellText(newRow, 3, model.get("sqrOForm"));
        //mergeCells();

        List<Long> cellsWidth = table.getCTTable().getTblGrid().getGridColList().stream().map(CTTableCol::getW).collect(Collectors.toList());
        newRow.setH(GeneratorUtils.calculateRowHeight(newRow, cellsWidth));

        if (needTableBreak()) {
            table.getCTTable().removeTr(table.getCTTable().sizeOfTrArray() - 1);
            sliceTable();
            writeRow(model);
        }
    }

    static void ctTableRowArray(String curAddress, int i, int j, List<CTTableRow> list, CTTable ctTable) {
        for (CTTableRow row : list) {
            if (row.equals(ctTable.getTrArray(0))) continue;
            if (row.getTcArray(1).getTxBody().getPArray(0).getRArray().length > 0) {
                String address = GeneratorUtils.getCellText(row, 1);
                if (!address.equals(curAddress)) {
                    i++;
                    j = list.indexOf(row);
                    curAddress = address;
                } else {
                    CTTableRow r = ctTable.getTrArray(j);
                    int k = list.indexOf(row);
                    r.getTcArray(0).setRowSpan(1 + k - j);
                    r.getTcArray(1).setRowSpan(1 + k - j);
                }
                GeneratorUtils.addCellText(row, 0, i);
            }
        }
    }

    public abstract void writeTotal();

    public abstract void writeNoData();

    public abstract void endSlideTable();


    public abstract void sliceTable();

    public abstract int getCaptionRowsNumber();
}
