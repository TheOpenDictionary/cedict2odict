package com.odict.cedict2odict;

import java.util.List;

public class DictionaryXMLUsage {
    private List<String> definitions;
    private String pronunciation;

    DictionaryXMLUsage(String pronunciation, List<String> definitions) {
        this.pronunciation = pronunciation;
        this.definitions = definitions;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("<usage pronunciation=\"%s\">", this.pronunciation));

        for (String def: this.definitions) {
            sb.append(String.format("<definition>%s</definition>", def));
        }

        sb.append("</usage>");

        return sb.toString();
    }
}
