package fotofinish;

import javafx.scene.control.TextField;

public class NumberFieldFX extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    public int getIntValue() {
        return Integer.parseInt(this.getText());
    }

    private boolean validate(String text) {
        return ("".equals(text) || text.matches("[0-9]"));
    }
}