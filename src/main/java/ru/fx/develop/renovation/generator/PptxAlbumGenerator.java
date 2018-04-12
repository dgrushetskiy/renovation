package ru.fx.develop.renovation.generator;

import javafx.collections.ObservableList;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.fx.develop.renovation.generator.writer.DisabledPersonWriter;
import ru.fx.develop.renovation.generator.writer.HomeForDemolitionWriter;
import ru.fx.develop.renovation.generator.writer.VeteranOrgWriter;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.service.GenerateService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.prefs.Preferences;

@Component
public class PptxAlbumGenerator {
    public static final int TEMPLATE_SLIDES_NUMBER = 1;
    public static final int SHAPE_TABLE_1 = 0;
    public static final int CELL_MARGIN_TOP = 12700;
    public static final int CELL_MARGIN_BOTTOM = 12700;
    public static final int TABLE_MARGIN_TOP = 20;
    public static final int TABLE_DEFAULT_ROW_HEIGHT = 152995;
    private static final Preferences prefs = Preferences.userNodeForPackage(PptxAlbumGenerator.class);
    public static final String FILE_EXTENSION = ".pptx";
    private static XMLSlideShow pptx;
    @Autowired
    private GenerateService generateService;
    @Value("classpath:templates/report.pptx")
    private Resource reportTemplate;
    private XSLFSlide currentSlide;
    private List<ContentsData> contentsData;
    private TableData tableData;
    private Map<XSLFSlide, List<XSLFShape>> shapesToDelete = new HashMap<>();

    public TableData getTableData() {
        return tableData;
    }

