/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_gui;

import javax.swing.text.*;
/**
 *
 * @author amatibag
 */
public class CustomDocumentFilter extends DocumentFilter {
    private String allowedCharacters;

    public CustomDocumentFilter(String allowedCharacters) {
        this.allowedCharacters = allowedCharacters;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (isValid(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isValid(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isValid(String text) {
        for (char c : text.toCharArray()) {
            if (!allowedCharacters.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }
}
    

