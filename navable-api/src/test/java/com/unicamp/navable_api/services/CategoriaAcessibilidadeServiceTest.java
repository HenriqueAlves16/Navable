package com.unicamp.navable_api.services;

import com.unicamp.navable_api.api.model.CategoriaAcessibilidadeDTO;
import com.unicamp.navable_api.persistance.entities.CategoriaAcessibilidade;
import com.unicamp.navable_api.persistance.repositories.CategoriaAcessibilidadeRepository;
import com.unicamp.navable_api.services.impl.CategoriaAcessibilidadeService;
import com.unicamp.navable_api.services.mappers.CategoriaAcessibilidadeMapper;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoriaAcessibilidadeServiceTest {

    @Mock
    private CategoriaAcessibilidadeRepository categoriaRepository;

    @InjectMocks
    private CategoriaAcessibilidadeService categoriaService;

    private final CategoriaAcessibilidadeMapper categoriaMapper = CategoriaAcessibilidadeMapper.INSTANCE;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCategorias() {
        CategoriaAcessibilidade categoria1 = new CategoriaAcessibilidade();
        categoria1.setCategoriaAcId(1);
        categoria1.setNome("Rampa");
        categoria1.setGrupo("Mobilidade");

        CategoriaAcessibilidade categoria2 = new CategoriaAcessibilidade();
        categoria2.setCategoriaAcId(2);
        categoria2.setNome("Elevador");
        categoria2.setGrupo("Mobilidade");

        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria1, categoria2));

        var resultado = categoriaService.getAllCategorias();

        assertEquals(2, resultado.size());
        assertEquals("Rampa", resultado.get(0).getNome());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    public void testGetCategoriaById() {
        CategoriaAcessibilidade categoria = new CategoriaAcessibilidade();
        categoria.setCategoriaAcId(1);
        categoria.setNome("Banheiro acessível");
        categoria.setGrupo("Infraestrutura");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        CategoriaAcessibilidadeDTO resultado = categoriaService.getCategoriaById(1);

        assertNotNull(resultado);
        assertEquals("Banheiro acessível", resultado.getNome());
        verify(categoriaRepository, times(1)).findById(1);
    }

    @Test
    public void testGetCategoriaById_NotFound() {
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.getCategoriaById(99);
        });

        assertEquals("Categoria not found with id 99", exception.getMessage());
        verify(categoriaRepository, times(1)).findById(99);
    }
}
