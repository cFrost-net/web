package net.cfrost.web.core.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import net.cfrost.web.core.security.jwt.util.JwtInfo;
import net.cfrost.web.core.security.jwt.util.NullValueException;
import net.cfrost.web.core.security.jwt.util.PropertyReader;

import org.apache.commons.codec.digest.DigestUtils;

public class JwtAuthenticator {

    private static Timer timer;

    private static Key signatureKey;

    private static long keyLifeCycle;
    private static long tokenLifeCycle;

    private static boolean debugMode;
    private static String debugToken;
    private static String debugIssuer;
    private static String debugJwt;
    
    private JwtAuthenticator() {};

    public static String generateToken(String issuer) {

        if (JwtAuthenticator.signatureKey == null) {
            throw new JwtAuthenticatorInitException("JwtAuthenticator has not been initialized!");
        }

        Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder();
        Date expireDate = new Date(now.getTime() + JwtAuthenticator.tokenLifeCycle);
        jwtBuilder.setExpiration(expireDate);
        jwtBuilder.setIssuer(DigestUtils.sha1Hex(issuer));
        jwtBuilder.signWith(SignatureAlgorithm.HS512, signatureKey);
        String token = jwtBuilder.compact();
        return token;
    }

    public static void verifyToken(String token, String issuer) {

        if (JwtAuthenticator.signatureKey == null) {
            throw new JwtAuthenticatorInitException("JwtAuthenticator has not been initialized!");
        }

        try {

            if (debugMode && debugToken.equals(token)) {
                Jwts.parser().requireIssuer(DigestUtils.sha1Hex(issuer)).setSigningKey(signatureKey).parseClaimsJws(debugJwt);
                return;
            }

            Jwts.parser().requireIssuer(DigestUtils.sha1Hex(issuer)).setSigningKey(signatureKey).parseClaimsJws(token);
            // OK, we can trust this JWT
            return;
        } catch (MissingClaimException mce) {
            // the parsed JWT did not have the iss field
            throw mce;
        } catch (IncorrectClaimException ice) {
            // the parsed JWT had a sub field, but its value was not equal to
            // 'jsmith'
            throw ice;
        } catch (ExpiredJwtException eje) {
            // the parsed JWT has expired
            throw eje;
        } catch (SignatureException se) {
            // don't trust the JWT!
            throw se;
        } catch (JwtException other) {
            throw other;
        }
    }

    private static void loadConfig(String configPath) {
        try {
            PropertyReader propertyReader = new PropertyReader(configPath);

            JwtAuthenticator.keyLifeCycle = Long.parseLong((String) propertyReader.getProperty("keyLifeCycle")) * 1000L;
            JwtAuthenticator.tokenLifeCycle = Long.parseLong((String) propertyReader.getProperty("tokenLifeCycle")) * 1000L;
            
            JwtAuthenticator.signatureKey = MacProvider.generateKey();
            JwtInfo.info("Signature Key has been set!");
            
            JwtInfo.info("Signature Key life cycle is " + keyLifeCycle + " ms");
            JwtInfo.info("Token life cycle is " + tokenLifeCycle + " ms");

            String debugModeStatus = "false";
            try {
                debugModeStatus = (String) propertyReader.getProperty("debugMode");
            } catch (NullValueException e) {
                debugModeStatus = "false";
            }
            if ("TRUE".equals(debugModeStatus.toUpperCase())) {
                JwtAuthenticator.debugMode = true;
                JwtAuthenticator.debugToken = (String) propertyReader.getProperty("debugToken");
                JwtAuthenticator.debugIssuer = (String) propertyReader.getProperty("debugIssuer");
                JwtAuthenticator.debugJwt = Jwts.builder().setIssuer(DigestUtils.sha1Hex(debugIssuer)).signWith(SignatureAlgorithm.HS512, signatureKey).compact();
                JwtInfo.warning("Debug mode has enabled! DebugToken is \"" + debugToken + "\" and DebugIssuer is \"" + debugIssuer + "\"");  
            } else {
                JwtAuthenticator.debugMode = false;
                JwtAuthenticator.debugToken = null;
                JwtAuthenticator.debugIssuer = null;
                JwtAuthenticator.debugJwt = null;
                JwtInfo.info("Debug mode has disabled!");
            }

        } catch (Exception e) {
            throw new JwtAuthenticatorInitException(e);
        }
    }

    private static void startSignatureKeyRefreshTask() {        
        JwtAuthenticator.timer = new Timer();
        JwtAuthenticator.timer.schedule(new TimerTask() {
            public void run() {
                JwtAuthenticator.signatureKey = MacProvider.generateKey();
                JwtInfo.info("Signature Key refreshed at " + new Date() + "!");
            }
        }, JwtAuthenticator.keyLifeCycle, JwtAuthenticator.keyLifeCycle);
        JwtInfo.info("Signature Key refresh schedule has started");        
    }

    protected static void init() throws JwtAuthenticatorInitException {
        final String jwtConfigLocation = "config.properties";
        JwtInfo.warning("JwtConfigLocation has not been set, use default location: \"" + jwtConfigLocation + "\"");
        JwtAuthenticator.init(jwtConfigLocation);
    }
    
    protected static void init(String jwtConfigLocation) throws JwtAuthenticatorInitException {

        if (JwtAuthenticator.signatureKey != null)
            throw new JwtAuthenticatorInitException("JwtAuthenticator had already been initialized!");
        
        JwtAuthenticator.loadConfig(jwtConfigLocation);
        
        JwtAuthenticator.startSignatureKeyRefreshTask();
        
        JwtInfo.info("JwtAuthenticator has been initialized");
    }

    protected static void destroy() {
        if (JwtAuthenticator.timer != null) {
            JwtAuthenticator.timer.cancel();
            JwtInfo.info("Signature Key refresh schedule has stoped");
        }
        JwtAuthenticator.signatureKey = null;
        JwtAuthenticator.debugJwt = null;
        JwtAuthenticator.timer = null;
        JwtInfo.info("JwtAuthenticator has been destroyed");
    }
}
