package net.cfrost.web.core.security.jwt.util;

public class JwtInfo {

    public static void info(String info){
        System.out.println("[JWT] " + info);
    }
    public static void warning(String info){
        System.out.println("[JWT-WARNING] " + info);
    }
}
