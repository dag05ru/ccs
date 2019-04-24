package ccs;

import ccs.model.DeviceStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeviceStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deviceStatusCreate() throws Exception {
        String testDeviceStatus = "{\n" +
                "  \"id\": \"432cb4c1-438d-4a42-b9b0-8bbf9db6e60c\",\n" +
                "  \"ip\": \"172.16.165.52\"\n" +
                "}";
        this.mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).
                content(testDeviceStatus)).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void deviceStatusDelete() throws Exception {
        this.mockMvc.perform(delete("/432cb4c1-438d-4a42-b9b0-8bbf9db6e60c/")).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void deviceStatusUpdate() throws Exception {
        String testDeviceStatus = "{\n" +
                "  \"ip\": \"172.16.165.53\"\n" +
                "}";

        this.mockMvc.perform(put("/432cb4c1-438d-4a42-b9b0-8bbf9db6e60c/").
                contentType(MediaType.APPLICATION_JSON).content(testDeviceStatus)).
                andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("172.16.165.53")));
    }

    @Test
    public void deviceStatusLoadById() throws Exception {
        this.mockMvc.perform(get("/432cb4c1-438d-4a42-b9b0-8bbf9db6e60c/")).
                andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("172.16.165.52")));
    }




}