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
import pl.npp.nopodajpodajserver.model.reservation.Reservation;
import pl.npp.nopodajpodajserver.model.reservation.Term;
import pl.npp.nopodajpodajserver.model.user.Customer;
import pl.npp.nopodajpodajserver.repository.ICustomerRepository;
import pl.npp.nopodajpodajserver.repository.IPlaceRepository;
import pl.npp.nopodajpodajserver.repository.IReservationRepository;
import pl.npp.nopodajpodajserver.repository.ITermRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class ReservationRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private IReservationRepository reservationRepository;
    @Autowired
    private IPlaceRepository placeRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private ITermRepository termRepository;

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
        Place place = new Place();
        Term term = new Term();
        Reservation reservation = new Reservation();
        Reservation reservation2 = new Reservation();
        List<Term> terms = new ArrayList<>();

        place.setTermList(terms);
        reservation.setPlace(place);
        reservation.setCustomer(customer);
        reservation.setTerm(term);

        customerRepository.save(customer);
        placeRepository.save(place);
        termRepository.save(term);
        reservationRepository.save(reservation);
        reservationRepository.save(reservation2);

    }

    @Test
    public void getReservations() throws Exception {
        this.mockMvc.perform(get("/reservations")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void getReservation() throws Exception {
        this.mockMvc.perform(get("/reservations/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void addReservation() throws Exception {
        String json = json(new Reservation());
        this.mockMvc.perform(post("/reservations")
                .contentType(contentType)
                .content(json)).andExpect(status().isCreated());
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}