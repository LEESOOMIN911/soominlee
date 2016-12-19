

-- 실험데이터 
---------------------------------------------------------------------------------------------- 
-- ADMIN 
INSERT INTO MCARE_MANAGER (USER_ID,
                           USER_NAME,
                           ENABLED_YN,
                           DEPT_NAME,
                           PWD_VALUE,
                           CREATE_DT,
                           CREATE_ID,
                           UPDATE_DT,
                           UPDATE_ID)
     VALUES ('admin',
             'admin',
             'Y',
             'DBS',
             '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b',
             SYSDATE,
             'CONSOLE',
             NULL,
             NULL);


-- MCARE_MENU
--------------------------------------------------------
--  파일이 생성됨 - 10월 14일 
--------------------------------------------------------
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('certificationResult','certification',40,'CONT','인증결과','N','/mobile/user/certificationResult.page',null,'휴대폰 인증, iPin 인증의 인증 결과를 반환 받는다.','N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('appointmentSearchDetail','appointmentSearch',10,'CONT','예약조회상세','Y','/mobile/appointmentSearch/appointmentSearchDetail.page',null,'예약조회 상세','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('searchId','userRegister',22,'CONT','아이디찾기','N','/mobile/user/searchId.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('registerPWD','userRegister',21,'CONT','비밀번호 설정','N','/mobile/user/registerPWD.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('resetPWD','userRegister',26,'CONT','비밀번호 재 설정','N','/mobile/user/resetPWD.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('userAgreemet','userRegister',27,'CONT','서비스약관 수락','N','/mobile/user/userAgreement.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('treatmentHistoryDetail','treatmentHistory',10,'CONT','이력조회상세','Y','/mobile/history/treatmentHistoryDetail.page',null,'이력조회상세','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('resultExam',null,150,'CONT','검사결과','Y','/mobile/resultExam/resultExam.page','/resources/css/images/mobile/main/resultExam.png','검사결과조회','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('prescription',null,70,'CONT','처방조회','Y','/mobile/prescription/prescription.page','/resources/css/images/mobile/main/prescription.png','처방조회','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('ticket',null,10,'CONT','번호표발급','Y','/mobile/ticket/ticket.page','/resources/css/images/mobile/main/ticket.png','번호표발급 - 번호표발급기 목록','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('issueTicket','ticket',10,'CONT','번호표발급','Y','/mobile/ticket/issueTicket.page',null,'번호표발급','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('parking',null,100,'CONT','주차관리','Y','/mobile/parking/parking.page','/resources/css/images/mobile/main/parking.png','주차관리','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('parkingUpdate','parking',10,'CONT','주차관리수정','Y','/mobile/parking/parkingUpdate.page',null,'주차관리 수정','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('registerUser','userRegister',23,'CONT','사용자 등록','N','/mobile/user/registerUser.page',null,null,'Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('help',null,240,'CONT','도움말','Y','/mobile/help/help.page','/resources/css/images/mobile/main/help.png','도움말','N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('paymentDetail','treatmentHistoryDetail',10,'CONT','진료비 상세내역','Y','/mobile/history/paymentDetail.page',null,'진료비 내역 상세','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('nextLocation',null,55,'CONT','가셔야 할 곳','Y','/mobile/nextLocation/nextLocation.page','/resources/css/images/mobile/main/navigation.png','가셔야 할 곳','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('nurse',null,160,'CONT','간호요청','Y','/mobile/nurse/nurse.page','/resources/css/images/mobile/main/nurse.png','간호호출','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('changePassWord',null,210,'CONT','비밀번호 변경','Y','/mobile/user/resetPWDNotCerti.page','/resources/css/images/mobile/main/resetPWD.png','비밀번호를 새로 설정하거나 변경한다.','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('checkUserAgreement',null,230,'CONT','동의서 확인','N','/mobile/user/userAgreement.page',null,'동의서 확인 --> 이거중복. 삭제될 메뉴','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('smsCertification','certification',34,'CONT','SMS인증 코드','N','/mobile/user/smsCertification.page',null,'SMS인증 대기 화면','N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('telNo',null,90,'CONT','주요전화번호','Y','/mobile/telNo/telNo.page','/resources/css/images/mobile/main/telNo.png','주요전화번호','N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('searchPWD',null,220,'CONT','비밀번호 찾기','N','/mobile/user/searchPWD.page',null,'비밀번호 찾기','N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('mobileCard',null,50,'CONT','환자카드','Y','/mobile/mobileCard/mobileCard.page','/resources/css/images/mobile/main/mobileCard.png','환자카드','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('appointmentSearch',null,40,'CONT','예약조회','Y','/mobile/appointmentSearch/appointmentSearch.page','/resources/css/images/mobile/main/appointmentSearch.png','예약조회','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('sms','certification',30,'CONT','SMS','N','/mobile/user/sms.page',null,'병원에 등록한 휴대폰 번호','N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('certification','userRegister',20,'CONT','본인인증','N','/mobile/user/certification.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('iPin','certification',40,'CONT','i-Pin','N','/mobile/user/iPin.page',null,'i-Pin 인증','N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('searchPNumber',null,210,'CONT','환자번호 찾기','N','/mobile/user/searchPId.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('helper',null,0,'CONT','도우미','N','/mobile/helper/helper.page','/resources/css/images/mobile/main/helper.png','도우미','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('treatmentHistory',null,30,'CONT','수진이력조회','Y','/mobile/history/treatmentHistory.page','/resources/css/images/mobile/main/treatmentHistory.png','이력조회','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('waitingTime',null,20,'CONT','진료대기조회','Y','/mobile/waitingTime/waitingTime.page','/resources/css/images/mobile/main/waitingTime.png','진료대기조회','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('userRegister',null,910,'SIDE','사용등록','Y','/mobile/user/register.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('healthHandbook',null,80,'CONT','건강수첩','Y','/mobile/healthHandbook/healthHandbook.page','/resources/css/images/mobile/main/healthHandbook.png','건강수첩','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('healthHandbookInsert','healthHandbook',10,'CONT','건강수첩등록','Y','/mobile/healthHandbook/healthHandbookInsert.page',null,null,'Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('healthHandbookUpdate','healthHandbook',20,'CONT','건강수첩수정','Y','/mobile/healthHandbook/healthHandbookUpdate.page',null,null,'Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('reservation',null,60,'CONT','진료예약','Y','/mobile/reservation/reservation.page','/resources/css/images/mobile/main/reservation.png','예약','N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('agreement',null,100,'CONT','동의서 보기','Y','/mobile/agreement/agreement.page','/resources/css/images/mobile/main/agreement.png','동의서확인','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('announceArrival',null,70,'CONT','진료도착확인','Y','/mobile/announceArrival/announceArrival.page','/resources/css/images/mobile/main/announceArrival.png','진료도착확인','Y');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('checkPlusFailed','certification',20,'CONT','휴대폰 인증실패','N','/mobile/user/checkPlusFailed.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('checkPlusSuccess','certification',10,'CONT','휴대폰 인증성공','N','/mobile/user/checkPlusSuccess.page',null,null,'N');
Insert into MCARE_MENU (MENU_ID,PARENT_MENU_ID,MENU_ORDER,MENU_TYPE,MENU_NAME,ENABLED_YN,ACCESS_URI_ADDR,IMAGE_URI_ADDR,MENU_DESC,AUTH_YN) values ('iPinSuccess','certification',41,'CONT','iPin인증성공','Y','/mobile/user/iPinSuccess.page',null,null,'N');


-- MCARE_VERSION  
Insert into MCARE_VERSION (PLATFORM_TYPE,VERSION_VALUE,APP_NAME,MARKET_URL) values ('A','1.0','pnuh','market://details?id=com.kakao.talk');
Insert into MCARE_VERSION (PLATFORM_TYPE,VERSION_VALUE,APP_NAME,MARKET_URL) values ('I','1.0','pnuh','https://itunes.apple.com/kr/app/kakaotog-kakaotalk/id362057947?mt=8');

-- 실험용 전화번호 
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '대표전화', NULL, '051-240-7000',1);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '전화예약', NULL, '051-240-7300',2);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '응급의료센터', NULL, '051-240-7501',3);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '장례식장', NULL, '051-240-7161',4);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '장기이식상담', NULL, '051-240-7865',5);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '현관안내', NULL, '051-240-7166',6);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '가정간호사업실', NULL, '051-240-7500',7);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '복약상담실', NULL, '051-240-7584',8);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '외국인진료상담', NULL, '051-240-7472',9);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '고객지원센터', NULL, '051-240-7031',10);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '부산대학교병원 ', ' 노동조합', '051-240-7748',11);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' 초진외래접수', '051-240-7149',12);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' 재진외래접수', '051-240-7150',13);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' 입원환자관리', '051-240-7195',14);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' 퇴원관리', '051-240-7148',15);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' 입원수속', '051-240-7154',16);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' 제증명', '051-240-7197',17);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' 자동차보험', '051-240-7097',18);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' 산재 및 의료급여', '051-240-7153',19);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' A동 1층', '051-240-7150',20);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' A동 2층', '051-240-7477',21);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' A동 3층', '051-240-7462',22);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' B동 2층', '051-240-7152',23);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' B동 3층', '051-240-7437',24);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '원무팀 ', ' B동 4층(치과수납)', '051-240-7157',25);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '진료챠트복사', NULL, '051-240-7093',26);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '건강증진센터', NULL, '051-240-7891',27);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '응급의료센터', NULL, '051-240-7601',28);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '부산지역암센터 ', ' 운영지원팀', '051-240-7464',29);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '새마을금고', NULL, '051-240-7861',30);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '농협', NULL, '051-247-5626',31);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '편의점', NULL, '051-240-7172',32);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '주차관리실정문', NULL, '051-240-7797',33);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '주차관리실타워', NULL, '051-240-7181',34);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '의료기점', NULL, '051-240-7788',35);
INSERT INTO MCARE_TELNO(TELNO_SEQ, BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER) VALUES(SEQ_MCARE_TELNO_TS.NEXTVAL, '응급전화', NULL, '051-240-7158',36);

