package com.unicamp.navable_api.persistance;

import com.unicamp.navable_api.persistance.entities.*;
import com.unicamp.navable_api.persistance.repositories.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class EstabelecimentoRepositoryTest {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @BeforeEach
    public void setUp() {
        Estabelecimento est1 = new Estabelecimento(1, 1, "Establishment 1", -23.5505, -46.6333, "image1.png", "Address 1");
        Estabelecimento est2 = new Estabelecimento(2, 1, "Establishment 2", -23.5510, -46.6340, "image2.png", "Address 2");
        Estabelecimento est3 = new Estabelecimento(3, 2, "Establishment 3", -23.5520, -46.6350, "image3.png", "Address 3");

        estabelecimentoRepository.saveAll(Arrays.asList(est1, est2, est3));

        Avaliacao avaliacao1 = new Avaliacao(1, 1, 1, "Great service", 0, LocalDate.now());
        Avaliacao avaliacao2 = new Avaliacao(2, 1, 1, "Average experience", 2, LocalDate.now());
        Avaliacao avaliacao3 = new Avaliacao(3, 2, 1, "Not good", 1, LocalDate.now());

        avaliacaoRepository.saveAll(Arrays.asList(avaliacao1, avaliacao2, avaliacao3));
    }

    @Test
    public void testFindNearby() {
        double latitude = -23.5505;
        double longitude = -46.6333;
        List<Object[]> result = estabelecimentoRepository.findNearby(latitude, longitude);
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    public void testFindByNome() {
        String nome = "Establishment 1";
        List<Estabelecimento> result = estabelecimentoRepository.findByNome(nome);
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getNome()).isEqualTo("Establishment 1");
    }

    @Test
    public void testFindAverageNotaByEstabelecimentoId() {
        Integer estId = 1;
        Double averageNota = estabelecimentoRepository.findAverageNotaByEstabelecimentoId(estId);
        assertThat(averageNota).isNotNull();
        assertThat(averageNota).isEqualTo(1); // Assuming the average of the ratings for establishment 1
    }
}
