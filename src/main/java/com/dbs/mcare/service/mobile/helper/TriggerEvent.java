package com.dbs.mcare.service.mobile.helper;
/**
 * 비콘에서 올라오는 이벤트 
 * 굳이 비콘이라는 용어를 서버쪽 소스에 박아두고 싶지는 않았음 
 * 
 * 
 * @author hyeeun 
 *
 */
public enum TriggerEvent {
	WELCOME, 
	IN, 
	OUT, 
	GOODBYE,
	UNKNOWN
}