-- MCARE_CATEGORY 
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (329,'_실험용모음','언제 없어질지 모르는 실험용 API들의 모임 ',null,'test',to_date('15/10/16','RR/MM/DD'),null,to_date('15/10/16','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (330,'도우미','도우미에서 사용할 용도 만드는 API 집합',null,'helper',to_date('15/10/17','RR/MM/DD'),null,to_date('15/10/17','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (331,'도우미용 Push','도우미 push 관련 ',null,'helperpush',to_date('15/10/17','RR/MM/DD'),null,to_date('15/10/17','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (41,'처방조회',null,null,'prescription',to_date('15/09/10','RR/MM/DD'),null,to_date('15/10/05','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (289,'번호표발급기',null,null,'ticket',to_date('15/10/08','RR/MM/DD'),null,to_date('15/10/08','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (83,'동의서','동의서 카테고리',null,'agreement',to_date('15/09/24','RR/MM/DD'),null,to_date('15/09/24','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (32,'토큰관리','device token 관련 작업',null,'token',to_date('15/09/04','RR/MM/DD'),'SCRIPT',to_date('15/09/04','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (33,'진료이력조회','진료 이력및 수납 내역',null,'history',to_date('15/09/04','RR/MM/DD'),'SCRIPT',to_date('15/09/16','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (30,'사용자관리','사용자 관리를 위한 API집합',null,'user',to_date('15/09/04','RR/MM/DD'),'SCRIPT',to_date('15/09/04','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (31,'앱관리','등록된 최신 앱 버전 확인',null,'app',to_date('15/09/04','RR/MM/DD'),'SCRIPT',to_date('15/09/04','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (34,'검사결과','검사결과 ',null,'totalexam',to_date('15/09/05','RR/MM/DD'),null,to_date('15/09/05','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (61,'가셔야 할 곳',null,null,'roadlist',to_date('15/09/18','RR/MM/DD'),null,to_date('15/09/18','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (62,'대기시간조회',null,null,'waittime',to_date('15/09/18','RR/MM/DD'),null,to_date('15/09/18','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (81,'예약','예약 관련 내용
',null,'reservation',to_date('15/09/18','RR/MM/DD'),null,to_date('15/09/18','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (269,'호출','각종 호출용. 지금은 간호호출밖에 없지만 ',null,'call',to_date('15/10/05','RR/MM/DD'),null,to_date('15/10/14','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (270,'주차관리','주차관리',null,'park',to_date('15/10/05','RR/MM/DD'),null,to_date('15/10/05','RR/MM/DD'),null);
Insert into MCARE_API_CATEGORY (CAT_SEQ,CAT_NAME,CAT_DESC,PARENT_CAT_SEQ,PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID) values (309,'전화번호',null,null,'telno',to_date('15/10/08','RR/MM/DD'),null,to_date('15/10/08','RR/MM/DD'),null);
commit; 

-- cat_seq 
ALTER SEQUENCE SEQ_MCARE_API_CATEGORY_CS INCREMENT BY 331;
SELECT SEQ_MCARE_API_CATEGORY_CS.NEXTVAL FROM DUAL; 
ALTER SEQUENCE SEQ_MCARE_API_CATEGORY_CS INCREMENT BY 1; 
commit; 


-- MCARE_API 
REM INSERTING into MCARE_API
SET DEFINE OFF;
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (244,'SQL','mcareDataSource','테이블 변경에 따라 수정함 ','GET','최신 동의서 조회',null,'LIST','getNewAgreementList',null,null,83,'동의서','/agreement/',to_date('15/09/24','RR/MM/DD'),'admin',to_date('15/10/22','RR/MM/DD'),'admin','SELECT
AGREEMENT_SEQ,
AGREEMENT_ID,
VERSION_NUMBER,
AGREEMENT_ORDER,
AGREEMENT_NAME,
AGREEMENT_CONTENTS,
ENABLED_YN,
REQUIRED_YN,
NEW_YN,
TYPE_NAME 
FROM MCARE_AGREEMENT
WHERE NEW_YN = ''Y'' AND 
      ENABLED_YN = ''Y'' AND
	  TYPE_NAME = ''ALL'' AND
      AGREEMENT_SEQ NOT IN (SELECT AGREEMENT_SEQ 
                            FROM MCARE_USER_AGREEMENT 
                            WHERE P_ID=:pid)');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (873,'SQL','mcareDataSource','','GET','14세 미만 최신 동의서 조회',null,'LIST','getUnder14NewAgreementList',null,null,83,'동의서','/agreement/',to_date('15/09/24','RR/MM/DD'),'admin',to_date('15/10/22','RR/MM/DD'),'admin','SELECT
AGREEMENT_SEQ,
AGREEMENT_ID,
VERSION_NUMBER,
AGREEMENT_ORDER,
AGREEMENT_NAME,
AGREEMENT_CONTENTS,
ENABLED_YN,
REQUIRED_YN,
NEW_YN,
TYPE_NAME 
FROM MCARE_AGREEMENT
WHERE NEW_YN = ''Y'' AND 
      ENABLED_YN = ''Y'' AND
	  TYPE_NAME = ''UNDER14'' AND
      AGREEMENT_SEQ NOT IN (SELECT AGREEMENT_SEQ 
                            FROM MCARE_USER_AGREEMENT 
                            WHERE P_ID=:pid)');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (664,'SQL','mcareExtDataSource',null,'GET','순번기 대상창구 목록',null,'LIST','deskList',null,null,289,'번호표발급기','/ticket/',to_date('15/10/08','RR/MM/DD'),'admin',to_date('15/10/11','RR/MM/DD'),'admin','  SELECT   CHU_NO AS seq_no,
           CHU_NM AS SECNM,
           NVL((SELECT   b.CALL_NO          
                 FROM   DISP_AI_call b
                WHERE       b.CHU_NO = ''99''
                        AND b.instcd = a.instcd
                        AND b.SEQ_NO = a.seq_no),''-'')
              AS DELEY,
           INSTCD  as INSTCD 
    FROM   SMART.DISP_AI_GATE_NM_GUBN A
   WHERE   INSTCD = ''1'' AND GUBN = ''Y'' and a.seq_no = :seqCd 
ORDER BY   SORT');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (181,'WEB_SERVICE',null,null,'GET','가셔야할곳',null,'LIST','getVisitInfo',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetPatientVisitInfo&rcpn_no={receiptNo}',61,'가셔야 할 곳','/roadlist/',to_date('15/09/18','RR/MM/DD'),'admin',to_date('15/10/22','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (201,'WEB_SERVICE',null,'  "rcpn_apnt_kind_nm": "진료과예약", 예약 종류','GET','예약 리스트',null,'LIST','getRevList',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetReservationInfo&pid={pId}&apnt_stdt={startDt}&apnt_endt={endDt}',81,'예약','/reservation/',to_date('15/09/18','RR/MM/DD'),'admin',to_date('15/10/08','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (182,'WEB_SERVICE',null,'진료일자는 오늘껄로... ','GET','진료대기시간 조회',null,'MAP','getwaitingList',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetOutPatApntList&pid={pId}&mdcr_date={date}',62,'대기시간조회','/waittime/',to_date('15/09/18','RR/MM/DD'),'admin',to_date('15/10/19','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (241,'SQL','mcareDataSource','로그인에 성공한 경우, 로그인 실패 횟수를 0으로 초기화한다. 
테이블 변경에 따라 수정 ','PUT','로그인실패횟수초기화',null,'INT','clearLoginFailCnt',null,null,30,'사용자관리','/user/',to_date('15/09/22','RR/MM/DD'),'admin',to_date('15/10/01','RR/MM/DD'),'admin','UPDATE MCARE_USER
SET LOGIN_FAIL_CNT=0
WHERE P_ID=:pId');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (53,'SQL','mcareDataSource','토큰 존재여부 버전확인. 

API타입이 SQL인 경우
1. HttpMethodType이 GET이고, ResultType이 Map이면 queryForMap이 실행
2. HttpMethodType이 GET이고, ResultType이 Map이 아니면 queryForList가 실행
3. HttpMethodType이 GET이 아니면, update가 실행됨 


SELECT RECEIVER_ID  FROM MNS_RECEIVER_DEVICE WHERE DEVICE_TOKEN_ID=''${deviceTokenId}''  AND RECEIVER_ID=''${receiverId}'' ','GET','디바이스 토큰 확인',null,'MAP','checkDeviceToken',null,null,32,'토큰관리','/token/',to_date('15/09/04','RR/MM/DD'),'SCRIPT',to_date('15/09/09','RR/MM/DD'),'admin','SELECT COUNT(1) AS USER_CNT  FROM MNS_RECEIVER_DEVICE WHERE DEVICE_TOKEN_ID=:deviceTokenId AND  RECEIVER_ID=:receiverId AND PLATFORM_TYPE=:platformType ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (54,'SQL','mcareDataSource','토큰저장','POST','디바이스 토큰 저장',null,'INT','saveDeviceToken',null,null,32,'토큰관리','/token/',to_date('15/09/04','RR/MM/DD'),'SCRIPT',to_date('15/09/09','RR/MM/DD'),'admin','INSERT INTO MNS_RECEIVER_DEVICE (RECEIVER_ID, DEVICE_TOKEN_ID, PLATFORM_TYPE) VALUES (:receiverId, :deviceTokenId, :platformType)');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (59,'SQL','mcareDataSource','플랫폼 최신버전 ','GET','버전확인',null,'MAP','checkAppVersion',null,null,31,'앱관리','/app/',to_date('15/09/04','RR/MM/DD'),'admin',to_date('15/09/25','RR/MM/DD'),'admin','SELECT * 
FROM MCARE_VERSION 
WHERE PLATFORM_TYPE=:platformType');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (60,'WEB_SERVICE','mcareDataSource','환자정보찾기 
pt_nm : 이영주 
clph_no : 핸드폰 번호 
pt_nm, clph_no 까지 합쳐서 검색하는건 필요없기 때문에 pId로만 검색해서 사용한다. 

vhcl_no	STRING		차량번호
sths_yn	STRING		재원여부 -- 병원에 존재하는지 여부 (있는 사람만 간호호출 가능) 
','GET','환자정보조회',null,'MAP','getUserInfo',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.userinfo&pid={pId}&pt_nm=&clph_no=',30,'사용자관리','/user/',to_date('15/09/04','RR/MM/DD'),'admin',to_date('15/10/17','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (563,'SQL','mcareDataSource',null,'PUT','비밀번호 변경',null,'INT','resetPWD',null,null,30,'사용자관리','/user/',to_date('15/10/01','RR/MM/DD'),'admin',to_date('15/10/01','RR/MM/DD'),'admin','UPDATE MCARE_USER
SET PASSWORD_VALUE=:passwordValue,
       PASSWORD_UPDATE_DT=SYSDATE
WHERE
       P_ID=:pId


');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (816,'SQL','mcareDataSource',null,'PUT','임시 비밀번호 변경',null,'INT','tempResetPWD',null,null,30,'사용자관리','/user/',to_date('15/10/01','RR/MM/DD'),'admin',to_date('15/10/01','RR/MM/DD'),'admin',
'UPDATE MCARE_USER
SET PASSWORD_VALUE=:passwordValue,
       PASSWORD_UPDATE_DT=TO_DATE(''1970/01/01'', ''YYYY/MM/DD'')
WHERE
       P_ID=:pId


');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (623,'SQL','mcareExtDataSource','1 : 환자복 교체 
2 : 시트교체 
4 : 수액교체 
- 숫자는 OR 연산 
- 문자는 콤마로 구분 ','PUT','간호호출',null,'INT','nurse',null,null,269,'호출','/call/',to_date('15/10/05','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_NURSE(NURSE_SEQ, P_ID, REQ_CODE_VALUE, REQ_VALUE, REQ_ETC_VALUE) 
VALUES(SEQ_MCARE_IF_NURSE_SEQ.NEXTVAL, :pId, :reqCodeValue, :reqValue, :reqEtcValue)');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (624,'SQL','mcareExtDataSource','환자의 차량번호 등록/수정.
삭제는 없다고 함. 화면에서 공백입력 막아야 함. ','PUT','차량번호 관리',null,'INT','carNo',null,null,270,'주차관리','/park/',to_date('15/10/05','RR/MM/DD'),'admin',to_date('15/10/19','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PARKING (PARK_SEQ, P_ID, CAR_NO_VALUE) 
VALUES(SEQ_MCARE_IF_PARK_SEQ.NEXTVAL, :pId, :carNoValue)
');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (643,'SQL','mcareExtDataSource','이전호출내역 ','GET','미처리내역',null,'MAP','nursePrevReq',null,null,269,'호출','/call/',to_date('15/10/05','RR/MM/DD'),'admin',to_date('15/10/20','RR/MM/DD'),'admin','SELECT P_ID, REQ_CODE_VALUE, REQ_VALUE, REQ_ETC_VALUE, REG_DT, UPDATE_DT, RECEIVE_DT 
FROM MCARE_INF_NURSE 
WHERE P_ID=:pId AND UPDATE_DT IS NULL ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (683,'SQL','mcareDataSource',null,'GET','getList',null,'LIST','getList',null,null,309,'전화번호','/telno/',to_date('15/10/08','RR/MM/DD'),'admin',to_date('15/10/08','RR/MM/DD'),null,'SELECT BUILDING_DESC, ROOM_DESC, TEL_VALUE, TELNO_ORDER 
FROM MCARE_TELNO 
ORDER BY TELNO_ORDER ASC ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (723,'SQL','mcareDataSource','환자번호에 달려있는 device token들을 가져오기 ','GET','환자의 토큰목록',null,'LIST','getDeviceTokenList',null,null,329,'_실험용모음','/test/',to_date('15/10/16','RR/MM/DD'),'admin',to_date('15/10/16','RR/MM/DD'),null,'SELECT DEVICE_TOKEN_ID, PLATFORM_TYPE FROM MNS_RECEIVER_DEVICE WHERE RECEIVER_ID=:pId');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (725,'WEB_SERVICE',null,'height	STRING		키
weight	STRING		몸무게
glus_rslt	STRING		혈당','GET','혈당_키_몸무게',null,'MAP','getPersonHealthInfo',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetPersonHealthInfo&pid={pId}&prsc_stdt={startDate}&prsc_endt={endDate}',34,'검사결과','/totalexam/',to_date('15/10/17','RR/MM/DD'),'admin',to_date('15/10/17','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (726,'WEB_SERVICE',null,'- 예약리스트 내용을 참조 해서 보냄
- 예약을 조회하면 receiptNo(=rcpn_no), mdcr_cd(=crmCd)가 들어있음 
- crmCd는 아마 진료과에서의 진료 순번이 아닐까 추측만 해봄 ','POST','도착확인',null,'INT','reqArrivedConfirm',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqSetNurseArrivedConfirm&rcpn_no={receiptNo}&mdcr_prgr_stat_cd=F&mcrm_cd={crmCd}&userid=BEACON',62,'대기시간조회','/waittime/',to_date('15/10/17','RR/MM/DD'),'admin',to_date('15/10/17','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (728,'SQL','mcareDataSource','현재상태를 갱신함 ','PUT','현재상태갱신',null,'INT','updateUserState',null,null,330,'도우미','/helper/',to_date('15/10/17','RR/MM/DD'),'admin',to_date('15/10/17','RR/MM/DD'),'admin','UPDATE MCARE_USER_STAT 
SET STATE_CD=:stateCd, STATE_DT =SYSDATE
WHERE P_ID=:pId');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (732,'SQL','mcareExtDataSource',null,'PUT','귀가_차량번호알림_MA-40-07-40-00',null,'INT','sendCarNo',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/19','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHAT_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''MA-40-07-40-00'', :whatValue, SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (727,'SQL','mcareDataSource','현재 상태 확인 ','GET','현재상태조회',null,'MAP','getCurrState',null,null,330,'도우미','/helper/',to_date('15/10/17','RR/MM/DD'),'admin',to_date('15/10/17','RR/MM/DD'),null,'SELECT P_ID, STATE_CD, STATE_DT 
FROM MCARE_USER_STATE 
WHERE P_ID=:pId');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (729,'SQL','mcareExtDataSource','예약하지 않고, 병원에 온 환자를 위해 번호표 발급하라고 알려주기 ','PUT','접수_번호표발급_MA-10-02-20-00',null,'INT','reqReceiptTicketGuide',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/17','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHERE_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''MA-10-02-20-00'', ''원무과'', SYSDATE, :localeCd)');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (731,'SQL','mcareExtDataSource','환자가 직접 도착알림을 해야하는 경우 ','PUT','접수_도착알림필요_MM-10-01-10-00',null,'INT','reqArrivedConfirm',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/17','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHERE_VALUE, WHEN_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''MM-10-01-10-00'', :whereValue, :whenValue, SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (733,'SQL','mcareExtDataSource',null,'PUT','귀가_다음내원일_MA-40-05-40-00',null,'INT','nextReservation',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/19','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHEN_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''MA-40-05-40-00'', :whenValue, SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (734,'SQL','mcareExtReservationDataSource','예약','POST','진료예약',null,'INT','reservation',null,null,81,'예약','/reservation/',to_date('15/10/19','RR/MM/DD'),'admin',to_date('15/10/23','RR/MM/DD'),'admin','insert into appointment(
BOOKING_IDX
,MEMBER_IDX
,HOPE_DATE
,HOPE_TIME
,PATIENT_ID
,PATIENT_NAME
,PATIENT_BIRTHDAY
,PATIENT_GENDER
,CREATE_DT
,PROC_STATUS
,DISEASE
,TREAT_CODE
,TREAT_NAME
,DOCTOR_NO
,DOCTOR_NAME
,CREATE_DATE
)
values( NVL(
(select NVL(max(a.BOOKING_IDX) + 1,1) as BOOKING_IDX 
from appointment# a where a.BOOKING_IDX like  to_char(sysdate,''yyyymmdd'')||''%''),
to_char(sysdate,''yyyymmdd'')||''000001'')
,''000000119582''
,:date
,:time
,:pId
,:pNm
,:birthDt
,:genderCd
,sysdate
,''n''
,:symptom
,:departmentCd
,:departmentNm
,:doctorId
,:doctorNm
,to_char(sysdate,''yyyymmdd'')
)');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (741,'SQL','mcareExtDataSource','# 아무개님, 내시경실로 가세요 (검사실) ','PUT','진료_검사실안내_ML-20-40-30-00',null,'INT','guideExamRoom',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHERE_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''ML-20-40-30-00'', :whereValue,  SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (744,'SQL','mcareExtReservationDataSource','날짜와 증상으로 예약 중간테이블에 insert 되었는지 체크','GET','예약테이블확인',null,'LIST','reservationTest',null,null,81,'예약','/reservation/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','select * from  appointment#   where hope_date = :date  or DISEASE like ''%:symptom%''');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (749,'WEB_SERVICE',null,null,'GET','인터넷 예약 조회',null,'MAP','getRevInternet',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetHomePageReservationInfo&pid={pId}',81,'예약','/reservation/',to_date('15/10/23','RR/MM/DD'),'admin',to_date('15/10/23','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (730,'SQL','mcareExtDataSource','예약에 대해 도착알림이 완료되었음을 알림 
','PUT','접수_도착알림완료_MM-10-01-40-00',null,'INT','notifyArrivedConfirmedSuccess',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/17','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHERE_VALUE, WHEN_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''MM-10-01-40-00'', :whereValue, :whenValue, SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (735,'SQL','mcareExtDataSource','{0}시, {1}에 {2} 있습니다.  [길찾기] ','PUT','접수_안내_MA-10-05-40-00',null,'INT','todayGuide',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHEN_VALUE, WHERE_VALUE, WHAT_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''MA-10-05-40-00'', :whenValue, :whereValue, :whatValue, SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (736,'SQL','mcareExtDataSource','# 대기번호 xx, 현재 대기인원 yy명입니다. [진료대기시간조회] ','PUT','진료_대기시간_MM-10-02-40-00',null,'INT','waitingTime',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHAT_VALUE, HOW_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''MM-10-02-40-00'', :whatValue, :howValue, SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (737,'SQL','mcareExtDataSource','# xx님, xx번 창구로 방문하세요. ','PUT','진료_창구호출_ML-10-02-31-00',null,'INT','callWaiting',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHERE_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''ML-10-02-31-00'', :whereValue,  SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (738,'SQL','mcareExtDataSource','# 내과로 가세요. [길찾기] ','PUT','진료_진료과안내_ML-20-03-30-00',null,'INT','guideDestMap',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/22','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHERE_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''ML-20-03-30-00'', :whereValue,  SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (739,'SQL','mcareExtDataSource','# 내과는 현재 10분 지연되고 있습니다. ','PUT','진료_지연안내_ML-20-03-40-00',null,'INT','delayGuide',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHERE_VALUE, WHEN_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''ML-20-03-40-00'', :whereValue,  :whenValue, SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (740,'SQL','mcareExtDataSource','# 내과 5번방으로 오세요 ','PUT','진료_진료실안내_ML-20-03-31-00',null,'INT','guideDoctorRoom',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, WHERE_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''ML-20-03-31-00'', :whereValue,  SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (742,'SQL','mcareExtDataSource','# 진료가 완료되었습니다. 수납해주세요. ','PUT','수납_수납안내_ML-30-04-30-00',null,'INT','payGuide',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin','INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''ML-30-04-30-00'', SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (743,'SQL','mcareExtDataSource','# 원내약국에서 약을 찾아가세요. ','PUT','귀가_약찾기_ML-40-06-40-00',null,'INT','medicineGuide',null,null,331,'도우미용 Push','/helperpush/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),null,'INSERT INTO MCARE_INF_PUSH(PUSH_SEQ, P_ID, P_NAME, MSG_NO_VALUE, REG_DT, LOCALE_CD) 
VALUES(SEQ_MCARE_IF_PUSH_SEQ.NEXTVAL, :pId, :pName, ''ML-40-06-40-00'', SYSDATE, :localeCd) ');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (745,'SQL','mcareDataSource','기본값은 하루만 검색하면 될듯 ','GET','도우미메시지',null,'LIST','messageList',null,null,330,'도우미','/helper/',to_date('15/10/21','RR/MM/DD'),'admin',to_date('15/10/22','RR/MM/DD'),'admin','SELECT  USER_MSG, TO_CHAR(SEND_DT, ''yyyy-MM-dd HH24:MI:ss'') AS SEND_DT 
FROM MCARE_HELPER_CONTENTS  
WHERE P_ID=:pId AND TO_CHAR(SEND_DT, ''yyyyMMdd'') BETWEEN :startDate AND :endDate 
ORDER BY SEND_DT ASC 
');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (747,'WEB_SERVICE',null,'오늘 예약된 항목중에서 상태가 접수인 것이 도착확인 대상임 
','GET','도착확인 대상',null,'LIST','arriveConfirmTargetList',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetReservationInfo&pid={pId}&apnt_stdt={date}&apnt_endt={date}&hstr_stat_cdnm=접수',62,'대기시간조회','/waittime/',to_date('15/10/22','RR/MM/DD'),'admin',to_date('15/10/22','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (61,'WEB_SERVICE',null,null,'GET','검사결과 대분류',null,'LIST','getResultTotal',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.getResultTotalExamList&pid={pId}&prsc_stdt={startDt}&prsc_endt={endDt}',34,'검사결과','/totalexam/',to_date('15/09/08','RR/MM/DD'),'admin',to_date('15/10/05','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (81,'WEB_SERVICE',null,'prsc_sqno 파라미터에 콤마로 구분하여 처방키를 여러개 줄 수 있음 

exmn_grp_nm	검사분류
exmn_nm	검사명
rslt_valu	검사결과
rfvl	참고치
rslt_uncd	단위
nrml_dvcd	하이/로우
frtm_rslt	이전결과값
','GET','검사결과 상세',null,'LIST','getResultDetail',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.getDetailResultTotalExamList&prsc_sqno={prescriptionNo}',34,'검사결과','/totalexam/',to_date('15/09/08','RR/MM/DD'),'admin',to_date('15/10/07','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (101,'WEB_SERVICE',null,null,'GET','수진이력정보조회(외래)',null,'LIST','getOutList',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetHomoMdcrExamHist&pid={pId}&mdcr_stdt={startDt}&mdcr_endt={endDt}',33,'이력조회','/history/',to_date('15/09/10','RR/MM/DD'),'admin',to_date('15/10/04','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (121,'WEB_SERVICE',null,'파라미터 맞추는 작업 했음. ','GET','수진이력정보조회(입원)',null,'LIST','getInList',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetHomaMdcrExamHist&pid={pId}&mdcr_stdt={startDt}&mdcr_endt={endDt}',33,'이력조회','/history/',to_date('15/09/10','RR/MM/DD'),'admin',to_date('15/10/04','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (122,'WEB_SERVICE',null,'pt_nm	환자명
mdcr_dept_nm	진료과
prsc_date	처방일
mdpr_enm	약품명
prsc_dosg	처방량
prsc_notm	횟수
prsc_nody	일수
itom_nm	용법명
','GET','처방조회',null,'LIST','getPrescList',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetDrugMedicationInfo&pid={pId}&prsc_stdt={startDt}&prsc_endt={endDt}',41,'처방조회','/prescription/',to_date('15/09/10','RR/MM/DD'),'admin',to_date('15/10/07','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (142,'SQL','mcsSmsDataSource',null,'POST','문자발송',null,'INT','putSms',null,null,30,'사용자관리','/user/',to_date('15/09/16','RR/MM/DD'),'admin',to_date('15/09/24','RR/MM/DD'),'admin','INSERT INTO SDK_SMS_SEND (MSG_ID, USER_ID, SCHEDULE_TYPE, SUBJECT, NOW_DATE,  SEND_DATE, 
CALLBACK, DEST_COUNT, DEST_INFO, SMS_MSG)
VALUES (SDK_SMS_SEQ.nextval, ''app'', 0, NULL,
to_char(sysdate,''YYYYMMDDHH24MISS''), to_char(sysdate,''YYYYMMDDHH24MISS''), :callBack,1, :destInfo, :smsMsg)');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (583,'SQL','mcareDataSource','환자의 환자 번호 와 패스워드가 유효한지 확인한다.','GET','비밀번호 확인',null,'MAP','checkPWD',null,null,30,'사용자관리','/user/',to_date('15/10/01','RR/MM/DD'),'admin',to_date('15/10/01','RR/MM/DD'),'admin','SELECT 
CASE WHEN COUNT(*) = 0 THEN ''false''
           ELSE ''true'' END AS validPassword 
FROM MCARE_USER 
WHERE P_ID=:pId 
AND PASSWORD_VALUE=:passwordValue');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (163,'WEB_SERVICE',null,null,'GET','진료비 상세내역',null,'MAP','getBillDetail',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetBillIssueDetailInfo&bill_sqno={billNo}',33,'이력조회','/history/',to_date('15/09/16','RR/MM/DD'),'admin',to_date('15/09/30','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (164,'WEB_SERVICE',null,null,'GET','API TEST',null,'MAP','test',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetMdexApntInfo&pid=150388847&apnt_stdt=20150815&apnt_endt=20150915',41,'처방조회','/prescription/',to_date('15/09/16','RR/MM/DD'),'admin',to_date('15/09/16','RR/MM/DD'),null,null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (603,'WEB_SERVICE',null,'3개의 값은 선택적으로 입력됨 ','GET','환자번호 찾기',null,'MAP','findPid',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.userinfo&pt_nm={pNm}&clph_no={cellphoneNo}&pid=',30,'사용자관리','/user/',to_date('15/10/01','RR/MM/DD'),'admin',to_date('15/10/26','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (703,'WEB_SERVICE',null,'mdcr_dept_abcd	진료과약어코드	STRING
mdcr_dept_nm	진료과명	STRING
sort_no	순번	NUMBER','GET','1_예약가능한 진료과',null,'LIST','getRevDept',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetInternetReservationDeptInfo',81,'예약','/reservation/',to_date('15/10/11','RR/MM/DD'),'admin',to_date('15/10/11','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (704,'WEB_SERVICE',null,'mdcr_dept_abcd	진료과약어코드	STRING
mdcr_dr_nm	진료의사명	STRING
mdcr_dr_id	진료의사id	STRING
','GET','2_진료과에 의사',null,'LIST','getRevDoc',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetInternetReservationDrInfo&mdcr_dept_abcd={departmentCd}',81,'예약','/reservation/',to_date('15/10/11','RR/MM/DD'),'admin',to_date('15/10/11','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (705,'WEB_SERVICE',null,'        "dowk": "3",
        "cal_date": "20151103",
        "dowknm": "화"','GET','3_의사의 예약가능날짜',null,'LIST','getRevDate',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetInternetAppointment&ei_interface=111|1&destnation_smonth={startYearMonth}&destnation_emonth={endYearMonth}&de&mdcr_dr_lcno={doctorId}&mdcr_dept_engl_abnm={departmentCd}',81,'예약','/reservation/',to_date('15/10/11','RR/MM/DD'),'admin',to_date('15/10/21','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (706,'WEB_SERVICE',null,'* resvusablecnt --예약가능시간
* respersonnelamt --예약인원총,
* respersonnelfst --예약인원초진,
* respersonnelrev --예약인원재진,              
* usapersonnelamt --가능인원총,
* usapersonnelfst --가능인원초진,
* usapersonnelrev --가능인원재진','GET','4_날짜에 시간',null,'LIST','getRevTime',null,'http://200.2.10.243:8088/phis/.live?submit_id=medicalscheduleapp.internetgetdrusabletime&ei_interface=111|1&mdcr_date={date}&mdcr_dr_id={doctorId}&mdcr_dept_engl_abnm={departmentCd}',81,'예약','/reservation/',to_date('15/10/11','RR/MM/DD'),'admin',to_date('15/10/11','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (663,'SQL','mcareExtDataSource',null,'GET','순번기 목록',null,'LIST','list',null,null,289,'번호표발급기','/ticket/',to_date('15/10/08','RR/MM/DD'),'admin',to_date('15/10/08','RR/MM/DD'),null,' SELECT   SEQ_NO AS seq_no,
           SEQ_NM AS SECNM,
           NVL((SELECT   b.CALL_NO          
                 FROM   DISP_AI_call b
                WHERE       b.CHU_NO = ''99''
                        AND b.instcd = a.instcd
                        AND b.SEQ_NO = a.seq_no),''-'')
              AS DELEY,
           INSTCD  as INSTCD 
    FROM   SMART.DISP_AI_GATE_NM A
   WHERE   INSTCD = ''1'' AND GUBN = ''Y''
ORDER BY   SORT');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (222,'SQL','mcareDataSource','환자번호를 이용해서 등록된 사용자 정보를 조회한다. ','GET','사용자 등록정보 조회',null,'MAP','getLoginInfo',null,null,30,'사용자관리','/user/',to_date('15/09/22','RR/MM/DD'),'admin',to_date('15/10/22','RR/MM/DD'),'admin','SELECT P_ID, P_NAME, LOGIN_FAIL_CNT, PASSWORD_VALUE, PASSWORD_UPDATE_DT, LOCAL_CIPHER_KEY_VALUE 
FROM MCARE_USER 
WHERE P_ID=:pId');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (162,'WEB_SERVICE',null,'API 맞추는 작업 했음. ','GET','진료비 합계',null,'MAP','getBillSum',null,'http://200.2.10.243:8088/phis/.live?submit_id=beaconapp.reqGetBillIssueInfo&rcpn_no={receiptNo}',33,'이력조회','/history/',to_date('15/09/16','RR/MM/DD'),'admin',to_date('15/09/30','RR/MM/DD'),'admin',null);
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (221,'SQL','mcareDataSource','로그인 실패 횟수를 1 증가시키기 
DB변경에 따라 수정 ','PUT','로그인실패횟수증가',null,'INT','incLoginFailCnt',null,null,30,'사용자관리','/user/',to_date('15/09/22','RR/MM/DD'),'admin',to_date('15/10/22','RR/MM/DD'),'admin','UPDATE MCARE_USER 
SET LOGIN_FAIL_CNT = (SELECT LOGIN_FAIL_CNT + 1 FROM MCARE_USER WHERE P_ID=:pId) 
WHERE P_ID =:pId');
Insert into MCARE_API (API_SEQ,API_TYPE,DATA_SOURCE_NAME,API_DESC,HTTP_METHOD_TYPE,API_NAME,TARGET_NAME,RESULT_TYPE,REQ_URL_ADDR,REQ_URL_NAME,TARGET_URL_ADDR,CAT_SEQ,CAT_NAME,CAT_PATH_NAME,CREATE_DT,CREATE_ID,UPDATE_DT,UPDATE_ID,QUERY_MSG) values (243,'SQL','mcareDataSource',null,'GET','사용자 동의서 조회',null,'LIST','getUserAgreementList',null,null,30,'사용자관리','/user/',to_date('15/09/24','RR/MM/DD'),'admin',to_date('15/10/02','RR/MM/DD'),'admin',
'SELECT
    AGREE.AGREEMENT_SEQ,
    AGREE.AGREEMENT_ORDER,
    AGREE.AGREEMENT_NAME,
    AGREE.AGREEMENT_CONTENTS,
    AGREE.AGREEMENT_ID,
    AGREE.VERSION_NUMBER,
    AGREE.REQUIRED_YN,
    AGREE.ENABLED_YN,
    AGREE.NEW_YN,
	AGERE.TYPE_NAME,
    USERAGREE.AGREEMENT_YN,
    USERAGREE.REGISTER_DT
  FROM
    MCARE_AGREEMENT AGREE,
    (
      SELECT
        USERAGR.USER_AGREEMENT_SEQ,
        USERAGR.AGREEMENT_SEQ,
        USERAGR.P_ID,
        USERAGR.AGREEMENT_YN,
        USERAGR.REGISTER_DT
      FROM
        MCARE_USER_AGREEMENT USERAGR,
        (
          SELECT
            AGREEMENT_ID, MAX(USER_AGREEMENT_SEQ) AS USER_AGREEMENT_SEQ
          FROM
            MCARE_USER_AGREEMENT
          WHERE
            P_ID=:pid
          GROUP BY AGREEMENT_ID
        ) NEWUSERAGR
      WHERE
        USERAGR.USER_AGREEMENT_SEQ = NEWUSERAGR.USER_AGREEMENT_SEQ
    ) USERAGREE
  WHERE
    AGREE.AGREEMENT_SEQ = USERAGREE.AGREEMENT_SEQ');



-- api_seq
ALTER SEQUENCE SEQ_MCARE_API_AS INCREMENT BY 749;
SELECT SEQ_MCARE_API_AS.NEXTVAL FROM DUAL; 
ALTER SEQUENCE SEQ_MCARE_API_AS INCREMENT BY 1; 


-- MCARE_API_PARAM 
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1365,723,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1713,221,'STRING','환자번호','pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1448,624,'STRING','환자번호','pId','000000002');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1449,624,'STRING','차량번호','carNoValue','가나다1234');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1805,734,'STRING','진료희망일자','date','20151022');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1806,734,'STRING','진료희망시간','time','1100');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1757,747,'STRING','환자번호','pId','110373821');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1758,747,'STRING','오늘날짜','date','20140721');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1759,181,'STRING',null,'receiptNo','102152855');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1807,734,'STRING','환자번호','pId','12345678');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1808,734,'STRING','환자이름','pNm','테스트');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1809,734,'STRING','생년월일','birthDt','19810725');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1810,734,'STRING','성별 (1,3:남, 2,4:여, 5,7:외국인남, 6,8:외국인여)','genderCd','1');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1811,734,'STRING','증상','symptom','테스트');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1812,734,'STRING','진료과코드','departmentCd','FM');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1813,734,'STRING','의사번호','doctorId','654321');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1814,734,'STRING','진료과명','departmentNm','가정의학과');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1815,734,'STRING','진료의','doctorNm','테스트의');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1565,730,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1566,730,'STRING','도착알림을 보낸 장소','whereValue',' 치과');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1567,730,'STRING','시간정보','whenValue','17시 30분');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1635,623,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1419,727,'STRING','환자번호','pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1760,244,'STRING','환자번호','pid','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1568,730,'STRING','locale ','localeCd','en');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1569,730,'STRING','환자명','pName','API실험용환자');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1609,737,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1610,737,'STRING','환자명','pName','김수한무거북이와두루미');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1611,737,'STRING','창구번호','whereValue','5');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1446,182,'STRING','환자번호','pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1447,182,'STRING','진료일자','date','20151001');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1612,737,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1624,743,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1636,623,'STRING','요청코드','reqCodeValue','1, 2, 4');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1637,623,'STRING','요청코드에 대한 text를 콤마로 구분한것','reqValue','환자복 교체, 시트교체, 수액교체');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1625,743,'STRING','환자명','pName','한경태');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1626,743,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1631,741,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1632,741,'STRING','환자명','pName','고기반찬좋아');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1633,741,'STRING','장소','whereValue','주사실');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1634,741,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1638,623,'STRING','주관식요청 ','reqEtcValue','TV좀 꺼주세요 ');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1761,222,'STRING','환자번호','pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1556,643,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1557,733,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1558,733,'STRING','다음내원일','whenValue','2015-10-30 10:30');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1559,733,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1560,733,'STRING','환자명','pName','API실험용환자');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1621,742,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1622,742,'STRING','환자명','pName','깍두기오빠');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1623,742,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1423,728,'STRING','환자번호','pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1424,728,'STRING','상태코드 ','stateCd','UNKNOWN');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1561,732,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1404,725,'STRING','환자번호','pId','070193358');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1405,725,'STRING','검색시작날짜_오늘로 설정해서 검색','startDate','20150902');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1406,725,'STRING','검색종료날짜_오늘로 설정해서 검색','endDate','20150902');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1562,732,'STRING','차량번호','whatValue','가나1234');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1428,726,'STRING','의미모름','crmCd','D0101_3');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1429,726,'STRING','접수번호','receiptNo','92136215');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1613,740,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1563,732,'STRING','locale ','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1564,732,'STRING','환자명','pName','API실험용환자');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1445,60,'STRING','환자번호','pId','970411295');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1614,740,'STRING','환자명','pName','박씨부인');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1615,740,'STRING','장소','whereValue','내과 5번방');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1570,731,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1571,731,'STRING','도착알림을 해야하는 장소','whereValue','치과');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1572,731,'STRING','예약시간','whenValue','13시 30분');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1573,731,'STRING','locale ','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1574,731,'STRING','환자명','pName','김수한무거북이와두루미');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1578,729,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1579,729,'STRING','locale ','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1580,729,'STRING','환자명','pName','김수한무거북이와두루미');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1586,736,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1587,736,'STRING','환자명 ','pName','홍길동');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1588,736,'STRING','대기번호','whatValue','32');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1589,736,'STRING','현재 대기인원 ','howValue','3');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1590,736,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1604,739,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1605,739,'STRING','환자명','pName','심청');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1606,739,'STRING','진료과','whereValue','이비인후과');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1607,739,'STRING','지연시간(분)','whenValue','13');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1608,739,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1616,740,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1665,744,'STRING','진료예약일자','date','20151209');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1666,744,'STRING','증상','symptom','테스트');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1691,745,'STRING','환자번호','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1692,745,'STRING','시작날짜 ','startDate','20151001');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1693,745,'STRING','종료날짜 ','endDate','20151030');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (640,142,'STRING','발신자 번호','callBack','01029502538');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (641,142,'STRING','이름^전화번호','destInfo','test5^01091081550');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1679,735,'STRING','환자번호 ','pId','dbs201');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (663,59,'STRING','플랫폼유형','platformType','A');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1680,735,'STRING','환자명','pName','튼튼한환자');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (642,142,'STRING','내용','smsMsg','부산대SMS테스트');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1316,704,'STRING','진료과','departmentCd','I1');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1329,706,'STRING','예약할 날짜','date','20151126');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1330,706,'STRING','의사ID','doctorId','050868');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1829,603,'STRING','환자명','pNm','이영주');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1001,243,'STRING',null,'pid','dbs202');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1681,735,'STRING','시간','whenValue','10:30');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1639,705,'STRING','의사ID','doctorId','2010296');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1640,705,'STRING','진료과ID','departmentCd','FM');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1331,706,'STRING','진료과코드','departmentCd','I1');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1641,705,'STRING','시작년월','startYearMonth','201509');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1341,664,'STRING','순번기 구분자','seqCd','C1');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1804,749,'STRING','환자번호','pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (823,163,'STRING','영수증번호','billNo','47220142');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1261,201,'STRING',null,'pId','110373821');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (241,53,'STRING','토큰','deviceTokenId','dyxgA1Gar8k:APA91bEz7xQOOHZh4F6aMIqQ6xUugagyi4eKdAUoOf1-xjC3EpwmvOgZFssLTGdoY-wJoXw_YpYAEa8K2APUYU7cP2KRLlmbNBmeSilKzQKalNiwwmF2f4FqKmvmxElIt3mVNx23Ez21');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (242,53,'STRING','환자번호','receiverId','111');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (243,53,'STRING','플랫폼 유형 ','platformType','A:Android, I:iOS');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1262,201,'STRING',null,'startDt','20110129');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1263,201,'STRING',null,'endDt','20150916');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (767,162,'STRING','접수번호','receiptNo','104958716');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (927,563,'STRING','sha256암호화된 패스워드','passwordValue',null);
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (928,563,'STRING','환자번호','pId','1234');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1830,603,'STRING','전화번호 ','cellphoneNo','01025480834');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (926,241,'STRING','환자번호','pId','123456789');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (929,583,'STRING','환자번호','pId','dbs202');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (930,583,'STRING','패스워드','passwordValue',null);
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1025,101,'STRING','차트번호','pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1026,101,'STRING','진료시작일','startDt','20150102');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1027,101,'STRING','진료종료일','endDt','20150902');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1028,121,'STRING',null,'pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1029,121,'STRING',null,'startDt','20150102');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1030,121,'STRING',null,'endDt','20150902');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1081,61,'STRING','환자번호','pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1082,61,'STRING','시작일 ','startDt','20150902');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1083,61,'STRING','종료일','endDt','20150902');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1642,705,'STRING','종료년월','endYearMonth','201510');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1181,122,'STRING',null,'pId','150346410');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1182,122,'STRING',null,'startDt','20150825');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (244,54,'STRING','토큰','deviceTokenId','111');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1183,122,'STRING',null,'endDt','20150825');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (245,54,'STRING','환자번호','receiverId','111');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (246,54,'STRING','android:A, iOS:I','platformType','A');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1221,81,'STRING','처방키','prescriptionNo','1604469212, 1604405175, 1604405176, 1604405177, 1604405178, 1604405179, 1604405180, 1604405174, 1604339096, 1604339479, 1604339089, 1604339090, 1604339142, 1604339143, 1604339144, 1604339480, 1604339481, 1604339091, 1604339141, 1604339145, 1604339146, 1604339092, 1604339093, 1604338386, 1604338387, 1604338289, 1604336613, 1604336614, 1604336533, 1604336519, 1604336520, 1604336521, 1604336523, 1604336524, 1604336527, 1604336530, 1604336525, 1604336526, 1604336528, 1604336522, 1604336531, 1603687882, 1603687884, 1603687883, 1603277331, 1603263899, 1603263900, 1603263901, 1603201545, 1603106759, 1603106758, 1603041905, 1603041904, 1603017445, 1603017446, 1603017447, 1603017448, 1603017449, 1602950235, 1602956453, 1602956454, 1602956452, 1602932856, 1602903715, 1602903716, 1602903717, 1602903714, 1602903713, 1602935553, 1602935554, 1602928750, 1602925663, 1602904845, 1602921992, 1602901876, 1602901877, 1602901878, 1602899822, 1602899535, 1602898926, 1602915813, 1602915797, 1602915798, 1602915800, 1602915801, 1602915804, 1602915806, 1602915807, 1602915808, 1602915809, 1602915814, 1602915799, 1602915802, 1602915803, 1602915805, 16029158121602915815');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1736,738,'STRING','환자번호','pId','dbs197');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1737,738,'STRING','환자명','pName','서영일');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1738,738,'STRING','목적지','whereValue','원무과');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1682,735,'STRING','장소 ','whereValue','대장항문과');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1739,738,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1683,735,'STRING','무엇','whatValue','검사');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (1684,735,'STRING','locale','localeCd','ko');
Insert into MCARE_API_PARAM (PARAM_SEQ,API_SEQ,DATA_TYPE,PARAM_DESC,PARAM_NAME,SAMPLE_VALUE) values (2565,873,'STRING','환자번호','pid','000000002');

-- param_seq 
ALTER SEQUENCE SEQ_MCARE_API_PARAM_PS INCREMENT BY 1830;
SELECT SEQ_MCARE_API_PARAM_PS.NEXTVAL FROM DUAL; 
ALTER SEQUENCE SEQ_MCARE_API_PARAM_PS INCREMENT BY 1; 


-- MCARE_API_HEADER 
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (621,747,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (622,181,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (613,182,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (604,725,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (611,726,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (612,60,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (644,603,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (596,706,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (615,705,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (591,704,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (597,703,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (141,53,'Content-Type','application/json');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (561,201,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (283,162,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (303,163,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (383,101,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (384,121,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (401,61,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (501,122,'Accept','application/xml');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (142,54,'Content-Type','application/json');
Insert into MCARE_API_HEADER (HEADER_SEQ,API_SEQ,HEADER_NAME,HEADER_VALUE) values (541,81,'Accept','application/xml');

-- header_seq
ALTER SEQUENCE SEQ_MCARE_API_HEADER_HS INCREMENT BY 644;
SELECT SEQ_MCARE_API_HEADER_HS.NEXTVAL FROM DUAL; 
ALTER SEQUENCE SEQ_MCARE_API_HEADER_HS INCREMENT BY 1; 



-- MCARE_PUSH_FORM 
INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('guide',
             'GUIDE',
             'Y',
             NULL,
             'Y',
             '순수한 텍스트 메시지 전달',
             TO_DATE ('11/03/2015 21:58:50', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('welcome',
             'GUIDE',
             'Y',
             NULL,
             'Y',
             '웰컴메시지용',
             TO_DATE ('11/03/2015 21:58:50', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('agreement',
             'PAGE',
             'Y',
             'agreement',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('announceArrival',
             'PAGE',
             'Y',
             'announceArrival',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('appointmentSearch',
             'PAGE',
             'Y',
             'appointmentSearch',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('healthHandbook',
             'PAGE',
             'Y',
             'healthHandbook',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('issueTicket',
             'PAGE',
             'Y',
             'issueTicket',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('mobileCard',
             'PAGE',
             'Y',
             'mobileCard',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('nextLocation',
             'PAGE',
             'Y',
             'nextLocation',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('nurse',
             'PAGE',
             'Y',
             'nurse',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('openMap',
             'MAP',
             'Y',
             NULL,
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('parking',
             'PAGE',
             'Y',
             'parking',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('prescription',
             'PAGE',
             'Y',
             'prescription',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('reservation',
             'PAGE',
             'Y',
             'reservation',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('resultExam',
             'PAGE',
             'Y',
             'resultExam',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('telNo',
             'PAGE',
             'Y',
             'telNo',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('ticket',
             'PAGE',
             'Y',
             'ticket',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('treatmentHistory',
             'PAGE',
             'Y',
             'treatmentHistory',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

INSERT INTO MCARE_PUSH_FORM (FORM_ID,
                             FORM_TYPE,
                             USE_YN,
                             MENU_ID,
                             INCLUDE_NAME_YN,
                             FORM_DESC,
                             REG_DT)
     VALUES ('waitingTime',
             'PAGE',
             'Y',
             'waitingTime',
             'Y',
             '일괄입력 (DBS)',
             TO_DATE ('11/03/2015 21:58:57', 'MM/DD/YYYY HH24:MI:SS'));

COMMIT;


--MCARE_POI_MAPPING 데이터
--1층
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동1층 약제부','약제부', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동1층 약제부(TDM실)','약제부', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '본관1층 약제부','약제부', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동1층 신장내과','신장내과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동1층 영상의학과','영상의학과 제2일반영상촬영진단실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동1층 초음파진단실(예약)(예약)','영상의학과 초음파실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동1층 초음파진단실(예약)(예약)','영상의학과 초음파실 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 영상의학과','일반촬영실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 일반영상진단실','일반촬영실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 일반촬영실','일반촬영실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 일반촬영실(예약)','일반촬영실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 채혈실접수','채혈실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 정신건강의학과','정신건강의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 정신건강의학과(예약)','정신건강의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 정형외과','정형외과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 신경외과','신경외과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 주사실','신경외과 주사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동1층 통합예약실(예약)','B동1층 통합예약실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동4층또는1층 통합예약실(예약)','B동1층 통합예약실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'D동1층 재활의학센터','재활의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '재활의학과 근전도실(예약)','재활의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '재활의학과 물리치료실','재활센터 운동치료실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '재활의학과 소아치료실','재활센터 소아치료실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '재활의학과 재활언어치료실','재활센터 언어치료실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '재활의학과 작업치료실','재활센터 작업치료실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'E동1층 응급의학과','응급실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터1층 통합예약실(1창구)(예약)','암센터 원무과', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터1층 통합예약실(6번창구)(예약)','암센터 원무과', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터1층 채혈실접수','암센터 채혈실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터1층 심전도실','암센터 심전도실', 'DBS', SYSDATE, 'Y');

-- 2층 
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 알레르기실(예약)','호흡기알레르기내과 운동부하검사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 폐기능검사실','호흡기알레르기내과 운동부하검사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 폐기능검사실(예약)','호흡기알레르기내과 운동부하검사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 심전도실','순환기내과 심전도실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 흉부외과','흉부외과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 순환기 내과','순환기내과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 호흡기내과','호흡기알레르기내과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 호흡기내과(마약)','호흡기알레르기내과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동2층 호흡기알레르기내과','호흡기알레르기내과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동2층 이비인후과','이비인후과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동2층 감염내과','감염내과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터2층 내시경실(예약)','소화기병센터', 'DBS', SYSDATE, 'Y');

-- 3층
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동3층 신경과','신경과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동3층 심장검사실(예약)','심장검사실 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동3층 심장검사실(예약)(예약)','심장검사실 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동3층 성형외과','성형외과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'J동3,4층 안과','안과 3층 접수/수납', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'J동3층 안과','안과 3층 접수/수납', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터3층 소화기내과','소화기내과', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터3층 소화기내과(마약)','소화기내과', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'C동 3층 외과(유방암 클리닉)','유방암클리닉', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터3층 산부인과','유방암클리닉', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터3층 외과','유방암클리닉', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터3층 기관지내시경실(예약)','암센터 기관지내시경실', 'DBS', SYSDATE, 'Y');

-- 4층
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동4층 마취통증의학과','마취통증의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동4층 류마티스내과','류마티스내과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동4층 내분비내과','내분비대사내과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동4층또는1층 통합예약실(예약)','B4층 수납', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동4층 내분비내과 당뇨교육실','내분비대사내과 당뇨교육실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터4층 영상의학과','영상의학센터 접수실', 'DBS', SYSDATE, 'Y');

-- 5층
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 가정의학과','가정의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 외과','외과접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 피부과','피부과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 비뇨기과','비뇨기과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 구강내과','치과치료센터 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 구강악안면외과','치과치료센터 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 치과교정과','치과치료센터 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 치과보존과','치과치료센터 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 치과보철과','치과치료센터 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 치과진료센터','치과치료센터 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 치주과','치과치료센터 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동5층 구강방사선과','치과치료센터 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'J동5층 소아과','소아청소년과 간호사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'J동5층 소아심전도실','소아청소년과 간호사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'J동5층 소아심장초음파실(예약)','소아청소년과 간호사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터5층 항암주사실','암센터 항암주사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터5층 혈액종양학과','암센터 혈액종양내과', 'DBS', SYSDATE, 'Y');

-- 6층 
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동6층 산부인과','산부인과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동6층 유방외과','유방외과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'J동6층 체외수정실','난임센터/정자센터', 'DBS', SYSDATE, 'Y');

-- 지하 1층
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'A동지하 감마나이프센터','감마나이프센터 간호사실', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동 지하1층 핵의학과','핵의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동 지하1층 핵의학과(예약)','핵의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'B동지하 PET-CT실','핵의학과 접수', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, '암센터지하1층 병리과','병리과', 'DBS', SYSDATE, 'Y');

-- 지하 2층
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'E동B2층 방사선종양학과','방사선종양학과', 'DBS', SYSDATE, 'Y');
INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) VALUES (SEQ_MCARE_POI_PS.NEXTVAL, 'E동 방사선종양학과','방사선종양학과', 'DBS', SYSDATE, 'Y');
COMMIT;
