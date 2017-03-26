package Patterns;

import DataStructures.CharacterDefinitionFile;

/**
 * Created by bclapa on 23.03.2017.
 */
public class CapitalLetterDefinitionFiles {
    public static CharacterDefinitionFile getCapitalAFileDefinition() {
        return getLetterDefinition("A");
    }

    public static CharacterDefinitionFile getCapitalBFileDefinition() {
        return getLetterDefinition("B");
    }

    public static CharacterDefinitionFile getCapitalCFileDefinition() {
        return getLetterDefinition("C");
    }

    public static CharacterDefinitionFile getCapitalDFileDefinition() {
        return getLetterDefinition("D");
    }

    public static CharacterDefinitionFile getCapitalEFileDefinition() {
        return getLetterDefinition("E");
    }

    public static CharacterDefinitionFile getCapitalFFileDefinition() {
        return getLetterDefinition("F");
    }

    public static CharacterDefinitionFile getCapitalGFileDefinition() {
        return getLetterDefinition("G");
    }

    public static CharacterDefinitionFile getCapitalHFileDefinition() {
        return getLetterDefinition("H");
    }

    public static CharacterDefinitionFile getCapitalIFileDefinition() {
        return getLetterDefinition("I");
    }

    public static CharacterDefinitionFile getCapitalJFileDefinition() {
        return getLetterDefinition("J");
    }

    public static CharacterDefinitionFile getCapitalKFileDefinition() {
        return getLetterDefinition("K");
    }

    public static CharacterDefinitionFile getCapitalLFileDefinition() {
        return getLetterDefinition("L");
    }

    public static CharacterDefinitionFile getCapitalMFileDefinition() {
        return getLetterDefinition("M");
    }

    public static CharacterDefinitionFile getCapitalNFileDefinition() {
        return getLetterDefinition("N");
    }

    public static CharacterDefinitionFile getCapitalOFileDefinition() {
        return getLetterDefinition("O");
    }

    public static CharacterDefinitionFile getCapitalPFileDefinition() {
        return getLetterDefinition("P");
    }

    public static CharacterDefinitionFile getCapitalRFileDefinition() {
        return getLetterDefinition("R");
    }

    public static CharacterDefinitionFile getCapitalSFileDefinition() {
        return getLetterDefinition("S");
    }

    public static CharacterDefinitionFile getCapitalTFileDefinition() {
        return getLetterDefinition("T");
    }

    public static CharacterDefinitionFile getCapitalUFileDefinition() {
        return getLetterDefinition("U");
    }

    public static CharacterDefinitionFile getCapitalWFileDefinition() {
        return getLetterDefinition("W");
    }

    public static CharacterDefinitionFile getCapitalYFileDefinition() {
        return getLetterDefinition("Y");
    }

    public static CharacterDefinitionFile getCapitalZFileDefinition() {
        return getLetterDefinition("Z");
    }

    public static CharacterDefinitionFile getCapitalŻFileDefinition() {
        return getLetterDefinition("Ż");
    }

    public static CharacterDefinitionFile[] getAll(){
        return new CharacterDefinitionFile[]{
                getLetterDefinition("A"),
                getLetterDefinition("B"),
                getLetterDefinition("C"),
                getLetterDefinition("D"),
                getLetterDefinition("E"),
                getLetterDefinition("F"),
                getLetterDefinition("G"),
                getLetterDefinition("H"),
                getLetterDefinition("I"),
                getLetterDefinition("J"),
                getLetterDefinition("K"),
                getLetterDefinition("L"),
                getLetterDefinition("M"),
                getLetterDefinition("N"),
                getLetterDefinition("O"),
                getLetterDefinition("P"),
                getLetterDefinition("R"),
                getLetterDefinition("S"),
                getLetterDefinition("T"),
                getLetterDefinition("U"),
                getLetterDefinition("W"),
                getLetterDefinition("Y"),
                getLetterDefinition("Z"),
                getLetterDefinition("Ż"),
        };
    }

    private static String getLetterRelativePath(String letter) {
        return String.format("CapitalLetters/%s.bmp", letter);
    }

    private static CharacterDefinitionFile getLetterDefinition(String letter) {
        return new CharacterDefinitionFile(letter, CapitalLetterDefinitionFiles.class.getClassLoader().getResource(getLetterRelativePath(letter)).getFile());
    }
}
