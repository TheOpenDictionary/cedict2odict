package com.odict.cedict2odict;

import java.util.*;

public class DictionaryXMLBuilder {
    private Map<String, DictionaryXMLEntry> entries = new HashMap<>();
    private String name;

    DictionaryXMLBuilder(String name) {
        this.name = name;
    }

    public void addEntry(String word, String alternate, String pronunciation, List<String> definitions) {
        DictionaryXMLUsage usage = new DictionaryXMLUsage(pronunciation, definitions);

        if (entries.containsKey(word)) {
            entries.get(word).addUsage(usage);
        } else {
            List<DictionaryXMLUsage> usages = new ArrayList<>();
            usages.add(usage);
            entries.put(word, new DictionaryXMLEntry(word, alternate, usages));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("<dictionary name=\"%s\">", this.name));

        for (Map.Entry<String, DictionaryXMLEntry> entry : this.entries.entrySet())
            sb.append(entry.getValue().toString());

        sb.append("</dictionary>");

        return sb.toString();
    }
}
