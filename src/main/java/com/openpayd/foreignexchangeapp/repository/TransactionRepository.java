package com.openpayd.foreignexchangeapp.repository;

import com.openpayd.foreignexchangeapp.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("SELECT t FROM transactions t WHERE ((:idList) is null or t.id IN (:idList)) " +
			"AND (((:dateStart) is null and (:dateEnd) is null) or t.date BETWEEN :dateStart AND :dateEnd)")
	Page<Transaction> findAllByFilters(@Param("idList") List<Long> idList,
	                                   @Param("dateStart") ZonedDateTime dateStart,
	                                   @Param("dateEnd") ZonedDateTime dateEnd, Pageable pageable);
}
