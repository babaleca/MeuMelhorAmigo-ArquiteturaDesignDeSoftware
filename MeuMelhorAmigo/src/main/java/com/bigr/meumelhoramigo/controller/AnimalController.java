package com.bigr.meumelhoramigo.controller;

import com.bigr.meumelhoramigo.modelo.Animal;
import com.bigr.meumelhoramigo.modelo.AnimalEncontrado;
import com.bigr.meumelhoramigo.modelo.AnimalPerdido;
import com.bigr.meumelhoramigo.modelo.StatusAnimal;
import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.service.AnimalService;
import com.bigr.meumelhoramigo.service.FileStorageService;
import com.bigr.meumelhoramigo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UsuarioService usuarioService;

    /** Listagem publica. Filtros (busca, status, especie) sao client-side. */
    @GetMapping("/animais")
    public String listar(Model model) {
        List<Animal> animais = animalService.listarTodos();
        model.addAttribute("animais", animais);
        model.addAttribute("total", animais.size());
        model.addAttribute("countDisp",
                animais.stream().filter(a -> a.getStatus() == StatusAnimal.DISPONIVEL).count());
        model.addAttribute("countPerd",
                animais.stream().filter(a -> a.getStatus() == StatusAnimal.PERDIDO).count());
        model.addAttribute("countEnc",
                animais.stream().filter(a -> a.getStatus() == StatusAnimal.ENCONTRADO).count());
        model.addAttribute("countAdot",
                animais.stream().filter(a -> a.getStatus() == StatusAnimal.ADOTADO).count());
        return "animais-lista";
    }

    /** Detalhe de um animal especifico. */
    @GetMapping("/animais/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        Animal animal = animalService.buscarPorId(id).orElse(null);
        if (animal == null) {
            return "redirect:/animais";
        }
        model.addAttribute("animal", animal);
        return "detalhe-animal";
    }

    /** Formulario de cadastro. */
    @GetMapping("/animais/cadastrar")
    public String cadastrarForm(Model model) {
        return "cadastrar-animal";
    }

    /** Recebe o cadastro (encontrado ou perdido), com upload de foto. */
    @PostMapping("/animais/cadastrar")
    public String cadastrar(@RequestParam String tipo,
                            @RequestParam String especie,
                            @RequestParam(required = false) String raca,
                            @RequestParam(required = false) String cor,
                            @RequestParam(required = false) String tamanho,
                            @RequestParam(required = false) String sexo,
                            @RequestParam(required = false) String local,
                            @RequestParam(required = false) String descricao,
                            // campos de AnimalEncontrado
                            @RequestParam(required = false) String condicaoSaude,
                            @RequestParam(required = false) String localEncontro,
                            @RequestParam(required = false)
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEncontro,
                            // campos de AnimalPerdido
                            @RequestParam(required = false) String nome,
                            @RequestParam(required = false) String caracteristicas,
                            @RequestParam(required = false) String recompensa,
                            @RequestParam(required = false)
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDesaparecimento,
                            @RequestParam(required = false) MultipartFile foto,
                            Principal principal) {

        Animal animal;
        if ("perdido".equalsIgnoreCase(tipo)) {
            AnimalPerdido perdido = new AnimalPerdido();
            perdido.setNome(nome);
            perdido.setCaracteristicas(caracteristicas);
            perdido.setRecompensa(recompensa);
            perdido.setDataDesaparecimento(dataDesaparecimento);
            perdido.setStatus(StatusAnimal.PERDIDO);
            animal = perdido;
        } else {
            AnimalEncontrado encontrado = new AnimalEncontrado();
            encontrado.setCondicaoSaude(condicaoSaude);
            encontrado.setLocalEncontro(localEncontro);
            encontrado.setDataEncontro(dataEncontro);
            encontrado.setStatus(StatusAnimal.ENCONTRADO);
            animal = encontrado;
        }

        animal.setEspecie(especie);
        animal.setRaca(raca);
        animal.setCor(cor);
        animal.setTamanho(tamanho);
        animal.setSexo(sexo);
        animal.setLocal(local);
        animal.setDescricao(descricao);

        // Upload da foto: salva na pasta /uploads e guarda o caminho
        String caminhoFoto = fileStorageService.salvar(foto);
        animal.setFoto(caminhoFoto);

        // Responsavel = usuario autenticado
        if (principal != null) {
            Usuario responsavel = usuarioService.buscarPorEmail(principal.getName()).orElse(null);
            animal.setResponsavel(responsavel);
        }

        Animal salvo = animalService.salvar(animal);
        return "redirect:/animais/" + salvo.getId();
    }
}
