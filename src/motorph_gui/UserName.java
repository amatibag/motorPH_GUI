/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author amatibag
 */
public class UserName {
    public static String getLoginName(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("password.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {  // Ensure there are enough columns
                    String csvUsername = data[1];
                    String firstName = data[4];  // 5th column (index 4) for first name
                    String lastName = data[5];   // 6th column (index 5) for last name

                    if (username.equals(csvUsername)) {
                        return firstName + " " + lastName;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

