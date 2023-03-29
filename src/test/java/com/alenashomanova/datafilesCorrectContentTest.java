package com.alenashomanova;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class datafilesCorrectContentTest {
    private ClassLoader cl = datafilesCorrectContentTest.class.getClassLoader();

    @Test
    @Description("The .pdf file has a title 'JUnit 5 User Guide'")
    void pdfFileContentTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("datafiles.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("junit-user-guide-5.9.2.pdf")) {
                    PDF pdf = new PDF(zs);
                    Assertions.assertEquals("JUnit 5 User Guide", pdf.title);
                }
            }
        }
    }

    @Test
    @Description("The .xls database has correct table attributes")
    void xlsFileContentTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("datafiles.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("file_example.xls")) {
                    XLS xls = new XLS(zs);
                    Assertions.assertEquals("First Name", xls.excel.getSheetAt(0).getRow(0).getCell(1).toString());
                    Assertions.assertEquals("Last Name", xls.excel.getSheetAt(0).getRow(0).getCell(2).toString());
                    Assertions.assertEquals("Gender", xls.excel.getSheetAt(0).getRow(0).getCell(3).toString());
                    Assertions.assertEquals("Country", xls.excel.getSheetAt(0).getRow(0).getCell(4).toString());
                    Assertions.assertEquals("Age", xls.excel.getSheetAt(0).getRow(0).getCell(5).toString());
                    Assertions.assertEquals("Date", xls.excel.getSheetAt(0).getRow(0).getCell(6).toString());
                    Assertions.assertEquals("Id", xls.excel.getSheetAt(0).getRow(0).getCell(7).toString());
                }
            }
        }
    }

    @Test
    @Description("The .csv database has correct table attributes")
    void csvFileContentTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("people-100.csv")){
            CSVReader csvReader = new CSVReader(new InputStreamReader(is));
            List<String[]> content = csvReader.readAll();
            Assertions.assertArrayEquals(new String[]{"Index", "First Name", "Last Name", "Sex", "Email"}, content.get(0));

        }
    }

    @Test
    @Description("The .xls database has correct table attributes")
    void xlsZipFileContentTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("datafiles.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("people-100.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                    List<String[]> string = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[] {"Index", "User Id", "First Name", "Last Name", "Sex", "Email", "Phone", "Date of birth", "Job Title"}, string.get(0));
                }
            }
        }
    }

    @Test
    void verifyCorrectPokemonTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("minun-pokemon.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            Pokemon pokemon = mapper.readValue(isr, Pokemon.class);

            Assertions.assertEquals("Minun", pokemon.name);
            Assertions.assertTrue(pokemon.isPokemon);
            Assertions.assertEquals(312, pokemon.index);
            Assertions.assertEquals("Electric", pokemon.type);
            Assertions.assertEquals("Thunder Punch", pokemon.strongestAttack.attackname);
            Assertions.assertEquals(75, pokemon.strongestAttack.power);
            Assertions.assertArrayEquals(new String[] {"Hoenn", "Central Kalos"}, pokemon.pokedexes);
        }


    }
}