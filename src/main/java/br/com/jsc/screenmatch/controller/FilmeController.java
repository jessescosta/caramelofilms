package br.com.jsc.screenmatch.controller;

import br.com.jsc.screenmatch.domain.filme.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Controller                         // marcar a classe como um controlador no Spring
@RequestMapping("/filmes")          // usada para definir a URL base para todas as requisições em um controlador
public class FilmeController {

    private List<Filme> listFilmes = new ArrayList<>();
    @Autowired
    private FilmeRepository repository;


    @GetMapping("/formulario")
    public  String carregaPaginaFormulario(Long id ,Model model){
        if (id != null){
            var filme = repository.getReferenceById(id);
            model.addAttribute("filme",filme);
        }
        return "filmes/formulario";                                                                 //pasta/paginaHTML
    }
    @GetMapping
    public  String carregaPaginaListagem(Model model){
        //model.addAttribute("lista",repository.findAll());
        List<Filme> filmes = repository.findAllOrderByNome();
        model.addAttribute("lista",filmes);

        return "filmes/listagem";
    }
    @GetMapping("/pesquisa")
    public  String carregaPaginaBuscaFilme(Model model){
        //System.out.println(model.addAttribute("busca",listFilmes));
        return "filmes/pesquisa";
    }
    @PostMapping
    @Transactional
    public String cadastrarFilme(DadosCadastroFilme dados){
        var filme = new Filme(dados);
        repository.save(filme);
        return "redirect:/filmes";
    }
    @DeleteMapping
    @Transactional
    public String removeFilme(Long id){
        repository.deleteById(id);
        return "redirect:/filmes";
    }

    @PutMapping
    @Transactional
    public String alteraFilme(DadosAlteracaoFilme dados){
        var filme = repository.getReferenceById(dados.id());
        filme.atualizaDados(dados);
        return "redirect:/filmes";
    }
    @PostMapping("/pesquisa")
    @Transactional
    public String cadastraFilmePesquisado(DadosBuscaFilmeOmdb dados){
        System.out.println(dados);
        String retorno;
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        String endereco = "https://www.omdbapi.com/?t=" + dados.title().replace(" ", "+") + "&apikey=f2071ac3";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            dados = gson.fromJson(json, DadosBuscaFilmeOmdb.class);
            Filme filme = new Filme();
            filme.buscaFilme(dados);
            //System.out.println(endereco + "  "+json);
            repository.save(filme);
            listFilmes.add(filme);
            System.out.println(listFilmes);
            retorno = "redirect:/filmes";

        } catch (Exception e) {
            System.out.println("[Erro] --> " + e.getMessage());

            retorno = "/filmes/pesquisa";
        }

        return retorno;
    }

}
