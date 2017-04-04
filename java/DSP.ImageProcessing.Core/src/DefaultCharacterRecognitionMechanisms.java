import DataStructures.CharacterPattern;
import DataStructures.LetterData;
import Interface.BasicImageOperations;
import Interface.CharacterRecognitionMechanisms;
import Interface.ImageContentOperations;
import Patterns.PatternProvider;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by bclapa on 26.03.2017.
 */
public class DefaultCharacterRecognitionMechanisms implements CharacterRecognitionMechanisms {

    private BasicImageOperations basicImageOperations;
    private ImageContentOperations imageContentOperations;
    private PatternProvider patternProvider;

    public DefaultCharacterRecognitionMechanisms(BasicImageOperations basicImageOperations,
                                                 ImageContentOperations imageContentOperations,
                                                 PatternProvider patternProvider) {
        this.basicImageOperations = basicImageOperations;
        this.imageContentOperations = imageContentOperations;
        this.patternProvider = patternProvider;
    }

    @Override
    public String recogniseUsingCorrelation(Mat letter) {
        String character = null;
        double max = -1;

        Mat trimmed = this.imageContentOperations.trimToContours(letter);

        for (CharacterPattern pattern : patternProvider.getCharacterPatterns()) {
            Mat destination = new Mat();

            Imgproc.resize(trimmed, destination, new Size(pattern.getWidth(), pattern.getHeight()));
            destination = basicImageOperations.binarizeColorImage(destination);

            double[] letterImageVector = basicImageOperations.getNormalizedBinaryImageVector(destination);

            double scalarProduct = calculateScalarProduct(letterImageVector, pattern.getVector());
            if (scalarProduct > max) {
                character = pattern.getCharacter();
                max = scalarProduct;
            }
        }

        return character;
    }

    public String recogniseTextLineCharactersUsingCorrelation(LetterData[] textLine, Mat image) {
        String result = "";

        for (LetterData letter : textLine) {
            Mat letterImage = image.submat(new Rect(letter.getX(), letter.getY(), letter.getWidth(), letter.getHeight()));

            String recognisedLetter = recogniseUsingCorrelation(letterImage);
            result += recognisedLetter;
        }

        return result;
    }

    private double calculateScalarProduct(double[] sourceVector, double[] patternVector) {
        double scalarProduct = 0;

        for (int i = 0; i < sourceVector.length; i++) {
            scalarProduct += sourceVector[i] * patternVector[i];
        }

        return scalarProduct;
    }
}