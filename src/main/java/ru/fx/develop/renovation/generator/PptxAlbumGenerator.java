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
import ru.fx.develop.renovation.model.*;
import ru.fx.develop.renovation.service.GenerateService;
import ru.fx.develop.renovation.util.DateUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class PptxAlbumGenerator {

   // private static String dataReport = new House().getAddress();

    private static final String FILE_PATH_DIR_OUTPUT = "D:\\Отчеты\\";
    private static final String FILE_EXTENSION = ".pptx";
    public static final int SHAPE_TABLE_1 = 0;

    public static final int CELL_MARGIN_TOP = 12700;
    public static final int CELL_MARGIN_BOTTOM = 12700;

    public static final int TABLE_MARGIN_TOP = 20;
    public static final int TABLE_DEFAULT_ROW_HEIGHT = 152995;
    @Autowired
    private GenerateService generateService;



    @Value("classpath:templates/report.pptx")
    private Resource reportTemplate;
    private static XMLSlideShow pptx;
    private XSLFSlide currentSlide;
    private List<ContentsData> contentsData;
    private TableData tableData;
    private Map<XSLFSlide, List<XSLFShape>> shapesToDelete = new HashMap<>();

    public TableData getTableData() {
        return tableData;
    }

    public void generateReport(ObservableList<House> houseData){
        try {
            contentsData = new ArrayList<>();
            pptx = new XMLSlideShow(reportTemplate.getInputStream());
            reportTemplate.getInputStream().close();
            //создаем второй слайд
            currentSlide = pptx.createSlide();
            //импорт слайда с фигурами
            currentSlide.importContent(pptx.getSlides().get(0));
//            List<Searchable> searchablesHouses = generateService.getSearchHouse()
//            Searchable searchable = null;
//            String model = resolveModelName(searchable);

            createSlideForHome(pptx, houseData);
//            List<Searchable> searchableHouseList = generateService.getSearchHouse(houseData, new House().getUnom());
//            searchableHouseList.stream()
//                    .forEach(h ->{
//                        createSlideForHome(h,pptx,houseData);
//                    });

           // createSlideForHome(searchableHouseList,pptx,houseData);



            for (int i = 0; i < 1; i++) {
                pptx.removeSlide(0);
            }
           // String FILE_PATH_POWERPOINT_OUTPUT =
            houseData.forEach(house -> {
                File file = new File(FILE_PATH_DIR_OUTPUT + house.getAddress() + " дата файла " +  DateUtil.format(LocalDate.now()) +   FILE_EXTENSION);
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


        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void createSlideForHome(XMLSlideShow pptx, ObservableList<House> houseData){
        try {
            tableData = new TableData();
            tableData.setHomeForDemolitionPrimaryRightResult(generateService.dataPrimaryRight(houseData));//showDataHomeForDemolitionPrimary(houseDem));
          //  tableData.setHomeForDemolitionSecondaryRightResult(generateService.dataSecondRight(houseData));
          //  tableData.setHomeForDemolitionSecondaryRightResult(generateService.showDataHomeForDemolitionSecondary(houseDem));
            tableData.setVeteransOrgResult(generateService.dataVeteransOrg(houseData));
            tableData.setDisabledPersonResult(generateService.dataDisabledPerson(houseData));

            Integer firstSlide = pptx.getSlides().indexOf(currentSlide);

            addTableNonHousing(pptx,tableData);
            addTableVeteranOrg(pptx, tableData);
            addTableDisabledPerson(pptx, tableData);
//            houseData.stream().forEach(house -> {
//                getTableData().getHomeForDemolitionPrimaryRightResult();
//                System.out.println(getTableData().getHomeForDemolitionPrimaryRightResult().size());
//                getTableData().getVeteransOrgResult();
//                System.out.println(getTableData().getVeteransOrgResult().size());
//                getTableData().getDisabledPersonResult();
//                System.out.println(getTableData().getDisabledPersonResult().size());
//            });
            Integer lastSlide = pptx.getSlides().indexOf(currentSlide);
            contentsData.add(new ContentsData(" ",lastSlide - firstSlide + 1));

            shapesToDelete.keySet().stream()
                    .forEach(slide -> shapesToDelete.get(slide).stream()
                            .forEach(slide::removeShape));

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void addTableNonHousing(XMLSlideShow pptx, TableData tableData){
        XSLFTable tableNonHousing = (XSLFTable) currentSlide.getShapes().get(1);
        HomeForDemolitionWriter homeForDemolitionWriter = new HomeForDemolitionWriter(pptx, currentSlide, tableNonHousing);
        if (tableData.getHomeForDemolitionPrimaryRightResult().isEmpty()){
            homeForDemolitionWriter.writeNoData();
        } else {
            tableData.getHomeForDemolitionPrimaryRightResult()
                    .stream().forEach(homeForDemolitionWriter::writeRow);
           // homeForDemolitionWriter.writeTotal();
//            tableData.getHomeForDemolitionSecondaryRightResult()
//                    .stream().forEach(homeForDemolitionWriter::writeRow);

            homeForDemolitionWriter.writeTotal();
           // homeForDemolitionWriter.mergeCells();

            homeForDemolitionWriter.writeTotalRF();
            homeForDemolitionWriter.writeTotalMoscow();
            homeForDemolitionWriter.writeTotalFizYurFace();
            homeForDemolitionWriter.writeTotalAll();

        }

        homeForDemolitionWriter.endSlideTable();

        homeForDemolitionWriter.getShapesToDelete().keySet().stream()
                .forEach(slide ->{
                    if (shapesToDelete.containsKey(slide)){
                        shapesToDelete.get(slide).addAll(homeForDemolitionWriter.getShapesToDelete().get(slide));
                    }else {
                        shapesToDelete.put(slide,homeForDemolitionWriter.getShapesToDelete().get(slide));
                    }
                });
        currentSlide = homeForDemolitionWriter.getSlide();
    }

    private void addTableVeteranOrg(XMLSlideShow pptx, TableData tableData){
        XSLFTable tableVeteranOrganisations = (XSLFTable) currentSlide.getShapes().get(5);
        VeteranOrgWriter veteranOrgWriter = new VeteranOrgWriter(pptx, currentSlide, tableVeteranOrganisations);
        if (tableData.getVeteransOrgResult().isEmpty()){
            veteranOrgWriter.writeNoData();
        } else {
            tableData.getVeteransOrgResult()
                    .stream()
                    .forEach(veteranOrgWriter::writeRow);
            veteranOrgWriter.writeTotal();
        }

        veteranOrgWriter.endSlideTable();
        veteranOrgWriter.getShapesToDelete().keySet().stream()
                .forEach(slide ->{
                    if (shapesToDelete.containsKey(slide)){
                        shapesToDelete.get(slide).addAll(veteranOrgWriter.getShapesToDelete().get(slide));
                    }else {
                        shapesToDelete.put(slide,veteranOrgWriter.getShapesToDelete().get(slide));
                    }
                });

        currentSlide = veteranOrgWriter.getSlide();
    }

    private void addTableDisabledPerson(XMLSlideShow pptx, TableData tableData){
        XSLFTable tableDisablePeople = (XSLFTable) currentSlide.getShapes().get(3);
        DisabledPersonWriter disabledPersonWriter = new DisabledPersonWriter(pptx, currentSlide,tableDisablePeople);

        if (tableData.getDisabledPersonResult().isEmpty()){
            disabledPersonWriter.writeNoData();
        } else {
            tableData.getDisabledPersonResult()
                    .stream()
                    .forEach(disabledPersonWriter::writeRow);
            //disabledPersonWriter.mergeCells();
            disabledPersonWriter.writeTotal();
            disabledPersonWriter.writeCountFirstGroup();
            disabledPersonWriter.writeCountSecondGroup();
            disabledPersonWriter.writeCountThreeGroup();
            disabledPersonWriter.writeCountFourGroup();
        }

        disabledPersonWriter.endSlideTable();
        disabledPersonWriter.getShapesToDelete().keySet().stream()
                .forEach(slide ->{
                            if (shapesToDelete.containsKey(slide)) {
                                shapesToDelete.get(slide).addAll(disabledPersonWriter.getShapesToDelete().get(slide));
                            }else {
                                shapesToDelete.put(slide, disabledPersonWriter.getShapesToDelete().get(slide));
                            }
                });

        currentSlide = disabledPersonWriter.getSlide();
    }

//    private String resolveModelName(Searchable searchableHouse){
//        if (searchableHouse instanceof PrimaryRight) return "PrimaryRight";
//        if (searchableHouse instanceof SecondaryRight) return "SecondaryRight";
//        if (searchableHouse instanceof ShareHolder) return "ShareHolder";
//        if (searchableHouse instanceof DisabledPeople) return "DisabledPeople";
//        if (searchableHouse instanceof VeteranOrg) return "VeteranOrg";
//        return null;
//    }



    public class TableData{
        List<Map> HomeForDemolitionPrimaryRightResult = new ArrayList<>();
        List<Map> HomeForDemolitionSecondaryRightResult = new ArrayList<>();
        List<Map> HomeForDemolitionShareHolderResult = new ArrayList<>();
        List<Map> DisabledPersonResult = new ArrayList<>();
        List<Map> VeteransOrgResult = new ArrayList<>();

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

    public class ContentsData{
        private String name;
        private Integer page;

        public ContentsData(String name, Integer page) {
            this.name = name;
            this.page = page;
        }
    }


}
