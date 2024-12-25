package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    @Autowired
    public DatabaseConduit(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public void save(Transaction transaction) {
        Optional<UserRecord> senderOpt = userRepository.findById(transaction.getSenderId());
        Optional<UserRecord> recipientOpt = userRepository.findById(transaction.getRecipientId());

        UserRecord sender = senderOpt.get();
        UserRecord recipient = recipientOpt.get();

        float amount = transaction.getAmount();
        float incentive = transaction.getIncentive();

        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, amount, incentive);
        transactionRecordRepository.save(transactionRecord);

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount + incentive);

        save(sender);
        save(recipient);
    }

    public boolean validate(Transaction transaction) {
        Optional<UserRecord> senderOpt = userRepository.findById(transaction.getSenderId());
        Optional<UserRecord> recipientOpt = userRepository.findById(transaction.getRecipientId());
        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            UserRecord sender = senderOpt.get();
            float amount = transaction.getAmount();

            return sender.getBalance() >= amount;
        }
        return false;
    }
}
