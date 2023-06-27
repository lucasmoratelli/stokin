package com.example.semestral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AcessLevelSingleton {
    public static int acessLevel;

    public static boolean canAcess() {
        return acessLevel == 2;
    }

}
