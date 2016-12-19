package com.dbs.mcare.service.admin.agg.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.template.GenericRepository;
import com.dbs.mcare.service.admin.agg.repository.dao.AggUserTmp;

@Repository 
public class AggUserTmpRepository extends GenericRepository<AggUserTmp> { 
	
	/**
	 * 연령대별 통계 데이터 얻기 
	 * @return
	 * @throws MCareServiceException
	 */
	public List<AggUserTmp> findByAgeOrder() throws MCareServiceException {
		return super.queryForList("findByAgeOrder", new MapSqlParameterSource()); 

	}	
	
	public List<AggUserTmp> findByPostno() throws MCareServiceException { 
		return super.queryForList("findByPostno", new MapSqlParameterSource()); 
	}
}
