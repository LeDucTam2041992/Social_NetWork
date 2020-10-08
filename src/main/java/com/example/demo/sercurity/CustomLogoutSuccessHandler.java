package com.example.demo.sercurity;

import com.example.demo.model.StatusUser;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    UserRepository userRepository;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            try {
                String userName = null;
                Object principal = authentication.getPrincipal();

                if (principal instanceof UserDetails) {
                    userName = ((UserDetails) principal).getUsername();
                } else {
                    userName = principal.toString();
                }
                System.out.println(userName);
                User user = userRepository.findByEmail(userName).orElse(null);
                System.out.println(user.getEmail());
                user.setStatusUser(new StatusUser(0,"off"));
                userRepository.save(user);
                request.getSession().invalidate();
                System.out.println("User Successfully Logout");
                //you can add more codes here when the user successfully logs out,
                //such as updating the database for last active.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        //redirect to login
        response.sendRedirect("/login");
    }
}
