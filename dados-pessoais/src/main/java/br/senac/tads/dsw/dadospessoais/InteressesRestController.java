package br.senac.tads.dsw.dadospessoais;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senac.tads.dsw.dadospessoais.persistence.entities.InteresseEntity;
import br.senac.tads.dsw.dadospessoais.persistence.repository.InteresseRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/interesses")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-jwt")
public class InteressesRestController {

    @Autowired
    private InteresseRepository interesseRepository;

    @GetMapping
    public List<String> findAll() {
        // List<InteresseEntity> entities = interesseRepository.findAll();
        // List<String> resultado = new ArrayList<>();
        // for (InteresseEntity entity : entities) {
        //     resultado.add(entity.getNome());
        // }
        // return resultado;
        return interesseRepository.findAll().stream()
            .map(entity -> entity.getNome()).toList();
    }

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<?> criarInteresses() {
        InteresseEntity i1 = new InteresseEntity(1, "Java");
        interesseRepository.save(i1);
        interesseRepository.save(new InteresseEntity(2, "Spring Boot"));
        interesseRepository.save(new InteresseEntity(3, "HTML"));
        interesseRepository.save(new InteresseEntity(4, "CSS"));
        interesseRepository.save(new InteresseEntity(5, "Javascript"));
        interesseRepository.save(new InteresseEntity(6, "SQL"));
        return ResponseEntity.ok().build();
    }

}
