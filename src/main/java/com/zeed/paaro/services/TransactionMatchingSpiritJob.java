package com.zeed.paaro.services;

import com.zeed.paaro.lib.enums.TransactionStatus;
import com.zeed.paaro.lib.models.TransferRequestMap;
import com.zeed.paaro.lib.models.WalletTransferTransaction;
import com.zeed.paaro.lib.repository.TransferRequestMapRepository;
import com.zeed.paaro.lib.repository.WalletTransferTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class TransactionMatchingSpiritJob {

    private Logger logger = LoggerFactory.getLogger(TransactionMatchingSpiritJob.class.getName());

    @Autowired
    private WalletTransferTransactionRepository walletTransferTransactionRepository;

    @Autowired
    private TransferRequestMapRepository transferRequestMapRepository;

    @Value("${paaro.service.naira.string:NGN}")
    private String nairaCurrencyType;

    private void mapTransactions() {

        List<WalletTransferTransaction> fromNairaWalletTransactions = getFromNairaWalletTransactions();
        List<WalletTransferTransaction> notFromNairaAccountTransactions = getNotFromNairaAccountTransaction();

        if (CollectionUtils.isEmpty(fromNairaWalletTransactions) || CollectionUtils.isEmpty(notFromNairaAccountTransactions)) {
            return;
        }

        final Map<WalletTransferTransaction, WalletTransferTransaction> transactionMap = new HashMap<>();

        fromNairaWalletTransactions.stream().parallel().forEach( transaction -> {

            Iterator<WalletTransferTransaction> transactionIterator = notFromNairaAccountTransactions.iterator();

            while (transactionIterator.hasNext()) {
                WalletTransferTransaction walletTransferTransaction = transactionIterator.next();
                if (walletTransferTransaction.getEquivalentAmount().compareTo(transaction.getActualAmount()) == 0 &&
                        walletTransferTransaction.getFromCurrency().getType().equals(transaction.getToCurrency().getType())) {
                    transactionMap.put(transaction,walletTransferTransaction);
                    transactionIterator.remove();
                    break;
                }
            }

        });

        saveTransactionMapping(transactionMap);

    }

    public List<WalletTransferTransaction> getFromNairaWalletTransactions() {

        List<WalletTransferTransaction> walletTransferTransactions = walletTransferTransactionRepository.findAllByFromCurrency_TypeAndTransferRequestMapIsNull(nairaCurrencyType);

        return walletTransferTransactions;

    }

    private List<WalletTransferTransaction> getNotFromNairaAccountTransaction () {
        List<WalletTransferTransaction> walletTransferTransactions = walletTransferTransactionRepository.findAllByFromCurrency_TypeNotInAndTransferRequestMapIsNull(nairaCurrencyType);

        return walletTransferTransactions;
    }

    public void saveTransactionMapping(Map<WalletTransferTransaction, WalletTransferTransaction> transactionHashMap) {

        for (Map.Entry<WalletTransferTransaction,WalletTransferTransaction> map: transactionHashMap.entrySet()) {
            WalletTransferTransaction nairaHolderTransaction = map.getKey();
            WalletTransferTransaction otherCurrencyHolderTransaction = map.getValue();
            TransferRequestMap transferRequestMap = new TransferRequestMap();
            transferRequestMap.setNairaHolderTransaction(nairaHolderTransaction);
            transferRequestMap.setOtherCurrencyHolderTransaction(otherCurrencyHolderTransaction);
            transferRequestMap.setDateMapped(new Date());
            transferRequestMapRepository.save(transferRequestMap);

            nairaHolderTransaction.setTransferRequestMap(transferRequestMap);
            nairaHolderTransaction.setTransactionStatus(TransactionStatus.SYSTEM_MAPPED_REQUEST);
            otherCurrencyHolderTransaction.setTransferRequestMap(transferRequestMap);
            otherCurrencyHolderTransaction.setTransactionStatus(TransactionStatus.SYSTEM_MAPPED_REQUEST);

            walletTransferTransactionRepository.save(nairaHolderTransaction);
            walletTransferTransactionRepository.save(otherCurrencyHolderTransaction);

        }

    }

    @PostConstruct
    public void test() {
        mapTransactions();
    }

}
