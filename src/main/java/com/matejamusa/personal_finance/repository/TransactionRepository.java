package com.matejamusa.personal_finance.repository;

import com.matejamusa.personal_finance.enums.GraphType;
import com.matejamusa.personal_finance.dto.TransactionDTO;
import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.model.GraphPoint;
import com.matejamusa.personal_finance.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionRepository<T extends Transaction>{
    void create(T transaction, Category category);
    T findById(Long id);
    Page<TransactionDTO> findAllByAccountId(Long accountId, Pageable pageable);
    List<GraphPoint> getGraphByTypeAndAccountId(Long accountId, GraphType type);
}
