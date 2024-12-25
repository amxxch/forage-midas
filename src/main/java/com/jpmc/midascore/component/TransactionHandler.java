package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionHandler {
    private final DatabaseConduit dbConduit;
    private final IncentiveController incentiveController;

    public TransactionHandler(DatabaseConduit dbConduit, IncentiveController incentiveController) {
        this.dbConduit = dbConduit;
        this.incentiveController = incentiveController;
    }

    public void processTransaction(Transaction transaction){
        if (dbConduit.validate(transaction)) {
            Incentive incentive = incentiveController.getIncentive(transaction);
            transaction.setIncentive(incentive.getAmount());
            dbConduit.save(transaction);
        }
    }
}
