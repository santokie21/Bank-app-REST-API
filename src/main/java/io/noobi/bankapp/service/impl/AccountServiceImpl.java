package io.noobi.bankapp.service.impl;

import io.noobi.bankapp.DTO.AccountDTO;
import io.noobi.bankapp.mapper.AccountMapper;
import io.noobi.bankapp.model.Account;
import io.noobi.bankapp.repository.AccountRepository;
import io.noobi.bankapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = AccountMapper.mapToAccount(accountDTO);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO getAccountsById(Long id) {
        Account account= accountRepository.
                findById(id).
                orElseThrow(()->new RuntimeException("Account Doesn't exists"));

        return AccountMapper.mapToAccountDTO(account);
    }

    @Override
    public AccountDTO deposit(Long id, double amount) {
        Account account= accountRepository.
                findById(id).
                orElseThrow(()->new RuntimeException("Account Doesn't exists"));

        double total = account.getBalance()+amount;
        account.setBalance(total);
        Account depositedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDTO(depositedAccount);
    }

    @Override
    public AccountDTO withdraw(Long id, double amount) {
        Account account= accountRepository.
                findById(id).
                orElseThrow(()->new RuntimeException("Account Doesn't exists"));

        double balance = account.getBalance();

        if(balance<amount) {
            throw new RuntimeException("Insufficient Balace to withdraw");
        }

        double total=  balance - amount;
        account.setBalance(total);
        Account withdrawAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDTO(withdrawAccount);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {

        List<Account> accounts= accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDTO(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account= accountRepository.
                findById(id).
                orElseThrow(()->new RuntimeException("Account Doesn't exists"));

        accountRepository.deleteById(id);
    }
}
