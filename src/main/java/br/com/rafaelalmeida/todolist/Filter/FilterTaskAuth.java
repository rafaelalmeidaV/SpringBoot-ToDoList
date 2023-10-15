package br.com.rafaelalmeida.todolist.Filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelalmeida.todolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            new String(authDecode);

            System.out.println("Auth: " + new String(authDecode));

            String[] authParse = new String(authDecode).split(":");
            String username = authParse[0];
            String password = authParse[1];

            // validar se ele existe
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.setStatus(401);
                return;
            } else {
                // validar se a senha está correta
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (!passwordVerify.verified) {
                    response.setStatus(401);
                    return;
                } else {
                    request.setAttribute("user-id", user.getId());
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }else {
            filterChain.doFilter(request, response);
            
        }   

    }

}
