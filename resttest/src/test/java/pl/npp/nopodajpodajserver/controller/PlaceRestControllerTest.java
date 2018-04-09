package pl.npp.nopodajpodajserver.controller;

import net.bytebuddy.asm.Advice;
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
import pl.npp.nopodajpodajserver.model.place.PlaceType;
import pl.npp.nopodajpodajserver.model.rateSystem.Rate;
import pl.npp.nopodajpodajserver.model.reservation.Reservation;
import pl.npp.nopodajpodajserver.model.reservation.Term;
import pl.npp.nopodajpodajserver.model.user.Owner;
import pl.npp.nopodajpodajserver.repository.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class PlaceRestControllerTest {
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
    private IPlaceRepository placeRepository;
    @Autowired
    private ITermRepository termRepository;
    @Autowired
    private IRateRepository rateRepository;
    @Autowired
    private IReservationRepository reservationRepository;
    @Autowired
    private IPlaceAmenitiesRepository placeAmenitiesRepository;

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
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        Reservation reservation = new Reservation();
        Rate rate = new Rate();
        Term term = new Term();
        PlaceAmenities placeAmenities = new PlaceAmenities();
        Owner owner = new Owner();
        Place place = new Place();
        Place place1 = new Place();

        List<Reservation> reservations = new ArrayList<>();
        List<Rate> rates = new ArrayList<>();
        List<Term> terms = new ArrayList<>();

        reservations.add(reservation);
        rates.add(rate);
        terms.add(term);

        place.setOwner(owner);
        place.setCity("Gdynia");
        place.setStreet("Swietojanska");
        place.setPostCode("00-000");
        place.setPlaceType(PlaceType.footBallPitch);
        place.setLocalizationCoordinates("54+516703,18+541222");
        place.setCost(new BigDecimal(250));
        place.setScore(5);
        place.setReservations(reservations);
        place.setTermList(terms);
        place.setRateList(rates);
        place.setPlaceAmenities(placeAmenities);
        reservationRepository.save(reservation);
        rateRepository.save(rate);
        termRepository.save(term);
        ownerRepository.save(owner);
        placeAmenitiesRepository.save(placeAmenities);

        placeRepository.save(place);
        placeRepository.save(place1);

    }

    @Test
    public void getPlaces() throws Exception {
        this.mockMvc.perform(get("/places")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void getPlace() throws Exception {
        this.mockMvc.perform(get("/places/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void addPlace() throws Exception {
    }

    @Test
    public void deletePlace() throws Exception {
        String json = json(new Place());
        this.mockMvc.perform(post("/places")
                .contentType(contentType)
                .content(json)).andExpect(status().isCreated());
    }


    @Test
    public void findByCity() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByCityAndStreet() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia/street/Swietojanska")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByPostCode() throws Exception {
        this.mockMvc.perform(get("/places/byPostCode/00-000")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findCityAndStreetAndPostCode() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia/street/Swietojanska/postCode/00-000")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByPlaceType() throws Exception {
        this.mockMvc.perform(get("/places/byPlaceType/footBallPitch")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByCoordinates() throws Exception {
        this.mockMvc.perform(get("/places/byCoordinates/54+516703,18+541222")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findCityAndStreetAndPostCodeAndPlaceType() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia/street/Swietojanska/postCode/00-000/placeType/footBallPitch")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByCityAndPlaceType() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia/placeType/footBallPitch")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByCityAndStreetAndPlaceType() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia/street/Swietojanska/placeType/footBallPitch")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByPostCodeAndPlaceType() throws Exception {
        this.mockMvc.perform(get("/places/byPostCode/00-000/placeType/footBallPitch")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByCityAndCost() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia/cost/250")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByCityAndCostAndPlaceType() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia/placeType/footBallPitch")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByCityAndPlaceTypeAndScore() throws Exception {
        this.mockMvc.perform(get("/places/byCity/Gdynia/placeType/footBallPitch/score/5")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}