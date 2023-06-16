package br.com.jsc.screenmatch.domain.filme;

import jakarta.persistence.*;

@Entity
@Table(name="filmes")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer duracaoEmMinutos;
    private Integer anoLancamento;
    private String genero;

    public Filme() {}
    public Filme (DadosCadastroFilme dados){
        this.nome = dados.nome();
        this.duracaoEmMinutos = dados.duracao();
        this.anoLancamento = dados.ano();
        this.genero = dados.genero();
    }
    @Override
    public String toString() {
        return "Filme{" +
                "nome='" + nome + '\'' +
                ", duracaoEmMinutos=" + duracaoEmMinutos +
                ", anoLancamento=" + anoLancamento +
                ", genero='" + genero + '\'' +
                '}';
    }
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public Integer getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }
    public Integer getAnoLancamento() {
        return anoLancamento;
    }
    public String getGenero() {
        return genero;
    }

    public void atualizaDados(DadosAlteracaoFilme dados) {
        this.nome = dados.nome();
        this.duracaoEmMinutos = dados.duracao();
        this.anoLancamento = dados.ano();
        this.genero = dados.genero();
    }
    public void buscaFilme(DadosBuscaFilmeOmdb dados){
        this.nome = dados.title();
        this.anoLancamento = Integer.valueOf(dados.year().substring(0,4));
        this.duracaoEmMinutos = Integer.valueOf(dados.runtime().substring(0,3).replace(" ",""));
        this.genero = dados.genre();
    }

}
