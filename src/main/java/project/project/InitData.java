package project.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.*;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.*;
import project.project.dto.contract.ContractDto;
import project.project.dto.roominfo.RoomInfoRegistrationDto;
import project.project.dto.room.RoomRegistrationDto;
import project.project.repository.InquiryRepository;
import project.project.repository.UserRepository;
import project.project.repository.WishlistRepository;
import project.project.repository.contractrepository.ContractRepository;
import project.project.repository.roomrepository.RoomRepository;
import project.project.service.ContractService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitData {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;
    private final WishlistRepository wishlistRepository;
    private final ContractRepository contractRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() throws JsonProcessingException {


        //RoomInfo 생성
        List<Option> options = new ArrayList<>();
        options.add(Option.냉장고);
        options.add(Option.가스레인지);
        RoomInfoRegistrationDto roomInfoDto = new RoomInfoRegistrationDto(20.0, 30.0, "2", "3", Bearing.남, true, false, options);
        RoomInfo roomInfo = RoomInfo.makeRoomInfo(roomInfoDto);

        //User 생성 후 저장
        User user = new User("alstmd1226@naver.com", "강민준", "123");
        userRepository.save(user);

        List<MaintenanceItem> maintenanceItems = new ArrayList<>();
        maintenanceItems.add(MaintenanceItem.가스);
        maintenanceItems.add(MaintenanceItem.전기);

        RoomRegistrationDto roomDto = new RoomRegistrationDto(user.getId(), "기존 세입자", HouseType.단독, RoomType.원룸,
                12312, "부천로 57번길 50", "201호", "심곡동", 23.12312, 30.123123, 500, 40, 5.0, LocalDate.now(), maintenanceItems
                , null, "부천역 10분거리 복층형 원룸", "오세요 오세요 오세요");

        Room room = Room.makeRoom(roomDto, roomInfo, new String[]{"project1.jpg", "project2.jpg", "project3.jpg", "project4.jpg"}, user);
        roomRepository.save(room);

        //WishList 저장
        Wishlist wishlist1 = new Wishlist(user, room);
        Wishlist wishlist2 = new Wishlist(user, room);
        wishlistRepository.save(wishlist1);
        wishlistRepository.save(wishlist2);

        //Inquiry 저장
        Inquiry inquiry1 = new Inquiry(user, room);
        Inquiry inquiry2 = new Inquiry(user, room);
        Inquiry inquiry3 = new Inquiry(user, room);
        inquiryRepository.save(inquiry1);
        inquiryRepository.save(inquiry2);
        inquiryRepository.save(inquiry3);

        User[] users=new User[5];
        for(int i=0;i<users.length;i++){
            users[i]=new User("user"+i,"이름"+i,"123","0105564541"+i,new Address(12312, "부천로 57번길 50", "201호", "심곡동"+i));
            userRepository.save(users[i]);
        }

        for(int i=0;i<10;i++){
            List<String> list=new ArrayList<>();
            for(int j=0;j<i+3;j++){
                list.add("특약사항"+j);
            }
            ContractDto contractDto = new ContractDto(null, null, "부천로 57번길 50" + i, "지목" + i, "대지권비율" + i, 20.01 + i, "단독주택" + i,
                    "주택" + i, 20.02 + i, i + "호", 20.03 + i, 5000000 + i, 500000 + i, null, 4500000, 400000 + i, LocalDate.now(),
                    LocalDate.now(), LocalDate.now(), LocalDate.now(), 10 + i, "선불", 20 + i, list, "임대인" + i,
                    "0105564541" + i, "안창로 69번가길 11-" + i, "971226191262" + i,
                    "임차인" + i, "0105517541" + i, "중앙대로 626 " + i, "960118191263" + i);

            Random random = new Random();
            int num = random.nextInt(5);
            Contract contract = new Contract(contractDto, users[num]);
            contractRepository.save(contract    );
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode itemNodes = objectMapper.readTree(getInitStringData()).get("items");
        if (itemNodes!=null&&itemNodes.isArray()){
            for (JsonNode itemNode : itemNodes) {
                RoomInfoRegistrationDto roomInfoRegistrationDto = new RoomInfoRegistrationDto(itemNode.path("전용면적").path("m2").asDouble(),
                        itemNode.path("전용면적").path("m2").asDouble(),
                        itemNode.get("floor").toString(),
                        itemNode.get("building_floor").toString(),
                        randomCreateEnum(Bearing.class),
                        randomBoolean(),
                        randomBoolean(),
                        randomCreateEnumList(Option.class));

                RoomInfo roomInfo1 = RoomInfo.makeRoomInfo(roomInfoRegistrationDto);

                int i = new Random().nextInt(5);


                RoomRegistrationDto roomRegistrationDto = new RoomRegistrationDto(users[i].getId(), "임대인", randomCreateEnum(HouseType.class), randomCreateEnum(RoomType.class),
                        1, itemNode.get("address1").toString(), "", "",
                        itemNode.path("random_location").path("lat").asDouble(),
                        itemNode.path("random_location").path("lng").asDouble(),
                        itemNode.get("deposit").asInt(), itemNode.get("rent").asInt(),
                        itemNode.get("manage_cost").asDouble(), LocalDate.now(), randomCreateEnumList(MaintenanceItem.class),
                        null, itemNode.get("title").toString(), "어서오십쇼 여긴 당신의 집입니다.");

                String[] img =new String[4];
                for(int j=0;j<4;j++){
                    img[j]="project"+new Random().nextInt(10)+".jpg";
                }

                Room room1 = Room.makeRoom(roomRegistrationDto, roomInfo1, img, users[i]);

                roomRepository.save(room1);
            }
        }
    }


    private Boolean randomBoolean(){
        int i = new Random().nextInt(1);
        return i==0?true:false;
    }

    private <T extends Enum<T>> T randomCreateEnum(Class<T> enumClass){
        T[] enumValues = enumClass.getEnumConstants();
        int i = new Random().nextInt(enumValues.length);
        return enumValues[i];
    }
    private <T extends Enum<T>> List<T> randomCreateEnumList(Class<T> enumClass){
        T[] enumValues = enumClass.getEnumConstants();
        int i = new Random().nextInt(enumValues.length);
        List<T> list=new ArrayList<>();
        for(int j=0;j<=i;j++){
            list.add(enumValues[j]);
        }
        return list;
    }

    private String getInitStringData(){
        String data="{\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37184835,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37184835/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 100,\n" +
                "            \"rent\": 38,\n" +
                "            \"size_m2\": 16.53,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 16.53,\n" +
                "                \"p\": \"5\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 16.53,\n" +
                "                \"p\": \"5\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"4\",\n" +
                "            \"floor_string\": \"4\",\n" +
                "            \"building_floor\": \"8\",\n" +
                "            \"title\": \"\uD83D\uDFE6\uD83D\uDFE8\uD83C\uDF3B부천역 1등\uD83C\uDF3B\uD83D\uDFE8\uD83D\uDFE6허위zero! 실매물\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 송내동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.484211492599435,\n" +
                "                \"lng\": 126.7729810151746\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 송내동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"5\",\n" +
                "            \"reg_date\": \"2023-06-28T12:20:24+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"송내동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 송내동\",\n" +
                "                \"localText\": \"부천시 송내동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37185168,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37185168/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 100,\n" +
                "            \"rent\": 30,\n" +
                "            \"size_m2\": 16.53,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 16.53,\n" +
                "                \"p\": \"5\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 16.53,\n" +
                "                \"p\": \"5\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"8\",\n" +
                "            \"floor_string\": \"8\",\n" +
                "            \"building_floor\": \"9\",\n" +
                "            \"title\": \"\uD83D\uDC96\uD83D\uDC9F\uD83D\uDC96  풀옵션 원룸  원룸전문건물 엘리베이터\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 송내동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48418782767133,\n" +
                "                \"lng\": 126.77318514664644\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 송내동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"5\",\n" +
                "            \"reg_date\": \"2023-06-28T13:32:27+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"송내동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 송내동\",\n" +
                "                \"localText\": \"부천시 송내동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37209876,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37209876/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 500,\n" +
                "            \"rent\": 40,\n" +
                "            \"size_m2\": 20,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 20,\n" +
                "                \"p\": \"6.1\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 20,\n" +
                "                \"p\": \"6.1\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"2\",\n" +
                "            \"floor_string\": \"2\",\n" +
                "            \"building_floor\": \"4\",\n" +
                "            \"title\": \"✅✅부천역 올리모델링✅ 첫입주✅\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48805246883932,\n" +
                "                \"lng\": 126.77915873225493\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"3\",\n" +
                "            \"reg_date\": \"2023-06-30T16:44:17+09:00\",\n" +
                "            \"is_new\": true,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37137257,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37137257/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 300,\n" +
                "            \"rent\": 32,\n" +
                "            \"size_m2\": 33.06,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 33.06,\n" +
                "                \"p\": \"10\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 33.06,\n" +
                "                \"p\": \"10\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"반지하\",\n" +
                "            \"floor_string\": \"반지하\",\n" +
                "            \"building_floor\": \"4\",\n" +
                "            \"title\": \"1,5룸풀옵션. 보300-32만 부천대학5분\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"02\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.4856154080732,\n" +
                "                \"lng\": 126.77544061127743\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"3\",\n" +
                "            \"reg_date\": \"2023-06-30T08:59:08+09:00\",\n" +
                "            \"is_new\": true,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37156892,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37156892/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 500,\n" +
                "            \"rent\": 42,\n" +
                "            \"size_m2\": 19.83,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"3\",\n" +
                "            \"floor_string\": \"3\",\n" +
                "            \"building_floor\": \"5\",\n" +
                "            \"title\": \"\uD83D\uDFE6\uD83D\uDFE8\uD83C\uDF3B부천역 1등\uD83C\uDF3B\uD83D\uDFE8\uD83D\uDFE6허위zero! 실매물\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"02\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.49003074509308,\n" +
                "                \"lng\": 126.78291310929532\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"3\",\n" +
                "            \"reg_date\": \"2023-06-30T13:05:06+09:00\",\n" +
                "            \"is_new\": true,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37091873,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37091873/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 500,\n" +
                "            \"rent\": 40,\n" +
                "            \"size_m2\": 26.45,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 26.45,\n" +
                "                \"p\": \"8\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 26.45,\n" +
                "                \"p\": \"8\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"2\",\n" +
                "            \"floor_string\": \"2\",\n" +
                "            \"building_floor\": \"4\",\n" +
                "            \"title\": \"부천역 5분 리모델 상가주택원룸\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48571060406177,\n" +
                "                \"lng\": 126.77879336160689\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"3\",\n" +
                "            \"reg_date\": \"2023-06-20T17:12:23+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37131282,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37131282/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 100,\n" +
                "            \"rent\": 40,\n" +
                "            \"size_m2\": 16.53,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 16.53,\n" +
                "                \"p\": \"5\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 16.53,\n" +
                "                \"p\": \"5\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"3\",\n" +
                "            \"floor_string\": \"3\",\n" +
                "            \"building_floor\": \"7\",\n" +
                "            \"title\": \"\uD83C\uDF40\uD83C\uDF40깔끔한 원룸!!~ 엘有/부천역 5분/풀옵션/TV有~\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48704598962418,\n" +
                "                \"lng\": 126.78623143965892\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"6\",\n" +
                "            \"reg_date\": \"2023-06-23T15:28:29+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37203485,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37203485/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 300,\n" +
                "            \"rent\": 37,\n" +
                "            \"size_m2\": 19.83,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"2\",\n" +
                "            \"floor_string\": \"2\",\n" +
                "            \"building_floor\": \"5\",\n" +
                "            \"title\": \"▶ 풀옵션   월세◀\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48867651633245,\n" +
                "                \"lng\": 126.78619143225076\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"5\",\n" +
                "            \"reg_date\": \"2023-06-29T17:23:04+09:00\",\n" +
                "            \"is_new\": true,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37187371,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37187371/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 500,\n" +
                "            \"rent\": 38,\n" +
                "            \"size_m2\": 19.83,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"2\",\n" +
                "            \"floor_string\": \"2\",\n" +
                "            \"building_floor\": \"6\",\n" +
                "            \"title\": \"\uD83C\uDF31[풀옵션*역세권]*엘리베이터有*깔끔*CCTV有*현관보안\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48772873289236,\n" +
                "                \"lng\": 126.78574020595607\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"5\",\n" +
                "            \"reg_date\": \"2023-06-28T14:41:06+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 36546756,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/36546756/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 500,\n" +
                "            \"rent\": 40,\n" +
                "            \"size_m2\": 19.83,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"1\",\n" +
                "            \"floor_string\": \"1\",\n" +
                "            \"building_floor\": \"3\",\n" +
                "            \"title\": \"\uD83C\uDF40\uD83C\uDF40 신축 원룸!!~ 부천역 도보7분/풀옵션/넓고 깔끔\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48583311183012,\n" +
                "                \"lng\": 126.78751551955038\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"5\",\n" +
                "            \"reg_date\": \"2023-06-07T19:06:54+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": true,\n" +
                "                \"readAt\": \"2023-05-08T03:19:44.098Z\",\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37063053,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37063053/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 500,\n" +
                "            \"rent\": 38,\n" +
                "            \"size_m2\": 16.53,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 16.53,\n" +
                "                \"p\": \"5\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 16.53,\n" +
                "                \"p\": \"5\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"2\",\n" +
                "            \"floor_string\": \"2\",\n" +
                "            \"building_floor\": \"6\",\n" +
                "            \"title\": \"부천역 가깝고 밝고 환환 원룸\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48773971192676,\n" +
                "                \"lng\": 126.78513599121672\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"5\",\n" +
                "            \"reg_date\": \"2023-06-18T17:44:21+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 36935000,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/36935000/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 500,\n" +
                "            \"rent\": 50,\n" +
                "            \"size_m2\": 27.8,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 27.8,\n" +
                "                \"p\": \"8.4\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 27.8,\n" +
                "                \"p\": \"8.4\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"2\",\n" +
                "            \"floor_string\": \"2\",\n" +
                "            \"building_floor\": \"5\",\n" +
                "            \"title\": \"\uD83D\uDFE6\uD83D\uDFE6올리모델링\uD83C\uDF80베란다+넓음\uD83C\uDF80깨끗한풀옵션\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48657800880308,\n" +
                "                \"lng\": 126.77808566263931\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"6\",\n" +
                "            \"reg_date\": \"2023-06-27T17:02:41+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37128408,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37128408/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 500,\n" +
                "            \"rent\": 35,\n" +
                "            \"size_m2\": 23.14,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 23.14,\n" +
                "                \"p\": \"7\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 23.14,\n" +
                "                \"p\": \"7\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"3\",\n" +
                "            \"floor_string\": \"3\",\n" +
                "            \"building_floor\": \"3\",\n" +
                "            \"title\": \"화이트톤 리모델링 가성비굿 주방분리\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"02\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48819725089571,\n" +
                "                \"lng\": 126.78215093854845\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"5\",\n" +
                "            \"reg_date\": \"2023-06-23T13:11:59+09:00\",\n" +
                "            \"is_new\": false,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37172785,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37172785/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 300,\n" +
                "            \"rent\": 30,\n" +
                "            \"size_m2\": 19.83,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"1\",\n" +
                "            \"floor_string\": \"1\",\n" +
                "            \"building_floor\": \"3\",\n" +
                "            \"title\": \"원룸풀옵션  보300-30만 부천대학5분\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48956614792221,\n" +
                "                \"lng\": 126.77495202156815\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"0\",\n" +
                "            \"reg_date\": \"2023-06-30T08:34:24+09:00\",\n" +
                "            \"is_new\": true,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡동\",\n" +
                "                \"localText\": \"부천시 심곡동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"section_type\": null,\n" +
                "            \"item_id\": 37209923,\n" +
                "            \"images_thumbnail\": \"https://ic.zigbang.com/ic/items/37209923/1.jpg\",\n" +
                "            \"sales_type\": \"월세\",\n" +
                "            \"sales_title\": \"월세\",\n" +
                "            \"deposit\": 300,\n" +
                "            \"rent\": 38,\n" +
                "            \"size_m2\": 19.83,\n" +
                "            \"공급면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"전용면적\": {\n" +
                "                \"m2\": 19.83,\n" +
                "                \"p\": \"6\"\n" +
                "            },\n" +
                "            \"계약면적\": null,\n" +
                "            \"room_type_title\": null,\n" +
                "            \"floor\": \"4\",\n" +
                "            \"floor_string\": \"4\",\n" +
                "            \"building_floor\": \"5\",\n" +
                "            \"title\": \"부천역세권, 깨끗하고 넓은 원룸\",\n" +
                "            \"is_first_movein\": null,\n" +
                "            \"room_type\": \"01\",\n" +
                "            \"address\": \"부천시 심곡본동\",\n" +
                "            \"random_location\": {\n" +
                "                \"lat\": 37.48087405353835,\n" +
                "                \"lng\": 126.78098563323039\n" +
                "            },\n" +
                "            \"is_zzim\": false,\n" +
                "            \"status\": true,\n" +
                "            \"service_type\": \"원룸\",\n" +
                "            \"tags\": [\n" +
                "                \"추천\"\n" +
                "            ],\n" +
                "            \"address1\": \"경기도 부천시 심곡본동\",\n" +
                "            \"address2\": null,\n" +
                "            \"address3\": null,\n" +
                "            \"manage_cost\": \"4\",\n" +
                "            \"reg_date\": \"2023-06-30T12:36:06+09:00\",\n" +
                "            \"is_new\": true,\n" +
                "            \"contract\": \"\",\n" +
                "            \"addressOrigin\": {\n" +
                "                \"local1\": \"경기도\",\n" +
                "                \"local2\": \"부천시\",\n" +
                "                \"local3\": \"심곡본동\",\n" +
                "                \"local4\": \"\",\n" +
                "                \"fullText\": \"경기도 부천시 심곡본동\",\n" +
                "                \"localText\": \"부천시 심곡본동\"\n" +
                "            },\n" +
                "            \"action\": {\n" +
                "                \"isRead\": false,\n" +
                "                \"readAt\": null,\n" +
                "                \"isInquired\": false,\n" +
                "                \"inquiredAt\": null,\n" +
                "                \"isRewarded\": false,\n" +
                "                \"rewardedAt\": null,\n" +
                "                \"isReported\": false,\n" +
                "                \"reportedAt\": null,\n" +
                "                \"isChecked\": false,\n" +
                "                \"checkedAt\": null,\n" +
                "                \"isZzim\": false\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        return data;
    }

}
