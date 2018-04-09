package pl.npp.nopodajpodajserver.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.npp.nopodajpodajserver.NoPodajPodajServerApplication;
import pl.npp.nopodajpodajserver.model.user.Owner;
import pl.npp.nopodajpodajserver.repository.IOwnerRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Paweł Lelental
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoPodajPodajServerApplication.class)
@WebAppConfiguration
public class OwnerRestControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;


    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private IOwnerRepository ownerRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception{
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Owner owner = new Owner();
        Owner owner1 = new Owner();
        owner.setPassword(DigestUtils.sha1Hex("test"));
        owner1.setEmail("w@w.pl");
        ownerRepository.save(owner);
        ownerRepository.save(owner1);
    }
    @Test
    public void getOwners() throws Exception {
        this.mockMvc.perform(get("/owners")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void getOwner() throws Exception {
        this.mockMvc.perform(get("/owners/2")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void addOwner() throws Exception {
        Owner owner = new Owner();
        owner.setPassword("foo");
        String json = json(owner);
        this.mockMvc.perform(post("/owners")
                .contentType(contentType)
                .content(json)).andExpect(status().isCreated());
    }

    @Test
    public void deleteOwner() throws Exception {
        mockMvc.perform(post("/owners/2")).andExpect(status().isOk());
    }

    @Test
    public void checkEncrypt() throws Exception {
        assertEquals(DigestUtils.sha1Hex("test"),ownerRepository.findById(1).getPassword());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


}