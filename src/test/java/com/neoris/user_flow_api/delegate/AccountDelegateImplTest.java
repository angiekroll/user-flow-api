package com.neoris.user_flow_api.delegate;
import com.neoris.user_flow_api.delegate.impl.AccountDelegateImpl;
import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.AccountDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.mapper.AccountMapper;
import com.neoris.user_flow_api.service.AccountService;
import com.neoris.user_flow_api.service.CustomerService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountDelegateImplTest {

  private AccountDelegate accountDelegate;

  @Mock
  private AccountService accountService;

  @Mock
  private CustomerService customerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    accountDelegate = new AccountDelegateImpl(accountService, customerService);
  }

  @Test
  void createAccount_success() throws UserFlowException {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setAccountType("AHORRO");
    accountDTO.setBalance(BigDecimal.valueOf(1000));
    accountDTO.setCustomerId(1L);

    Customer customer = new Customer();
    when(customerService.findById(1L)).thenReturn(customer);

    Account account = AccountMapper.INSTANCE.accountDTOToAccount(accountDTO);
    account.setCustomer(customer);
    when(accountService.saveAll(anyList())).thenReturn(List.of(account));

    List<AccountDTO> result = accountDelegate.createAccount(List.of(accountDTO));

    assertEquals(1, result.size());
    assertEquals("AHORRO", result.get(0).getAccountType());
    assertEquals(BigDecimal.valueOf(1000), result.get(0).getBalance());
  }

  @Test
  void updateAccount_success() throws UserFlowException {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setAccountType("corriente");
    accountDTO.setBalance(BigDecimal.valueOf(5000));

    Account accountFound = new Account();
    when(accountService.findById(1L)).thenReturn(accountFound);

    AccountMapper.INSTANCE.updateAccountFromAccountDTO(accountDTO, accountFound);
    when(accountService.save(accountFound)).thenReturn(accountFound);

    AccountDTO result = accountDelegate.updateAccount(accountDTO, 1L);

    assertEquals("corriente", result.getAccountType());
    assertEquals(BigDecimal.valueOf(5000), result.getBalance());
  }

  @Test
  void getAccountById_success() throws UserFlowException {
    Account account = new Account();
    when(accountService.findById(1L)).thenReturn(account);

    AccountDTO result = accountDelegate.getAccountById(1L);

    assertNotNull(result);
  }

  @Test
  void deleteAccount_success() throws UserFlowException {
    Account account = new Account();
    when(accountService.findById(1L)).thenReturn(account);

    accountDelegate.deleteAccount(1L);

    verify(accountService, times(1)).delete(account);
  }

}
