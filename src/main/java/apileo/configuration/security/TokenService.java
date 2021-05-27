package apileo.configuration.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import apileo.model.Gestor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${apileo.jwt.expiration}")
	private String expiration;

	@Value("${apileo.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Gestor logado = (Gestor) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

		String compact = Jwts.builder() //
				.setIssuer("BackendJuniorLeonardoDuarte") //
				.setSubject(logado.getIdGestor().toString()) //
				.setIssuedAt(hoje) //
				.setExpiration(dataExpiracao) //
				.signWith(SignatureAlgorithm.HS256, secret) //
				.compact();

		return compact;
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdGestor(String token) {
		Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(body.getSubject());
	}

}