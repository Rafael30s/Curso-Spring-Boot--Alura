package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TokenService {

    //@Value("$(forum.jwt.expiration)")
    //private String expiration;
    private Long expiration = 86400000L;

    @Value("$(forum.jwt.secret)")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        System.out.println("DATA ATUAL:"+ hoje);
        System.out.println("DATA TIME HOJE"+hoje.getTime());
        System.out.println("DATA TIME amanhã"+(hoje.getTime()+expiration));
        System.out.println("DATA TIME amanhã"+new Date(hoje.getTime() + expiration));
        LocalDateTime dataHoje=LocalDateTime.now();
        System.out.println("CACETE:"+dataHoje);
        //LocalDateTime amanha= new LocalDateTime();

        Date dataExpiracao = new Date(hoje.getTime() + expiration);

        return Jwts.builder()
                .setIssuer("API do forum da Alura")
                .setSubject(logado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
