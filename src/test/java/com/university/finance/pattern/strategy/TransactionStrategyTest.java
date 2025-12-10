package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionStrategyTest {
    
    // Teste le type de transaction de DepositStrategy
    @Test
    public void testDepositStrategyGetTransactionType() {
        DepositStrategy strategy = new DepositStrategy();
        assertEquals(Transaction.TransactionType.DEPOSIT, strategy.getTransactionType());
    }
    
    // Teste le type de transaction de WithdrawStrategy
    @Test
    public void testWithdrawStrategyGetTransactionType() {
        WithdrawStrategy strategy = new WithdrawStrategy();
        assertEquals(Transaction.TransactionType.WITHDRAW, strategy.getTransactionType());
    }
    
    // Teste le type de transaction de TransferStrategy
    @Test
    public void testTransferStrategyGetTransactionType() {
        TransferStrategy strategy = new TransferStrategy();
        assertEquals(Transaction.TransactionType.TRANSFER, strategy.getTransactionType());
    }
    
    // Teste TransferStrategy avec type VIRIN
    @Test
    public void testTransferStrategyWithVirinType() {
        TransferStrategy strategy = new TransferStrategy(Transaction.TransactionType.VIRIN);
        assertEquals(Transaction.TransactionType.VIRIN, strategy.getTransactionType());
    }
}

