package br.com.furiabot.quiz;

import org.json.JSONObject;

public class QuizSession {
    private JSONObject perguntaAtual;
    private int contadorPerguntas;
    private int contadorErros; // Novo atributo para contar erros

    public QuizSession(JSONObject perguntaAtual) {
        this.perguntaAtual = perguntaAtual;
        this.contadorPerguntas = 1; // Inicializa perguntas respondidas
        this.contadorErros = 0; // Inicializa erros
    }

    public JSONObject getPerguntaAtual() {
        return perguntaAtual;
    }

    public void novaPergunta() {
        this.perguntaAtual = Quiz.obterPerguntaAleatoria();
        contadorPerguntas++; // Incrementa o número de perguntas respondidas
    }

    public void incrementarErro() {
        contadorErros++; // Adiciona erro
    }

    public boolean quizFinalizado() {
        return contadorPerguntas >= 5 || contadorErros >= 2; // Se 5 perguntas ou 2 erros, encerra
    }

    public int getContadorPerguntas() {
        return contadorPerguntas;
    }

    public int getContadorErros() {
        return contadorErros;
    }
}
