package org.example.final_exam.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {
    public static String hashPassword(String palinPassword){
        return BCrypt.hashpw(palinPassword, BCrypt.gensalt());
    }
    public static boolean checkPassword(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
