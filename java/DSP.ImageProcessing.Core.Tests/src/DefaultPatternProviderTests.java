import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import DataStructures.CharacterPattern;
import Interface.BasicImageOperations;
import Patterns.DefaultPatternProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by bclapa on 07.04.2017.
 */
public class DefaultPatternProviderTests {
    @Test
    public void patternProviderShouldInitialize() {
        BasicImageOperations imageOperations = new DefaultBasicImageOperations();
        DefaultPatternProvider patternProvider = new DefaultPatternProvider(imageOperations);

        assertNotNull(patternProvider);
    }

    @Test
    public void getCharacterPatternsShouldReturnListOfCharacterPatterns() {
        BasicImageOperations imageOperations = new DefaultBasicImageOperations();
        DefaultPatternProvider patternProvider = new DefaultPatternProvider(imageOperations);

        List<CharacterPattern> patterns = patternProvider.getCharacterPatterns();

        Assert.assertTrue(patterns.size() > 0);
    }

    @Test
    public void getCharacterPatternsShouldReturnPatternsWithValuesBetween0And1() {
        BasicImageOperations imageOperations = new DefaultBasicImageOperations();
        DefaultPatternProvider patternProvider = new DefaultPatternProvider(imageOperations);

        List<CharacterPattern> patterns = patternProvider.getCharacterPatterns();
        boolean met = true;

        for (CharacterPattern pattern : patterns) {
            for (double i : pattern.getVector()) {
                if (i > 1 || i < 0) {
                    met = false;
                    break;
                }
            }
        }

        Assert.assertTrue(met);
    }
}
