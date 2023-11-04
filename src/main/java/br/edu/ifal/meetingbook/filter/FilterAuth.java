package br.edu.ifal.meetingbook.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.ifal.meetingbook.entities.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtém o caminho da servlet da requisição
        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/users/")) {
            // Obtém a autenticação do cabeçalho da requisição
            var authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Basic ")) {
                // Se a autenticação estiver ausente ou não for no formato "Basic", retorne um erro de não autorizado
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // Decodifica as credenciais de autenticação
            var authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecoded);

            // Divide as credenciais em nome de usuário e senha
            String[] credentials = authString.split(":");
            if (credentials.length != 2) {
                // Se as credenciais estiverem incompletas, retorne um erro de não autorizado
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String username = credentials[0];
            String password = credentials[1];

            // Valida o usuário com base no nome de usuário
            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                // Se o usuário não for encontrado, retorne um erro de não autorizado
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                // Valida a senha usando BCrypt
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    // Se a senha for válida, siga em frente com a requisição
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    // Se a senha não for válida, retorne um erro de não autorizado
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        } else {
            // Se o caminho da servlet não começar com "/users/", continue com a cadeia de filtros
            filterChain.doFilter(request, response);
        }
    }
}