    public void generateReport(ObservableList<House> houseData) {
        try {
            contentsData = new ArrayList<>();
            pptx = new XMLSlideShow(reportTemplate.getInputStream());
            reportTemplate.getInputStream().close();
            //создаем второй слайд
            currentSlide = pptx.createSlide();
            //импорт слайда с фигурами
            currentSlide.importContent(pptx.getSlides().get(0));

            createSlideForHome(pptx, houseData);
            String filePathDirOutput = getFilePathDirOutput() + '\\';

            for (int i = 0; i < 1; i++) {
                pptx.removeSlide(0);
            }
            houseData.forEach(house -> {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
                File file = new File(filePathDirOutput + house.getAddress() + " дата файла " + dateFormat.format(new Date()) + FILE_EXTENSION);
                file.getParentFile().mkdirs();
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    pptx.write(outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Create file: " + file.getAbsolutePath());
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getFilePathDirOutput() {
        String path = prefs.get("filePathDirOutput", null);
        return path != null && new File(path).exists() ? path : null;
    }

    public static void setFilePathDirOutput(String path) {
        prefs.put("filePathDirOutput", path);
    }



    private void createSlideForHome(XMLSlideShow pptx, ObservableList<House> houseData) {//Integer contentsSlidesNumber
        try {
            tableData = new TableData();
            tableData.setHomeForDemolitionPrimaryRightResult(generateService.dataPrimaryRight(houseData));//showDataHomeForDemolitionPrimary(houseDem));

            //  tableData.setHomeForDemolitionSecondaryRightResult(generateService.dataSecondRight(houseData));
            //  tableData.setHomeForDemolitionSecondaryRightResult(generateService.showDataHomeForDemolitionSecondary(houseDem));
            tableData.setVeteransOrgResult(generateService.dataVeteransOrg(houseData));
            tableData.setDisabledPersonResult(generateService.dataDisabledPerson(houseData));

            Integer firstSlide = pptx.getSlides().indexOf(currentSlide);


            addTableNonHousing(pptx, tableData);

            addTableDisabledPerson(pptx, tableData);

            addTableVeteranOrg(pptx, tableData);

            Integer lastSlide = pptx.getSlides().indexOf(currentSlide);
            contentsData.add(new ContentsData(houseData.toString(), lastSlide - firstSlide + 1));

            shapesToDelete.keySet().stream()
                    .forEach(slide -> shapesToDelete.get(slide).stream()
                            .forEach(slide::removeShape));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addTableNonHousing(XMLSlideShow pptx, TableData tableData) {
        XSLFTable tableNonHousing = (XSLFTable) currentSlide.getShapes().get(1);
        HomeForDemolitionWriter rowWriter = new HomeForDemolitionWriter(pptx, currentSlide, tableNonHousing);
        if (tableData.getHomeForDemolitionPrimaryRightResult().isEmpty()) {
            rowWriter.writeNoData();
        } else {
            tableData.getHomeForDemolitionPrimaryRightResult()
                    .stream().forEach(rowWriter::writeRow);
            rowWriter.writeTotal();
            rowWriter.writeAllTotalsSquarm();
        }
        rowWriter.endSlideTable();

        rowWriter.getShapesToDelete().keySet().stream()
                .forEach(slide -> {
                    if (shapesToDelete.containsKey(slide)) {
                        shapesToDelete.get(slide).addAll(rowWriter.getShapesToDelete().get(slide));
                    } else {
                        shapesToDelete.put(slide, rowWriter.getShapesToDelete().get(slide));
                    }
                });
        currentSlide = rowWriter.getSlide();
    }

    private void addTableVeteranOrg(XMLSlideShow pptx, TableData tableData) {
        XSLFTable tableVeteranOrganisations = (XSLFTable) currentSlide.getShapes().get(5);
        VeteranOrgWriter veteranOrgWriter = new VeteranOrgWriter(pptx, currentSlide, tableVeteranOrganisations);
        if (tableData.getVeteransOrgResult().isEmpty()) {
            veteranOrgWriter.writeNoData();
        } else {
            tableData.getVeteransOrgResult()
                    .stream()
                    .forEach(veteranOrgWriter::writeRow);
            veteranOrgWriter.writeTotal();
        }

        veteranOrgWriter.endSlideTable();
        veteranOrgWriter.getShapesToDelete().keySet().stream()
                .forEach(slide -> {
                    if (shapesToDelete.containsKey(slide)) {
                        shapesToDelete.get(slide).addAll(veteranOrgWriter.getShapesToDelete().get(slide));
                    } else {
                        shapesToDelete.put(slide, veteranOrgWriter.getShapesToDelete().get(slide));
                    }
                });

        currentSlide = veteranOrgWriter.getSlide();
    }

    private void addTableDisabledPerson(XMLSlideShow pptx, TableData tableData) {
        XSLFTable tableDisablePeople = (XSLFTable) currentSlide.getShapes().get(3);
        DisabledPersonWriter disabledPersonWriter = new DisabledPersonWriter(pptx, currentSlide, tableDisablePeople);

        if (tableData.getDisabledPersonResult().isEmpty()) {
            disabledPersonWriter.writeNoData();
        } else {
            tableData.getDisabledPersonResult()
                    .stream()
                    .forEach(disabledPersonWriter::writeRow);
            disabledPersonWriter.writeCountGrups();
        }

        disabledPersonWriter.endSlideTable();
        disabledPersonWriter.getShapesToDelete().keySet().stream()
                .forEach(slide -> {
                    if (shapesToDelete.containsKey(slide)) {
                        shapesToDelete.get(slide).addAll(disabledPersonWriter.getShapesToDelete().get(slide));
                    } else {
                        shapesToDelete.put(slide, disabledPersonWriter.getShapesToDelete().get(slide));
                    }
                });

        currentSlide = disabledPersonWriter.getSlide();
    }

    public class TableData {
        List<Map> HomeForDemolitionPrimaryRightResult = new ArrayList<>();
        List<Map> HomeForDemolitionSecondaryRightResult = new ArrayList<>();
        List<Map> HomeForDemolitionShareHolderResult = new ArrayList<>();
        List<Map> DisabledPersonResult = new ArrayList<>();
        List<Map> VeteransOrgResult = new ArrayList<>();
        BigDecimal sqrOFormHouse;

        public BigDecimal getSqrOFormHouse() {
            return sqrOFormHouse;
        }

        public void setSqrOFormHouse(BigDecimal sqrOFormHouse) {
            this.sqrOFormHouse = sqrOFormHouse;
        }

        public List<Map> getHomeForDemolitionPrimaryRightResult() {
            return HomeForDemolitionPrimaryRightResult;
        }

        public void setHomeForDemolitionPrimaryRightResult(List<Map> homeForDemolitionPrimaryRightResult) {
            HomeForDemolitionPrimaryRightResult = homeForDemolitionPrimaryRightResult;
        }

        public List<Map> getHomeForDemolitionSecondaryRightResult() {
            return HomeForDemolitionSecondaryRightResult;
        }

        public void setHomeForDemolitionSecondaryRightResult(List<Map> homeForDemolitionSecondaryRightResult) {
            HomeForDemolitionSecondaryRightResult = homeForDemolitionSecondaryRightResult;
        }

        public List<Map> getHomeForDemolitionShareHolderResult() {
            return HomeForDemolitionShareHolderResult;
        }

        public void setHomeForDemolitionShareHolderResult(List<Map> homeForDemolitionShareHolderResult) {
            HomeForDemolitionShareHolderResult = homeForDemolitionShareHolderResult;
        }

        public List<Map> getDisabledPersonResult() {
            return DisabledPersonResult;
        }

        public void setDisabledPersonResult(List<Map> disabledPersonResult) {
            DisabledPersonResult = disabledPersonResult;
        }

        public List<Map> getVeteransOrgResult() {
            return VeteransOrgResult;
        }

        public void setVeteransOrgResult(List<Map> veteransOrgResult) {
            VeteransOrgResult = veteransOrgResult;
        }
    }

    public class ContentsData {
        private String name;
        private Integer page;

        public ContentsData(String name, Integer page) {
            this.name = name;
            this.page = page;
        }
    }


}
