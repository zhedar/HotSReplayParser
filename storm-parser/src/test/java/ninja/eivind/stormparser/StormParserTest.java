package ninja.eivind.stormparser;

import ninja.eivind.mpq.MpqArchive;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Eivind Vegsundvåg
 */
public class StormParserTest {

    private StormParser parser;
    private String fileName;

    @Before
    public void setUp() {
        URL resource = ClassLoader.getSystemClassLoader().getResource("test.StormReplay");
        assertNotNull("Could not load test resource", resource);
        fileName = resource.getFile();
        parser = new StormParser(new File(fileName));
    }

    @Test
    public void testGetMatchId() {
        String expected = "5543abb9-af35-3ce6-a026-e9d5517f2964";
        String actual = parser.getMatchId();

        assertEquals("Match ID is calculated as expected", expected, actual);
    }

    @Test
    public void testConcatenate() {
        final List<String> ids = Arrays.asList("246918", "325864", "330051", "368155", "896668", "897902", "958319", "1233098", "1580053", "2344031");
        final String seed = "2906602328";
        String expected = "2469183258643300513681558966688979029583191233098158005323440312906602328";
        String actual = parser.concatenate(ids, seed);

        assertEquals("Parser concatenates IDs and seed correctly", expected, actual);
    }

    @Test
    public void testGetRandomSeed() throws IOException {
        MpqArchive archive = new MpqArchive(new File(fileName));
        Map<String, ByteBuffer> files = archive.getFiles(StormParser.REPLAY_INIT_DATA);
        final String expected = "2906602328";
        final String actual = parser.getRandomSeed(files);

        assertEquals("Parser returns correct random seed as String", expected, actual);
    }
}
