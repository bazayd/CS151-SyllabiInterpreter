import dataManipulation.ChatNLPProcessing;
import dataManipulation.PossibleIntents;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatNLPProcessingTest {
    private static ChatNLPProcessing chat;
    @BeforeAll
    static void setUpBeforeAll () {
        chat = new ChatNLPProcessing();
    }
    @Test
    void findGeneralIntent () {
        assertEquals (PossibleIntents.SEE_DATES, chat.findGeneralIntent("see dates"));
    }
}
