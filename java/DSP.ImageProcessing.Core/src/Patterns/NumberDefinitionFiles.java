package Patterns;

import DataStructures.CharacterDefinitionFile;

import java.util.Collection;

/**
 * Created by bclapa on 23.03.2017.
 */
public class NumberDefinitionFiles {
    public static CharacterDefinitionFile getCapital0FileDefinition() {
        return getDigitDefinition("0");
    }

    public static CharacterDefinitionFile getCapital1FileDefinition() {
        return getDigitDefinition("1");
    }

    public static CharacterDefinitionFile getCapital2FileDefinition() {
        return getDigitDefinition("2");
    }

    public static CharacterDefinitionFile getCapital3FileDefinition() {
        return getDigitDefinition("3");
    }

    public static CharacterDefinitionFile getCapital4FileDefinition() {
        return getDigitDefinition("4");
    }

    public static CharacterDefinitionFile getCapital5FileDefinition() {
        return getDigitDefinition("5");
    }

    public static CharacterDefinitionFile getCapital6FileDefinition() {
        return getDigitDefinition("6");
    }

    public static CharacterDefinitionFile getCapital7FileDefinition() {
        return getDigitDefinition("7");
    }

    public static CharacterDefinitionFile getCapitalFileDefinition() {
        return getDigitDefinition("8");
    }

    public static CharacterDefinitionFile getCapital9FileDefinition() {
        return getDigitDefinition("9");
    }

    public static CharacterDefinitionFile[] getAll(){
        return new CharacterDefinitionFile[]{
                getDigitDefinition("0"),
                getDigitDefinition("1"),
                getDigitDefinition("2"),
                getDigitDefinition("3"),
                getDigitDefinition("4"),
                getDigitDefinition("5"),
                getDigitDefinition("6"),
                getDigitDefinition("7"),
                getDigitDefinition("8"),
                getDigitDefinition("9"),
        };
    }

    private static String getLetterRelativePath(String letter) {
        return String.format("Digits/%s.bmp", letter);
    }

    private static CharacterDefinitionFile getDigitDefinition(String digit) {
        return new CharacterDefinitionFile(digit, NumberDefinitionFiles.class.getClassLoader().getResource(getLetterRelativePath(digit)).getFile());
    }
}
