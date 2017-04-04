package Interface;

import DataStructures.LetterData;
import org.opencv.core.Mat;

/**
 * Created by bclapa on 23.03.2017.
 */
public interface CharacterRecognitionMechanisms {
    String recogniseUsingCorrelation(Mat letter);
    String recogniseTextLineCharactersUsingCorrelation(LetterData[] textLine, Mat image);
}

