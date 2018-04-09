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
import pl.npp.nopodajpodajserver.model.place.PlaceAmenities;
import pl.npp.nopodajpodajserver.repository.IPlaceAmenitiesRepository;
import pl.npp.nopodajpodajserver.repository.IPlaceRepository;

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
public class PlaceAmenitiesRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private IPlaceAmenitiesRepository placeAmenitiesRepository;
    @Autowired
    private IPlaceRepository placeRepository;

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
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Place place = new Place();
        Place place1 = new Place();
        PlaceAmenities placeAmenities = new PlaceAmenities();
        PlaceAmenities placeAmenities1 = new PlaceAmenities();
        placeAmenities.setPlace(place);
        placeAmenities1.setPlace(place1);
        placeRepository.save(place);
        placeRepository.save(place1);
        placeAmenitiesRepository.save(placeAmenities);
        placeAmenitiesRepository.save(placeAmenities1);
    }

    @Test
    public void getPlaceAmenities() throws Exception {
        this.mockMvc.perform(get("/placeAmenities/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }
    @Test
    public void getPlaceAllAmenities() throws Exception {
        this.mockMvc.perform(get("/placeAmenities")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void addPlaceAmenities() throws Exception {
        String json = json(new PlaceAmenities());
        this.mockMvc.perform(post("/placeAmenities")
                .contentType(contentType)
                .content(json)).andExpect(status().isCreated());

    }

    @Test
    public void deletePlaceAmenities() throws Exception {
        mockMvc.perform(delete("/placeAmenities/2")).andExpect(status().isOk());

    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}