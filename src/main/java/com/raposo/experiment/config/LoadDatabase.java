import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.raposo.experiment.model.IUsuarioRepository;
import com.raposo.experiment.model.Usuario;

@Configuration
public class LoadDatabase {
    @Autowired
    private IUsuarioRepository repositoryUsuario;
    // classe para criptografar senha
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder PasswordEncoder;
    @Bean
    CommandLineRunner initDatabase(IUsuarioRepository  repository) {
        return args -> {
            //Salvar clientes
            var senha = PasswordEncoder.encode("12345");
            var usuario1 = new Usuario("Usuario da Silva", "usuario@email.com", senha);
            repositoryUsuario.saveAll(Arrays.asList(usuario1));
            System.out.println("Usuário teste:" + usuario1);
        };

        

    }

}