package pl.npp.nopodajpodajserver.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.npp.nopodajpodajserver.NoPodajPodajServerApplication;
import pl.npp.nopodajpodajserver.model.user.Customer;
import pl.npp.nopodajpodajserver.repository.ICustomerRepository;

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
public class CustomerRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;


    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ICustomerRepository customerRepository;

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
        Customer customer = new Customer();
        Customer customer1 = new Customer();
        customer.setPassword(DigestUtils.sha1Hex("test"));
        customer.setEmail("w2@w.pl");
        customer1.setEmail("w@w.pl");
        customerRepository.save(customer);
        customerRepository.save(customer1);
    }
    @Test
    public void getCustomers() throws Exception {
        this.mockMvc.perform(get("/customers")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void getCustomer() throws Exception {
        this.mockMvc.perform(get("/customers/2")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void addCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setPassword("foo");
        String json = json(customer);
        this.mockMvc.perform(post("/customers")
                .contentType(contentType)
                .content(json)).andExpect(status().isCreated());
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(post("/customers/2")).andExpect(status().isOk());
    }

    @Test
    public void checkEncrypt() throws Exception {
        assertEquals(DigestUtils.sha1Hex("test"),customerRepository.findByEmail("w2@w.pl").getPassword());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}