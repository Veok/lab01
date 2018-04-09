package pl.npp.nopodajpodajserver.controller;

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
import pl.npp.nopodajpodajserver.model.place.Place;
import pl.npp.nopodajpodajserver.model.rateSystem.Category;
import pl.npp.nopodajpodajserver.model.rateSystem.Rate;
import pl.npp.nopodajpodajserver.model.user.Customer;
import pl.npp.nopodajpodajserver.repository.ICustomerRepository;
import pl.npp.nopodajpodajserver.repository.IPlaceRepository;
import pl.npp.nopodajpodajserver.repository.IRateRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Paweł Lelental
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoPodajPodajServerApplication.class)
@WebAppConfiguration
public class RateRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IPlaceRepository placeRepository;
    @Autowired
    private IRateRepository rateRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

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
        Place place = new Place();
        Customer customer = new Customer();
        Rate rate = new Rate();
        Rate rate2 = new Rate();
        rate.setScore(5);
        rate.setPlace(place);
        rate.setCustomer(customer);
        rate2.setCategory(Category.price);

        placeRepository.save(place);
        customerRepository.save(customer);
        rateRepository.save(rate);
        rateRepository.save(rate2);
    }
    @Test
    public void getRates() throws Exception {
        this.mockMvc.perform(get("/rates")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void getRate() throws Exception {
        this.mockMvc.perform(get("/rates/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void addRate() throws Exception {
        String rateJson = json(new Rate());
        this.mockMvc.perform(post("/rates")
                .contentType(contentType)
                .content(rateJson)).andExpect(status().isCreated());
    }


    @Test
    public void findByScore() throws Exception {
        mockMvc.perform(get("/rates/byScore/5")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByCategory() throws Exception {
        mockMvc.perform(get("/rates/byCategory/price")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}