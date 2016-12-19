--- MCARE_INF_PUSH_MSG 테이블을 mcare 계정이 사용할 수 있도록 한다. (smart계정에서 실행) 
GRANT ALL ON MCARE_INF_PUSH_MSG TO mcare; 

--- MCARE_INF_PUSH_MSG 테이블의 SEQUENCE를 mcare 계정이 사용할 수 있도록 한다. (smart계정에서 실행) 
GRANT SELECT, ALTER ON SEQ_MCARE_INF_FORM_PUSH_PS TO mcare; 


--- mcare 계정이 smart계정의 MCARE_INF_PUSH_MSG 테이블을 같은 이름으로 바라볼 수 있도록 한다. (mcare계정에서 실행) 
CREATE  SYNONYM MCARE_INF_PUSH_MSG FOR smart.MCARE_INF_PUSH_MSG; 

--- mcare 계정이 smart계정의 MCARE_INF_PUSH_MSG 테이블의 시퀀스를 같은 이름으로 바라볼 수 있도록 한다. (mcare계정에서 실행) 
CREATE SYNONYM SEQ_MCARE_INF_FORM_PUSH_PS FOR smart.SEQ_MCARE_INF_FORM_PUSH_PS; 