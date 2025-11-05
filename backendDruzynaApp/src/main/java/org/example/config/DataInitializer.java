package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.model.KategoriaWiekowa;
import org.example.repository.KategoriaWiekowaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final KategoriaWiekowaRepository kategoriaRepo;

    @Override
    public void run(String... args) {
        if (kategoriaRepo.count() == 0) {
            List<KategoriaWiekowa> kategorie = Arrays.asList(
                    new KategoriaWiekowa(null, "Żak (2019-2020)", 2019, 2020,
                            "Kategoria dla najmłodszych", null),
                    new KategoriaWiekowa(null, "Orlak (2017-2018)", 2017, 2018,
                            "Kategoria dla dzieci 6-7 lat", null),
                    new KategoriaWiekowa(null, "Junior Młodszy (2015-2016)", 2015, 2016,
                            "Kategoria dla dzieci 8-9 lat", null),
                    new KategoriaWiekowa(null, "Junior (2013-2014)", 2013, 2014,
                            "Kategoria dla dzieci 10-11 lat", null),
                    new KategoriaWiekowa(null, "Młodzik (2011-2012)", 2011, 2012,
                            "Kategoria dla dzieci 12-13 lat", null),
                    new KategoriaWiekowa(null, "Trampkarz (2009-2010)", 2009, 2010,
                            "Kategoria dla młodzieży 14-15 lat", null)
            );

            kategoriaRepo.saveAll(kategorie);
            System.out.println("✅ Zainicjalizowano kategorie wiekowe");
        }
    }
}
