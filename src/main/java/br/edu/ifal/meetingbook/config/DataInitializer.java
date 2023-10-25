package br.edu.ifal.meetingbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.ifal.meetingbook.user.IUserRepository;
import br.edu.ifal.meetingbook.user.UserModel;

@Component
public class DataInitializer implements CommandLineRunner { // Permite executar código durante a inicialização do app

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o repositório de usuários está vazio (sem registros)
        if (userRepository.count() == 0) {
            // Se estiver vazio, cria um usuário padrão para inicialização
            // Hash da senha "alice" usando BCrypt com força de trabalho 12
            var hashedPassword = BCrypt.withDefaults().hashToString(12, "admin".toCharArray());
            
            // Cria um novo objeto UserModel para o usuário inicial
            UserModel user = new UserModel("Ademar da Silva", "adsilva", "admin@gerencia.com", hashedPassword, "Gerente");

            // Salva o usuário no repositório
            userRepository.save(user);
        }
    }
}
