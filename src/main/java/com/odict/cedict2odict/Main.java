package com.odict.cedict2odict;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: cedict2odict.jar [output_file] -[s|t]");
            System.exit(0);
        }

        try {
            URL url = new URL("https://www.mdbg.net/chinese/export/cedict/cedict_1_0_ts_utf-8_mdbg.zip");
            File file = new File(".dict.zip");

            FileUtils.copyURLToFile(url, file);

            try (ZipFile zip = new ZipFile(".dict.zip")) {
                final Enumeration<? extends ZipEntry> entries = zip.entries();

                while (entries.hasMoreElements()) {
                    final ZipEntry entry = entries.nextElement();
                    InputStream stream = zip.getInputStream(entry);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

                    String pattern = "(.*?)\\s(.*?)\\s\\[(.*?)]\\s/(.*)/";
                    String line;
                    DictionaryXMLBuilder builder = new DictionaryXMLBuilder("CC-CEDICT");

                    Pattern regex = Pattern.compile(pattern);

                    System.out.println("Reading file...");

                    while ((line = bufferedReader.readLine()) != null) {
                        Matcher matcher = regex.matcher(line);

                        if (matcher.find()) {
                            String traditional = matcher.group(1);
                            String simplified = matcher.group(2);
                            String pronunciation = matcher.group(3);
                            String definition = matcher.group(4);
                            List<String> definitions = Arrays.stream(
                                    definition.split("/"))
                                    .map(x -> escapeHtml4(x))
                                    .collect(Collectors.toList());

                            builder.addEntry(simplified, traditional, pronunciation, definitions);
                        }
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter(args[0]));
                    writer.write(builder.toString());

                    writer.close();

                    System.out.println("Successfully wrote OD-XML file to " + args[0]);

                    bufferedReader.close();
                }
            }

            boolean deleted = file.delete();

            if (!deleted)
                System.out.println("Could not delete temporary dictionary file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
