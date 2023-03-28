package project.asc.AnsimCar.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import project.asc.AnsimCar.domain.Account;

import java.util.Collection;

public class AccountContext extends User {

    private final Account account;

    public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getEmail(), account.getPassword(), authorities);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
