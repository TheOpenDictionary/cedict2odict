package com.odict.cedict2odict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryXMLEntry {
    private List<DictionaryXMLUsage> usages;
    private String term;
    private String alternate;

    DictionaryXMLEntry(String term, String alternate) {
        this(term, alternate, new ArrayList<>());
    }

    DictionaryXMLEntry(String term, String alternate, List<DictionaryXMLUsage> usages) {
        this.term = term;
        this.alternate = alternate;
        this.usages = usages;
    }

    public void addUsage(DictionaryXMLUsage usage) {
        this.usages.add(usage);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(
            String.format(
                "<entry term=\"%s\" alt=\"%s\"><ety>",
                this.term,
                this.alternate
            )
        );

        for (DictionaryXMLUsage usage : this.usages)
            sb.append(usage.toString());

        sb.append("</ety></entry>");

        return sb.toString();
    }
}
