package tumminia.wrpsrlibrary.security;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import tumminia.wrpsrlibrary.exceptions.UnauthorizedException;
import tumminia.wrpsrlibrary.users.User;

@Component
public class JWTTools {

	@Value("${spring.jwt.secret}")
	private String secret;

	public String createToken(User u) {
		String token = Jwts.builder().claim("idUtente", u.getIdUtente().toString())
				.claim("role", u.getRuolo().toString()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

		return token;

	}

	// METODO PER VERIFICARE IL TOKEN
	public void verifyToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new UnauthorizedException("Il token non è valido! Effettua nuovamente il login");
		}

	}

	public UUID extractUserId(String token) {
		return UUID.fromString(Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build()
				.parseClaimsJws(token).getBody().get("idUtente", String.class));
	}

	public String extractUserRole(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
				.getBody().get("role", String.class);
	}

}