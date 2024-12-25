package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class TransactionListener {

    private final TransactionHandler transactionHandler;

    @Autowired
    public TransactionListener(TransactionHandler transactionHandler){
        this.transactionHandler = transactionHandler;
    }

    @KafkaListener(
            topics = "${general.kafka-topic}",
            properties = {"spring.json.value.default.type=com.jpmc.midascore.foundation.Transaction"}
    )
    public void listener(Transaction transaction) {
        transactionHandler.processTransaction(transaction);
    }

}
