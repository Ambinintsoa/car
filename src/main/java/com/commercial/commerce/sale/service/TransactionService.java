package com.commercial.commerce.sale.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.commercial.commerce.UserAuth.Repository.UserRepository;
import com.commercial.commerce.sale.entity.TransactionEntity;
import com.commercial.commerce.sale.repository.TransactionRepository;
import com.commercial.commerce.sale.utils.Statistique;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionEntity insert(TransactionEntity transaction) {
        transaction.setId(
                transactionRepository.insertCustom(transaction.getPurchase().getId(), transaction.getReceiver().getId(),
                        transaction.getSender().getId()));
        return transaction;
    }

    public List<TransactionEntity> getAllTransactions() {
        return transactionRepository.findAllActive();
    }

    public List<TransactionEntity> selectWithPagination(int offset, int limit) {
        return transactionRepository.selectWithPagination(limit, offset);
    }

    public List<TransactionEntity> selectTransactions(String id) {
        return transactionRepository.findAllValid(id);
    }

    public Optional<TransactionEntity> getById(String id) {
        return transactionRepository.findById(id);
    }

    public void updateState(TransactionEntity transaction, int state) {
        transactionRepository.updateTransactionState(transaction.getId(), state);
    }

    public long pagination(int limit) {
        long number = transactionRepository.count();
        return (number + limit - 1) / limit;
    }

    public List<Statistique> statistiques() {
        List<Object[]> obj = transactionRepository.getStatsPerMonth();
        List<Statistique> stat = new ArrayList<>();
        for (Object[] iterable_element : obj) {
            Statistique stats = new Statistique();
            stats.setLabel((String) iterable_element[0]);
            stats.setCount((Long) iterable_element[1]);
            stat.add(stats);
        }
        return stat;
    }
}
