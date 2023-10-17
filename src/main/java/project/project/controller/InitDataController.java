package project.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.Room;
import project.project.domain.RoomInfo;
import project.project.domain.User;
import project.project.domain.enum_type.*;
import project.project.dto.room.RoomRegistrationDto;
import project.project.dto.roominfo.RoomInfoRegistrationDto;
import project.project.repository.UserRepository;
import project.project.repository.roomrepository.RoomRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@Slf4j
@RequiredArgsConstructor
public class InitDataController {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;



    @GetMapping("/testdata")
    public String testdata(){

        return "testdata";
    }

    @PostMapping("/testdata")
    @Transactional
    public String testdataSave(MultipartFile file) throws IOException, InvalidFormatException {
        OPCPackage opcPackage = OPCPackage.open(file.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
        XSSFSheet sheet = workbook.getSheetAt(0);


        for(int i=1;i<sheet.getLastRowNum()+1;i++){
            XSSFRow row = sheet.getRow(i);
            if(row==null) break;
            Random random = new Random();
            RoomInfoRegistrationDto roomInfoDto = new RoomInfoRegistrationDto(randomDouble(random.nextDouble(10) + 15),
                    randomDouble(random.nextDouble(10) + 15),
                    i + "층",
                    i + 1 + "층",
                    randomCreateEnum(Bearing.class),
                    randomBoolean(),
                    randomBoolean(),
                    randomCreateEnumList(Option.class));

            RoomInfo roomInfo = RoomInfo.makeRoomInfo(roomInfoDto);

            long i1 = random.nextLong(6)+1;
            log.info("userId={}",i1);
            User findUser = userRepository.findById(i1).get();


            String address = row.getCell(2).getStringCellValue();
            log.info("address={}",address);
            double lat = row.getCell(3).getNumericCellValue();
            log.info("lat={}",lat);
            double lng = row.getCell(4).getNumericCellValue();

            RoomRegistrationDto roomDto = new RoomRegistrationDto(findUser.getId(), "임대인", randomCreateEnum(HouseType.class),
                    randomCreateEnum(RoomType.class), null, address, null, null,
                    lat, lng, (random.nextInt(8) + 1) * 100, (random.nextInt(8) + 1) * 10,
                    randomDouble(random.nextDouble(5) + 1), LocalDate.now(), randomCreateEnumList(MaintenanceItem.class),
                    null, "최고의 매물 당신의 집입니다.", "역세권 10분 이내 최고의 인프라를 갖춘 방");

            String[] img = new String[4];
            for (int j = 0; j < 4; j++) {
                img[j] = "project" + new Random().nextInt(10) + ".jpg";
            }

            Room room = Room.makeRoom(roomDto, roomInfo, img, findUser);

            roomRepository.save(room);


        }

        return "redirect:/";
    }

    private Double randomDouble(Double num){
        return Math.round(num*10)/10.0;
    }

    private Boolean randomBoolean() {
        int i = new Random().nextInt(1);
        return i == 0 ? true : false;
    }

    private <T extends Enum<T>> T randomCreateEnum(Class<T> enumClass) {
        T[] enumValues = enumClass.getEnumConstants();
        int i = new Random().nextInt(enumValues.length);
        return enumValues[i];
    }

    private <T extends Enum<T>> List<T> randomCreateEnumList(Class<T> enumClass) {
        T[] enumValues = enumClass.getEnumConstants();
        int i = new Random().nextInt(enumValues.length);
        List<T> list = new ArrayList<>();
        for (int j = 0; j <= i; j++) {
            list.add(enumValues[j]);
        }
        return list;
    }
}
