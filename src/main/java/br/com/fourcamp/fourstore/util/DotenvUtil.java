package br.com.fourcamp.fourstore.util;

import io.github.cdimascio.dotenv.Dotenv;

public final class DotenvUtil {
        static Dotenv dotenv = Dotenv.load();
        static String dbHost = dotenv.get("DB_HOST");
        static String dbPort = dotenv.get("DB_PORT");
        static String dbName = dotenv.get("DB_NAME");
        static String dbUser = dotenv.get("DB_USER");
        static String dbPassword = dotenv.get("DB_PASSWORD");

        public static void loadDenv() {
            System.setProperty("spring.datasource.url", "jdbc:mariadb://" + dbHost + ":" + dbPort + "/" + dbName);
            System.setProperty("spring.datasource.username", dbUser);
            System.setProperty("spring.datasource.password", dbPassword);
        }

        private DotenvUtil() {

        }


}
