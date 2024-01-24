package com.commercial.commerce.sale.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.commercial.commerce.UserAuth.Repository.UserRepository;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.entity.PurchaseEntity;
import com.commercial.commerce.sale.entity.TransactionEntity;
import com.commercial.commerce.sale.repository.PurchaseRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnonceService annonceService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public PurchaseEntity insert(PurchaseEntity annonce) {
        annonce.setId(purchaseRepository.insertCustom(annonce.getAnnouncement(), annonce.getUser().getId(),
                annonce.getMontant()));
        annonce.setUser(userRepository.findById(annonce.getUser().getId()).get());
        return annonce;
    }

    public List<PurchaseEntity> getAllPurchase() {
        return purchaseRepository.findAllActive();
    }

    public List<PurchaseEntity> getAllPurchaseValid(String id, int offset, int limit) {

        return purchaseRepository.findAllActiveValid(Long.parseLong(refreshTokenService.getId(id)), limit, offset);
    }

    public List<PurchaseEntity> selectWithPagination(int offset, int limit) {
        return purchaseRepository.selectWithPagination(limit, offset);
    }

    public List<PurchaseEntity> selectPurchase(String id, int offset, int limit) {
        List<AnnonceEntity> annonces = annonceService.getAnnoncesByVendeur(id);
        System.out.print(annonces.size());
        String[] idannonce = new String[annonces.size()];
        for (int i = 0; i < annonces.size(); i++) {
            idannonce[i] = annonces.get(i).getId();
        }
        return purchaseRepository.findAllValid(idannonce, limit, offset);
    }

    public Optional<PurchaseEntity> getById(String id) {
        return purchaseRepository.findById(id);
    }

    public void updateState(PurchaseEntity purchase, int state) {
        purchaseRepository.updatePurchaseState(purchase.getId(), state);
    }

    public TransactionEntity achat(PurchaseEntity purchase, Long user) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setPurchase(purchase);
        transaction.setReceiver(userRepository.findById(user).get());
        transaction.setSender(purchase.getUser());
        transaction.setDate(new Date(System.currentTimeMillis()));
        transaction = transactionService.insert(transaction);
        return transaction;
    }
}