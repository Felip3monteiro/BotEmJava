package br.com.furiabot.quiz;

import org.json.JSONObject;

/**
 * A classe {@code QuizSession} representa uma sessão de um quiz interativo,
 * armazenando as informações relacionadas ao progresso do usuário, como a pergunta atual,
 * número de perguntas respondidas e o número de erros cometidos.
 */
public class QuizSession {

    private JSONObject perguntaAtual;
    private int contadorPerguntas;
    private int contadorErros; // Contador para erros cometidos durante o quiz

    /**
     * Construtor para inicializar uma nova sessão de quiz.
     * 
     * @param perguntaAtual Um objeto {@link JSONObject} que representa a pergunta atual do quiz.
     */
    public QuizSession(JSONObject perguntaAtual) {
        this.perguntaAtual = perguntaAtual;
        this.contadorPerguntas = 1; // Inicializa com a primeira pergunta já contada
        this.contadorErros = 0; // Inicializa sem erros
    }

    /**
     * Obtém a pergunta atual da sessão.
     * 
     * @return Um objeto {@link JSONObject} contendo a pergunta atual.
     */
    public JSONObject getPerguntaAtual() {
        return perguntaAtual;
    }

    /**
     * Gera uma nova pergunta aleatória e incrementa o número de perguntas respondidas.
     */
    public void novaPergunta() {
        this.perguntaAtual = Quiz.obterPerguntaAleatoria();
        contadorPerguntas++; // Incrementa o contador de perguntas respondidas
    }

    /**
     * Incrementa o contador de erros cometidos pelo usuário.
     */
    public void incrementarErro() {
        contadorErros++;
    }

    /**
     * Verifica se o quiz foi finalizado. O quiz é considerado finalizado 
     * quando o usuário responde a 5 perguntas ou comete 2 erros.
     * 
     * @return {@code true} se o quiz foi finalizado; {@code false} caso contrário.
     */
    public boolean quizFinalizado() {
        return contadorPerguntas >= 5 || contadorErros >= 2;
    }

    /**
     * Obtém o número total de perguntas respondidas pelo usuário na sessão.
     * 
     * @return O número de perguntas respondidas.
     */
    public int getContadorPerguntas() {
        return contadorPerguntas;
    }

    /**
     * Obtém o número total de erros cometidos pelo usuário na sessão.
     * 
     * @return O número de erros cometidos.
     */
    public int getContadorErros() {
        return contadorErros;
    }
}