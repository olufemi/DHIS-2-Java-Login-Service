package org.dhis.security;


import org.dhis.security.DefaultLoginService;
import org.dhis.security.LoginService;
import org.dhis.user.User;
import org.junit.Before;
import org.junit.Test;


public class LoginServiceTest
{
   
   
    private DefaultLoginService defaultLoginService;
     private LoginService loginService;
     User userDao;
    
    @Before
    public void before()
    {
        loginService = new DefaultLoginService();
    }
    
  
    
    @Test
    public void testFoo() {
        // TODO Implement at least two unit tests verifying the LoginService interface behavior
        System.out.println("***** Loading User Olufemi *****");
        defaultLoginService.loadUser("olufemi", 5);
        System.out.println("***** Setting Olufemi  for Validation *****");
        userDao.setUsername("Olufemi");
        System.out.println("***** Testing Olufemi in failed Attempt in cache again max_attempt *****");
        AuthenticationEvent event=new AuthenticationEvent("Olufemi");
        if (defaultLoginService.isBlocked(userDao)) {
            System.out.println("***** Max attempts exceeded , You cant login*****");
            
            defaultLoginService.registerAuthenticationFailure(event );
        } else {
             defaultLoginService.registerAuthenticationSuccess(event);
            System.out.println("***** Wow! you are successfully Login *****");
        }

    }
    
}
