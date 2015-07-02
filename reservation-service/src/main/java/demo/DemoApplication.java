package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@EnableDiscoveryClient
@SpringBootApplication // @IWantToGoHomeEarly
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    HealthIndicator ccc() {
        return () -> Health.status("I <3 Chicago!").build();
    }

    @Bean
    CommandLineRunner runner(ReservationRepository rr) {
        return args -> {

            Arrays.asList("Heath,Jeff,Josh,Rod,Juergen,Charlie".split(","))
                    .forEach(n -> rr.save(new Reservation(n)));

            rr.findAll().forEach(System.out::println);

        };
    }
}

@RestController
class ReservationRestController {

    @Autowired
    private ReservationRepository reservationRepository;

    @RequestMapping(value = "/reservations",
            produces = MediaType.APPLICATION_JSON_VALUE ,
            method = RequestMethod.GET)
    Collection<Reservation> reservations() {
        return this.reservationRepository.findAll();
    }

}

interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Collection<Reservation> findByReservationName(String rn);
}


@Entity
class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String reservationName;

    public Reservation() { // why JPA why???
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationName='" + reservationName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }
}