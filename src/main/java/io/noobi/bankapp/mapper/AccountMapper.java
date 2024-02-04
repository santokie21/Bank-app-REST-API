package io.noobi.bankapp.mapper;

import io.noobi.bankapp.DTO.AccountDTO;
import io.noobi.bankapp.model.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDTO accountDTO) {
        Account account=new Account(
                accountDTO.getId(),
                accountDTO.getAccountHolderName(),
                accountDTO.getBalance()
        );

        return account;
    }

    public static AccountDTO mapToAccountDTO(Account account) {
        AccountDTO accountDTO=new AccountDTO(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );

        return accountDTO;
    }
}
