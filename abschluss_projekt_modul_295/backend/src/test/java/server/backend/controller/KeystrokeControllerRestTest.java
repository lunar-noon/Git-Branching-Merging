package backend.controller;

import backend.model.Keystroke;
import backend.service.KeystrokeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Die Schnittstelle funktioniert und die Junit testet nicht, aber theoretisch sollte
@WebMvcTest(KeystrokeControllerRest.class)
public class KeystrokeControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KeystrokeService keystrokeService;

    @Test
    public void testGetAllKeystrokes() throws Exception {
        List<Keystroke> keystrokes = Collections.singletonList(new Keystroke());
        BDDMockito.given(keystrokeService.getAllKeystrokes()).willReturn(keystrokes);

        mockMvc.perform(get("/api/keystrokes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateKeystroke() throws Exception {
        Long keystrokeId = 1L;
        Keystroke updatedKeystroke = new Keystroke();
        updatedKeystroke.setKeystrokeId(keystrokeId);

        BDDMockito.given(keystrokeService.updateKeystroke(updatedKeystroke)).willReturn(updatedKeystroke);

        mockMvc.perform(put("/api/keystrokes/{keystrokeId}", keystrokeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedKeystroke)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
