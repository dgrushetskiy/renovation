package ru.fx.develop.renovation.generator;

import org.apache.poi.util.Units;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableCell;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableRow;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.math.BigDecimal;
import java.text.AttributedString;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GeneratorUtils {

    public static final Locale RU = new Locale("ru");

    public static void addCellText(CTTableRow newRow, int index, String text) {
        CTTextParagraph p = newRow.getTcArray(index).getTxBody().getPArray(0);
        CTRegularTextRun r = p.getRArray().length > 0 ? p.getRArray(0) : p.addNewR();
        r.setT(text != null ? text : "");
    }

    public static void addCellText(CTTableRow newRow, int index, String text, byte[] fillColor) {
        CTTableCell cell = newRow.getTcArray(index);
        cell.getTcPr().unsetNoFill();
        cell.getTcPr().addNewSolidFill().addNewSrgbClr().setVal(fillColor);
        CTTextParagraph p = cell.getTxBody().getPArray(0);
        CTRegularTextRun r = p.getRArray().length > 0 ? p.getRArray(0) : p.addNewR();
        r.setT(text != null ? text : "");
    }

    public static void addCellText(CTTableRow newRow, int index, Object obj) {
        CTTextParagraph p = newRow.getTcArray(index).getTxBody().getPArray(0);
        CTRegularTextRun r = p.getRArray().length > 0 ? p.getRArray(0) : p.addNewR();
        String text = "";
        if (obj != null) {
            if (obj instanceof BigDecimal) text = isIntegerValue((BigDecimal) obj) ?
                    NumberFormat.getIntegerInstance(RU).format(obj) :
                    NumberFormat.getNumberInstance(RU).format(obj);
            else text = obj.toString();
        }
        r.setT(text);
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    public static String getCellText(CTTableRow row, int cellIndex) {
        return row.getTcArray(cellIndex).getTxBody().getPArray(0).getRArray(0).getT();
    }

    public static String getCellText(CTTableCell cell) {
        if (cell.getTxBody().getPList().isEmpty()) return "";
        if (cell.getTxBody().getPArray(0).getRList().isEmpty()) return "";
        return cell.getTxBody().getPArray(0).getRArray(0).getT();
    }

    public static BigDecimal getCellTextAsBigDecimal(CTTableRow row, int cellIndex) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            result = new BigDecimal(row.getTcArray(cellIndex).getTxBody().getPArray(0).getRArray(0).getT());
        } catch (NumberFormatException ex) {
        }
        return result;
    }

    public static int calculateTextLines(String text, long cellWidth, int style) {
        if (text == null || text.isEmpty()) return 1;

        Font currFont = new Font("Times New Roman", style, 10);
        AttributedString attrStr = new AttributedString(text);
        attrStr.addAttribute(TextAttribute.FONT, currFont);

        FontRenderContext frc = new FontRenderContext(null, true, true);
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        int nextPos = 0;
        int lineCnt = 0;
        while (measurer.getPosition() < text.length()) {
            nextPos = measurer.nextOffset(cellWidth / Units.EMU_PER_POINT);
            lineCnt++;
            measurer.setPosition(nextPos);
        }
        return lineCnt;
    }

    public static int calculateTextLines(String text, long cellWidth) {
        return calculateTextLines(text, cellWidth, 0);
    }

    public static long calculateRowHeight(CTTableRow row, List<Long> cellsWidth) {
        final long[] maxHeight = {0};
        row.getTcList().stream()
                .forEach(tc -> {
                    Long cellWidth = cellsWidth.get(row.getTcList().indexOf(tc));
                    if (tc.isSetGridSpan()) {
                        cellWidth = cellsWidth.subList(row.getTcList().indexOf(tc), row.getTcList().indexOf(tc) + tc.getGridSpan())
                                .stream()
                                .mapToLong(Long::longValue)
                                .sum();
                    }
                    int lines = calculateTextLines(getCellText(tc), cellWidth);
                    if (tc.isSetRowSpan()) {
                        lines = Math.max(lines - tc.getRowSpan() + 1, 1);
                    }
                    long curHeight = (PptxAlbumGenerator.TABLE_DEFAULT_ROW_HEIGHT + PptxAlbumGenerator.CELL_MARGIN_TOP + PptxAlbumGenerator.CELL_MARGIN_BOTTOM) * lines;
                    if (curHeight > maxHeight[0]) maxHeight[0] = curHeight;
                });
        return maxHeight[0];
    }
}
