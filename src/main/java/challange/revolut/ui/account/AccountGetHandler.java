package challange.revolut.ui.account;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import challange.revolut.domain.Account;
import challange.revolut.domain.repository.AccountBalanceRepository;
import challange.revolut.domain.valueobject.AccountId;
import challange.revolut.domain.valueobject.Currency;
import challange.revolut.ui.GetHandler;
import challange.revolut.ui.account.dto.AccountBalanceDTO;
import lombok.SneakyThrows;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class AccountGetHandler implements GetHandler {

    private AccountBalanceRepository repository;
    private ObjectMapper mapper;

    @Inject
    public AccountGetHandler(AccountBalanceRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getPathInfo() == null){
            doBadRequest(resp);
        } else {
            String id = req.getPathInfo().split("/")[1];
            UUID uuid = UUID.fromString(id);
            getById(uuid, resp);
        }
    }

    private void getById(UUID uuid, HttpServletResponse resp) {
        repository.findByAccountId(new AccountId(uuid))
                .map( this::dto )
                .ifPresentOrElse(
                        b -> doReturnAccountBalance(b, resp),
                        () -> doReturnNothing(resp));
    }

    @SneakyThrows
    private void doReturnAccountBalance(AccountBalanceDTO dto, HttpServletResponse response){
        response.setStatus(SC_OK);
        mapper.writeValue(response.getWriter(), dto);
    }

    @SneakyThrows
    private void doReturnNothing(HttpServletResponse response){
        response.setStatus(SC_OK);
    }

    private void doBadRequest(HttpServletResponse resp) throws IOException {
        resp.setStatus(SC_BAD_REQUEST);
    }

    private AccountBalanceDTO dto(Account account) {
        UUID id = account.getAccountId().getId();
        Currency currency = account.getBalance().getCurrency();
        double value = account.getBalance().getAmount().getValue().doubleValue();

        return AccountBalanceDTO.builder()
                .balance(value)
                .id(id)
                .currency(currency)
                .build();
    }


}
